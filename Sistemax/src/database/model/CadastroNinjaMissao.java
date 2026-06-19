package database.model;

import java.sql.Date;

public class CadastroNinjaMissao {

    private Long id;
    private Long idNinja;
    private Long idMissao;
    private String funcao;
    private Date dataParticipacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdNinja() {
        return idNinja;
    }

    public void setIdNinja(Long idNinja) {
        this.idNinja = idNinja;
    }

    public Long getIdMissao() {
        return idMissao;
    }

    public void setIdMissao(Long idMissao) {
        this.idMissao = idMissao;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Date getDataParticipacao() {
        return dataParticipacao;
    }

    public void setDataParticipacao(Date dataParticipacao) {
        this.dataParticipacao = dataParticipacao;
    }
}