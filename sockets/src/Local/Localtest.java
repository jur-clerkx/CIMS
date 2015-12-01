/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Local;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Jur
 */
public class Localtest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, InvalidKeyException, ClassNotFoundException {
        String wachtwoord = "hallo";
        System.out.println("Wachtwoord length: " + wachtwoord.length());
        System.out.println("Wachtwoord bytes: " + wachtwoord.getBytes().length);
        byte[] key = new byte[8];
        for(int i=0;  i < 8; i++) {
            if (wachtwoord.length() > i) {
                key[i] = wachtwoord.getBytes()[i];
            } else {
                key [i] = (byte) i;
            }
        }
        System.out.println("Generated key: " + Arrays.toString(key));
        System.out.println("\n");
        SecretKey key64 = new SecretKeySpec(key, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, key64 );
        
        //Create localtime object to test with
        LocalTime time = LocalTime.now();
        
        //Print normal stream
        System.out.println("Printing normal stream");
        ObjectOutputStream out = new ObjectOutputStream(System.out);
        out.writeObject(time);
        
        //Print cipher stream
        System.out.println("\n");
        System.out.println("Printing cipherstream");
        ObjectOutputStream cout = new ObjectOutputStream(new CipherOutputStream(System.out, cipher));
        cout.writeObject(time);
    }

}
