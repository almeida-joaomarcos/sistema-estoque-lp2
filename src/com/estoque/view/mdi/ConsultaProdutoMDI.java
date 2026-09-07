package com.estoque.view.mdi;

import com.estoque.dao.ProdutoDAO;
import com.estoque.model.Produto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaProdutoMDI extends JInternalFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private JComboBox<String> cbFiltro;
    private JTextField txtPesquisa;
    private JButton btnBuscar, btnAlterar, btnExcluir;
    private List<Produto> listaProdutos;
    private JDesktopPane desktopPane;

    public ConsultaProdutoMDI(JDesktopPane desktopPane) {
        super("Consulta de Produtos", true, true, true, true);
        this.desktopPane = desktopPane;
        setSize(700, 400);
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
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel rodapePanel = new JPanel();
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
        try {
            ProdutoDAO dao = new ProdutoDAO();
            String filtro = cbFiltro.getSelectedItem().toString();
            String texto = "Todos".equals(filtro) ? "" : txtPesquisa.getText();
            listaProdutos = dao.listarPorFiltro(filtro, texto);

            for (Produto p : listaProdutos) {
                modelo.addRow(new Object[]{p.getCodigo(), p.getNome(), p.getCategoria(), p.getPreco(), p.getQuantidadeEstoque(), p.getSituacao()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
        }
    }

    private void alterar() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            Produto selecionado = listaProdutos.get(linha);
            FormProdutoMDI form = new FormProdutoMDI(selecionado, this::carregarDados);
            desktopPane.add(form);
            form.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para alterar.");
        }
    }

    private void excluir() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            Produto selecionado = listaProdutos.get(linha);
            int opc = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o produto " + selecionado.getNome() + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (opc == JOptionPane.YES_OPTION) {
                try {
                    new ProdutoDAO().excluir(selecionado.getCodigo());
                    JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
                    carregarDados();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.");
        }
    }
}