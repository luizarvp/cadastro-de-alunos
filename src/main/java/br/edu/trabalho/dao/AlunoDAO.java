package br.edu.trabalho.dao;

import br.edu.trabalho.database.Database;
import br.edu.trabalho.model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    public List<Aluno> listar(String filtro) throws SQLException {
        String sql = """
                SELECT id, nome, matricula, curso, email
                FROM alunos
                WHERE nome LIKE ? OR matricula LIKE ? OR curso LIKE ?
                ORDER BY nome
                """;
        String busca = "%" + filtro.trim() + "%";
        List<Aluno> alunos = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, busca);
            statement.setString(2, busca);
            statement.setString(3, busca);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    alunos.add(fromResult(result));
                }
            }
        }
        return alunos;
    }

    public void salvar(Aluno aluno) throws SQLException {
        String sql = aluno.getId() == null
                ? "INSERT INTO alunos (nome, matricula, curso, email) VALUES (?, ?, ?, ?)"
                : "UPDATE alunos SET nome = ?, matricula = ?, curso = ?, email = ? WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, aluno.getNome());
            statement.setString(2, aluno.getMatricula());
            statement.setString(3, aluno.getCurso());
            statement.setString(4, aluno.getEmail());
            if (aluno.getId() != null) {
                statement.setInt(5, aluno.getId());
            }
            statement.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM alunos WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private Aluno fromResult(ResultSet result) throws SQLException {
        return new Aluno(
                result.getInt("id"),
                result.getString("nome"),
                result.getString("matricula"),
                result.getString("curso"),
                result.getString("email"));
    }
}
