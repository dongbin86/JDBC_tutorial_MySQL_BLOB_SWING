import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Santer on 31.10.2015.
 */
public class JDBC_Connection {
    private static Connection connection = null;
    private static JDBC_Connection jdbc_connection = null;
    Properties properties = new Properties();

    private String URL = null;
    private  String USERNAME = null;
    private  String PASSWORD = null;

    private JDBC_Connection(){}
    public Connection getConnection(){

        try {
            //get from properites sittings
            properties.load(new FileInputStream("C:\\Users\\Santer\\Desktop\\Development\\Projects\\JDBC_tutorial_MySQL_BLOB_SWING\\src\\main\\java\\prop.properties"));
            URL = properties.getProperty("dbUrl");
            USERNAME = properties.getProperty("user");
            PASSWORD=properties.getProperty("password");
            System.out.println(URL + " " + USERNAME + " " + PASSWORD);
            connection= DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
