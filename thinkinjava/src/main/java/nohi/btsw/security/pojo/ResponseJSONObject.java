package nohi.btsw.security.pojo;

/**
 * Created by penghui on 2017/5/16.
 */
public class ResponseJSONObject {

    //数据
    private String data = "";

    //签名串
    private String sign = "";

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }



}
