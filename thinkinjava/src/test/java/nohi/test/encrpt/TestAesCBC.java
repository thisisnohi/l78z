package nohi.test.encrpt;

import nohi.encrpty_aes.AesCbcPkcs7Util2;
import nohi.encrpty_aes.ok.AesCbcPkcs7Util;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-11-03 14:06
 **/
public class TestAesCBC {

    /**
     * ccb公众号提供
     */
    @Test
    public void test1() throws NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidParameterSpecException {
        String content = "this is content";
        String encryStr = AesCbcPkcs7Util.aesEncode(content, AesCbcPkcs7Util.AES_KEY, AesCbcPkcs7Util.AES_IV);
        System.out.println("encryStr:" + encryStr);
        String deCrpyStr = AesCbcPkcs7Util.aesDecode(encryStr, AesCbcPkcs7Util.AES_KEY, AesCbcPkcs7Util.AES_IV);
        System.out.println("deCrpyStr:" + deCrpyStr);

        // 网上解密方式
        deCrpyStr = AesCbcPkcs7Util2.decrypt(encryStr, AesCbcPkcs7Util.AES_KEY);
        System.out.println("deCrpyStr:" + deCrpyStr);
    }

    /**
     * AES/CBC/PKCS7Padding 网上搜索内容
     * https://blog.csdn.net/java_zjh/article/details/80915655
     */
    @Test
    public void test2() throws Exception{
        String content = "this is content";
        content = AesCbcPkcs7Util2.encrypt(content, AesCbcPkcs7Util.AES_KEY);
        System.out.println("enstr:" + content);
        content = AesCbcPkcs7Util2.decrypt(content, AesCbcPkcs7Util.AES_KEY);
        System.out.println("destr:" + content);
    }
}
