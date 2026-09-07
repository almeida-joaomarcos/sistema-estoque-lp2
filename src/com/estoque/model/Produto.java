package com.estoque.model;

import java.math.BigDecimal;

public class Produto {
    private Integer codigo;
    private String nome;
    private String descricao;
    private String categoria;
    private BigDecimal preco;
    private Integer quantidadeEstoque;
    private String situacao;

    public Produto() {}

    public Produto(Integer codigo, String nome, String descricao, String categoria, BigDecimal preco, Integer quantidadeEstoque, String situacao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.situacao = situacao;
    }

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