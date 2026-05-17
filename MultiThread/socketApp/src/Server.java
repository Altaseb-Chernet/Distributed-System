import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Multi-threaded Socket Server that accepts client connections and handles them
 * concurrently using separate threads. Each client is handled by a ClientHandler.
 */
public class Server {
    private static final int PORT = 1234;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Starts the server and listens for incoming client connections.
     * Each client connection is handled in a separate thread.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logMessage("Server started and listening on port " + PORT);
            logMessage("Waiting for client connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientAddress = clientSocket.getInetAddress().getHostAddress();
                logMessage("Client connected from: " + clientAddress);

                // Handle each client in a separate thread
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.setName("ClientHandler-" + clientAddress);
                clientThread.start();
            }
        } catch (IOException e) {
            logError("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Logs a message with timestamp.
     *
     * @param message the message to log
     */
    private static void logMessage(String message) {
        System.out.println("[" + LocalDateTime.now().format(FORMATTER) + "] " + message);
    }

    /**
     * Logs an error message with timestamp.
     *
     * @param message the error message to log
     */
    private static void logError(String message) {
        System.err.println("[" + LocalDateTime.now().format(FORMATTER) + "] ERROR: " + message);
    }
}