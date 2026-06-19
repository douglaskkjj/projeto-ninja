package view;

import connection.ConnectionFactory;
import database.dao.CadastroNinjaDAO;
import database.model.CadastroNinja;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CadastroNinjaWindow extends JFrame {

    public static void main(String[] args) {
        new MenuPrincipalWindow();
    }

    private JTextField txNome, txVila, txcla, txRank_ninja, txNatureza_chakra;
    private JCheckBox checkStatus;
    private JButton btnCadastrar;

    private JTable tbNinjas;
    private DefaultTableModel modelNinjas;
    private JScrollPane spNinjas;

    public CadastroNinjaWindow() {
        setSize(1000, 600);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        componentesCriar();
        bancoDeDadosCarregar();
        setVisible(true);
    }

    private void bancoDeDadosCarregar() {
        try {
            Connection conexao = ConnectionFactory.getConnection(
                    "localhost", "3306", "mydb", "root", "041005"
            );

            CadastroNinjaDAO dao = new CadastroNinjaDAO(conexao);
            ArrayList<CadastroNinja> listaNinjas = dao.selectAll();

            for (CadastroNinja n : listaNinjas) {
                modelNinjas.addRow(new Object[]{
                        n.getNome(),
                        n.getVila(),
                        n.getCla(),
                        n.getRank_ninja(),
                        n.getNatureza_chakra(),
                        n.isStatus() ? "Ativo" : "Inativo"
                });
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void componentesCriar() {
        txNome = new JTextField();
        txNome.setBounds(10, 10, 250, 25);
        getContentPane().add(txNome);

        txVila = new JTextField();
        txVila.setBounds(10, 40, 250, 25);
        getContentPane().add(txVila);

        txcla = new JTextField();
        txcla.setBounds(10, 70, 250, 25);
        getContentPane().add(txcla);

        txRank_ninja = new JTextField();
        txRank_ninja.setBounds(10, 100, 250, 25);
        getContentPane().add(txRank_ninja);

        txNatureza_chakra = new JTextField();
        txNatureza_chakra.setBounds(10, 130, 250, 25);
        getContentPane().add(txNatureza_chakra);

        checkStatus = new JCheckBox("Ativo");
        checkStatus.setBounds(10, 160, 100, 25);
        checkStatus.setSelected(true);
        getContentPane().add(checkStatus);

        btnCadastrar = new JButton(new AbstractAction("Cadastrar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeDigitado = txNome.getText();
                String vilaDigitado = txVila.getText();
                String claDigitado = txcla.getText();
                String rankDigitado = txRank_ninja.getText();
                String naturezaDigitado = txNatureza_chakra.getText();
                boolean status = checkStatus.isSelected();

                CadastroNinja cn = new CadastroNinja();
                cn.setNome(nomeDigitado);
                cn.setVila(vilaDigitado);
                cn.setCla(claDigitado);
                cn.setRank_ninja(rankDigitado);
                cn.setNatureza_chakra(naturezaDigitado);
                cn.setStatus(status);

                try {
                    Connection conexao = ConnectionFactory.getConnection(
                            "localhost", "3306", "mydb", "root", "041005"
                    );

                    CadastroNinjaDAO dao = new CadastroNinjaDAO(conexao);

                    if (dao.insert(cn)) {
                        JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");

                        modelNinjas.addRow(new Object[]{
                                nomeDigitado,
                                vilaDigitado,
                                claDigitado,
                                rankDigitado,
                                naturezaDigitado,
                                status ? "Ativo" : "Inativo"
                        });

                        txNome.setText("");
                        txVila.setText("");
                        txcla.setText("");
                        txRank_ninja.setText("");
                        txNatureza_chakra.setText("");
                        checkStatus.setSelected(true);
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnCadastrar.setBounds(10, 195, 250, 25);
        getContentPane().add(btnCadastrar);

        modelNinjas = new DefaultTableModel();
        modelNinjas.addColumn("Nome");
        modelNinjas.addColumn("Vila");
        modelNinjas.addColumn("Cla");
        modelNinjas.addColumn("Rank ninja");
        modelNinjas.addColumn("Natureza chakra");
        modelNinjas.addColumn("Status");

        tbNinjas = new JTable(modelNinjas);

        spNinjas = new JScrollPane(tbNinjas);
        spNinjas.setBounds(280, 10, 700, 500);
        getContentPane().add(spNinjas);
    }
}