package com.estoque.view.mdi;

import com.estoque.dao.ProdutoDAO;
import com.estoque.model.Produto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Janela interna para consulta e ações sobre os registros no formato MDI.
 */
public class ConsultaProdutoMDI extends JInternalFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private JComboBox<String> cbFiltro;
    private JTextField txtPesquisa;
    private JButton btnBuscar, btnAlterar, btnExcluir;
    private List<Produto> listaProdutos;
    // Referência da área de trabalho necessária para adicionar o formulário interno ao DesktopPane
    private JDesktopPane desktopPane;

    public ConsultaProdutoMDI(JDesktopPane desktop) {
        super("Consulta de Produtos - MDI", true, true, true, true);
        this.desktopPane = desktop;
        setSize(680, 380);
        setLayout(new BorderLayout(5, 5));

        JPanel topoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbFiltro = new JComboBox<>(new String[]{"Todos", "Código", "Nome", "Categoria"});
        txtPesquisa = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        topoPanel.add(new JLabel("Filtrar por:"));
        topoPanel.add(cbFiltro);
        topoPanel.add(txtPesquisa);
        topoPanel.add(btnBuscar);
        add(topoPanel, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[]{"Código", "Nome", "Categoria", "Preço", "Estoque", "Situação"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel rodapePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        rodapePanel.add(btnAlterar);
        rodapePanel.add(btnExcluir);
        add(rodapePanel, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> carregarDados());
        btnAlterar.addActionListener(e -> alterar());
        btnExcluir.addActionListener(e -> excluir());

        carregarDados();
    }

    public void carregarDados() {
        modelo.setRowCount(0);
        String filtro = (String) cbFiltro.getSelectedItem();
        String termo = txtPesquisa.getText();
        listaProdutos = new ProdutoDAO().listarPorFiltro(filtro, termo);

        for (Produto p : listaProdutos) {
            modelo.addRow(new Object[]{p.getCodigo(), p.getNome(), p.getCategoria(), p.getPreco(), p.getQuantidadeEstoque(), p.getSituacao()});
        }
    }

    private void alterar() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            Produto p = listaProdutos.get(linha);
            // Passa a referência do método carregarDados (via method reference) para atualizar ao fechar
            FormProdutoMDI form = new FormProdutoMDI(p, this::carregarDados);
            // Anexa a janela de edição à mesma área de trabalho do MDI
            desktopPane.add(form);
            form.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para alterar.");
        }
    }

    private void excluir() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            Produto p = listaProdutos.get(linha);
            int opc = JOptionPane.showConfirmDialog(this, "Deseja excluir: " + p.getNome() + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (opc == JOptionPane.YES_OPTION) {
                new ProdutoDAO().excluir(p.getCodigo());
                carregarDados();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.");
        }
    }
}