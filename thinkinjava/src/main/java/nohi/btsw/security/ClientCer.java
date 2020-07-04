package nohi.btsw.security;//package com.tencent.housing.mobiles.member.security;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.Cipher;
//import java.io.*;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.security.*;
//import java.security.cert.X509Certificate;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.*;
//import java.util.Base64;
//
//@Component
//public class ClientCer {
//
//    private Logger logger = LoggerFactory.getLogger(ClientCer.class);
//
//    private PrivateKey clientPrivateKey = null;
//    private PublicKey serverPublicKey = null;
//    private X509Certificate x509Certificate = null;
//    private String signAlgName;
//
////    @Value("${ca.setting.cer.file}")
//    private String pfxFile;
//
////    @Value("${ca.setting.cer.password}")
//    private String password ;
//
////    @Value("${ca.setting.server.publickey}")
//    private String serverPublicKeyStr;
//
//    public void setPfxFile(String pfxFile) {
//        this.pfxFile = pfxFile;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setServerPublicKeyStr(String serverPublicKeyStr) {
//        this.serverPublicKeyStr = serverPublicKeyStr;
//    }
//
//    /**
//     * RSA最大加密明文大小
//     */
//    private final int MAX_ENCRYPT_BLOCK = 117;
//
//    /**
//     * RSA最大解密密文大小
//     */
//    private final int MAX_DECRYPT_BLOCK = 128;
//
//    public enum Algorithm {
//        SHA1withRSA, SHA256withRSA;
//    }
//
//    public enum Padding {
//        RSA_PKCS1_PADDING("RSA/ECB/PKCS1Padding");
//        public final String value;
//
//        private Padding(String value) {
//            this.value = value;
//        }
//    }
//
//    private void init() {
//        if (clientPrivateKey != null && signAlgName != null && serverPublicKey != null) {
//            return;
//        }
//        try {
//            byte[] p12Cert = readFile(pfxFile).toByteArray();
////            byte[] p12Cert = base64Decode(pfxFile);
//            readPfxCert(p12Cert, password);
//            serverPublicKey = getPublicKeyFromStr(serverPublicKeyStr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String getSignAlgName() {
//        return signAlgName;
//    }
//
//    /**
//     * readPfxCert
//     *
//     * @param p12Cert
//     * @param pfxPassword
//     * @throws Exception
//     */
//    private void readPfxCert(byte[] p12Cert, String pfxPassword) throws Exception {
//        String alias = "";
//        int certNum ;
//        ByteArrayInputStream inStream = new ByteArrayInputStream(p12Cert);
//
//        KeyStore keyStore = KeyStore.getInstance("PKCS12");
//        keyStore.load(inStream, pfxPassword.toCharArray());
//
//        Enumeration<String> EnumTemp = keyStore.aliases();
//        for (certNum = 0; EnumTemp.hasMoreElements(); certNum++) {
//            alias = EnumTemp.nextElement().toString();
//
////            logger.info("alias:" + alias);
//        }
////        logger.info("certnum:" + certnum);
//
//        clientPrivateKey = ((PrivateKey) keyStore.getKey(alias, pfxPassword.toCharArray()));
//        x509Certificate = (X509Certificate) keyStore.getCertificate(alias);
////		publicKey = x509Certificate.getPublicKey();
//        signAlgName = x509Certificate.getSigAlgName();
////		logger("publicKey:" + base64Encoder.encode(publicKey.getEncoded()));
//        logger.info("signAlgName:" + signAlgName);
//    }
//
//    /**
//     * readFile
//     *
//     * @param filename
//     * @return
//     * @throws Exception
//     */
//    private ByteArrayOutputStream readFile(String filename) throws Exception {
//        InputStream fileInStream = ClientCer.class.getResourceAsStream(filename);
//        ByteArrayOutputStream fileByteStream = new ByteArrayOutputStream();
//        int i = 0;
//        while ((i = fileInStream.read()) != -1) {
//            fileByteStream.write(i);
//        }
//        fileInStream.close();
//        return fileByteStream;
//    }
//
//    /**
//     * Signature
//     *
//     * @param plainText
//     * @return
//     * @throws Exception
//     */
//    private byte[] sign(byte[] plainText, String signatureAlgorithms){
//
//        byte[] result = "".getBytes();
//        try {
//            Signature sInstance = Signature.getInstance(signatureAlgorithms);
//            sInstance.initSign(clientPrivateKey);
//            sInstance.update(plainText);
//            result = sInstance.sign();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (SignatureException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        }
//        return  result;
//    }
//
//    /**
//     * @param plainText
//     * @return
//     * @throws Exception
//     */
//    public byte[] sign(byte[] plainText){
//        init();
//        return sign(plainText, signAlgName);
//    }
//
//    /**
//     * Verify
//     *
//     * @param publicKey
//     * @param strAlg
//     * @param plainText
//     * @param signedData
//     * @return
//     */
//    private boolean verify(PublicKey publicKey, String strAlg, byte[] plainText, byte[] signedData) {
//        try {
//
//            Signature vInstance = Signature.getInstance(strAlg);
//            vInstance.initVerify(publicKey);
//            vInstance.update(plainText);
//            return vInstance.verify(signedData);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    /**
//     * 验证client请求签名
//     *
//     * @param plainText
//     * @param signedData
//     * @return
//     */
//    public boolean verify(byte[] plainText, byte[] signedData) {
//        init();
//        return verify(serverPublicKey, signAlgName, plainText, signedData);
//    }
//
//
//    /**
//     * encrypt
//     *
//     * @param plainText
//     * @param key
//     * @return
//     * @throws Exception
//     * @throws NoSuchAlgorithmException
//     */
//    private byte[] encrypt(byte[] plainText, Key key){
//        ByteArrayOutputStream out = null;
//        byte[] encryptedData = null;
//        try {
//
//            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
//            cipher.init(Cipher.ENCRYPT_MODE, key);
//
//            int inputLen = plainText.length;
//            out = new ByteArrayOutputStream();
//            int offSet = 0;
//            byte[] cache;
//            int i = 0;
//            // 对数据分段加密
//            int MAX_ENCRYPT_BLOCK = ((RSAPublicKey)key).getModulus().bitLength()/8 - 11;
//            while (inputLen - offSet > 0) {
//                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
//                    cache = cipher.doFinal(plainText, offSet, MAX_ENCRYPT_BLOCK);
//                } else {
//                    cache = cipher.doFinal(plainText, offSet, inputLen - offSet);
//                }
//                out.write(cache, 0, cache.length);
//                i++;
//                offSet = i * MAX_ENCRYPT_BLOCK;
//            }
//             encryptedData = out.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//            if(out != null){
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return encryptedData;
//    }
//
//    /**
//     * 使用clientPublicKey对数据进行加密
//     *
//     * @param plainText
//     * @return
//     * @throws Exception
//     */
//    public byte[] encrypt(byte[] plainText){
//        init();
//        return encrypt(plainText, serverPublicKey);
//    }
//
//    private byte[] decrypt(byte[] cipherText, Key key){
//
//        ByteArrayOutputStream out = null;
//        byte[] decryptedData = null;
//        try {
//            if (clientPrivateKey == null) {
//                init();
//            }
//            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
//            cipher.init(Cipher.DECRYPT_MODE, key);
//
//            int inputLen = cipherText.length;
//            out = new ByteArrayOutputStream();
//            int offSet = 0;
//            byte[] cache;
//            int i = 0;
//            // 对数据分段解密
//            int MAX_DECRYPT_BLOCK = ((RSAPrivateKey)key).getModulus().bitLength() / 8;
//            while (inputLen - offSet > 0) {
//                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
//                    cache = cipher.doFinal(cipherText, offSet, MAX_DECRYPT_BLOCK);
//                } else {
//                    cache = cipher.doFinal(cipherText, offSet, inputLen - offSet);
//                }
//                out.write(cache, 0, cache.length);
//                i++;
//                offSet = i * MAX_DECRYPT_BLOCK;
//            }
//            decryptedData = out.toByteArray();
//
//            return decryptedData;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (out != null){
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return decryptedData;
//    }
//
//    /**
//     * 使用服务器私钥进行解密
//     *
//     * @param plainText
//     * @return
//     * @throws Exception
//     */
//    public byte[] decrypt(byte[] plainText) {
//        init();
//        return decrypt(plainText, clientPrivateKey);
//    }
//
//    private PublicKey getPublicKeyFromStr(String publicKeyStr) {
//        try {
//			byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
////            byte[] buffer = java.util.Base64.decode(publicKeyStr);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
//            return keyFactory.generatePublic(keySpec);
//        } catch (NoSuchAlgorithmException e) {
//            logger.error("无此算法");
//            e.printStackTrace();
//        } catch (InvalidKeySpecException e) {
//            logger.error("公钥非法");
//            e.printStackTrace();
//        } catch (NullPointerException e) {
//            logger.error("公钥数据为空");
//            e.printStackTrace();
////		} catch (IOException e) {
//        } catch (Exception e) {
//            logger.error("公钥字符串无法解析");
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public byte[] base64Decode(String text)  {
//        return java.util.Base64.decode(text);
//    }
//
//    public String base64Encode(byte[] text) {
//        return Base64.encode(text);
//    }
//
//    /**
//     * @param oriMap
//     * @return
//     */
//
//    public String getMapContentStr(Map<String, String> oriMap) {
//        if (oriMap == null || oriMap.isEmpty()) {
//            return null;
//        }
//        Map<String, String> sortedMap = new TreeMap<String, String>(new Comparator<String>() {
//            public int compare(String key1, String key2) {
//                return key1.compareTo(key2);
//            }
//        });
//
//        Set<String> keySet = oriMap.keySet();
//        Iterator<String> iter = keySet.iterator();
//        while (iter.hasNext()) {
//            String key = iter.next();
//            sortedMap.put(key, oriMap.get(key));
//        }
//
//        String params = "";
//        for (Map.Entry<String, String> entity : sortedMap.entrySet()) {
//            params = params + entity.getKey() + "=" + entity.getValue() + "&";
//        }
//        params = params.substring(0, params.lastIndexOf('&'));
////		System.out.println(params);
//        return params;
//    }
//
//
//    /**
//     * URLCode 解码
//     *
//     * @param encrpyText
//     * @return
//     */
//    public String urlDecode(String encrpyText){
//
//        if (encrpyText.contains("%")) {
//            try {
//                encrpyText = URLDecoder.decode(encrpyText, "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        } else if (encrpyText.contains(" ")) {
//            encrpyText = encrpyText.replaceAll(" ", "+");
//        }
//        return encrpyText;
//    }
//
//    public String urlEncode(String plainText)  {
//        try {
//            plainText = URLEncoder.encode(plainText, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return plainText;
//    }
//
//    public String sha1(String src){
//        MessageDigest messageDigest = null;
//        try {
//            messageDigest = MessageDigest.getInstance("SHA");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        messageDigest.update(src.getBytes());
//      return bytesToHexString(messageDigest.digest());
//    }
//
//    /**
//     * 字节转16进制字符串
//     * @param src
//     * @return
//     */
//    private  String bytesToHexString(byte[] src){
//        StringBuilder stringBuilder = new StringBuilder("");
//        if (src == null || src.length <= 0) {
//            return null;
//        }
//        for (int i = 0; i < src.length; i++) {
//            int v = src[i] & 0xFF;
//            String hv = Integer.toHexString(v);
//            if (hv.length() < 2) {
//                stringBuilder.append(0);
//            }
//            stringBuilder.append(hv);
//        }
//        return stringBuilder.toString();
//    }
//
//
//
//
//}
