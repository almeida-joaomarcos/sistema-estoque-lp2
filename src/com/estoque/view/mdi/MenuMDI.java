package com.estoque.view.mdi;

import javax.swing.*;
import java.awt.*;

/**
 * Janela mestre da arquitetura MDI.
 * Utiliza JFrame como moldura e JDesktopPane como área de trabalho que abriga os JInternalFrames.
 */
public class MenuMDI extends JFrame {
    // Área de trabalho virtual que gerencia o ciclo de vida e renderização das janelas filhas
    private JDesktopPane desktopPane;

    public MenuMDI() {
        setTitle("Sistema de Estoque - MDI");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializa o DesktopPane e define uma cor de fundo sutil para melhor contraste
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(new Color(230, 235, 240));
        add(desktopPane, BorderLayout.CENTER);

        // Construção da barra de menus superior
        JMenuBar menuBar = new JMenuBar();
        JMenu menuCadastros = new JMenu("Cadastros");
        JMenu menuConsultas = new JMenu("Consultas");
        JMenu menuSistema = new JMenu("Sistema");

        JMenuItem itemNovo = new JMenuItem("Novo Produto");
        JMenuItem itemConsultar = new JMenuItem("Listar / Gerenciar Produtos");
        JMenuItem itemSobre = new JMenuItem("Sobre");
        JMenuItem itemSair = new JMenuItem("Sair");

        // No MDI, as telas filhas não são independentes; elas precisam ser adicionadas ao DesktopPane
        itemNovo.addActionListener(e -> {
            FormProdutoMDI form = new FormProdutoMDI(null, null);
            desktopPane.add(form);
            form.setVisible(true);
        });

        itemConsultar.addActionListener(e -> {
            // Passa a referência do DesktopPane para que a tela de consulta consiga abrir o formulário dentro dele
            ConsultaProdutoMDI consulta = new ConsultaProdutoMDI(desktopPane);
            desktopPane.add(consulta);
            consulta.setVisible(true);
        });

        itemSobre.addActionListener(e -> JOptionPane.showMessageDialog(this, 
                "Sistema de Controle de Produtos - Estoque\nVersão MDI", 
                "Sobre", JOptionPane.INFORMATION_MESSAGE));
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuMDI().setVisible(true));
    }
}