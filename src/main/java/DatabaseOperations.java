import java.sql.*;
import java.util.*;

public class DatabaseOperations {
    private Connection connection;

    public DatabaseOperations() {
        //установление соединения с БД
        try{
            String connectionUrl = "jdbc:sqlserver://HOME-PC\\SQLEXPRESS;databaseName=Project1DB";
            String user = "sa";
            String pass = "123";
            connection = DriverManager.getConnection(connectionUrl, user, pass);

            System.out.println("Database connection successfully");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    //получение директорий текстовых файлов
    public ArrayList<String> getPathFiles(String word){
        ArrayList<String> paths = new ArrayList<String>();

        try{
            String query = "select filePath, wordFreq from Files, \n" +
                    "(select fileId, wordFreq from FileWords, WordFreq\n" +
                    "where FileWords.wordFreqId = WordFreq.wordId and WordFreq.wordKey = ?) as Table1\n" +
                    "where Files.fileId = Table1.fileId\n" +
                    "order by wordFreq desc";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, word);
            ResultSet set = statement.executeQuery();

            while(set.next()){
                String filePath = set.getString("filePath");
                paths.add(filePath);
            }

            statement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return  paths;
    }
}