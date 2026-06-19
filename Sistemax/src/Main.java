import connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        Connection conexao = ConnectionFactory.getConnection(
                "localhost",
                "3306",
                "mydb",
                "root",
                "041005"
        );

        if (conexao != null) {
            System.out.println("CONECTADO COM SUCESSO!");
        }

        conexao.close();
    }
}