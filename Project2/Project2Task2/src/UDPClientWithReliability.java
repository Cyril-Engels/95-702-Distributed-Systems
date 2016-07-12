import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * This class validates a UDP Client that provides reliable service
 * by resending request once last request timeouts.
 *
 * @author Yilong Chang
 */
public class UDPClientWithReliability {
    /**
     * This main method gets user input city name,
     * and calls getLocation to get the coordinate
     * of the city from server.
     * @param args Command Line Args
     */
    public static void main(String args[]) {
        // Scan in city name user wants to look up
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter city name and we will find its coordinates");
        String city = scanner.nextLine().trim();
        // Get the location from server and print
        String location = getLocation(city);
        if (location.length() != 0) {
            System.out.println("Reply: " + location);
        } else {
            System.out.println("Could not resolve " + city);
        }
    }

    /**
     * This method makes requests to server and resend request after timeout.
     *
     * @param city city name
     * @return city location
     */
    private static String getLocation(String city) {
        DatagramSocket aSocket = null;
        String location = "";
        try {
            byte[] m = city.getBytes();
            // Host and port
            InetAddress aHost = InetAddress.getByName("localhost");
            int serverPort = 6789;
            // Request and reply
            DatagramPacket request =
                    new DatagramPacket(m, city.length(), aHost, serverPort);
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket = new DatagramSocket();
            // Set timeout to be 1 second
            aSocket.setSoTimeout(1000);
            boolean receiveResponse = false;
            /* Repeatedly send request while no reply. */
            do {
                aSocket.send(request);
                try {
                    aSocket.receive(reply);
                    if (reply.getData()[0] != 0) {
                        location = new String(reply.getData());
                    }
                    receiveResponse = true;
                } catch (InterruptedIOException e) { // Use the InterruptedIOException to judge if timeout
                    System.out.println("Time out. Retry");
                }
            } while (!receiveResponse);
            aSocket.close();
            return location;
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) aSocket.close();
        }
        return null;
    }
}