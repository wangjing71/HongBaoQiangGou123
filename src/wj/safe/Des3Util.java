package wj.safe;

import sun.security.krb5.internal.crypto.Des;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

public class Des3Util {
    /*
     *  加密
     * */
    public static String encode(String plainText) {
        try {
            return Des3.encode(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /*
     *  解密
     * */
    public static String decode(String encryptText) {
        try {
            return Des3.decode(encryptText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
