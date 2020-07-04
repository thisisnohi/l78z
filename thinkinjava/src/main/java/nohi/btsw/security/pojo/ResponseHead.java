package nohi.btsw.security.pojo;

/**
 * Created by PengHui on 2017/5/17.
 */
/**
 * 数据中head信息
 */
public class ResponseHead{

    //交易流水号
    private String trans_seq_no = "";
    //请求日期
    private String response_date = "";
    //请求日期
    private String response_time = "" ;
    //响应码
    private String response_code = "";
    //失败描述
    private String error_msg = "";
    //请求报文摘要
    private String sha1 = "" ;

    public String getTrans_seq_no() {
        return trans_seq_no;
    }

    public void setTrans_seq_no(String trans_seq_no) {
        this.trans_seq_no = trans_seq_no;
    }

    public String getResponse_date() {
        return response_date;
    }

    public void setResponse_date(String response_date) {
        this.response_date = response_date;
    }

    public String getResponse_time() {
        return response_time;
    }

    public void setResponse_time(String response_time) {
        this.response_time = response_time;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }
}
