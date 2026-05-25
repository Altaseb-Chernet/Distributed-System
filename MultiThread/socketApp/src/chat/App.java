package chat;

/**
 * Launcher for the chat application. Use argument "server" or "client".
 */
public class App {
    public static void main(String[] args) {
        if (args.length > 0) {
            String mode = args[0].trim().toLowerCase();
            switch (mode) {
                case "server":
                    chat.server.Server.main(new String[0]);
                    break;
                case "client":
                    chat.client.Client.main(new String[0]);
                    break;
                default:
                    printUsage();
            }
        } else {
            printUsage();
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -cp classes chat.App <server|client>");
        System.out.println("Example to start server: java -cp classes chat.App server");
        System.out.println("Example to start client: java -cp classes chat.App client");
    }
}
