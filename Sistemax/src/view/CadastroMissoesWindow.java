package view;

import connection.ConnectionFactory;
import database.dao.CadastroMissoesDAO;
import database.dao.CadastroNinjaDAO;
import database.model.CadastroMissoes;
import database.model.CadastroNinja;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CadastroMissoesWindow extends JFrame {
    static void main(String[] args) {
        new MenuPrincipalWindow();
    }

    private JTextField txTitulo, txVilaOrigem;
    private JTextArea txDescricao;
    private JComboBox<String> cbRankMissao;
    private JComboBox<String> cbStatus;

    private JButton btnCadastrar, btnAlterar, btnExcluir;
    private JTable tbMissoes;
    private DefaultTableModel modelMissoes;
    private JScrollPane spMissoes;

    public CadastroMissoesWindow() {
        setSize(1000, 600);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        componentesCriar();
        bancoDeDadosCarregar();
        setVisible(true);
    }

    public void bancoDeDadosCarregar() {
        try {
            Connection conexao = ConnectionFactory.getConnection(
                    "localhost", "3306", "mydb", "root", "041005"
            );

            CadastroMissoesDAO dao = new CadastroMissoesDAO(conexao);
            ArrayList<CadastroMissoes> listaMissoes = dao.selectAll();

            for (CadastroMissoes m : listaMissoes) {
                modelMissoes.addRow(new Object[]{
                        m.getId(),
                        m.getTitulo(),
                        m.getDescricao(),
                        m.getRank_missao(),
                        m.getVila_origem(),
                        m.getStatus()
                });
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void componentesCriar() {
        txTitulo = new JTextField();
        txTitulo.setBounds(10, 10, 250, 25);
        add(txTitulo);

        txDescricao = new JTextArea();
        txDescricao.setBounds(10, 50, 250, 25);
        add(txDescricao);

        cbRankMissao = new JComboBox<>();
        cbRankMissao.addItem("D");
        cbRankMissao.addItem("C");
        cbRankMissao.addItem("B");
        cbRankMissao.addItem("A");
        cbRankMissao.addItem("S");
        cbRankMissao.setBounds(10, 90, 250, 25);
        add(cbRankMissao);

        txVilaOrigem = new JTextField();
        txVilaOrigem.setBounds(10, 130, 250, 25);
        add(txVilaOrigem);

        cbStatus = new JComboBox<>();
        cbStatus.addItem("Aberta");
        cbStatus.addItem("Concluida");
        cbStatus.addItem("Em andamento");
        cbStatus.addItem("Cancelada");
        cbStatus.setBounds(10, 170, 250, 25);
        add(cbStatus);

        btnCadastrar = new JButton(new AbstractAction("Cadastrar"){
            @Override
            public void actionPerformed(ActionEvent e) {
                String tituloDigitado = txTitulo.getText();
                String descricaoDigitado = txDescricao.getText();
                String rank_missaoDigitado = cbRankMissao.getSelectedItem().toString();
                String vila_origemDigitado = txVilaOrigem.getText();
                String statusDigitado = cbStatus.getSelectedItem().toString();

                CadastroMissoes cm = new CadastroMissoes();
                cm.setTitulo(tituloDigitado);
                cm.setDescricao(descricaoDigitado);
                cm.setRank_missao(rank_missaoDigitado);
                cm.setVila_origem(vila_origemDigitado);
                cm.setStatus(statusDigitado);

                try {
                    Connection conexao = ConnectionFactory.getConnection(
                            "localhost", "3306", "mydb", "root", "041005"
                    );

                    CadastroMissoesDAO dao = new CadastroMissoesDAO(conexao);

                    if (dao.insert(cm)) {
                        JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");

                        modelMissoes.setRowCount(0);
                        bancoDeDadosCarregar();

                        txTitulo.setText("");
                        txVilaOrigem.setText("");
                        cbRankMissao.setSelectedIndex(0);
                        txVilaOrigem.setText("");
                        cbStatus.setSelectedIndex(0);
                    }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
                }
            }
        });
        btnCadastrar.setBounds(10, 205, 250, 40);
        getContentPane().add(btnCadastrar);

        btnAlterar = new JButton(new AbstractAction("Alterar") {
            @Override
            public void actionPerformed(ActionEvent e) {

                int linha = tbMissoes.getSelectedRow();

                if (linha == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione uma missão");
                    return;
                }

                String tituloDigitado = txTitulo.getText();
                String descricaoDigitado = txDescricao.getText();
                String rank_missaoDigitado = cbRankMissao.getSelectedItem().toString();
                String vila_origemDigitado = txVilaOrigem.getText();
                String statusDigitado = cbStatus.getSelectedItem().toString();

                CadastroMissoes cm = new CadastroMissoes();

                cm.setId(Long.parseLong(modelMissoes.getValueAt(linha, 0).toString()));
                cm.setTitulo(txTitulo.getText());
                cm.setDescricao(txDescricao.getText());
                cm.setRank_missao(cbRankMissao.getSelectedItem().toString());
                cm.setVila_origem(txVilaOrigem.getText());
                cm.setStatus(cbStatus.getSelectedItem().toString());

                cm.setId(Long.parseLong(
                        modelMissoes.getValueAt(linha, 0).toString()
                ));

                try {
                    Connection conexao = ConnectionFactory.getConnection(
                            "localhost", "3306", "mydb", "root", "041005"
                    );

                    CadastroMissoesDAO dao = new CadastroMissoesDAO(conexao);

                    if (dao.update(cm)) {

                        modelMissoes.setValueAt(tituloDigitado, linha, 1);
                        modelMissoes.setValueAt(descricaoDigitado, linha, 2);
                        modelMissoes.setValueAt(rank_missaoDigitado, linha, 3);
                        modelMissoes.setValueAt(vila_origemDigitado, linha, 4);
                        modelMissoes.setValueAt(statusDigitado, linha, 5);

                        JOptionPane.showMessageDialog(null, "Missão alterada!");
                        modelMissoes.setRowCount(0);
                        bancoDeDadosCarregar();
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnAlterar.setBounds(10, 255, 250, 40);
        getContentPane().add(btnAlterar);

        btnExcluir = new JButton(new AbstractAction("Excluir") {
            @Override
            public void actionPerformed(ActionEvent e) {

                int linha = tbMissoes.getSelectedRow();

                if (linha == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione uma missão!");
                    return;
                }

                Long id = Long.parseLong(modelMissoes.getValueAt(linha, 0).toString());

                CadastroMissoes cm = new CadastroMissoes();
                cm.setId(id);

                try {
                    Connection conexao = ConnectionFactory.getConnection(
                            "localhost", "3306", "mydb", "root", "041005"
                    );

                    CadastroMissoesDAO dao = new CadastroMissoesDAO(conexao);

                    if (dao.delete(cm)) {
                        JOptionPane.showMessageDialog(null, "Missão excluída!");

                        modelMissoes.setRowCount(0);
                        bancoDeDadosCarregar();
                    } else {
                        JOptionPane.showMessageDialog(null, "Nenhuma missão foi excluída.");
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnExcluir.setBounds(10, 305, 250, 40);
        getContentPane().add(btnExcluir);

        modelMissoes = new DefaultTableModel();
        modelMissoes.addColumn("ID");
        modelMissoes.addColumn("Título");
        modelMissoes.addColumn("Descrição");
        modelMissoes.addColumn("Rank da Missão");
        modelMissoes.addColumn("Vila de Origem");
        modelMissoes.addColumn("Status");

        tbMissoes = new JTable(modelMissoes);

        tbMissoes.getSelectionModel().addListSelectionListener(e -> {
            int linha = tbMissoes.getSelectedRow();

            if (linha != -1) {
                txTitulo.setText(modelMissoes.getValueAt(linha, 1).toString());
                txDescricao.setText(modelMissoes.getValueAt(linha, 2).toString());
                cbRankMissao.setSelectedItem(modelMissoes.getValueAt(linha, 3).toString());
                txVilaOrigem.setText(modelMissoes.getValueAt(linha, 4).toString());
                cbStatus.setSelectedItem(modelMissoes.getValueAt(linha, 5).toString());
            }
        });

        spMissoes = new JScrollPane(tbMissoes);
        spMissoes.setBounds(280, 10, 700, 500);
        getContentPane().add(spMissoes);


    }




}
