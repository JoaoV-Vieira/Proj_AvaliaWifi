package repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBanco {

    private static Connection conn = null;

    public static Connection conectar() throws SQLException, IOException {
        if (conn == null || conn.isClosed()) {
            Properties props = carregarPropriedades();
            String url = props.getProperty("dburl");
            String user = props.getProperty("user");
            String password = props.getProperty("password");

            conn = DriverManager.getConnection(url, user, password);
        }
        return conn;
    }

    public static void desconectar() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    private static Properties carregarPropriedades() throws IOException {
        Properties props = new Properties();
        try (var inputStream = ConexaoBanco.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (inputStream == null) {
                throw new IOException("Arquivo database.properties não encontrado no classpath!");
            }
            props.load(inputStream);
        }
        return props;
    }
}
