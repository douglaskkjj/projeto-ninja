package view;

import connection.ConnectionFactory;
import database.dao.CadastroMissoesDAO;
import database.dao.CadastroNinjaDAO;
import database.dao.CadastroNinjaMissaoDAO;
import database.model.CadastroMissoes;
import database.model.CadastroNinja;
import database.model.CadastroNinjaMissao;
import database.model.Validacao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class CadastroNinjaMissaoWindow extends JFrame {
    static void main(String[] args) {
        new MenuPrincipalWindow();
    }

    private JComboBox<CadastroNinja> cbNinja;
    private JComboBox<CadastroMissoes> cbMissao;
    private JComboBox<String> cbFuncao;
    private JButton btnVincular;

    private JTable tbNinjaMissao;
    private DefaultTableModel modelNinjaMissao;
    private JScrollPane spVinculos;

    public CadastroNinjaMissaoWindow() {
        setSize(1000, 600);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        componentesCriar();
        vinculoCarregar();
        bancoDeDadosCarregar();
        setVisible(true);
    }

    public void bancoDeDadosCarregar() {
        try {
            Connection conexao = ConnectionFactory.getConnection(
                    "localhost", "3306", "mydb", "root", "041005"
            );

            CadastroNinjaMissaoDAO dao = new CadastroNinjaMissaoDAO(conexao);
            ArrayList<CadastroNinjaMissao> listaNinjaMissao = dao.selectAll();

            for (CadastroNinjaMissao cnm : listaNinjaMissao) {
                modelNinjaMissao.addRow(new Object[]{
                        cnm.getId(),
                        cnm.getIdNinja(),
                        cnm.getIdMissao(),
                        cnm.getFuncao(),
                        cnm.getDataParticipacao()
                });
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void componentesCriar() {
        cbNinja = new JComboBox<>();
        cbNinja.setBounds(10, 10, 250, 25);
        add(cbNinja);

        cbMissao = new JComboBox<>();
        cbMissao.setBounds(10, 40, 250, 25);
        add(cbMissao);

        cbFuncao = new JComboBox<>();
        cbFuncao.addItem("Líder");
        cbFuncao.addItem("Ataque");
        cbFuncao.addItem("Suporte");
        cbFuncao.addItem("Sensorial");
        cbFuncao.addItem("Médico");
        cbFuncao.addItem("Defesa");
        cbFuncao.setBounds(10, 90, 250, 25);
        add(cbFuncao);

        btnVincular = new JButton(new AbstractAction("Vincular") {
            @Override
            public void actionPerformed(ActionEvent e) {
                CadastroNinja ninja = (CadastroNinja) cbNinja.getSelectedItem();
                CadastroMissoes missao = (CadastroMissoes) cbMissao.getSelectedItem();

                if (ninja == null || missao == null) {
                    JOptionPane.showMessageDialog(null, "Cadastre ninja e missão antes!");
                    return;
                }

                String rankNinja = ninja.getRank_ninja();
                String rankMissao = missao.getRank_missao();

                System.out.println("Rank Ninja: " + rankNinja);
                System.out.println("Rank Missão: " + rankMissao);

                if (!Validacao.podeParticipar(rankNinja, rankMissao)) {
                    JOptionPane.showMessageDialog(null, "O ninja não pode participar da missão rank " + rankMissao);
                    return;
                }

                if (ninja == null || missao == null) {
                    JOptionPane.showMessageDialog(null, "Cadastre ninja e missão antes!");
                    return;
                }

                CadastroNinjaMissao cnm = new CadastroNinjaMissao();
                cnm.setIdNinja(ninja.getId());
                cnm.setIdMissao(missao.getId());
                cnm.setFuncao(cbFuncao.getSelectedItem().toString());
                cnm.setDataParticipacao(new Date(System.currentTimeMillis()));

                try {
                    Connection conexao = ConnectionFactory.getConnection(
                            "localhost", "3306", "mydb", "root", "041005"
                    );

                    CadastroNinjaMissaoDAO dao = new CadastroNinjaMissaoDAO(conexao);

                    if (dao.insert(cnm)) {
                        JOptionPane.showMessageDialog(null, "Vínculo realizado!");

                        modelNinjaMissao.setRowCount(0);
                        bancoDeDadosCarregar();
                    }

                } catch (SQLException ex) {
                    if (ex.getMessage().contains("Duplicate entry")) {
                        JOptionPane.showMessageDialog(null, "Ninja ja vinculado nessa missao!");
                    } else {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        btnVincular.setBounds(10, 130, 250, 25);
        getContentPane().add(btnVincular);

        modelNinjaMissao = new DefaultTableModel();
        modelNinjaMissao.addColumn("ID");
        modelNinjaMissao.addColumn("ID Ninja");
        modelNinjaMissao.addColumn("ID Missão");
        modelNinjaMissao.addColumn("Funcao");
        modelNinjaMissao.addColumn("Data Participacao");

        tbNinjaMissao = new JTable(modelNinjaMissao);

        spVinculos = new JScrollPane(tbNinjaMissao);
        spVinculos.setBounds(280, 10, 480, 400);
        add(spVinculos);
    }

    public void vinculoCarregar() {
        try {
            Connection conexao = ConnectionFactory.getConnection(
                    "localhost", "3306", "mydb", "root", "041005"
            );

            CadastroNinjaDAO ninjaDAO = new CadastroNinjaDAO(conexao);
            ArrayList<CadastroNinja> listaNinjas = ninjaDAO.selectAll();

            for (CadastroNinja n : listaNinjas) {
                cbNinja.addItem(n);
            }

            CadastroMissoesDAO missoesDAO = new CadastroMissoesDAO(conexao);
            ArrayList<CadastroMissoes> listaMissoes = missoesDAO.selectAll();

            for (CadastroMissoes m : listaMissoes) {
                cbMissao.addItem(m);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }



}
