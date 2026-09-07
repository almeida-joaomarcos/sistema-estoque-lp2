package com.estoque.view.mdi;

import com.estoque.dao.ProdutoDAO;
import com.estoque.model.Produto;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class FormProdutoMDI extends JInternalFrame {
    private JTextField txtCodigo, txtNome, txtDescricao, txtPreco, txtEstoque;
    private JComboBox<String> cbCategoria, cbSituacao;
    private JButton btnSalvar, btnCancelar;
    private Produto produtoAtual;
    private Runnable callbackAtualizacao;

    public FormProdutoMDI(Produto p, Runnable callback) {
        super(p == null ? "Cadastrar Produto" : "Editar Produto", true, true, true, true);
        this.produtoAtual = p;
        this.callbackAtualizacao = callback;
        setSize(450, 380);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtNome = new JTextField();
        txtDescricao = new JTextField();
        cbCategoria = new JComboBox<>(new String[]{"Alimentos", "Bebidas", "Limpeza", "Higiene", "Hortifruti", "Padaria"});
        txtPreco = new JTextField();
        txtEstoque = new JTextField();
        cbSituacao = new JComboBox<>(new String[]{"Ativo", "Inativo"});

        formPanel.add(new JLabel("Código:")); formPanel.add(txtCodigo);
        formPanel.add(new JLabel("Nome:")); formPanel.add(txtNome);
        formPanel.add(new JLabel("Descrição:")); formPanel.add(txtDescricao);
        formPanel.add(new JLabel("Categoria:")); formPanel.add(cbCategoria);
        formPanel.add(new JLabel("Preço (R$):")); formPanel.add(txtPreco);
        formPanel.add(new JLabel("Quantidade Estoque:")); formPanel.add(txtEstoque);
        formPanel.add(new JLabel("Situação:")); formPanel.add(cbSituacao);

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        btnPanel.add(btnSalvar);
        btnPanel.add(btnCancelar);
        add(btnPanel, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());

        if (produtoAtual != null) {
            preencherCampos();
        }
    }

    private void preencherCampos() {
        txtCodigo.setText(String.valueOf(produtoAtual.getCodigo()));
        txtNome.setText(produtoAtual.getNome());
        txtDescricao.setText(produtoAtual.getDescricao());
        cbCategoria.setSelectedItem(produtoAtual.getCategoria());
        txtPreco.setText(produtoAtual.getPreco().toString());
        txtEstoque.setText(String.valueOf(produtoAtual.getQuantidadeEstoque()));
        cbSituacao.setSelectedItem(produtoAtual.getSituacao());
    }

    private void salvar() {
        try {
            Produto p = produtoAtual == null ? new Produto() : produtoAtual;
            p.setNome(txtNome.getText());
            p.setDescricao(txtDescricao.getText());
            p.setCategoria(cbCategoria.getSelectedItem().toString());
            p.setPreco(new BigDecimal(txtPreco.getText().trim().replace(",", ".")));
            p.setQuantidadeEstoque(Integer.parseInt(txtEstoque.getText().trim()));
            p.setSituacao(cbSituacao.getSelectedItem().toString());

            ProdutoDAO dao = new ProdutoDAO();
            if (p.getCodigo() == null) {
                dao.inserir(p);
                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            } else {
                dao.alterar(p);
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
            }
            if (callbackAtualizacao != null) callbackAtualizacao.run();
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}