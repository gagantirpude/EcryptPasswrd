import org.mindrot.jbcrypt.BCrypt;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PasswordHandler {


    public static String hashPassword(String plainTextPassword) {
        String hashedPassword;
        hashedPassword = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
        return hashedPassword;
    }

    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        //with BCrypt.checkpw() method, we can verify the hashed password.
        //if the password is correct, it will return true.
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    public static void storePasswordInDatabase(String name, String username, String password) {
        try {
            String hashedPassword = hashPassword(password);

            //create Conn class object
            Conn con = new Conn();
            // Assume you have a table named 'users' with columns 'username' and 'password'
            String sql = "INSERT INTO users (name, username, password) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = con.connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, hashedPassword);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static boolean authenticateUser(String username, String password) {
        try {
            //create Conn class object
            Conn con = new Conn();

            // Assume you have a table named 'users' with columns 'username' and 'password'
            String sql = "SELECT password FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = con.connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        //get  password from database and compare with password entered by user.
                        String hashedPasswordFromDB = resultSet.getString("password");
                        //call verifyPassword method to verify password.
                        return verifyPassword(password, hashedPasswordFromDB); 
                        // true if password matches, false otherwise.
                        // after calling verifyPassword method, you can perform further actions based on the result.
                        //return keyword used to return the result to authenticateUser method who called form main function body.
                    }
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        try(Scanner sc = new Scanner(System.in)){
            System.out.print("Enter Your Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Your Username: ");
            String username = sc.nextLine();
            System.out.print("Enter Your Password: ");
            String password = sc.nextLine();

            //todo: store password in database
            storePasswordInDatabase(name, username, password);

            //todo: authenticate user
            if (authenticateUser( username, password)) {
                System.out.println("Authentication successful");
            } else {
                System.out.println("Authentication failed");
            }
        }


    }
}
