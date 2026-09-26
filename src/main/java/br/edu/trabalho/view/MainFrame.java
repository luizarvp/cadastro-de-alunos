package br.edu.trabalho.view;

import br.edu.trabalho.dao.AlunoDAO;
import br.edu.trabalho.model.Aluno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class MainFrame extends JFrame {
    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final JTextField nomeField = new JTextField();
    private final JTextField matriculaField = new JTextField();
    private final JTextField cursoField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField buscaField = new JTextField();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Matrícula", "Curso", "E-mail"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(tableModel);
    private Integer alunoSelecionadoId;

    public MainFrame() {
        setTitle("Cadastro de Alunos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 560);
        setLocationRelativeTo(null);
        criarInterface();
        carregarTabela();
    }

    private void criarInterface() {
        JPanel formulario = new JPanel(new GridLayout(1, 4, 8, 4));
        formulario.setBorder(BorderFactory.createTitledBorder("Dados do aluno"));
        formulario.add(labelComCampo("Nome", nomeField));
        formulario.add(labelComCampo("Matrícula", matriculaField));
        formulario.add(labelComCampo("Curso", cursoField));
        formulario.add(labelComCampo("E-mail", emailField));

        JButton salvarButton = new JButton("Salvar");
        JButton limparButton = new JButton("Limpar");
        JButton excluirButton = new JButton("Excluir selecionado");
        salvarButton.addActionListener(event -> salvar());
        limparButton.addActionListener(event -> limparFormulario());
        excluirButton.addActionListener(event -> excluirSelecionado());

        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        acoes.add(salvarButton);
        acoes.add(limparButton);
        acoes.add(excluirButton);

        JPanel topo = new JPanel(new BorderLayout(8, 8));
        topo.add(formulario, BorderLayout.CENTER);
        topo.add(acoes, BorderLayout.SOUTH);

        JPanel pesquisa = new JPanel(new BorderLayout(8, 8));
        pesquisa.setBorder(BorderFactory.createTitledBorder("Pesquisar"));
        pesquisa.add(buscaField, BorderLayout.CENTER);
        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(event -> carregarTabela());
        pesquisa.add(buscarButton, BorderLayout.EAST);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(event -> preencherFormulario());

        JPanel conteudo = new JPanel(new BorderLayout(8, 8));
        conteudo.add(pesquisa, BorderLayout.NORTH);
        conteudo.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel principal = new JPanel(new BorderLayout(8, 8));
        principal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        principal.add(topo, BorderLayout.NORTH);
        principal.add(conteudo, BorderLayout.CENTER);
        add(principal);
    }

    private JPanel labelComCampo(String texto, JTextField campo) {
        JPanel painel = new JPanel(new BorderLayout(4, 2));
        painel.add(new JLabel(texto), BorderLayout.NORTH);
        painel.add(campo, BorderLayout.CENTER);
        return painel;
    }

    private void carregarTabela() {
        try {
            tableModel.setRowCount(0);
            for (Aluno aluno : alunoDAO.listar(buscaField.getText())) {
                tableModel.addRow(new Object[]{aluno.getId(), aluno.getNome(), aluno.getMatricula(),
                        aluno.getCurso(), aluno.getEmail()});
            }
        } catch (SQLException exception) {
            mostrarErro(exception);
        }
    }

    private void salvar() {
        if (nomeField.getText().isBlank() || matriculaField.getText().isBlank()
                || cursoField.getText().isBlank() || emailField.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            alunoDAO.salvar(new Aluno(alunoSelecionadoId, nomeField.getText().trim(),
                    matriculaField.getText().trim(), cursoField.getText().trim(), emailField.getText().trim()));
            JOptionPane.showMessageDialog(this, "Aluno salvo com sucesso.");
            limparFormulario();
            carregarTabela();
        } catch (SQLException exception) {
            mostrarErro(exception);
        }
    }

    private void preencherFormulario() {
        int linha = table.getSelectedRow();
        if (linha < 0) return;
        alunoSelecionadoId = (Integer) tableModel.getValueAt(linha, 0);
        nomeField.setText((String) tableModel.getValueAt(linha, 1));
        matriculaField.setText((String) tableModel.getValueAt(linha, 2));
        cursoField.setText((String) tableModel.getValueAt(linha, 3));
        emailField.setText((String) tableModel.getValueAt(linha, 4));
    }

    private void excluirSelecionado() {
        if (alunoSelecionadoId == null) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int resposta = JOptionPane.showConfirmDialog(this, "Deseja excluir o aluno selecionado?",
                "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            try {
                alunoDAO.excluir(alunoSelecionadoId);
                limparFormulario();
                carregarTabela();
            } catch (SQLException exception) {
                mostrarErro(exception);
            }
        }
    }

    private void limparFormulario() {
        alunoSelecionadoId = null;
        nomeField.setText("");
        matriculaField.setText("");
        cursoField.setText("");
        emailField.setText("");
        table.clearSelection();
    }

    private void mostrarErro(Exception exception) {
        JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
