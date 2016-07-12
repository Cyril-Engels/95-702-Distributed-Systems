import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * This class validates a UDP Client that sends only one request
 * to server and waits for reply, which is not reliable.
 *
 * @author Yilong Chang
 */
public class UDPClient {
    /**
     * This main method send a request to server,
     * and tries to get the location of the user input city.
     * @param args Command Line Args
     */
    public static void main(String args[]) {
        DatagramSocket aSocket = null;
        try {
            // Scan in the location that user wants to look up
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter city name and we will find its coordinates");
            String city = scanner.nextLine().trim();
            aSocket = new DatagramSocket();
            byte[] m = city.getBytes();
            // Set host and port
            InetAddress aHost = InetAddress.getByName("localhost");
            int serverPort = 6789;
            // Send request
            DatagramPacket request =
                    new DatagramPacket(m, city.length(), aHost, serverPort);
            aSocket.send(request);
            // Read server response
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);
            if (reply.getData()[0]!= 0) {
                System.out.println("Reply: " + new String(reply.getData()));
            } else {
                System.out.println("Could not resolve " + city);
            }
            aSocket.close();
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) aSocket.close();
        }
    }
}