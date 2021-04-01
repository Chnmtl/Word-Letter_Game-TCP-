import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private static ArrayList<String> words = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("127.0.0.1", 3000);
        socket.setSoTimeout(15000);
        Scanner scanner = new Scanner(System.in);
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream, true);

        InputStream inputStream = socket.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(inputStream));

        System.out.println("Write a word and press enter to start the game! (You are Player 1.)");

        String receiveMessage, sendMessage;

        sendMessage = scanner.nextLine();
        words.add(sendMessage);
        printWriter.println(sendMessage);
        printWriter.flush();

        try {

            while (true) {
                receiveMessage = receiveRead.readLine();
                if (receiveMessage == null) {
                    System.out.println("Time is up. Player 2 won!");
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
        } catch (SocketTimeoutException exception){
            System.out.println("Time is up. You win.");
            socket.close();
        } catch (SocketException socketException){
            System.out.println("Time is up. Player 2 won!");
            socket.close();
        }
    }

    public static boolean isWordValid(String receiveMessage, String sendMessage) {
        String lastTwoLetters = receiveMessage.substring(receiveMessage.length() - 2);
        return lastTwoLetters.equals(sendMessage.substring(0, 2));
    }

}            