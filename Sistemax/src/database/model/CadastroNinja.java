package database.model;

public class CadastroNinja {

    private Long id;
    private String nome;
    private String vila;
    private String cla;
    private String rank_ninja;
    private String natureza_chakra;
    private boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVila() {
        return vila;
    }

    public void setVila(String vila) {
        this.vila = vila;
    }

    public String getCla() {
        return cla;
    }

    public void setCla(String cla) {
        this.cla = cla;
    }

    public String getRank_ninja() {
        return rank_ninja;
    }

    public void setRank_ninja(String rank_ninja) {
        this.rank_ninja = rank_ninja;
    }

    public String getNatureza_chakra() {
        return natureza_chakra;
    }

    public void setNatureza_chakra(String natureza_chakra) {
        this.natureza_chakra = natureza_chakra;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return nome;
    }
}
