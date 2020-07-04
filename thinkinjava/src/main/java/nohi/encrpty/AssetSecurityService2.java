package nohi.encrpty;

import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * 资产安全控制服务
 * 复制消息加解密、资产监控等
 * @author enbrau
 */
@Getter
public class AssetSecurityService2 {

//    private final static Base64.Decoder DECODER_64 = Base64.getDecoder();
    private final static BASE64Decoder DECODER_64 = new BASE64Decoder();
//    private final static Base64.Encoder ENCODER_64 = Base64.getEncoder();
    private final static BASE64Encoder ENCODER_64 = new BASE64Encoder();

    private KeyPairStore keyPairStore;

    public AssetSecurityService2(KeyPairStore keyPairStore) {
        this.keyPairStore = keyPairStore;
    }

    public static KeyPairStore generateKeyPair() throws IOException {
        // 生成密钥对
        RSAKeyPairGenerator rsaKeyPairGenerator = new RSAKeyPairGenerator();
        RSAKeyGenerationParameters rsaKeyGenerationParameters = new RSAKeyGenerationParameters(BigInteger.valueOf(3), new SecureRandom(), 1024, 25);
        // 初始化参数
        rsaKeyPairGenerator.init(rsaKeyGenerationParameters);
        AsymmetricCipherKeyPair keyPair = rsaKeyPairGenerator.generateKeyPair();
        // 公钥
        AsymmetricKeyParameter publicKey = keyPair.getPublic();
        // 私钥
        AsymmetricKeyParameter privateKey = keyPair.getPrivate();

        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(publicKey);
        PrivateKeyInfo privateKeyInfo = PrivateKeyInfoFactory.createPrivateKeyInfo(privateKey);

        // 变字符串
        ASN1Object asn1ObjectPublic = subjectPublicKeyInfo.toASN1Primitive();
        byte[] publicInfoByte = asn1ObjectPublic.getEncoded();
        ASN1Object asn1ObjectPrivate = privateKeyInfo.toASN1Primitive();
        byte[] privateInfoByte = asn1ObjectPrivate.getEncoded();

        KeyPairStore keyPairStore = new KeyPairStore();
//        keyPairStore.setPrivateKey(ENCODER_64.encodeToString(privateInfoByte));
        keyPairStore.setPrivateKey(ENCODER_64.encode(privateInfoByte));
//        keyPairStore.setPublicKey(ENCODER_64.encodeToString(publicInfoByte));
        keyPairStore.setPublicKey(ENCODER_64.encode(publicInfoByte));
        return keyPairStore;
    }

    public String encrypt(String content) throws IOException, InvalidCipherTextException {
        return this.encrypt(content, this.keyPairStore.getPublicKey());
    }

    public String encrypt(String content, String publicKeyStr) throws IOException, InvalidCipherTextException {
        AsymmetricBlockCipher cipher = new RSAEngine();
//        byte[] punlicKeyBytes = DECODER_64.decode(publicKeyStr);
        byte[] punlicKeyBytes = DECODER_64.decodeBuffer(publicKeyStr);
        byte[] encryptDataBytes = null;
        try {
            AsymmetricKeyParameter publicKey = PublicKeyFactory.createKey(punlicKeyBytes);
            cipher.init(true, publicKey);
            encryptDataBytes = cipher.processBlock(
                    content.getBytes(StandardCharsets.UTF_8), 0, content.getBytes(StandardCharsets.UTF_8).length
            );
        } catch (InvalidCipherTextException | IOException e) {
//            throw new ServiceRuntimeException(DevOpsError.XSS000020002, e);
            e.printStackTrace();
            throw e;
        }

//        return ENCODER_64.encodeToString(encryptDataBytes);
        return ENCODER_64.encode(encryptDataBytes);
    }

    public String encryptByPrivateKey(String content) throws IOException, InvalidCipherTextException {
        return encryptByPrivateKey(content, this.getKeyPairStore().getPrivateKey());
    }

    public String encryptByPrivateKey(String content, String privateKeyStr) throws IOException, InvalidCipherTextException {
        AsymmetricBlockCipher cipher = new RSAEngine();
//        byte[] privateKeyBytes = DECODER_64.decode(privateKeyStr);
        byte[] privateKeyBytes = DECODER_64.decodeBuffer(privateKeyStr);
        byte[] encryptDataBytes;
        try {
            AsymmetricKeyParameter privateKey = PrivateKeyFactory.createKey(privateKeyBytes);
            cipher.init(true, privateKey);
            encryptDataBytes = cipher.processBlock(
                    content.getBytes(StandardCharsets.UTF_8), 0, content.getBytes(StandardCharsets.UTF_8).length
            );
        } catch (InvalidCipherTextException | IOException e) {
//            throw new ServiceRuntimeException(DevOpsError.XSS000020002, e);
            e.printStackTrace();
            throw e;
        }

//        return ENCODER_64.encodeToString(encryptDataBytes);
        return ENCODER_64.encode(encryptDataBytes);
    }

    public String decrypt(String content) throws IOException, InvalidCipherTextException {
        return this.decrypt(content, this.keyPairStore.getPrivateKey(), false);
    }

    public String decrypt(String content, boolean deal) throws IOException, InvalidCipherTextException {
        return this.decrypt(content, this.keyPairStore.getPrivateKey(), deal);
    }

    public String decrypt(String content, String privateKeyStr, boolean deal) throws IOException, InvalidCipherTextException {
        AsymmetricBlockCipher cipher = new RSAEngine();
//        byte[] encryptDataBytes = DECODER_64.decode(content);
        byte[] encryptDataBytes = DECODER_64.decodeBuffer(content);
        // 解密
//        byte[] privateKeyBytes = DECODER_64.decode(privateKeyStr);
        byte[] privateKeyBytes = DECODER_64.decodeBuffer(privateKeyStr);
        byte[] raw;
        try {
            ASN1Object publicKeyObject = ASN1Primitive.fromByteArray(privateKeyBytes);
            AsymmetricKeyParameter publicKey = PrivateKeyFactory.createKey(privateKeyBytes);
            cipher.init(false, publicKey);
            raw = cipher.processBlock(encryptDataBytes, 0, encryptDataBytes.length);
        } catch (IOException | InvalidCipherTextException e) {
//            throw new ServiceRuntimeException(DevOpsError.XSS000020003, e);
            e.printStackTrace();
            throw e;
        }
        if (!deal) {
            return new String(raw, StandardCharsets.UTF_8);
        }
        int i = raw.length - 1;
        while (i > 0 && raw[i] != 0) {
            i--;
        }
        i++;
        byte[] data = new byte[raw.length - i];
        for (int j = i; j < raw.length; j++) {
            data[j - i] = raw[j];
        }
        return new String(data, StandardCharsets.UTF_8);
    }

    @Getter
    @Setter
    public static class KeyPairStore {
        private String publicKey;
        private String privateKey;
    }

}
