package com.estoque.dao;

import com.estoque.model.Produto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private static final List<Produto> BANCO_MEMORIA = new ArrayList<>();
    private static int geradorId = 1;

    static {
        BANCO_MEMORIA.add(new Produto(geradorId++, "Arroz Integral 1kg", "Tipo 1 grão longo", "Alimentos", new BigDecimal("6.50"), 80, "Ativo"));
        BANCO_MEMORIA.add(new Produto(geradorId++, "Detergente Neutro 500ml", "Biodegradável", "Limpeza", new BigDecimal("2.30"), 150, "Ativo"));
        BANCO_MEMORIA.add(new Produto(geradorId++, "Café Torrado 500g", "Extra forte", "Alimentos", new BigDecimal("16.90"), 40, "Ativo"));
    }

    public void inserir(Produto p) {
        p.setCodigo(geradorId++);
        BANCO_MEMORIA.add(p);
    }

    public void alterar(Produto p) {
        for (int i = 0; i < BANCO_MEMORIA.size(); i++) {
            if (BANCO_MEMORIA.get(i).getCodigo().equals(p.getCodigo())) {
                BANCO_MEMORIA.set(i, p);
                return;
            }
        }
    }

    public void excluir(int codigo) {
        BANCO_MEMORIA.removeIf(p -> p.getCodigo() == codigo);
    }

    public List<Produto> listarPorFiltro(String tipoFiltro, String valor) {
        if (valor == null || valor.trim().isEmpty() || "Todos".equalsIgnoreCase(tipoFiltro)) {
            return new ArrayList<>(BANCO_MEMORIA);
        }

        List<Produto> filtrados = new ArrayList<>();
        String termo = valor.trim().toLowerCase();

        for (Produto p : BANCO_MEMORIA) {
            if ("Código".equalsIgnoreCase(tipoFiltro) && String.valueOf(p.getCodigo()).equals(termo)) {
                filtrados.add(p);
            } else if ("Nome".equalsIgnoreCase(tipoFiltro) && p.getNome().toLowerCase().contains(termo)) {
                filtrados.add(p);
            } else if ("Categoria".equalsIgnoreCase(tipoFiltro) && p.getCategoria().toLowerCase().contains(termo)) {
                filtrados.add(p);
            }
        }
        return filtrados;
    }
}