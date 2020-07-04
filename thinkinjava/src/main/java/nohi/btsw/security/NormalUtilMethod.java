package nohi.btsw.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nohi.btsw.security.pojo.MessageJSONObject;
import nohi.btsw.security.pojo.RequestData;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class NormalUtilMethod {

    /***
     *httpclient的get方式调用远程接口
     *@param url 全部的post访问地址，例如：http://xxxx/xx/x
     *@param requestJSON,后台会拼装url时候加上"?requestJSON="
     *****/
    public static String doHttpClientGetMethod(String url, String requestJSON) throws Exception {
        String result = null;
//	    HttpClientBuilder builder = HttpClientBuilder.create();
//	    CloseableHttpClient client = builder.build();
//	    HttpGet get = new HttpGet(url+"?requestJSON="+requestJSON);
//	    CloseableHttpResponse response = client.execute(get);
//	    if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//	    	result = EntityUtils.toString(response.getEntity(), "utf-8");
//	    }
//	    client.close();
        return result;
    }

    /***
     *httpclient的post方式调用远程接口
     *@param url 全部的post访问地址，例如：http://xxxx/xx/x
     *@param requestJSON
     *****/
    public static String doHttpClientPostMethod(String url, String requestJSON) throws Exception {
        String result = null;
	    HttpClientBuilder builder = HttpClientBuilder.create();
	    CloseableHttpClient client = builder.build();
	    HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(requestJSON, "utf-8");
		entity.setContentType("application/x-www-form-urlencoded");
		post.setEntity(entity);
	    CloseableHttpResponse response = client.execute(post);
	    if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	    	result = EntityUtils.toString(response.getEntity(), "utf-8");
	    }
//	    client.close();
        return result;
    }


    /***
     *第三方通过传入参数，拼装url，不加密只是进行RequestData转换和 urlEncode
     *得到结果后请拼装requestJSON=返回值
     **/
    public static String getThirdGetUsDataStrEncrypt(String systemId, String transSeqNo, Map<String, Object> requestParam, CAClientCer clientcer) {
        String requestDataJson = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RequestData requestData = new RequestData();
            RequestData.RequestHead head = new RequestData.RequestHead();
            head.setSystemId(systemId);
            head.setTransSeqNo(transSeqNo);
            requestData.setHead(head);
            requestData.setBody(requestParam);
            requestDataJson = objectMapper.writeValueAsString(requestData);
            System.out.println("requestDataJson:" + requestDataJson);

            String data = clientcer.base64Encode(clientcer.encrypt(requestDataJson.getBytes()));
            String sign = clientcer.base64Encode(clientcer.sign(data.getBytes()));
            MessageJSONObject requestJSONObject = new MessageJSONObject();
            requestJSONObject.setData(data);
            requestJSONObject.setSign(sign);

            String requestJSON = objectMapper.writeValueAsString(requestJSONObject);
            System.out.println("requestJSON:" + requestJSON);
            return urlEncode(requestJSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return requestDataJson;
    }

    /***
     *第三方通过传入参数，拼装url，不加密只是进行RequestData转换和 urlEncode
     *得到结果后请拼装requestJSON=返回值
     **/
    public static String getThirdGetUsDataStr(String systemId, String transSeqNo, Map<String, Object> requestParam) {
        String requestDataJson = "";
//	  try {
//	      ObjectMapper objectMapper = new ObjectMapper();
//	      RequestData requestData=new RequestData();
//	      RequestHead head=new RequestHead();
//	      head.setSystemId(systemId);
//	      head.setTransSeqNo(transSeqNo);
//	      requestData.setHead(head);
//	      requestData.setBody(requestParam);
//		  requestDataJson = objectMapper.writeValueAsString(requestData);
//		  return urlEncode(requestDataJson);
//	   } catch (JsonProcessingException e) {
//		  e.printStackTrace();
//	   }
        return requestDataJson;
    }

    /**
     * urlcode编码
     *
     * @param plainText
     * @return
     */
    public static String urlEncode(String plainText) {

        try {
            return URLEncoder.encode(plainText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return plainText;
    }

    /**
     * URLCode 解码
     *
     * @param encrpyText
     * @return
     */
    public static String urlDecode(String encrpyText) {

        if (encrpyText.contains("%")) {
            try {
                encrpyText = URLDecoder.decode(encrpyText, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (encrpyText.contains(" ")) {
            encrpyText = encrpyText.replaceAll(" ", "+");
        }
        return encrpyText;
    }

    public static boolean clientValidateResponseData(CAClientCer clientCer, String responseDataStr, String responseSignStr) {
        boolean verifySignResult = clientCer.verify(responseDataStr.getBytes(), clientCer.base64Decode(responseSignStr));

        return verifySignResult;
    }

    /**
     * SHA1算法
     *
     * @param src
     * @return
     */
    public static String sha1(String src) {
        return sha1(src.getBytes());
    }

    /**
     * SHA1算法
     *
     * @param src
     * @return
     */
    private static String sha1(byte[] src) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(src);
        return bytesToHexString(messageDigest.digest());
    }

    /**
     * 字节转16进制字符串
     *
     * @param src
     * @return
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
