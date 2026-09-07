package com.estoque.view.sdi;

import javax.swing.*;
import java.awt.*;

public class MenuSDI extends JFrame {

    public MenuSDI() {
        setTitle("Sistema de Estoque - SDI");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menuCadastros = new JMenu("Cadastros");
        JMenu menuConsultas = new JMenu("Consultas");
        JMenu menuSistema = new JMenu("Sistema");

        JMenuItem itemNovo = new JMenuItem("Novo Produto");
        JMenuItem itemConsultar = new JMenuItem("Consultar / Listar Produtos");
        JMenuItem itemSobre = new JMenuItem("Sobre");
        JMenuItem itemSair = new JMenuItem("Sair");

        // Ações dos menus chamando os JFrames SDI
        itemNovo.addActionListener(e -> new FormProdutoSDI(null).setVisible(true));
        itemConsultar.addActionListener(e -> new ConsultaProdutoSDI().setVisible(true));
        itemSobre.addActionListener(e -> JOptionPane.showMessageDialog(this, 
                "Sistema de Controle de Produtos - Mercado\nVersão SDI", 
                "Sobre", 
                JOptionPane.INFORMATION_MESSAGE));
        itemSair.addActionListener(e -> System.exit(0));

        menuCadastros.add(itemNovo);
        menuConsultas.add(itemConsultar);
        menuSistema.add(itemSobre);
        menuSistema.addSeparator();
        menuSistema.add(itemSair);

        menuBar.add(menuCadastros);
        menuBar.add(menuConsultas);
        menuBar.add(menuSistema);
        setJMenuBar(menuBar);

        // Painel central para preenchimento visual
        JPanel centerPanel = new JPanel(new GridBagLayout());
        JLabel lblTitulo = new JLabel("CONTROLE DE ESTOQUE (SDI)");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        centerPanel.add(lblTitulo);
        add(centerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuSDI tela = new MenuSDI();
            tela.setVisible(true);
        });
    }
}