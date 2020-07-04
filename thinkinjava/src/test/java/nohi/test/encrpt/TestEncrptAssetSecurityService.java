package nohi.test.encrpt;

import nohi.encrpty.AssetSecurityService;
import nohi.encrpty.RSAEncrypt;
import nohi.utils.FileUtils;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-06-24 16:14
 **/
public class TestEncrptAssetSecurityService {

    @Test
    public void test1() throws IOException, InvalidCipherTextException {
        AssetSecurityService.KeyPairStore store = AssetSecurityService.generateKeyPair();
//        store.setPrivateKey("MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALdpZLBuQeoVgYl6hWipnR9i2PtOjpBlBn9PughATgAURoju3i9ExgE/UFx2beAh/xBimQ8/xCPUynbW2t8BwM9VPv2gSJ7tp4b21VgVaK8cdTwTMkVarhtpOgnz8xgEoCmF3g/xmrm0gZuNFwXdtDQztB4zwR4EOUu0lyg8SgEXAgEDAoGAHpGQyBJgUa5AQZRrkXGaL+XO1I0XwruBFTf0VrViVVi2bCfPsot2VYqNZL5npVr/2BBu19/2BfjMaSPPJSr1d5sjTQxOFZ9c50JSQsKCygvlkNLmw+c59U93gwkaelCWhqoj4cVMeEwlVhXH0ukBROzcYTEy+YomQDbcMsxNuD8CQQDoQm0ctEjSbTSXHGXBE27u88cOuyhVnEB4hOQG3CqB2VGIFKKMdXJw5wvyqAuujRew/Rnk2s1XYRogqJopGFf9AkEAyijCOb/UXwzm0cthxUSD5iAQEw6FmbYbE+gTtngPnz+wAPIixVp1erqLF8AIuR9+9Yy9J7R3b/KwSsVdSV9XowJBAJrW82h4MIxIzboS7oC3n0n32gnSGuO9gFBYmASSxwE7i7ANwbL49vXvXUxwB8mzZSCou+3nM4+WEWsbEXC65VMCQQCGxdbRKo2Us0SL3OvY2FfuwAq3XwO7zry38A0kUApqKnVV9sHY5vj8fFy6gAXQv6n5CH4aeE+f9yAx2OjblOUXAkAY8fMi78MLIOaUIcktWJDp3ENGQe+hkjXTIKsiDl4sqs7c5dCOZi1cCgX+I8VPSEQXcNm1+QcX6BX53+YRxW2R");
//        store.setPublicKey("MIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQC3aWSwbkHqFYGJeoVoqZ0fYtj7To6QZQZ/T7oIQE4AFEaI7t4vRMYBP1Bcdm3gIf8QYpkPP8Qj1Mp21trfAcDPVT79oEie7aeG9tVYFWivHHU8EzJFWq4baToJ8/MYBKAphd4P8Zq5tIGbjRcF3bQ0M7QeM8EeBDlLtJcoPEoBFwIBAw==");

        System.out.println("pub:" + store.getPublicKey());
        System.out.println("pri:" + store.getPrivateKey());

        AssetSecurityService service = new AssetSecurityService(store);
        String content = "{'a':'a1','b':123}";
        content = "这里是原报文这里是原报文这里是原报文这里是原报文这里是原报文这里是原报文";
        // 公钥加密
        String encrpty = service.encrypt(content);
        System.out.println("加密:" + encrpty);
        // 私钥解密
        System.out.println("解密:" + service.decrypt(encrpty));

    }
}
