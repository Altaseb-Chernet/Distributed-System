package M_Database;

import java.io.*;
import java.net.*;
import java.sql.*;

public class Server {

    static final String URL = "jdbc:mysql://localhost:3306/";
    static final String USER = "root";
    static final String PASS = "";

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(4000);
            System.out.println("Server waiting for client...");

            // 🔥 STEP 1: CONNECT (NO DB FIRST)
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            Statement stmt = conn.createStatement();

            // 🔥 STEP 2: CREATE DATABASE IF NOT EXISTS
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS studentdb");

            // 🔥 STEP 3: USE DATABASE
            stmt.execute("USE studentdb");

            // 🔥 STEP 4: CREATE TABLE IF NOT EXISTS
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS students (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(50)," +
                "age INT," +
                "gender VARCHAR(10)," +
                "department VARCHAR(50))"
            );

            // 🔥 STEP 5: INSERT DATA (only if empty)
            ResultSet check = stmt.executeQuery("SELECT COUNT(*) FROM students");
            check.next();
            if (check.getInt(1) == 0) {
                stmt.executeUpdate(
                    "INSERT INTO students (name, age, gender, department) VALUES " +
                    "('Abel',22,'Male','Software Engineering')," +
                    "('Sara',20,'Female','Information System')," +
                    "('Helen',23,'Female','Computer Science')," +
                    "('John',24,'Male','Software Engineering')," +
                    "('Liya',21,'Female','Information System')"
                );
                System.out.println("Sample data inserted.");
            }

            // 🔁 MULTI CLIENT LOOP
            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("Client connected");

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(), true);

                String request = in.readLine();

                PreparedStatement pstmt = null;
                ResultSet rs;

                switch (request) {

                    case "1":
                        pstmt = conn.prepareStatement("SELECT * FROM students");
                        break;

                    case "2":
                        pstmt = conn.prepareStatement(
                                "SELECT name, gender FROM students WHERE gender='Female'");
                        break;

                    case "3":
                        pstmt = conn.prepareStatement(
                                "SELECT name, gender FROM students WHERE gender='Male'");
                        break;

                    case "4":
                        pstmt = conn.prepareStatement(
                                "SELECT name, age FROM students WHERE age > 21");
                        break;

                    case "5":
                        pstmt = conn.prepareStatement(
                                "SELECT name, age FROM students WHERE gender='Female' AND age > 21");
                        break;

                    case "6":
                        pstmt = conn.prepareStatement(
                                "SELECT name, department FROM students WHERE department='Software Engineering'");
                        break;

                    case "7":
                        pstmt = conn.prepareStatement(
                                "SELECT name FROM students WHERE name LIKE 'A%'");
                        break;

                    case "8":
                        pstmt = conn.prepareStatement(
                                "SELECT name, age FROM students ORDER BY age DESC");
                        break;

                    default:
                        out.println("Invalid choice");
                        socket.close();
                        continue;
                }

                rs = pstmt.executeQuery();

                ResultSetMetaData meta = rs.getMetaData();
                int columns = meta.getColumnCount();

                while (rs.next()) {
                    String row = "";
                    for (int i = 1; i <= columns; i++) {
                        row += rs.getString(i) + " | ";
                    }
                    out.println(row);
                }

                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



