package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection(
            String host,
            String porta,
            String banco,
            String usuario,
            String senha
    ) throws SQLException {

        String url = "jdbc:mysql://" + host + ":" + porta + "/" + banco;

        return DriverManager.getConnection(url, usuario, senha);
    }
}