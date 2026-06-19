package view;

import connection.ConnectionFactory;
import database.dao.TotalizadorNinjaDAO;
import database.model.TotalizadorNinja;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TotalizadorNinjaWindow extends JFrame {

    private JButton btnGerar;
    private JTable tbTotalizadores;
    private DefaultTableModel modelTotalizadores;
    private JScrollPane spTotalizadores;

    public TotalizadorNinjaWindow() {
        setSize(800, 500);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        componentesCriar();
        setVisible(true);
    }

    private void componentesCriar() {
        btnGerar = new JButton(new AbstractAction("Gerar Totalizadores") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conexao = ConnectionFactory.getConnection(
                            "localhost", "3306", "mydb", "root", "041005"
                    );

                    TotalizadorNinjaDAO dao = new TotalizadorNinjaDAO(conexao);
                    dao.limparTotalizadores();

                    ArrayList<TotalizadorNinja> lista = dao.gerarTotalizadores();

                    modelTotalizadores.setRowCount(0);

                    for (TotalizadorNinja t : lista) {
                        dao.insert(t);

                        modelTotalizadores.addRow(new Object[]{
                                t.getDescricao(),
                                t.getQuantidade(),
                                t.getData_geracao(),
                        });
                    }

                    JOptionPane.showMessageDialog(null, "Totalizadores gerados e salvos!");

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnGerar.setBounds(10, 10, 200, 30);
        add(btnGerar);

        modelTotalizadores = new DefaultTableModel();
        modelTotalizadores.addColumn("Descrição");
        modelTotalizadores.addColumn("Quantidade");
        modelTotalizadores.addColumn("Data Geração");

        tbTotalizadores = new JTable(modelTotalizadores);

        spTotalizadores = new JScrollPane(tbTotalizadores);
        spTotalizadores.setBounds(10, 60, 760, 350);
        add(spTotalizadores);
    }

    public static void main(String[] args) {
        new TotalizadorNinjaWindow();
    }
}