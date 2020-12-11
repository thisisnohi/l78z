package nohi.test.sm42;

import nohi.test.sm4_1538.SM4Utils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-07-16 11:08
 **/
public class TestSM4 {

    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1)
            throw new IllegalArgumentException("this byteArray must not be null or empty");
        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }

    /**
     * keys:16
     * new String(keys):ڼ�:�t�sbRC�+
     * chars:32
     * new String(chars):DABCF23ACF0774B073627F105243B72B
     * -tmp:DABCF23ACF0774B073627F105243B72B
     * KEY:12345678901234561234567890123456
     *
     * @throws DecoderException
     */
    @Test
    public void de() throws DecoderException {
        String key = "124ABCDE7890CFE90303EAC31980BCAF";
        char[] chars = key.toCharArray();
        byte[] bytes = Hex.decodeHex(chars);
        System.out.println("bytes:" + bytes.length);
        System.out.println(new String(bytes));
    }

    public static void main(String[] args) {
        String key = "124ABCDE7890CFE90303EAC31980BCAF";
//        key = "qawsedrftgyhujik";
        String json = "待加密的字符";
        try {
            String _tmp = Sm4Util.generateKey();
            System.out.println("-tmp:" + _tmp);
            key = _tmp;
            key = "12345678901234561234567890123456";
            // 生成32位16进制密钥
            System.out.println("KEY:" + key);
            String cipher = Sm4Util.encryptEcb(key, json);
            System.out.println("密文:" + cipher);
            System.out.println(Sm4Util.verifyEcb(key, cipher, json));
            json = Sm4Util.decryptEcb(key, cipher);
            System.out.println("明文:" + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test解密() throws UnsupportedEncodingException {
        String key = "11HDESaAhiHHugDz";
        String cipherText = "SZeHYSkyEvr5pj8qms4P/W2josUgsM3u0nuNc29T+HQ0hyxroInvDw8nv6AroFsJ";
        String plainText = Sm4Util.decryptEcb(key, cipherText);
        System.out.println("明文: " + plainText);
    }
}
