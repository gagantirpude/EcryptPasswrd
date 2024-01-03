
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PasswordHashingExample {


    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static void storePasswordInDatabase(String name, String username, String hashedPassword) {
        try {
            //create Conn class object
            Conn con = new Conn();
            // Assume 'users' table with 'username' and 'password' columns
            String sql = "INSERT INTO users ( name, username, password) VALUES (?, ?, ?)";
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

    public static void main(String[] args) {
        // Example usage
        String name  = "Raju"; // Replace with your name or username
        String username = "mightyraju";
        String password = "1234";

        String hashedPassword = hashPassword(password);
        storePasswordInDatabase(name,username, hashedPassword);

        System.out.println("Password hashed and stored in the database.");
    }
}
