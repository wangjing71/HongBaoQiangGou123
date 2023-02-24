package wj.safe;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

/**
 * 3DES���ܹ�����
 */
public class Des3 {
    public static final String KEY1 = "1111", KEY2 = "2222", KEY3 = "3333"; // 用于加密
    private final static String encSecretKey = "62FB13F3AF7D9BAD07B8D4C7EB8F6110B494F158B5D44DC63161B0A239AED46E4F6E8F0F8A3CD1E0086988FA1FDD73F714BF23FC17C5CE6F32B33A0AD3200728C0BEF540BBF07D29";
    private final static String secretKey = new DesUtil().strDec(encSecretKey, KEY1, KEY2, KEY3);
    private final static String iv = "01234567";
    private final static String encoding = "utf-8";


    /*
    * 加密
    * */
    public static String encode(String plainText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64.encode(encryptData);
    }


    /*
    *  解密
    * */
    public static String decode(String encryptText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
        return new String(decryptData, encoding);
    }
}