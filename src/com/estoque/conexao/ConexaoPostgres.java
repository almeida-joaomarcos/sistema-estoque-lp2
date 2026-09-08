package com.estoque.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgres {
   
    private static final String URL = "jdbc:postgresql://localhost:5432/db_estoque";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection getConexao() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao PostgreSQL: " + e.getMessage(), e);
        }
    }
}
