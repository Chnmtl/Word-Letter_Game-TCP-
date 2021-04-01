import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {

    private static ArrayList<String> words = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(3000);
        System.out.println("The game will start soon! (You are player 2)");
        Socket socket = serverSocket.accept();
        socket.setSoTimeout(15000);
        Scanner scanner = new Scanner(System.in);
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream, true);

        InputStream inputStream = socket.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(inputStream));

        String receiveMessage, sendMessage;
        try {
            while (true) {
                receiveMessage = receiveRead.readLine();
                if (receiveMessage == null) {
                    System.out.println("Time is up, Player 1 won.");
                    break;
                }
                words.add(receiveMessage);
                System.out.println(receiveMessage);
                while (true) {
                    sendMessage = scanner.nextLine();
                    if ((words.contains(sendMessage)) || !isWordValid(receiveMessage, sendMessage)) {
                        System.out.println("This word is not valid, enter a new one!");
                    } else {
                        words.add(sendMessage);
                        printWriter.println(sendMessage);
                        printWriter.flush();
                        break;
                    }
                }
            }
        } catch (SocketTimeoutException socketException) {
            System.out.println("Time is up. You win.");
            socket.close();
        } catch (SocketException socketException) {
            System.out.println("Time is up. Player 1 won!");
            socket.close();
        }
    }

    public static boolean isWordValid(String receiveMessage, String sendMessage) {
        String lastTwoLetters = receiveMessage.substring(receiveMessage.length() - 2);
        return lastTwoLetters.equals(sendMessage.substring(0, 2));
    }
}