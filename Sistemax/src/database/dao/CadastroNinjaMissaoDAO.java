package database.dao;

import database.model.CadastroNinjaMissao;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;

public class CadastroNinjaMissaoDAO {

    private String insert =
            "INSERT INTO tb_ninja_missao (id_ninja, id_missao, funcao, data_participacao) VALUES (?, ?, ?, ?)";

    private String selectAll =
            "SELECT * FROM tb_ninja_missao";

    private PreparedStatement pstInsert;
    private PreparedStatement pstSelectAll;

    public CadastroNinjaMissaoDAO(Connection conexao) throws SQLException {
        pstInsert = conexao.prepareStatement(insert);
        pstSelectAll = conexao.prepareStatement(selectAll);
    }

    public boolean insert(CadastroNinjaMissao cadastroNinjaMissao) throws SQLException {
        pstInsert.setLong(1, cadastroNinjaMissao.getIdNinja());
        pstInsert.setLong(2, cadastroNinjaMissao.getIdMissao());
        pstInsert.setString(3, cadastroNinjaMissao.getFuncao());
        pstInsert.setDate(4, cadastroNinjaMissao.getDataParticipacao());

        pstInsert.execute();
        return pstInsert.getUpdateCount() > 0;
    }

    public ArrayList<CadastroNinjaMissao> selectAll() throws SQLException {
        ArrayList<CadastroNinjaMissao> cadastroNinjaMissao = new ArrayList<>();

        ResultSet rs = pstSelectAll.executeQuery();

        while (rs.next()) {
            CadastroNinjaMissao cnm = new CadastroNinjaMissao();

            cnm.setId(rs.getLong("id"));
            cnm.setIdNinja(rs.getLong("id_ninja"));
            cnm.setIdMissao(rs.getLong("id_missao"));
            cnm.setFuncao(rs.getString("funcao"));
            cnm.setDataParticipacao(new Date(System.currentTimeMillis()));

            cadastroNinjaMissao.add(cnm);
        }

        return cadastroNinjaMissao;
    }
}