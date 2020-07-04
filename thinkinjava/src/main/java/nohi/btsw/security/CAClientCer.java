package nohi.btsw.security;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;


public class CAClientCer {


    private PrivateKey clientPrivateKey = null;
    private PublicKey serverPublicKey = null;
    private X509Certificate x509Certificate = null;
    private String signAlgName;

    private String pfxFile;
    private String password ;
    private String serverPublicKeyStr;


    public CAClientCer(String serverPublicKeyStr,String pfxFile,String password){
        super();
    	this.serverPublicKeyStr=serverPublicKeyStr;
    	this.pfxFile=pfxFile;
    	this.password=password;
    	init();
    }


    /**
     * RSA最大加密明文大小
     */
    private final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private final int MAX_DECRYPT_BLOCK = 128;

    public enum Algorithm {
        SHA1withRSA, SHA256withRSA;
    }

    public enum Padding {
        RSA_PKCS1_PADDING("RSA/ECB/PKCS1Padding");
        public final String value;

        private Padding(String value) {
            this.value = value;
        }
    }

    private void init() {
        if (clientPrivateKey != null && signAlgName != null && serverPublicKey != null) {
            return;
        }
        try {
            byte[] p12Cert = base64Decode(pfxFile);
            readPfxCert(p12Cert, password);
            serverPublicKey = getPublicKeyFromStr(serverPublicKeyStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getSignAlgName() {
        return signAlgName;
    }

    /**
     * readPfxCert
     *
     * @param p12Cert
     * @param pfxPassword
     * @throws Exception
     */
    private void readPfxCert(byte[] p12Cert, String pfxPassword) throws Exception {
        String alias = "";
        int certNum ;
        ByteArrayInputStream inStream = new ByteArrayInputStream(p12Cert);

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(inStream, pfxPassword.toCharArray());

        Enumeration<String> EnumTemp = keyStore.aliases();
        for (certNum = 0; EnumTemp.hasMoreElements(); certNum++) {
            alias = EnumTemp.nextElement().toString();
        }

        clientPrivateKey = ((PrivateKey) keyStore.getKey(alias, pfxPassword.toCharArray()));
        x509Certificate = (X509Certificate) keyStore.getCertificate(alias);
        signAlgName = x509Certificate.getSigAlgName();
        System.out.println("signAlgName:" + signAlgName);
    }


    /**
     * Signature
     *
     * @param plainText
     * @return
     * @throws Exception
     */
    private byte[] sign(byte[] plainText, String signatureAlgorithms){

        byte[] result = "".getBytes();
        try {
            Signature sInstance = Signature.getInstance(signatureAlgorithms);
            sInstance.initSign(clientPrivateKey);
            sInstance.update(plainText);
            result = sInstance.sign();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return  result;
    }

    /**
     * @param plainText
     * @return
     * @throws Exception
     */
    public byte[] sign(byte[] plainText){
        init();
        return sign(plainText, signAlgName);
    }

    /**
     * Verify
     *
     * @param publicKey
     * @param strAlg
     * @param plainText
     * @param signedData
     * @return
     */
    private boolean verify(PublicKey publicKey, String strAlg, byte[] plainText, byte[] signedData) {
        try {

            Signature vInstance = Signature.getInstance(strAlg);
            vInstance.initVerify(publicKey);
            vInstance.update(plainText);
            return vInstance.verify(signedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 验证client请求签名
     *
     * @param plainText
     * @param signedData
     * @return
     */
    public boolean verify(byte[] plainText, byte[] signedData) {
        init();
        return verify(serverPublicKey, signAlgName, plainText, signedData);
    }


    /**
     * encrypt
     *
     * @param plainText
     * @param key
     * @return
     * @throws Exception
     * @throws NoSuchAlgorithmException
     */
    private byte[] encrypt(byte[] plainText, Key key){
        ByteArrayOutputStream out = null;
        byte[] encryptedData = null;
        try {

            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);

            int inputLen = plainText.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(plainText, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(plainText, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
             encryptedData = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return encryptedData;
    }

    /**
     * 使用clientPublicKey对数据进行加密
     *
     * @param plainText
     * @return
     * @throws Exception
     */
    public byte[] encrypt(byte[] plainText){
        init();
        return encrypt(plainText, serverPublicKey);
    }

    private byte[] decrypt(byte[] cipherText, Key key){

        ByteArrayOutputStream out = null;
        byte[] decryptedData = null;
        try {
            if (clientPrivateKey == null) {
                init();
            }
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, key);

            int inputLen = cipherText.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(cipherText, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(cipherText, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            decryptedData = out.toByteArray();

            return decryptedData;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return decryptedData;
    }

    /**
     * 使用服务器私钥进行解密
     *
     * @param plainText
     * @return
     * @throws Exception
     */
    public byte[] decrypt(byte[] plainText) {
        init();
        return decrypt(plainText, clientPrivateKey);
    }

    private PublicKey getPublicKeyFromStr(String publicKeyStr) {
        try {
            byte[] buffer = Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {

            e.printStackTrace();
        } catch (NullPointerException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    public byte[] base64Decode(String text)  {
        return Base64.decode(text);
    }

    public String base64Encode(byte[] text) {
        return Base64.encode(text);
    }
}
