import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Santer on 31.10.2015.
 */
public class BLOB_Writing_JDBC {
    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static String sql = "UPDATE employees set resume = ? where id  BETWEEN 5 and 7";

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        connection= JDBC_Connection.getJdbc_connection().getConnection(); //наше соеденение с БД
        /////before writing
        java.sql.Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");
        while (resultSet.next()){
//            System.out.println(resultSet.getInt(1) + " "
//                    + resultSet.getString(2) + " "  + resultSet.getString(3));
            System.out.printf("%3d%15s%20s%15s%15s%15s --  \n", resultSet.getInt(1) ,resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4),resultSet.getString(5),resultSet.getDouble(6));
        }

        ////after writing
        File file = new File("C:\\Users\\Santer\\Desktop\\Development\\jdbc tut\\jdbc-blob\\sample_resume.pdf");
        File file2 = new File("C:\\Users\\Santer\\Desktop\\Development\\ship2Big.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileInputStream fileInputStream2 = new FileInputStream(file2);

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setBinaryStream(1, fileInputStream);

        System.out.println("Читаем файл: " + file.getAbsolutePath());
        System.out.println("Записываем в БД файл, к id 5 6 7");

        //write picture
        preparedStatement = connection.prepareStatement("UPDATE employees set resume = ? where id =2 ");
        preparedStatement.setBinaryStream(1,fileInputStream2);
        preparedStatement.executeUpdate();
        System.out.println("Файлы добавлены");

        try {
            fileInputStream.close();
            fileInputStream2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.close();
        System.out.println("\nСоеденение закрыто!");
    }
}
