package com.estoque.dao;

import com.estoque.conexao.ConexaoPostgres;
import com.estoque.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void inserir(Produto p) {
        String sql = "INSERT INTO produto (nome, descricao, categoria, preco, quantidade, status) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoPostgres.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getDescricao());
            stmt.setString(3, p.getCategoria());
            stmt.setBigDecimal(4, p.getPreco());
            stmt.setInt(5, p.getQuantidade());
            stmt.setString(6, p.getStatus());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto: " + e.getMessage(), e);
        }
    }

    public void alterar(Produto p) {
        String sql = "UPDATE produto SET nome=?, descricao=?, categoria=?, preco=?, quantidade=?, status=? WHERE codigo=?";
        
        try (Connection conn = ConexaoPostgres.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getDescricao());
            stmt.setString(3, p.getCategoria());
            stmt.setBigDecimal(4, p.getPreco());
            stmt.setInt(5, p.getQuantidade());
            stmt.setString(6, p.getStatus());
            stmt.setInt(7, p.getCodigo());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    public void excluir(int codigo) {
        String sql = "DELETE FROM produto WHERE codigo=?";
        
        try (Connection conn = ConexaoPostgres.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto: " + e.getMessage(), e);
        }
    }

    public List<Produto> listarPorFiltro(String tipoFiltro, String valor) {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        boolean temFiltro = valor != null && !valor.trim().isEmpty() && !"Todos".equalsIgnoreCase(tipoFiltro);

        if (temFiltro) {
            if ("Código".equalsIgnoreCase(tipoFiltro)) {
                sql += " WHERE codigo = ?";
            } else if ("Nome".equalsIgnoreCase(tipoFiltro)) {
                sql += " WHERE LOWER(nome) LIKE ?";
            } else if ("Categoria".equalsIgnoreCase(tipoFiltro)) {
                sql += " WHERE LOWER(categoria) LIKE ?";
            }
        }

        try (Connection conn = ConexaoPostgres.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (temFiltro) {
                if ("Código".equalsIgnoreCase(tipoFiltro)) {
                    stmt.setInt(1, Integer.parseInt(valor.trim()));
                } else {
                    stmt.setString(1, "%" + valor.trim().toLowerCase() + "%");
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Produto p = new Produto(
                        rs.getInt("codigo"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("categoria"),
                        rs.getBigDecimal("preco"),
                        rs.getInt("quantidade"),
                        rs.getString("status")
                    );
                    produtos.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produtos: " + e.getMessage(), e);
        }
        return produtos;
    }
}
