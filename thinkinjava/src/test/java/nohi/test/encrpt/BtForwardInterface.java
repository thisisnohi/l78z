package nohi.test.encrpt;

import com.fasterxml.jackson.databind.ObjectMapper;
import nohi.btsw.security.CAClientCer;
import nohi.btsw.security.NormalUtilMethod;
import nohi.btsw.security.pojo.RequestData;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class BtForwardInterface {
    private static String adiWebAddress = "http://zlba.njfcj.gov.cn/adi/bitsADI/";
    private static String interfaceNameCode = "rental_app._getqzfryqx";

    @Test
    public void test(){
        System.out.println("=======");
    }

    @Test
    public void getQzf() {
        System.out.println("比特思维请求中转接口：");
        String jsStr = null;
        try {
            /**
             * 密文 秘钥
             */
            String serverPublicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+yLyxg2xb6LkkWnWZF+XXjkZN0Fhne1eJTA3EmOlMyD5fhOfs9Ri0qwWqRW9s4m4k4tgJX5tcGRwCd7x6PEyJXr3/YgcR81zFqMjsnCYdB24Ii3bLdGhcokwJ/cW2yTWwa7ujpjemEROv5JasFJwXKdJVz+uSJDYmOsylL1MvNwIDAQAB";
//            serverPublicKeyStr = "MIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQC3aWSwbkHqFYGJeoVoqZ0fYtj7To6QZQZ/T7oIQE4AFEaI7t4vRMYBP1Bcdm3gIf8QYpkPP8Qj1Mp21trfAcDPVT79oEie7aeG9tVYFWivHHU8EzJFWq4baToJ8/MYBKAphd4P8Zq5tIGbjRcF3bQ0M7QeM8EeBDlLtJcoPEoBFwIBAw==";

            System.out.println("比特思维接口——serverPublicKeyStr："+serverPublicKeyStr);
            String pfxFile = "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";
            System.out.println("比特思维接口——pfxFile："+pfxFile);
            String password = "123456";
            System.out.println("比特思维接口——password："+password);
            CAClientCer clientCer = new CAClientCer(serverPublicKeyStr, pfxFile, password);
            System.out.println("比特思维接口——clientCer："+clientCer);

            /**
             * 加密
             * 第二种post方式获取数据加密
             */
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println("比特思维接口——objectMapper："+objectMapper);
            String systemId = "zjsy_system";
            String transSeqNo = "201709220001";
            System.out.println("比特思维接口——systemId："+systemId);
            System.out.println("比特思维接口——transSeqNo："+transSeqNo);

            Map<String, Object> requestParam = new HashMap<>();
            requestParam.put("htbh", "1234567899");
            System.out.println("参数：" + requestParam);

            RequestData requestData = new RequestData();
            RequestData.RequestHead head = new RequestData.RequestHead();
            head.setSystemId(systemId);
            head.setTransSeqNo(transSeqNo);
            requestData.setHead(head);
            requestData.setBody(requestParam);
            String data  = objectMapper.writeValueAsString(requestData);
            System.out.println("加密前:" + data);
            data = clientCer.base64Encode(clientCer.encrypt(data.getBytes()));
            System.out.println("加密后：" + data);

            String responseDataPlainText = new String(clientCer.decrypt(clientCer.base64Decode(data)));
            System.out.println("解密后：" + responseDataPlainText);

            String sign = clientCer.base64Encode(clientCer.sign(data.getBytes()));
            System.out.println("签名：" + sign);

//            //请求返回值
//        String getreturnstr = NormalUtilMethod.doHttpClientPostMethod(adiWebAddress + interfaceNameCode, data);
//            String getreturnstr = NormalUtilMethod.doHttpClientPostMethod(host + method, encodestr);
//
//            System.out.println("|||||: " + getreturnstr);
//
//            MessageJSONObject responseJSONObject = objectMapper.readValue(getreturnstr, MessageJSONObject.class);
//
//            //加密数据
//            String responseDataStr = responseJSONObject.getData();
//
//            //签名
//            String responseSignStr = responseJSONObject.getSign();
//            System.out.println("返回签名字符串: " + responseSignStr);
//            System.out.println("返回数据密文: " + responseDataStr);
//
//
//            if (NormalUtilMethod.clientValidateResponseData(clientCer, responseDataStr, responseSignStr)) {
//                String responseDataPlainText = new String(clientCer.decrypt(clientCer.base64Decode(responseDataStr)));
//
//                System.out.println("原生返回值：" + responseDataPlainText);
//                responseDataPlainText = responseDataPlainText.replace("null", "''");
//                Object body = JSONObject.parseObject(responseDataPlainText).get("body");
//
//                System.out.println("String：" + responseDataPlainText);
//                jsStr = JSONObject.parseObject(responseDataPlainText).get("body").toString();
//                System.out.println("String:" + jsStr);
//                jsStr = JSONObject.toJSONString(jsStr);   //正式
//                System.out.println("String:" + jsStr);
//
//            }

        } catch (Exception e) {
            System.out.println("比特中转接口异常：" + e);
            System.out.println("比特中转接口异常：" + e);
        }

        System.out.println("最终返回值：" + jsStr);
    }
}
