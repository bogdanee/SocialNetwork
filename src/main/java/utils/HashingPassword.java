package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class HashingPassword {
        public static String hash(String password) throws NoSuchAlgorithmException {

        //Creating the MessageDigest object
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        //Passing data to the created MessageDigest Object
        md.update(password.getBytes());

        //Compute the message digest
        byte[] digest = md.digest();

        //Converting the byte array in to HexString format
        StringBuilder hexString = new StringBuilder();

            for (byte b : digest) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
        return hexString.toString();
    }
}