package view;

import javax.swing.*;

public class MenuPrincipalWindow extends JFrame {

    private JButton btnNinjas;
    private JButton btnMissoes;
    private JButton btnVinculos;
    private JButton btnTotaliza;

    public MenuPrincipalWindow() {
        setSize(500,300);
        setLayout(null);

        btnNinjas = new JButton("Cadastro de Ninjas");
        btnNinjas.setBounds(120, 30, 225, 40);
        add(btnNinjas);

        btnMissoes = new JButton("Cadastro de Missões");
        btnMissoes.setBounds(120, 80, 225, 40);
        add(btnMissoes);

        btnVinculos = new JButton("Cadastro de Vinculos");
        btnVinculos.setBounds(120, 130, 225, 40);
        add(btnVinculos);

        btnTotaliza = new JButton("Totalizadores");
        btnTotaliza.setBounds(120, 180, 225, 40);
        add(btnTotaliza);

        btnNinjas.addActionListener(e -> new CadastroNinjaWindow());

        btnMissoes.addActionListener(e -> new CadastroMissoesWindow());

        btnVinculos.addActionListener(e -> new CadastroNinjaMissaoWindow() );

        btnTotaliza.addActionListener(e -> new TotalizadorNinjaWindow());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MenuPrincipalWindow();
    }
}