import java.net.*;
import java.io.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class validates a UDP Server.
 *
 * @author Yilong Chang
 */
public class UDPServer {
    /**
     * This main method starts a server socket,
     * and accepts clients' request to find a city's location.
     *
     * @param args Command Line Args
     */
    public static void main(String args[]) {
        DatagramSocket aSocket = null;
        try {
            // Store four location info on server side
            Map<String, String> cityLocationPairs = new TreeMap<>();
            cityLocationPairs.put("Pittsburgh,PA", "40.440625,-79.995886");
            cityLocationPairs.put("Washington,DC", "38.89500, -77.03639");
            cityLocationPairs.put("New York,NY", "40.730610, -73.935242");
            cityLocationPairs.put("Seattle,WA", "47.6097, -122.3331");
            aSocket = new DatagramSocket(6789);
            // create socket at agreed port
            byte[] buffer;
            while (true) {
                buffer = new byte[1000];
                // Receive request
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                String city = new String(request.getData()).trim();

                System.out.println("Handling request for " + city);
                String location = "";
                /* If name in treeMap, write the location back.
                 * Else write an empty String back. */
                if (cityLocationPairs.keySet().contains(city)) {
                    location = cityLocationPairs.get(city);
                } else {
                    System.out.println("Was unable to handle request for " + city);
                }
                DatagramPacket reply = new DatagramPacket(location.getBytes(), location.length(),
                        request.getAddress(), request.getPort());
                aSocket.send(reply);
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) aSocket.close();
        }
    }
}