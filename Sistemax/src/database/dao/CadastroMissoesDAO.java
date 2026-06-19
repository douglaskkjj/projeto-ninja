package database.dao;

import database.model.CadastroMissoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CadastroMissoesDAO {

    private String insert =
            "INSERT INTO tb_missao (titulo, descricao, rank_missao, vila_origem, status) VALUES(?, ?, ?, ?, ?)";

    private String delete =
            "DELETE FROM tb_missao WHERE id = ?";

    private String update =
            "UPDATE tb_missao SET titulo = ?, descricao = ?, rank_missao = ?, vila_origem = ?, status = ? WHERE id = ?";

    private String selectAll =
            "SELECT * FROM tb_missao";

    private PreparedStatement pstInsert;
    private PreparedStatement pstDelete;
    private PreparedStatement pstUpdate;
    private PreparedStatement pstSelectAll;

    public CadastroMissoesDAO(Connection conexao) throws SQLException {
        pstInsert = conexao.prepareStatement(insert);
        pstDelete = conexao.prepareStatement(delete);
        pstUpdate = conexao.prepareStatement(update);
        pstSelectAll = conexao.prepareStatement(selectAll);
    }

    public boolean insert(CadastroMissoes cadastroMissoes) throws SQLException {
        pstInsert.setString(1, cadastroMissoes.getTitulo());
        pstInsert.setString(2, cadastroMissoes.getDescricao());
        pstInsert.setString(3, cadastroMissoes.getRank_missao());
        pstInsert.setString(4, cadastroMissoes.getVila_origem());
        pstInsert.setString(5, cadastroMissoes.getStatus());

        pstInsert.execute();
        return pstInsert.getUpdateCount() > 0;
    }

    public boolean update(CadastroMissoes cadastroMissoes) throws SQLException {
        pstUpdate.setString(1, cadastroMissoes.getTitulo());
        pstUpdate.setString(2, cadastroMissoes.getDescricao());
        pstUpdate.setString(3, cadastroMissoes.getRank_missao());
        pstUpdate.setString(4, cadastroMissoes.getVila_origem());
        pstUpdate.setString(5, cadastroMissoes.getStatus());
        pstUpdate.setLong(6, cadastroMissoes.getId());

        pstUpdate.execute();
        return pstUpdate.getUpdateCount() > 0;
    }

    public boolean delete(CadastroMissoes cm) throws SQLException {
        pstDelete.setLong(1, cm.getId());
        pstDelete.execute();
        return pstDelete.getUpdateCount() > 0;
    }

    public ArrayList<CadastroMissoes> selectAll() throws SQLException {
        ArrayList<CadastroMissoes> cadastroMissoes = new ArrayList<>();
        ResultSet rs = pstSelectAll.executeQuery();
        while (rs.next()) {
            CadastroMissoes cm =  new CadastroMissoes();
            cm.setId(rs.getLong("id"));
            cm.setTitulo(rs.getString("titulo"));
            cm.setDescricao(rs.getString("descricao"));
            cm.setRank_missao(rs.getString("rank_missao"));
            cm.setVila_origem(rs.getString("vila_origem"));
            cm.setStatus(rs.getString("status"));
            cadastroMissoes.add(cm);
        }
        return cadastroMissoes;
    }



}
