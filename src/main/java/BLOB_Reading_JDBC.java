import java.io.*;
import java.sql.*;
import java.util.ArrayList;

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

        int filesCount = 0;

        try{
            connection=JDBC_Connection.getJdbc_connection().getConnection();
            statement=connection.createStatement();
            int i = 1;
            String sql = "SELECT resume FROM employees where id=" + i;
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

                resultSet=statement.executeQuery("SELECT COUNT(resume) FROM employees");
                while (resultSet.next()){
                    filesCount = resultSet.getInt(1);
                }
                System.out.println("\n\nFiles  BLOB = " +filesCount);
                ArrayList<String > names = new ArrayList<String>();
                for (int j = 0; j < filesCount; j++) {
                    names.add("File_" + j + ".jpg");
                }
                System.out.println(names);

                //get id of BLOB
                ArrayList<Integer> idOfBlobs = new ArrayList<Integer>();
                resultSet = statement.executeQuery("SELECT id FROM employees where resume IS NOT NULL ");
                while (resultSet.next()){
                    idOfBlobs.add(resultSet.getInt(1));
                }
                System.out.println("Id where BLOB in 'resume' not null: " + idOfBlobs);

                //go to read ALL files BLOB from column
                //read from BLOB and write into file
//read BLOB
                    for (int k = 0; k < idOfBlobs.size(); k++) {
                        //make files
                        File file1 = new File("C:\\Users\\Santer\\Desktop\\Development\\Projects\\JDBC_tutorial_MySQL_BLOB_SWING\\files");
                        if (!file1.exists()) {
                            file1.mkdir();
                        }
                        File file = new File(file1 + "\\" + names.get(k));
                        fileOutputStream = new FileOutputStream(file);

                        resultSet = statement.executeQuery("SELECT resume FROM employees WHERE id =" + idOfBlobs.get(k));
                        if (resultSet.next()) {
                            Blob blob = resultSet.getBlob("resume");
                            inputStream = blob.getBinaryStream();
                            fileOutputStream = new FileOutputStream(file);

                            byte[] buffer1 = new byte[4096];
                            int bytesRead = -1;
                            while ((bytesRead = inputStream.read(buffer1)) != -1) {
                                fileOutputStream.write(buffer1, 0, bytesRead);
                            }
                        }

                    }
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
