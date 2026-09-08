package com.estoque.view.sdi;

import javax.swing.*;
import java.awt.*;

/**
 * Janela principal da arquitetura SDI.
 * Herda de JFrame e atua como container principal contendo a barra de menus.
 */
public class MenuSDI extends JFrame {
    
    public MenuSDI() {
        // Define o texto na barra de título da janela
        setTitle("Sistema de Estoque - SDI");
        // Define as dimensões da janela (largura, altura)
        setSize(700, 450);
        // Garante que o processo da JVM seja totalmente encerrado ao fechar a janela principal
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Centraliza a janela no monitor
        setLocationRelativeTo(null);
        // Define o gerenciador de layout raiz como BorderLayout
        setLayout(new BorderLayout());

        // Criação dos componentes da barra de menu superior
        JMenuBar menuBar = new JMenuBar();
        JMenu menuCadastros = new JMenu("Cadastros");
        JMenu menuConsultas = new JMenu("Consultas");
        JMenu menuSistema = new JMenu("Sistema");

        JMenuItem itemNovo = new JMenuItem("Novo Produto");
        JMenuItem itemConsultar = new JMenuItem("Listar / Gerenciar Produtos");
        JMenuItem itemSobre = new JMenuItem("Sobre");
        JMenuItem itemSair = new JMenuItem("Sair");

        // Tratamento de eventos utilizando classes internas anônimas
        // Ação para o item Novo Produto: abre a tela de formulário passando null para novo cadastro
        itemNovo.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FormProdutoSDI form = new FormProdutoSDI(null);
                form.setVisible(true);
            }
        });

        // Ação para o item Consultar: instancia e torna visível a janela independente de consulta
        itemConsultar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultaProdutoSDI consulta = new ConsultaProdutoSDI();
                consulta.setVisible(true);
            }
        });

        // Ação para o item Sobre: exibe uma caixa de mensagem modal informativa via JOptionPane
        itemSobre.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane.showMessageDialog(MenuSDI.this, 
                        "Sistema de Controle de Produtos - Estoque\nVersão SDI", 
                        "Sobre", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Ação para o item Sair: encerra o processo da Máquina Virtual Java (JVM)
        itemSair.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(0);
            }
        });

        // Montagem da hierarquia de menus
        menuCadastros.add(itemNovo);
        menuConsultas.add(itemConsultar);
        menuSistema.add(itemSobre);
        menuSistema.addSeparator(); // Linha divisória horizontal no menu
        menuSistema.add(itemSair);

        menuBar.add(menuCadastros);
        menuBar.add(menuConsultas);
        menuBar.add(menuSistema);
        
        // Acopla a barra de navegação no topo do JFrame
        setJMenuBar(menuBar);

        // Painel central para exibição do cabeçalho
        // O GridBagLayout sem restrições adicionais centraliza o componente no painel por padrão
        JPanel centerPanel = new JPanel(new GridBagLayout());
        JLabel lblTitulo = new JLabel("CONTROLE DE ESTOQUE (SDI)");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        centerPanel.add(lblTitulo);
        add(centerPanel, BorderLayout.CENTER);
    }

    // Ponto de entrada do programa
    public static void main(String[] args) {
        // Enfileira a criação da GUI na Event Dispatch Thread (EDT), garantindo segurança de threads no Swing
        SwingUtilities.invokeLater(() -> new MenuSDI().setVisible(true));
    }
}