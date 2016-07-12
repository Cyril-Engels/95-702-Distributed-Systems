import java.net.*;
import java.io.*;
import java.util.Scanner;
/**
 * This class is the Spy that provides his ID and password to login,
 * and update his location stored on the commander side.
 *
 * @author Yilong Chang
 */
public class TCPSpyUsingTEAandPasswords {
    /**
     * This main method starts a client socket, connects with server,
     * take in Spy's info, write to and read from server.
     * @param args Command Line Args
     */
    public static void main(String args[]) {
        Socket s = null;
        try {
            // Host name and port
            String destination = "127.0.0.1";
            int serverPort = 7896;
            s = new Socket(destination, serverPort);
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            DataInputStream in = new DataInputStream(s.getInputStream());

            // Get the input secret key, and use it to encrypt
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter symmetric key for TEA (taking first sixteen bytes):");
            String secretKey = scanner.nextLine().trim();
            TEA tea = new TEA(secretKey.getBytes());
            // Get ID, encrypt it and write to server
            System.out.println("Enter your ID:");
            String userID = scanner.nextLine().trim();
            byte[] cryptID = tea.encrypt(userID.getBytes());
            out.write(cryptID);
//            out.flush();
            // Get password, encrypt it and write to server
            System.out.println("Enter your password:");
            String password = scanner.nextLine().trim();
            byte[] cryptPassword = tea.encrypt(password.getBytes());
            out.write(cryptPassword);
//            out.flush();
            // Get location, encrypt it and write to server
            System.out.println("Enter your location:");
            String location = scanner.nextLine().trim();
            byte[] cryptLocation= tea.encrypt(location.getBytes());
            out.write(cryptLocation);
//            out.flush();
            // Read and print result from server.
            byte[]  result = new byte[1000];
            in.read(result);
            System.out.println("Received: " + new String(result));
            in.close();
            out.close();
            s.close();
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            if (s != null) try {
                s.close();
            } catch (IOException e) {
                System.out.println("close:" + e.getMessage());
            }
        }
    }
}