package nohi.test.encrpt;

import nohi.encrpty.RSAUtils;
import nohi.utils.FileUtils;
import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-06-28 16:16
 **/
public class RsaTest {


    public String _2Stri(String priKey) throws IOException {
        StringReader read = new StringReader(priKey);
        BufferedReader br = new BufferedReader(read);
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

    @Test
    public void testRsa1024() throws Exception {
        String path = "/Users/nohi/work/workspaces-nohi/nohithink/thinkinjava/src/test/resources";
        String priFile = path + "/rsa1024/rsa_private_key_pkcs8.pem";
        String pubFile = path + "/rsa1024/rsa_public_key.pem";

        PublicKey publicKey = RSAUtils.loadPublicKey(new FileInputStream(new File(pubFile)));
        PrivateKey privateKey = RSAUtils.loadPrivateKey(new FileInputStream(new File(priFile)));

        // 加密
        String data = "{\"e\":\"sign_check_fail\",\"c\":2}";
        System.out.println("明文:" + data);
        // 公钥加密
        String encryptStr = RSAUtils.encryptData(data, publicKey);
        System.out.println("加密数据:" + encryptStr);

        // 解密  私钥解密
        String uncrypt = RSAUtils.decryptData(encryptStr, privateKey);
        System.out.println("解密数据:" + encryptStr);

        // 签名 私钥签名
        String sign = RSAUtils.sign(encryptStr, privateKey);
        System.out.println("加签:" + sign);

        // 验签
        boolean result = RSAUtils.verify(encryptStr, sign, publicKey);
        System.out.println("验签:" + result);
    }

    @Test
    public void testRsa加密加签2() throws Exception {
        String dir = "/Users/nohi/work/workspaces-nohi/nohithink/thinkinjava/src/test/resources";
        String pubFile = dir + "/rsa1024/rsa_public_key.pem";
        String priFile = dir + "/rsa1024/rsa_private_key_pkcs8.pem";
        String loalPubFile = dir + "/rsa1024/rsa_public_key.pem";

        String priKey = FileUtils.readStringfromPath(priFile);
        String pubKey = FileUtils.readStringfromPath(pubFile);
        String localPub = FileUtils.readStringfromPath(loalPubFile);

//        System.out.println("priKey:\n" + priKey);
        System.out.println("pubKey:\n" + pubKey);
        System.out.println("localPub:\n" + localPub);

        pubKey = this._2Stri(pubKey);
        priKey = this._2Stri(priKey);
        localPub = this._2Stri(localPub);

        PrivateKey privateKey = RSAUtils.loadPrivateKey(priKey);
        PublicKey publicKey = RSAUtils.loadPublicKey(pubKey);
        PublicKey locaPublicKey = RSAUtils.loadPublicKey(localPub);
        // 加密
        String data = "{\"htbh\":\"20200629\"}";
        System.out.println(data);
        String encryptStr = RSAUtils.encryptData(data, publicKey);
        System.out.println("加密数据:" + encryptStr);

        // RSA签名
        String sign = RSAUtils.sign(encryptStr, privateKey);
        System.out.println("加签:" + sign);

        // 验签
        boolean result = RSAUtils.verify(encryptStr, sign, locaPublicKey);
        System.out.println("验签结果1:" + result);

        // 解密  私钥解密
        String uncrypt = RSAUtils.decryptData(encryptStr, privateKey);
        System.out.println("解密数据:" + uncrypt);
    }

    @Test
    public void testRsa加密加签() throws Exception {
        String path = "/Users/nohi/work/workspaces-nohi/nohithink/thinkinjava/src/test/resources";
        String priFile = path + "/RSA_PRI.KEY";
        String pubFile = path + "/RSA_PUB.KEY";
        String loalPubFile = path + "/RSA_PUB.KEY";

        String priKey = FileUtils.readStringfromPath(priFile);
        String pubKey = FileUtils.readStringfromPath(pubFile);
        String localPub = FileUtils.readStringfromPath(loalPubFile);
        System.out.println("priKey:" + priKey);
        System.out.println("pubKey:" + pubKey);
        System.out.println("localPub:" + localPub);

        System.out.println("==========================");
        pubKey = _2Stri(pubKey);
        priKey = _2Stri(priKey);
        localPub = _2Stri(localPub);
        System.out.println("priKey:\n" + priKey);
        System.out.println("pubKey:\n" + pubKey);
        System.out.println("localPub:\n" + localPub);

        PrivateKey privateKey = RSAUtils.loadPrivateKey(priKey);
        PublicKey publicKey = RSAUtils.loadPublicKey(pubKey);
        PublicKey locaPublicKey = RSAUtils.loadPublicKey(localPub);

        // 加密
        String data = "{\"htbh\":\"123456789abc\"}";
        System.out.println(data);
        String encryptStr = RSAUtils.encryptData(data, publicKey);
        System.out.println("加密数据:" + encryptStr);

        // RSA签名
        String sign = RSAUtils.sign(encryptStr, privateKey);
        System.out.println("加签:" + sign);

        // 验签
        boolean result = RSAUtils.verify(encryptStr, sign, locaPublicKey);
        System.out.println("验签:" + result);
    }

}
