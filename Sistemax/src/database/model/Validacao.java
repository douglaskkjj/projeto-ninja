package database.model;

public class Validacao {

    public static boolean podeParticipar(String rankNinja, String rankMissao) {
        rankNinja = rankNinja.toUpperCase();
        switch (rankNinja) {
            case "GENIN":
                return rankMissao.equals("D") || rankMissao.equals("C");

            case "CHUNIN":
                return rankMissao.equals("D") || rankMissao.equals("C") || rankMissao.equals("B");

            case "JOUNIN":
                return true;

            case "KAGE":
                return rankMissao.equals("A") || rankMissao.equals("S");

            default:
                return false;
        }
    }
}
