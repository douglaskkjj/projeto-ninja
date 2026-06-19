package database.dao;

import database.model.CadastroNinja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CadastroNinjaDAO {

    private String insert =
            "INSERT INTO tb_ninja(nome, vila, cla, rank_ninja, natureza_chakra, status) VALUES(?, ?, ?, ?, ?, ?)";

    private String delete =
            "DELETE FROM tb_ninja WHERE id = ?";

    private String update =
            "UPDATE tb_ninja SET nome = ?, vila = ?, cla = ?, rank_ninja = ?, natureza_chakra = ?, status = ? WHERE id = ?";

    private String selectAll =
            "SELECT * FROM tb_ninja";

    private PreparedStatement pstInsert;
    private PreparedStatement pstDelete;
    private PreparedStatement pstUpdate;
    private PreparedStatement pstSelectAll;

    public CadastroNinjaDAO(Connection conexao) throws SQLException {
        pstInsert = conexao.prepareStatement(insert);
        pstDelete = conexao.prepareStatement(delete);
        pstUpdate = conexao.prepareStatement(update);
        pstSelectAll = conexao.prepareStatement(selectAll);
    }

    public boolean insert(CadastroNinja cadastroNinja) throws SQLException {
        pstInsert.setString(1, cadastroNinja.getNome());
        pstInsert.setString(2, cadastroNinja.getVila());
        pstInsert.setString(3, cadastroNinja.getCla());
        pstInsert.setString(4, cadastroNinja.getRank_ninja());
        pstInsert.setString(5, cadastroNinja.getNatureza_chakra());
        pstInsert.setBoolean(6, cadastroNinja.isStatus());

        pstInsert.execute();
        return pstInsert.getUpdateCount() > 0;
    }

    public boolean update(CadastroNinja cadastroNinja) throws SQLException {
        pstUpdate.setString(1, cadastroNinja.getNome());
        pstUpdate.setString(2, cadastroNinja.getVila());
        pstUpdate.setString(3, cadastroNinja.getCla());
        pstUpdate.setString(4, cadastroNinja.getRank_ninja());
        pstUpdate.setString(5, cadastroNinja.getNatureza_chakra());
        pstUpdate.setBoolean(6, cadastroNinja.isStatus());
        pstUpdate.setLong(7, cadastroNinja.getId());

        pstUpdate.execute();
        return pstUpdate.getUpdateCount() > 0;
    }

    public boolean delete(CadastroNinja cadastroNinja) throws SQLException {
        pstDelete.setLong(1, cadastroNinja.getId());

        pstDelete.execute();
        return pstDelete.getUpdateCount() > 0;
    }

    public ArrayList<CadastroNinja> selectAll() throws SQLException {
        ArrayList<CadastroNinja> cadastroNinjas = new ArrayList<>();
        ResultSet rs = pstSelectAll.executeQuery();
        if(rs != null) {
            while (rs.next()) {
                CadastroNinja cn = new CadastroNinja();
                cn.setId(rs.getLong(1));
                cn.setNome(rs.getString(2));
                cn.setVila(rs.getString(3));
                cn.setCla(rs.getString(4));
                cn.setRank_ninja(rs.getString(5));
                cn.setNatureza_chakra(rs.getString(6));
                cn.setStatus(rs.getBoolean(7));
                cadastroNinjas.add(cn);
            }
        }
        return cadastroNinjas;
    }






}
