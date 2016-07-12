import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Created by changyilong on 10/4/15.
 */

import java.security.SecureRandom;

public final class Main {
    private static SecureRandom random = new SecureRandom();

    public static String nextSessionId() {

        return new BigInteger(128, random).toString(32);
    }
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
    private static String hashValueHexadecimal(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
    public static void main(String[] args) {
//        System.out.println(nextSessionId());
        System.out.println(hashValueHexadecimal(computeHash("36pqct7tb64cavujkocqp6mtoijames", "MD5")));

    }

}