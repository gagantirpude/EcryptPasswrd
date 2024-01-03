import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Scanner;

public class Main {


    public static String hashPasswordM(String plainPasswordText) {
        String hashedPassword;
         hashedPassword = BCrypt.hashpw(plainPasswordText, BCrypt.gensalt(12));
        return hashedPassword;
    }

    public static void storeInDatabase(String name, String username, String password) {

        try {
             Conn con = new Conn();

            // Create Query for Insert Data in Database.
            String query = "INSERT INTO users(name,username,password) VALUES ('" + name + "','" + username + "','" + password + "')";

            // For Insert Data in Database we use executeUpdate Method.
            con.statement.executeUpdate(query);

            // To Get User Value
            String getQuery = "SELECT password FROM users WHERE id='" + 1 + "'";
            ResultSet rs = con.statement.executeQuery(getQuery);
            while (rs.next()) {
                System.out.println(rs.getString("password"));
            }

        } catch (Exception e) {
            e.fillInStackTrace();
            System.out.println(e.getMessage());
        }

    }

    // main Method
    public static void main(String[] args) {
        // Tell Us Your Name

        // Create Scanner Object
        try (Scanner sc = new Scanner(System.in)) {
            // Ask User to Enter Name
            System.out.print("Enter Your Name: ");
            String ns = sc.nextLine();

            // Ask User to Enter there Username
            System.out.print("Enter Your Username: ");
            String username = sc.nextLine();

            // Ask User to Enter there Password
            System.out.print("Enter Your Password: ");
            String password = sc.nextLine();

            // Create HashPassword Method who Return Value
            String hashPassword = hashPasswordM(password);
            // System.out.println(hashPassword);

            // Store In Database
            // Create a Method for Database to Store Value
            storeInDatabase(ns, username, hashPassword);
        }

    }

}
