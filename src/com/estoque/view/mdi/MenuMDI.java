package com.estoque.view.mdi;

import javax.swing.*;
import java.awt.*;

public class MenuMDI extends JFrame {
    private JDesktopPane desktopPane;

    public MenuMDI() {
        setTitle("Sistema de Mercado - MDI");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        add(desktopPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuCadastros = new JMenu("Cadastros");
        JMenu menuConsultas = new JMenu("Consultas");
        JMenu menuSistema = new JMenu("Sistema");

        JMenuItem itemNovo = new JMenuItem("Novo Produto");
        JMenuItem itemConsultar = new JMenuItem("Listar / Gerenciar Produtos");
        JMenuItem itemSobre = new JMenuItem("Sobre");
        JMenuItem itemSair = new JMenuItem("Sair");

        itemNovo.addActionListener(e -> abrirJanelaInterna(new FormProdutoMDI(null, null)));
        itemConsultar.addActionListener(e -> abrirJanelaInterna(new ConsultaProdutoMDI(desktopPane)));
        itemSobre.addActionListener(e -> JOptionPane.showMessageDialog(this, "Sistema de Controle de Produtos - Mercado\nVersão MDI", "Sobre", JOptionPane.INFORMATION_MESSAGE));
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
    }

    private void abrirJanelaInterna(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuMDI().setVisible(true));
    }
}
