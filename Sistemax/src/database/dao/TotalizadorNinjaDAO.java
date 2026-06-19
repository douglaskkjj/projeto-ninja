package database.dao;

import database.model.TotalizadorNinja;

import java.sql.*;
import java.util.ArrayList;

public class TotalizadorNinjaDAO {

    private Connection conexao;

    public TotalizadorNinjaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void limparTotalizadores() throws SQLException {
        String sql = "DELETE FROM tb_totalizador_ninja";
        PreparedStatement pst = conexao.prepareStatement(sql);
        pst.execute();
    }

    public boolean insert(TotalizadorNinja totalizadorNinja) throws SQLException {
        String sql = "INSERT INTO tb_totalizador_ninja(descricao, quantidade, data_geracao) VALUES (?, ?, ?)";
        PreparedStatement pst = conexao.prepareStatement(sql);

        pst.setString(1, totalizadorNinja.getDescricao());
        pst.setInt(2, totalizadorNinja.getQuantidade());
        pst.setDate(3, totalizadorNinja.getData_geracao());

        pst.execute();
        return pst.getUpdateCount() > 0;
    }

    public ArrayList<TotalizadorNinja> gerarTotalizadores() throws SQLException {
        ArrayList<TotalizadorNinja> totalizadorNinja = new ArrayList<>();

        adicionarConsulta(totalizadorNinja, "Quantidade de ninjas por vila:  ","SELECT vila AS descricao, COUNT(*) AS quantidade FROM tb_ninja GROUP BY vila");

        adicionarConsulta(totalizadorNinja,"Quantidade de ninjas por rank:  ","SELECT rank_ninja AS descricao, COUNT(*) AS quantidade FROM tb_ninja GROUP BY rank_ninja");

        adicionarConsulta(totalizadorNinja, "Quantidade de ninjas por natureza de chakr: ","SELECT natureza_chakra AS descricao, COUNT(*) AS quantidade FROM tb_ninja GROUP BY natureza_chakra");

        adicionarConsulta(totalizadorNinja,"Quantidade de missões por rank:  ","SELECT rank_missao AS descricao, COUNT(*) AS quantidade FROM tb_missao GROUP BY rank_missao");

        adicionarConsulta(totalizadorNinja,"Quantidade de missões por status: ","SELECT status AS descricao, COUNT(*) AS quantidade FROM tb_missao GROUP BY status");

        adicionarConsulta(totalizadorNinja, "Quantidade de ninjas vinculados a missões:  ","SELECT 'Vinculados' AS descricao, COUNT(DISTINCT id_ninja) AS quantidade FROM tb_ninja_missao");

        adicionarConsulta(totalizadorNinja,"Quantidade de missões sem nenhum ninja vinculado:  ", "SELECT 'Sem ninja vinculado' AS descricao, COUNT(*) AS quantidade " + "FROM tb_missao m LEFT JOIN tb_ninja_missao nm ON m.id = nm.id_missao " +
        "WHERE nm.id_missao IS NULL");

        return totalizadorNinja;
    }

    private void adicionarConsulta(ArrayList<TotalizadorNinja> lista, String prefixo, String sql) throws SQLException {
        PreparedStatement pst = conexao.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            TotalizadorNinja tn = new TotalizadorNinja();

            tn.setDescricao(prefixo + rs.getString("descricao"));
            tn.setQuantidade(rs.getInt("quantidade"));
            tn.setData_geracao(new Date(System.currentTimeMillis()));
            lista.add(tn);
        }
    }
}