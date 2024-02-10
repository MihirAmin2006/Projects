import java.io.*;
import java.net.*;

public class chatAppClient {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java ChatClient <username> <server_ip>");
            return;
        }

        String username = args[0];
        String serverIP = args[1];
        final int PORT = 12345;

        Socket socket = new Socket(serverIP, PORT);
        System.out.println("Connected to the chat server.");

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        Thread receiverThread = new Thread(() -> {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        receiverThread.start();

        System.out.println("Welcome to the chat, " + username + "!");
        System.out.println("Type your messages below. Enter '/quit' to exit.");

        String input;
        while (true) {
            input = userInput.readLine();
            if (input.equalsIgnoreCase("/quit")) {
                break;
            }
            writer.println(username + ": " + input);
        }

        socket.close();
    }
}
