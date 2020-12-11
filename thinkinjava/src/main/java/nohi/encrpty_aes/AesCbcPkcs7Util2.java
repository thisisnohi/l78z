package nohi.encrpty_aes;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jetbrains.annotations.NotNull;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-11-03 14:19
 **/
public class AesCbcPkcs7Util2 {
    private static final String CHARSET_NAME = "ASCII";
    private static final String AES_NAME = "AES";
    public static final String ALGORITHM = "AES/CBC/PKCS7Padding";
    public static final String AES_IV = "c7f37834fe874230";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 加密
     *
     * @param content
     * @param key
     * @return
     */
    public static String encrypt(@NotNull String content, @NotNull String key) throws NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(CHARSET_NAME), AES_NAME);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(new byte[16]);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
            result = cipher.doFinal(content.getBytes(CHARSET_NAME));
        } catch (Exception ex) {
            throw ex;
        }
        return Base64.encodeBase64String(result);
    }

    /**
     * 解密
     *
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(@NotNull String content, @NotNull String key) throws NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException, InvalidParameterSpecException {
        try {
//            byte[] keyBytes = Arrays.copyOf(secret.getBytes("ASCII"), 16);
//            SecretKey key = new SecretKeySpec(keyBytes, "AES");
//            AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
//            params.init(new IvParameterSpec(iv.getBytes()));
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
//            cipher.init(Cipher.DECRYPT_MODE, key, params);
//            byte[] cleartext = Hex.decodeHex(content.toCharArray());
//            byte[] ciphertextBytes = cipher.doFinal(cleartext);
//            result = new String(ciphertextBytes, "UTF-8");
//
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(CHARSET_NAME), AES_NAME);
            AlgorithmParameters params = AlgorithmParameters.getInstance(AES_NAME);
            params.init(new IvParameterSpec(AES_IV.getBytes()));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, params);
            return new String(cipher.doFinal(Base64.decodeBase64(content)), CHARSET_NAME);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
