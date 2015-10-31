import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Santer on 31.10.2015.
 */
public class BLOB_Reading_JDBC {
    public static void main(String[] args) throws IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        try{
            connection=JDBC_Connection.getJdbc_connection().getConnection();
            statement=connection.createStatement();
            String sql = "SELECT resume FROM employees where id=2";
            resultSet=statement.executeQuery(sql);

            File gettedFile = new File("geted_from_DB.jpg");
            fileOutputStream = new FileOutputStream(gettedFile);

            if (resultSet.next()){
                inputStream = resultSet.getBinaryStream("resume");
                System.out.println("Reading from db...");
                System.out.println(sql);

                byte[] buffer = new byte[1024];
                while (inputStream.read(buffer) >0){
                    fileOutputStream.write(buffer);
                }

                System.out.println("\nSaved to file : " + gettedFile.getAbsolutePath());
                System.out.println("SUCCESS!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (inputStream != null){
                inputStream.close();
            }
            if(fileOutputStream != null){
                fileOutputStream.close();
            }
        }
    }

}
