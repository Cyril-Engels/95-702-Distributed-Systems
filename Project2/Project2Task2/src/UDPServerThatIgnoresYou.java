import java.net.*;
import java.io.*;
import java.util.Random;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class validates a UDP Server that ignore 90% of incoming requests.
 *
 * @author Yilong Chang
 */
public class UDPServerThatIgnoresYou {
    /**
     * This main method creates a UDO server socket and listen on requests.
     *
     * @param args Command Line Args
     */
    public static void main(String args[]) {
        DatagramSocket aSocket = null;
        try {
            // Store city and location in the map
            Map<String, String> cityLocationPairs = new TreeMap<>();
            cityLocationPairs.put("New York,NY", "40.730610, -73.935242");
            cityLocationPairs.put("Pittsburgh,PA", "40.440625,-79.995886");
            cityLocationPairs.put("Seattle,WA", "47.6097, -122.3331");
            cityLocationPairs.put("Washington,DC", "38.89500, -77.03639");
            aSocket = new DatagramSocket(6789);
            // create socket at agreed port
            while (true) {
                byte[] buffer = new byte[1000];
                Random rnd = new Random();
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                // Ignore 90% requests.
                if (rnd.nextInt(10) < 9) {
                    System.out.println("Got request " + new String(request.getData()) +
                            " but ignoring it.");
                    continue;
                } else { // Process requests
                    System.out.println("Got request " + new String(request.getData()));
                    System.out.println("And making a reply");
                }
                // Read city name
                String city = new String(request.getData()).trim();

                // Find its location
                String location = "";
                if (cityLocationPairs.keySet().contains(city)) {
                    location = cityLocationPairs.get(city);
                } else {
                    System.out.println("Was unable to handle request for " + city);
                }
                // Reply to the client
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