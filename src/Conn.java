import java.sql.*;

public class Conn {

    //todo: Get Connection
    Connection connection;
    Statement statement;


    //Global Variables
    private static final String dbURL = "jdbc:mysql://localhost:3306/my_database";
    private static final String dbUser = "root";
    private static final String dbPassword = "";

    Conn(){
        try{
            connection= DriverManager.getConnection(dbURL,dbUser,dbPassword);
            statement=connection.createStatement();
            System.out.println("Connection Successful");
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Connection Failed");
            System.exit(0);
            return;
        }
    }
    public static void main(String[] args) {
        new Conn();
    }
}
