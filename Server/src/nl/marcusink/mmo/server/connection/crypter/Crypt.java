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

    private static final String key = "EquilibriumSecnd"; // 128 bit // 16 byte
    private static Cipher cipher;
    private static SecretKeySpec aesKey;

    public static String encrypt(String plainText) throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            BadPaddingException,
            IllegalBlockSizeException,
            InvalidKeyException,
            UnsupportedEncodingException {
        // encrypt the text
//        Cipher cipher = getCipher();
//        Key easKey = getAESKey();
//
//        cipher.init(Cipher.ENCRYPT_MODE, easKey);
//
//        byte[] plainTextBytes = plainText.getBytes();
//        byte[] encrypted = cipher.doFinal(plainTextBytes);
//
//        String encryptedText = new String(encrypted);
//        return encryptedText;

        getCipher().init(Cipher.ENCRYPT_MODE, getAESKey());
        byte[] utf8 = plainText.getBytes("UTF8");
        byte[] encryptedData = getCipher().doFinal(utf8);
        return Base64.encode(encryptedData);
    }

    public static String decrypt(String encryptedText) throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException,
            Base64DecodingException,
            UnsupportedEncodingException {
        // decrypt the text
//        Cipher cipher = getCipher();
//        Key easKey = getAESKey();
//
//        cipher.init(Cipher.DECRYPT_MODE, easKey);
//
//        byte[] encryptedTextBytes = encryptedText.getBytes();
//
//        byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
//
//        String decryptedText = new String(decryptedTextBytes);
//        return decryptedText;
        getCipher().init(Cipher.DECRYPT_MODE, getAESKey());
        byte[] decodedData = Base64.decode(encryptedText);//this.b64Decoder.decodeBuffer(aData);
        byte[] utf8 = getCipher().doFinal(decodedData);
        return new String(utf8, "UTF8");
    }

    private static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        if(cipher == null) {
            // Create cipher
            cipher = Cipher.getInstance("AES");
        }
        return cipher;
    }

    private static Key getAESKey(){
        if (aesKey == null){
            // Create key
            aesKey = new SecretKeySpec(key.getBytes(), "AES");
        }
        return aesKey;
    }
}