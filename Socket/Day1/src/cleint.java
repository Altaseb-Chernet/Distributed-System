import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class cleint {
    public static void main(String[] args) {
        try {
            Socket sc = new Socket("localhost", 4000);
            System.out.println("connected to server");
            // from keyboard
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            // from server
            BufferedReader in = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            // to output to server
            PrintWriter out = new PrintWriter(sc.getOutputStream(), true);

            String Message;
            while (true) {
                Message = br.readLine();

                if ("exit".equalsIgnoreCase(Message)) {
                    break;
                }

                out.println(Message);
                System.out.println("from server: " + in.readLine());
            }
            out.println(Message);
            System.out.println("in from server : " + in.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}