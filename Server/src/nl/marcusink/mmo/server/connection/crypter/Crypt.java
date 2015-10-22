package nl.marcusink.mmo.server.connection.crypter;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Crypt {

    private static final String key = "EquilibriumSecnd";
    private static Cipher cipher;
    private static SecretKeySpec aesKey;

    public static String encrypt(String plainText) throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            BadPaddingException,
            IllegalBlockSizeException,
            InvalidKeyException,
            UnsupportedEncodingException {

        Cipher cipher = getCipher();
        Key aesKey = getAESKey();

        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] utf8 = plainText.getBytes("UTF8");
        byte[] encryptedData = cipher.doFinal(utf8);
        return Base64.encode(encryptedData);
    }

    public static String decrypt(String encryptedText) throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException,
            Base64DecodingException,
            UnsupportedEncodingException {

        Cipher cipher = getCipher();
        Key aesKey = getAESKey();

        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        byte[] decodedData = Base64.decode(encryptedText);
        byte[] utf8 = cipher.doFinal(decodedData);
        return new String(utf8, "UTF8");
    }

    private static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        if(cipher == null) {
            cipher = Cipher.getInstance("AES");
        }
        return cipher;
    }

    private static Key getAESKey(){
        if (aesKey == null){
            aesKey = new SecretKeySpec(key.getBytes(), "AES");
        }
        return aesKey;
    }
}