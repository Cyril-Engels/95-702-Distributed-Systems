import java.math.BigInteger;
import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * This class is the Spy Commander that validates
 * spies' id and password, and update their locations after their login.
 *
 * @author Yilong Chang
 */
public class TCPSpyCommanderUsingTEAandPasswords {
    // Desktop Path
    public static final String filePath = System.getProperty("user.home") + "/Desktop/";
    public static final String fileName = "SecretAgents.kml";
    /* Store the key value pairs of id and hash of salt + password. */
    public static Map<String, String> idHashOfSltPlusPassword;
    /* Store the key value pairs of id and salt. */
    public static Map<String, String> idSalt;
    /* Store the key value pairs of id and location of spy. */
    public static Map<String, String> spiesLocation;
    /* Store the key value pairs of id and name of spy. */
    public static Map<String, String> idName;
    public static TEA tea;

    /* To produce random String as salt
     * Reference: http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
     */
    private static SecureRandom random = new SecureRandom();

    /* To check if ASCII
     * Reference: http://stackoverflow.com/questions/3585053/in-java-is-it-possible-to-check-if-a-string-is-only-ascii
     */
    private static CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();

    /**
     * This main method starts a Server socket to listen on clients' connection requests,
     * and starts a new Connection thread to connect with a client.
     *
     * @param args Command Line Args
     */
    public static void main(String args[]) {
        // Put ID and Name
        storeIdAndName();
        // Put ID and salt
        storeIdAndSalt();
        //Put initial locations
        storeInitialLocation();
        // Put the id and Hash
        storeIdAndPassword();
        // Store initial locations of HBH
        writeFile();

        try {
            // Take the symmetric key
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter symmetric key for TEA (taking first sixteen bytes):");
            String secretKey = sc.nextLine().trim();
            System.out.println(" Waiting for spies to visit...");
            tea = new TEA(secretKey.getBytes());
            int serverPort = 7896; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {
                // Listen
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
                c.tea = tea;
                c.start();
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }

    /**
     * This method creates some salt.
     *
     * @return random String
     */
    public static String createSalt() {
        return new BigInteger(128, random).toString(32);
    }

    /**
     * This method stores key value pair of Spy ID and Name.
     */
    private static void storeIdAndName() {
        idName = new HashMap<>();
        idName.put("jamesb", "James");
        idName.put("joem", "Joe");
        idName.put("mikem", "Mike");
    }

    /**
     * This method stores key value pair of Spy ID and Salt.
     */
    private static void storeIdAndSalt() {
        idSalt = new HashMap<>();
        idSalt.put("jamesb", "36pqct7tb64cavujkocqp6mtoi");
        idSalt.put("joem", "10dq9bd5k4orhtr37pk37jci0v");
        idSalt.put("mikem", "7c3orstki8r6s8ai1uq8o5nh62");
    }

    public static void storeIdAndPassword() {
        idHashOfSltPlusPassword = new HashMap<>();
        idHashOfSltPlusPassword.put("jamesb", "1fb9956b4d010f3a0b1a6afdf15eeb87");
        idHashOfSltPlusPassword.put("joem", "6fa95e461e9328017d16c8a9294b6755");
        idHashOfSltPlusPassword.put("mikem", "d74563219a1511536e57f961e532ec93");
    }

    /**
     * This method stores key value pair of Spy ID and initial locations.
     */
    private static void storeInitialLocation() {
        spiesLocation = new HashMap<>();
        spiesLocation.put("seanb", "-79.945389,40.444216,0.00000");
        spiesLocation.put("jamesb", "-79.945389,40.444216,0.00000");
        spiesLocation.put("joem", "-79.945389,40.444216,0.00000");
        spiesLocation.put("mikem", "-79.945389,40.444216,0.00000");
    }

    /**
     * This method checks if a String is pure ASCII.
     *
     * @param text input String
     * @return if ASCII
     */
    public static boolean isAscii(String text) {
        return asciiEncoder.canEncode(text);
    }

    /**
     * This method computes the hash digest of a given String.
     *
     * @param text   input String
     * @param method Hash method
     * @return hash digest
     */
    public static byte[] computeHash(String text, String method) {
        byte[] digest = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(method);
            messageDigest.update(text.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digest;
    }

    /**
     * This method transforms byte array of hash value to Hexadecimal representation.
     *
     * @param b input byte array of hash digest
     * @return Hexadecimal representation of the input byte array
     */
    public static String hashValueHexadecimal(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    /**
     * This method writes and rewrites to the .kml new location
     * every time a spy updates his location successfully.
     */
    public static void writeFile() {
        String content = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<kml xmlns=\"http://earth.google.com/kml/2.2\"\n" +
                "><Document>\n" +
                "<Style id=\"style1\">\n" +
                "<IconStyle>\n" +
                "<Icon>\n" +
                "<href>http://maps.gstatic.com/intl/en_ALL/mapfiles/ms/micons/blue- dot.png</href>\n" +
                "</Icon> </IconStyle> </Style> <Placemark>\n" +
                "<name>seanb</name>\n" +
                "<description>Spy Commander</description> <styleUrl>#style1</styleUrl>\n" +
                "<Point>\n" +
                "<coordinates>-79.945289,40.44431,0.00000</coordinates> </Point>\n" +
                "</Placemark> <Placemark>\n" +
                "<name>jamesb</name> <description>Spy</description> <styleUrl>#style1</styleUrl> <Point>\n" +
                "<coordinates>%s</coordinates> </Point>\n" +
                "</Placemark>\n" +
                "<Placemark> <name>joem</name> <description>Spy</description> <styleUrl>#style1</styleUrl> <Point>\n" +
                "<coordinates>%s</coordinates> </Point>\n" +
                "</Placemark>\n" +
                "<Placemark> <name>mikem</name> <description>Spy</description> <styleUrl>#style1</styleUrl> <Point>\n" +
                "<coordinates>%s</coordinates> </Point>\n" +
                "</Placemark>\n" +
                "</Document>\n" +
                "</kml>\n", spiesLocation.get("jamesb"), spiesLocation.get("joem"), spiesLocation.get("mikem"));
        try {
            // If file not exists, create one
            File file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            // Write file
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.write(content);

            bufferWriter.flush();
            fileWriter.flush();
            bufferWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    TEA tea;

    /**
     * Constructor.
     *
     * @param aClientSocket client socket
     */
    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
//            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    /**
     * Thread's business logic.
     */
    public void run() {
        try {
            // Read ID


            byte[] userID;
            userID = new byte[1000];
            int length = in.read(userID);
            userID = Arrays.copyOf(userID, length);
            /* Use the right secret key to decrypt the ID
             * If the decrypt code is not ASCII the secret key received must be illegal.
             * Close the socket immediately.
             * If the decrypt code is ASCII(very unlikely correct secret key, still possible),
             * continue to check if the ID in the map and the correctness of password.
             * */
            byte[] decryptIDBytes = tea.decrypt(userID);
            String decryptID = new String(decryptIDBytes);
            System.out.println(decryptID);
            if (!TCPSpyCommanderUsingTEAandPasswords.isAscii(decryptID)) {
                clientSocket.close();
                System.out.printf("Got visit" + " illegal symmetric key used");
                return;
            }
            // Read and decrypt the password
            byte[] password = new byte[1000];
            length = in.read(password);
            password = Arrays.copyOf(password, length);
            byte[] decryptPasswordBytes = tea.decrypt(password);
            String decryptPassword = new String(decryptPasswordBytes);
            // Add salt
            String salt = TCPSpyCommanderUsingTEAandPasswords.idSalt.get(decryptID);
            String saltPlusPassword = salt + decryptPassword;
            // Compute hash
            String computedPassword = TCPSpyCommanderUsingTEAandPasswords.hashValueHexadecimal(TCPSpyCommanderUsingTEAandPasswords.computeHash(saltPlusPassword, "MD5"));

            // Read location.
            byte[] location = new byte[1000];
            length = in.read(location);
            location = Arrays.copyOf(location, length);
            byte[] decryptLocationBytes = tea.decrypt(location);
            String decryptLocation = new String(decryptLocationBytes);

            // Check ID and write back response if not correct
            if (!TCPSpyCommanderUsingTEAandPasswords.idHashOfSltPlusPassword.containsKey(decryptID)) {
                System.out.println(String.format("Got visit from %s. Illegal ID attempt.", TCPSpyCommanderUsingTEAandPasswords.idName.get(decryptID)));
                out.write("Not a valid user-id or password.".getBytes());
                clientSocket.close();
                return;
            }

            // Check password and write back response if not correct
            if (!TCPSpyCommanderUsingTEAandPasswords.idHashOfSltPlusPassword.get(decryptID).equals(computedPassword)) {
                System.out.println(String.format("Got visit from %s. Illegal Password attempt.", TCPSpyCommanderUsingTEAandPasswords.idName.get(decryptID)));
                out.write("Not a valid user-id or password.".getBytes());
                clientSocket.close();
                return;
            }
            // If all correct, save new location, write back response and rewrite file
            TCPSpyCommanderUsingTEAandPasswords.spiesLocation.put(decryptID, decryptLocation);
            System.out.println(String.format("Got visit from %s.", TCPSpyCommanderUsingTEAandPasswords.idName.get(decryptID)));
            out.write("Thank you. Your location was securely transmitted to Intelligence Headquarters.".getBytes());
            clientSocket.close();
            TCPSpyCommanderUsingTEAandPasswords.writeFile();
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {/*close failed*/
                e.printStackTrace();
            }
        }
    }
}
