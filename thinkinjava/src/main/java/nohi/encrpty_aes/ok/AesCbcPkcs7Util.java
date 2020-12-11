package nohi.encrpty_aes.ok;


import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 公众号Aes加解密工具类
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-11-03 14:03
 **/
public class AesCbcPkcs7Util {

    private AesCbcPkcs7Util() {

    }

    private static final Logger logger = LoggerFactory.getLogger(AesCbcPkcs7Util.class);

    private static BouncyCastleProvider bouncyCastleProvider = null;

    public static final String AES_KEY = "ea591e457ac2445c";

    public static final String AES_IV = "c7f37834fe874230";

    /**
     * AES解密
     *
     * @param content 待解密的内容
     * @param secret  秘钥
     * @return 解密后的内容
     */
    public static String aesDecode(String content, String secret, String iv) {
        String result = "";
        try {
            Security.addProvider(getBouncyCastleProvider());
            byte[] keyBytes = Arrays.copyOf(secret.getBytes("ASCII"), 16);
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
            params.init(new IvParameterSpec(iv.getBytes()));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, key, params);
            byte[] cleartext = Hex.decodeHex(content.toCharArray());
            byte[] ciphertextBytes = cipher.doFinal(cleartext);
            result = new String(ciphertextBytes, "UTF-8");
        } catch (Exception e) {
            logger.error("AES解密出错", e);
        }
        return result;
    }

    /**
     * AES加密
     *
     * @param content 待加密的内容
     * @param secret  秘钥
     * @return 加密后的内容
     */
    public static String aesEncode(String content, String secret, String iv) {
        String result = "";
        try {
            Security.addProvider(getBouncyCastleProvider());
            byte[] keyBytes = Arrays.copyOf(secret.getBytes("ASCII"), 16);
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
            params.init(new IvParameterSpec(iv.getBytes()));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, params);
            byte[] cleartext = content.getBytes("UTF-8");
            byte[] ciphertextBytes = cipher.doFinal(cleartext);
            result = new String(Hex.encodeHex(ciphertextBytes));
        } catch (Exception e) {
            logger.error("AES加密出错", e);
        }
        return result;
    }

    private static BouncyCastleProvider getBouncyCastleProvider() {
        if (null == bouncyCastleProvider) {
            bouncyCastleProvider = new BouncyCastleProvider();
        }
        return bouncyCastleProvider;
    }

}

