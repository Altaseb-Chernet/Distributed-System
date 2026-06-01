package socketapp;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {

    private Socket socket;

    // Database connection is provided by DBConnection

    // Map of username -> writer for broadcasting / private messages
    private static final Map<String, PrintWriter> clients = new ConcurrentHashMap<>();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        String username = null;

        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);
            Connection conn = DBConnection.getConnection()
        ) {

            out.println("Connected to server.");
            out.println("Enter username:");

            // negotiate username
            while (true) {
                String candidate = in.readLine();
                if (candidate == null) {
                    return; // client closed connection
                }
                candidate = candidate.trim();
                if (candidate.isEmpty()) {
                    out.println("Username cannot be empty. Enter username:");
                    continue;
                }
                if (clients.containsKey(candidate)) {
                    out.println("Username already taken. Enter a different username:");
                    continue;
                }
                username = candidate;
                clients.put(username, out);
                break;
            }

            System.out.println("User connected: " + username + " (" + socket.getInetAddress() + ")");
            broadcast("Server", username + " joined the chat.");

            out.println("Options:");
            out.println("1 - Show all users (from DB)");
            out.println("@username message  - send private message");
            out.println("all message - broadcast to everyone");
            out.println("exit - Quit");

            String input;

            while ((input = in.readLine()) != null) {
                input = input.trim();
                if (input.isEmpty()) continue;

                if (input.equals("1")) {

                    String query = "SELECT * FROM users";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(query)) {

                        out.println("---- Users ----");

                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String name = rs.getString("name");
                            out.println(id + " - " + name);
                        }

                        out.println("----------------");
                    } catch (SQLException e) {
                        out.println("Error querying users: " + e.getMessage());
                    }

                } else if (input.equalsIgnoreCase("exit")) {
                    out.println("Disconnected.");
                    break;

                } else if (input.startsWith("@")) {
                    // private message: @user message
                    int space = input.indexOf(' ');
                    if (space == -1) {
                        out.println("Usage: @username message");
                        continue;
                    }
                    String target = input.substring(1, space);
                    String msg = input.substring(space + 1);
                    sendPrivate(username, target, msg, out);

                } else if (input.startsWith("all ")) {
                    String msg = input.substring(4);
                    broadcast(username, msg);

                } else {
                    out.println("Invalid option or unknown command.");
                }
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (username != null) {
                clients.remove(username);
                broadcast("Server", username + " left the chat.");
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void broadcast(String from, String message) {
        String text = from + ": " + message;
        for (Map.Entry<String, PrintWriter> e : clients.entrySet()) {
            try {
                e.getValue().println(text);
            } catch (Exception ex) {
                // ignore individual failures
            }
        }
    }

    private static void sendPrivate(String from, String to, String message, PrintWriter senderOut) {
        PrintWriter pw = clients.get(to);
        if (pw != null) {
            pw.println("(private) " + from + ": " + message);
            senderOut.println("(to " + to + ") " + message);
        } else {
            senderOut.println("User '" + to + "' not found or offline.");
        }
    }
}
