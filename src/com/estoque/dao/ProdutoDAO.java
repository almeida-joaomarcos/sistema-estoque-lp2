package com.estoque.dao;

import com.estoque.model.Produto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Padrão Data Access Object (DAO).
 * Centraliza as operações de manipulação de dados (CRUD) isolando a lógica da interface gráfica.
 */
public class ProdutoDAO {
    // Coleção estática compartilhada em memória para persistência temporária antes do PostgreSQL
    private static final List<Produto> BANCO_MEMORIA = new ArrayList<>();
    // Gerador sequencial de IDs simulando a sequence serial do banco de dados relacional
    private static int geradorId = 1;

    // Bloco estático de inicialização: popula a base de testes assim que a classe é carregada na JVM
    static {
        BANCO_MEMORIA.add(new Produto(geradorId++, "Arroz Integral 1kg", "Tipo 1 grão longo", "Alimentos", new BigDecimal("6.50"), 80, "Ativo"));
        BANCO_MEMORIA.add(new Produto(geradorId++, "Detergente Neutro 500ml", "Biodegradável", "Limpeza", new BigDecimal("2.30"), 150, "Ativo"));
        BANCO_MEMORIA.add(new Produto(geradorId++, "Café Torrado 500g", "Extra forte", "Alimentos", new BigDecimal("16.90"), 40, "Ativo"));
    }

    // Operação Create (Inserir registro)
    public void inserir(Produto p) {
        // Atribui o identificador único autoincrementado e adiciona à coleção
        p.setCodigo(geradorId++);
        BANCO_MEMORIA.add(p);
    }

    // Operação Update (Alterar registro)
    public void alterar(Produto p) {
        // Varre a lista em busca do registro com o mesmo código identificador
        for (int i = 0; i < BANCO_MEMORIA.size(); i++) {
            if (BANCO_MEMORIA.get(i).getCodigo().equals(p.getCodigo())) {
                // Substitui o objeto antigo pelo objeto atualizado na mesma posição
                BANCO_MEMORIA.set(i, p);
                return;
            }
        }
    }

    // Operação Delete (Excluir registro)
    public void excluir(int codigo) {
        // Remove da lista o elemento correspondente ao ID informado via predicado
        BANCO_MEMORIA.removeIf(p -> p.getCodigo() == codigo);
    }

    // Operação Read (Consultar com filtros)
    public List<Produto> listarPorFiltro(String tipoFiltro, String valor) {
        // Se o valor de busca for nulo/vazio ou a opção "Todos" estiver marcada, retorna a lista completa
        if (valor == null || valor.trim().isEmpty() || "Todos".equalsIgnoreCase(tipoFiltro)) {
            // Retorna uma nova instância para proteger a lista original contra efeitos colaterais
            return new ArrayList<>(BANCO_MEMORIA);
        }

        List<Produto> filtrados = new ArrayList<>();
        String termo = valor.trim().toLowerCase();

        // Itera sobre a base aplicando a filtragem conforme a coluna selecionada
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