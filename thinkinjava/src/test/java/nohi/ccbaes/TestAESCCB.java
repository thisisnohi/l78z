package nohi.ccbaes;

import com.nankang.tool.EncrypterAESTool;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;

/**
 * @author NOHI
 * 2021-08-09 15:23
 **/
public class TestAESCCB {

    @Test
    public void testEncode(){
        String key = "Pq5hf2h8cz/c1S8xNlIZLw==";
        String res = "这里是中文...";
        System.out.println("原文:" + res);
        res = EncrypterAESTool.encryptByStr(res, key);
        System.out.println("加密后:" + res);
        res = EncrypterAESTool.decryptByStr(res, key);
        System.out.println("解密后:" + res);
    }

    @Test
    public void getKey() throws Exception {
        String key = "Pq5hf2h8cz/c1S8xNlIZLw==";
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes());
        kg.init(128, secureRandom);
        SecretKey secretKey = kg.generateKey();
        System.out.println(new String(secretKey.getEncoded()));
        System.out.println(new String(Base64.encodeBase64(secretKey.getEncoded())));
    }
}
