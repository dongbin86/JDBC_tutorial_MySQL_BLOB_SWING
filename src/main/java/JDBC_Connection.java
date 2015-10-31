import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Santer on 31.10.2015.
 */
public class JDBC_Connection {
    private static Connection connection = null;
    private static JDBC_Connection jdbc_connection = null;
    private final String URL = "jdbc:mysql://localhost:3306/demo";
    private final String USERNAME = "root";
    private final String PASSWORD = "root";

    private JDBC_Connection(){}
    public Connection getConnection(){
        try {
            connection= DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static   JDBC_Connection getJdbc_connection(){
        if (jdbc_connection==null){
            jdbc_connection=new JDBC_Connection();
        }
        return jdbc_connection;
    }
}
