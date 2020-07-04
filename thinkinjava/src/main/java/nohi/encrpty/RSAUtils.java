package nohi.encrpty;

import org.apache.commons.lang3.ArrayUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;



public class RSAUtils {
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static String RSA = "RSA";
    private final static Base64.Decoder DECODER_64 = Base64.getDecoder();
    private final static Base64.Encoder ENCODER_64 = Base64.getEncoder();


    public static KeyPair generateRSAKeyPair() {
        return generateRSAKeyPair(1024);
    }

    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encryptData(String data, PublicKey publicKey){
        return ENCODER_64.encodeToString(RSAUtils.encryptData(data.getBytes(), publicKey));
    }

    public static byte[] encryptData(byte[] data, PublicKey publicKey) {
        try {
            byte[] dataReturn = new byte[0];
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // 加密时超过117字节就报错。为此采用分段加密的办法来加密
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.length; i += 100) {
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i,
                        i + 100));
                sb.append(new String(doFinal));
                dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
            }
            return dataReturn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证数字签名函数入口
     *
     * @param plainBytes 待验签明文字节数组
     * @param signBytes  待验签签名后字节数组
     * @param publicKey  验签使用公钥
     * @return 验签是否通过
     * @throws Exception
     */
    public static boolean verifyDigitalSign(byte[] plainBytes, byte[] signBytes, PublicKey publicKey) throws Exception {
        boolean isValid = false;
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(publicKey);
            signature.update(plainBytes);
            isValid = signature.verify(signBytes);
            return isValid;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(String.format("验证数字签名时没有[%s]此类算法", SIGN_ALGORITHMS));
        } catch (InvalidKeyException e) {
            throw new Exception("验证数字签名时公钥无效");
        } catch (SignatureException e) {
            throw new Exception("验证数字签名时出现异常");
        }
    }

    public static String rsaSign(byte[] encryptByte, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(privateKey);
            signature.update(encryptByte);
            byte[] signed = signature.sign();
            return (new BASE64Encoder()).encodeBuffer(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptData(String content, PrivateKey privateKey) throws IOException {
        return new String(RSAUtils.decryptData((new BASE64Decoder()).decodeBuffer(content), privateKey));
    }

    public static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // 解密时超过128字节就报错。为此采用分段解密的办法来解密
            byte[] dataReturn = new byte[0];
            for (int i = 0; i < encryptedData.length; i += 256) {
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(encryptedData, i,
                        i + 256));
                dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
            }

            return dataReturn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static PublicKey getPublicKey(String modulus, String publicExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger bigIntModulus = new BigInteger(modulus);
        BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String modulus, String privateExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger bigIntModulus = new BigInteger(modulus);
        BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
        byte[] buffer = DECODER_64.decode(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        byte[] buffer = DECODER_64.decode(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey loadPublicKey(InputStream in) throws Exception {
        return loadPublicKey(readKey(in));
    }

    public static PrivateKey loadPrivateKey(InputStream in) throws Exception {
        return loadPrivateKey(readKey(in));
    }

    private static String readKey(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            if (readLine.charAt(0) == '-') {
                continue;
            } else {
                sb.append(readLine);
//                sb.append('\r');
            }
        }
        return sb.toString();
    }

    /**
     * 验签
     *
     * @param srcData   原始字符串
     * @param publicKey 公钥
     * @param sign      签名
     * @return 是否验签通过
     */
    public static boolean verify(String srcData, String sign, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initVerify(publicKey);
        signature.update(srcData.getBytes());
        return signature.verify(DECODER_64.decode(sign));
    }

    public static String sign(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        return ENCODER_64.encodeToString(signature.sign());
    }
}
