package connecting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connecting {
    private final String url = "jdbc:postgresql://localhost:5432/stock";
    private final String user = "postgres";
    private final String password = "postgres";


    public Connection connection() {
        Connection conn = null;
        String url = "jdbc:postgresql://localhost:5432/stock";
        String user = "postgres";
        String password = "postgres";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

}
