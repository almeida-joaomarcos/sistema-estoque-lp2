package com.estoque.view.sdi;

import com.estoque.dao.ProdutoDAO;
import com.estoque.model.Produto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Tela de listagem, consulta parametrizada e exclusão/edição na arquitetura SDI.
 */
public class ConsultaProdutoSDI extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private JComboBox<String> cbFiltro;
    private JTextField txtPesquisa;
    private JButton btnBuscar, btnAlterar, btnExcluir;
    private List<Produto> listaProdutos;

    public ConsultaProdutoSDI() {
        setTitle("Consulta e Gerenciamento de Produtos - SDI");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        // Painel superior com FlowLayout à esquerda contendo os controles de pesquisa
        JPanel topoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbFiltro = new JComboBox<>(new String[]{"Todos", "Código", "Nome", "Categoria"});
        txtPesquisa = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        topoPanel.add(new JLabel("Filtrar por:"));
        topoPanel.add(cbFiltro);
        topoPanel.add(txtPesquisa);
        topoPanel.add(btnBuscar);
        add(topoPanel, BorderLayout.NORTH);

        // Inicialização do DefaultTableModel com as colunas definidas
        // Sobrescreve isCellEditable para retornar false, impedindo que o usuário edite células diretamente na tabela
        modelo = new DefaultTableModel(new Object[]{"Código", "Nome", "Categoria", "Preço", "Estoque", "Situação"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabela = new JTable(modelo);
        // O JTable deve ser encapsulado em um JScrollPane para permitir rolagem e exibição do cabeçalho
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Painel inferior com os botões de ação sobre os registros selecionados
        JPanel rodapePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAlterar = new JButton("Alterar Registro");
        btnExcluir = new JButton("Excluir Registro");
        rodapePanel.add(btnAlterar);
        rodapePanel.add(btnExcluir);
        add(rodapePanel, BorderLayout.SOUTH);

        // Associações de eventos
        btnBuscar.addActionListener(e -> carregarDados());
        btnAlterar.addActionListener(e -> alterar());
        btnExcluir.addActionListener(e -> excluir());

        // Carregamento inicial dos dados ao abrir a tela
        carregarDados();
    }

    // Consulta os registros no DAO e reconstrói o modelo de linhas da JTable
    public void carregarDados() {
        // Zera as linhas existentes para não duplicar registros na tabela
        modelo.setRowCount(0);
        String filtro = (String) cbFiltro.getSelectedItem();
        String termo = txtPesquisa.getText();
        listaProdutos = new ProdutoDAO().listarPorFiltro(filtro, termo);

        // Percorre a lista de entidades e insere cada registro como uma nova linha na tabela
        for (Produto p : listaProdutos) {
            modelo.addRow(new Object[]{p.getCodigo(), p.getNome(), p.getCategoria(), p.getPreco(), p.getQuantidadeEstoque(), p.getSituacao()});
        }
    }

    // Obtém o registro selecionado e abre o formulário de edição
    private void alterar() {
        // Captura o índice da linha clicada pelo usuário (-1 se nenhuma estiver selecionada)
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            Produto p = listaProdutos.get(linha);
            FormProdutoSDI form = new FormProdutoSDI(p);
            
            // Adiciona um WindowListener para detectar quando o formulário for fechado
            // WindowAdapter implementa a interface WindowListener, permitindo sobrescrever apenas o método desejado
            form.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    // Atualiza a tabela assim que o formulário fechar após a alteração
                    carregarDados();
                }
            });
            form.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma linha da tabela para alterar.");
        }
    }

    // Executa a exclusão após confirmação expressa do usuário
    private void excluir() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            Produto p = listaProdutos.get(linha);
            // Caixa de diálogo de confirmação conforme exigido pelo edital
            int opc = JOptionPane.showConfirmDialog(this, 
                    "Deseja realmente excluir: " + p.getNome() + "?", 
                    "Confirmação", JOptionPane.YES_NO_OPTION);
            
            // Se o usuário clicar em SIM (YES_OPTION), o registro é removido e a tabela é atualizada
            if (opc == JOptionPane.YES_OPTION) {
                new ProdutoDAO().excluir(p.getCodigo());
                carregarDados();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma linha da tabela para excluir.");
        }
    }
}