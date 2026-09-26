package br.edu.trabalho.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Database {
    private static final String URL = "jdbc:sqlite:cadastro-alunos.db";

    private Database() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initialize() {
        String sql = """
                CREATE TABLE IF NOT EXISTS alunos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    matricula TEXT NOT NULL UNIQUE,
                    curso TEXT NOT NULL,
                    email TEXT NOT NULL
                )
                """;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException exception) {
            throw new IllegalStateException("Não foi possível inicializar o banco de dados.", exception);
        }
    }
}
