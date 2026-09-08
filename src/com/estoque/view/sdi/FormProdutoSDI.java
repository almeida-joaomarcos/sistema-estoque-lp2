package com.estoque.view.sdi;

import com.estoque.dao.ProdutoDAO;
import com.estoque.model.Produto;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

/**
 * Janela de formulário para inserção e edição de dados no formato SDI.
 */
public class FormProdutoSDI extends JFrame {
    // Declaração dos componentes visuais do formulário
    private JTextField txtCodigo, txtNome, txtDescricao, txtPreco, txtEstoque;
    private JComboBox<String> cbCategoria, cbSituacao;
    private JButton btnSalvar, btnCancelar;
    // Armazena a referência do produto que está sendo editado (ou null se for novo)
    private Produto produtoAtual;

    public FormProdutoSDI(Produto p) {
        this.produtoAtual = p;
        // Ajusta o título dinamicamente conforme a operação realizada
        setTitle(p == null ? "Cadastrar Produto" : "Editar Produto");
        setSize(420, 360);
        // DISPOSE_ON_CLOSE fecha e libera os recursos apenas desta janela, mantendo o menu principal aberto
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel estruturado em GridLayout (7 linhas, 2 colunas, 8px de espaçamento horizontal e vertical)
        // Isso alinha com precisão cada JLabel ao seu respectivo campo de entrada
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 8, 8));
        // Adiciona margens internas (padding) de 15 pixels ao redor do formulário
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtCodigo = new JTextField();
        // Desabilita a edição do ID, pois o valor é gerado automaticamente pela camada de persistência
        txtCodigo.setEditable(false);
        txtNome = new JTextField();
        txtDescricao = new JTextField();
        cbCategoria = new JComboBox<>(new String[]{"Alimentos", "Bebidas", "Limpeza", "Higiene", "Hortifruti", "Padaria"});
        txtPreco = new JTextField();
        txtEstoque = new JTextField();
        cbSituacao = new JComboBox<>(new String[]{"Ativo", "Inativo"});

        // Adiciona os pares (Rótulo, Campo) à grade
        formPanel.add(new JLabel("Código:")); formPanel.add(txtCodigo);
        formPanel.add(new JLabel("Nome:")); formPanel.add(txtNome);
        formPanel.add(new JLabel("Descrição:")); formPanel.add(txtDescricao);
        formPanel.add(new JLabel("Categoria:")); formPanel.add(cbCategoria);
        formPanel.add(new JLabel("Preço (R$):")); formPanel.add(txtPreco);
        formPanel.add(new JLabel("Estoque:")); formPanel.add(txtEstoque);
        formPanel.add(new JLabel("Situação:")); formPanel.add(cbSituacao);

        add(formPanel, BorderLayout.CENTER);

        // Painel inferior para organização dos botões de ação alinhados à direita
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        btnPanel.add(btnSalvar);
        btnPanel.add(btnCancelar);
        add(btnPanel, BorderLayout.SOUTH);

        // Registro dos ouvintes de eventos
        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());

        // Se um produto existente foi passado como argumento, popula os campos para edição
        if (produtoAtual != null) {
            preencherCampos();
        }
    }

    // Carrega as propriedades do objeto Produto nos componentes visuais
    private void preencherCampos() {
        txtCodigo.setText(String.valueOf(produtoAtual.getCodigo()));
        txtNome.setText(produtoAtual.getNome());
        txtDescricao.setText(produtoAtual.getDescricao());
        cbCategoria.setSelectedItem(produtoAtual.getCategoria());
        txtPreco.setText(produtoAtual.getPreco().toString());
        txtEstoque.setText(String.valueOf(produtoAtual.getQuantidadeEstoque()));
        cbSituacao.setSelectedItem(produtoAtual.getSituacao());
    }

    // Coleta, converte e envia os dados para a persistência
    private void salvar() {
        try {
            // Reutiliza a instância em edição ou inicializa um novo objeto
            Produto p = (produtoAtual == null) ? new Produto() : produtoAtual;
            p.setNome(txtNome.getText().trim());
            p.setDescricao(txtDescricao.getText().trim());
            p.setCategoria((String) cbCategoria.getSelectedItem());
            // Substitui vírgula por ponto para evitar NumberFormatException ao instanciar BigDecimal
            p.setPreco(new BigDecimal(txtPreco.getText().trim().replace(",", ".")));
            // Converte o valor textual em inteiro para o estoque
            p.setQuantidadeEstoque(Integer.parseInt(txtEstoque.getText().trim()));
            p.setSituacao((String) cbSituacao.getSelectedItem());

            ProdutoDAO dao = new ProdutoDAO();
            // Verifica o código: se for nulo, insere novo; caso contrário, atualiza o existente
            if (p.getCodigo() == null) {
                dao.inserir(p);
                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            } else {
                dao.alterar(p);
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
            }
            // Fecha o formulário após a conclusão da operação
            dispose();
        } catch (Exception ex) {
            // Em caso de falha de conversão numérica ou validação, exibe aviso ao usuário
            JOptionPane.showMessageDialog(this, "Erro ao processar dados: " + ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }
}