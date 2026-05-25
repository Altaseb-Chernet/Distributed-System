package chat.server;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles individual client connections in a separate thread.
 */
public class ClientHandler implements Runnable {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String EXIT_COMMAND = "exit";
    private static final String HELP_COMMAND = "help";

    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String clientAddress = clientSocket.getInetAddress().getHostAddress();
            logMessage("Handler started for client: " + clientAddress);

            sendWelcomeMessage(output);

            String userInput;
            while ((userInput = input.readLine()) != null) {
                userInput = userInput.trim();

                if (userInput.isEmpty()) {
                    continue;
                }

                logMessage("Received from " + clientAddress + ": " + userInput);

                if (userInput.equalsIgnoreCase(EXIT_COMMAND)) {
                    output.println("You have been disconnected. Goodbye!");
                    logMessage("Client " + clientAddress + " disconnected.");
                    break;
                } else if (userInput.equalsIgnoreCase(HELP_COMMAND)) {
                    sendHelpMessage(output);
                } else {
                    output.println("Echo: " + userInput);
                }
            }
        } catch (IOException e) {
            logError("Error handling client: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void sendWelcomeMessage(PrintWriter output) {
        output.println("========================================");
        output.println("Welcome to the Chat Server!");
        output.println("========================================");
        output.println("Commands:");
        output.println("  help - Show available commands");
        output.println("  exit - Disconnect from server");
        output.println("========================================\n");
    }

    private void sendHelpMessage(PrintWriter output) {
        output.println("\n--- Available Commands ---");
        output.println("help - Display this help message");
        output.println("exit - Disconnect from server");
        output.println("Any other input will be echoed back.\n");
    }

    private void closeConnection() {
        try {
            clientSocket.close();
            logMessage("Client socket closed.");
        } catch (IOException e) {
            logError("Error closing client socket: " + e.getMessage());
        }
    }

    private static void logMessage(String message) {
        System.out.println("[" + LocalDateTime.now().format(FORMATTER) + "] " + message);
    }

    private static void logError(String message) {
        System.err.println("[" + LocalDateTime.now().format(FORMATTER) + "] ERROR: " + message);
    }
}
