package com.estoque.view.mdi;

import com.estoque.dao.ProdutoDAO;
import com.estoque.model.Produto;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

/**
 * Janela interna de cadastro e edição no padrão MDI.
 * Herda de JInternalFrame para poder habitar dentro do JDesktopPane.
 */
public class FormProdutoMDI extends JInternalFrame {
    private JTextField txtCodigo, txtNome, txtDescricao, txtPreco, txtEstoque;
    private JComboBox<String> cbCategoria, cbSituacao;
    private JButton btnSalvar, btnCancelar;
    private Produto produtoAtual;
    // Interface funcional padrão do Java para executar uma ação de retorno (atualizar tabela da tela pai)
    private Runnable aoFecharCallback;

    public FormProdutoMDI(Produto p, Runnable callback) {
        // Chamada explícita ao construtor pai do JInternalFrame:
        // super(título, redimensionável?, fechável?, maximizável?, minimizável?)
        super(p == null ? "Cadastrar Produto" : "Editar Produto", true, true, true, true);
        this.produtoAtual = p;
        this.aoFecharCallback = callback;
        setSize(400, 350);
        setLayout(new BorderLayout(8, 8));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 6, 6));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
        formPanel.add(new JLabel("Estoque:")); formPanel.add(txtEstoque);
        formPanel.add(new JLabel("Situação:")); formPanel.add(cbSituacao);

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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
            Produto p = (produtoAtual == null) ? new Produto() : produtoAtual;
            p.setNome(txtNome.getText().trim());
            p.setDescricao(txtDescricao.getText().trim());
            p.setCategoria((String) cbCategoria.getSelectedItem());
            p.setPreco(new BigDecimal(txtPreco.getText().trim().replace(",", ".")));
            p.setQuantidadeEstoque(Integer.parseInt(txtEstoque.getText().trim()));
            p.setSituacao((String) cbSituacao.getSelectedItem());

            ProdutoDAO dao = new ProdutoDAO();
            if (p.getCodigo() == null) {
                dao.inserir(p);
                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            } else {
                dao.alterar(p);
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
            }
            
            // Dispara o callback se fornecido, notificando a janela chamadora para atualizar a JTable
            if (aoFecharCallback != null) aoFecharCallback.run();
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
}