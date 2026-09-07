package com.estoque.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgres {
    private static final String URL = "jdbc:postgresql://localhost:5432/mercado_db";
    private static final String USER = "postgres";
    private static final String PASS = "sua_senha"; // Altere para a sua senha

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}