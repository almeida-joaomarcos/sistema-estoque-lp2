package com.estoque.model;

import java.math.BigDecimal;

/**
 * Classe de entidade que representa a tabela de produtos no sistema.
 * Aplica os conceitos de POO: encapsulamento, construtores sobrecarregados e métodos de acesso.
 */
public class Produto {
    
    // Atributos privados para garantir o encapsulamento dos dados
    private Integer codigo;
    private String nome;
    private String descricao;
    private String categoria;
    // BigDecimal previne perdas de precisão em operações e valores monetários
    private BigDecimal preco;
    private Integer quantidadeEstoque;
    private String situacao;

    // Construtor padrão (sem parâmetros) necessário para instanciação genérica
    public Produto() {}

    // Construtor com todos os parâmetros para inicialização rápida ao consultar o banco
    public Produto(Integer codigo, String nome, String descricao, String categoria, BigDecimal preco, Integer quantidadeEstoque, String situacao) {
        // O uso do operador 'this' resolve o sombreamento de variáveis entre os atributos e os parâmetros
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.situacao = situacao;
    }

    // Métodos Getters e Setters para leitura e modificação controlada dos atributos
    public Integer getCodigo() { return codigo; }
    public void setCodigo(Integer codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }
}