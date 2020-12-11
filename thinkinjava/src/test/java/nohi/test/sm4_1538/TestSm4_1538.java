package nohi.test.sm4_1538;

import org.junit.Test;

import java.io.IOException;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-07-16 15:37
 **/
public class TestSm4_1538 {

    @Test
    public void main() throws IOException {
        String plainText = "字节数组转换为十六进制字符串";
        SM4Utils sm4 = new SM4Utils();
        sm4.secretKey = "11HDESaAhiHHugDz";
//        sm4.secretKey = "1234567890123456";
        plainText.getBytes("UTF-8");
        System.out.println("ECB模式");
        String cipherText = sm4.encryptData_ECB(plainText);
        System.out.println("密文: " + cipherText);

        plainText = sm4.decryptData_ECB(cipherText);
        System.out.println("明文: " + plainText);
        System.out.println("");

        System.out.println("CBC模式");
        sm4.iv = "UISwD9fW6cFh9SNS";
        cipherText = sm4.encryptData_CBC(plainText);
        System.out.println("密文: " + cipherText);
        System.out.println("");

        plainText = sm4.decryptData_CBC(cipherText);
        System.out.println("明文: " + plainText);
        //PI4ke7HMoUMD/LOSHWX5/g==

    }

    @Test
    public void test解密() {
        String key = "11HDESaAhiHHugDz";
        String cipherText = "SZeHYSkyEvr5pj8qms4P/W2josUgsM3u0nuNc29T+HQ0hyxroInvDw8nv6AroFsJ";
        cipherText = "SZeHYSkyEvr5pj8qms4P/W2josUgsM3u0nuNc29T+HQ0hyxroInvDw8nv6AroFsJ";
        SM4Utils sm4 = new SM4Utils();
        sm4.secretKey = key;
        String plainText = sm4.decryptData_ECB(cipherText);
        System.out.println("明文: " + plainText);
    }
}
