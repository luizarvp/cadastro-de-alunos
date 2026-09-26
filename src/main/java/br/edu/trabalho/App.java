package br.edu.trabalho;

import br.edu.trabalho.database.Database;
import br.edu.trabalho.view.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        Database.initialize();

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // A aplicação continua com o visual padrão caso o tema do sistema falhe.
            }
            new MainFrame().setVisible(true);
        });
    }
}
