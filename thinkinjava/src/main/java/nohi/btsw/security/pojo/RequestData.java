package nohi.btsw.security.pojo;

/**
 * Created by penghui on 2017/5/17.
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求数据
 */
public class RequestData implements MessageJSONData<RequestData.RequestHead> {

    public RequestData() {
    }

    public RequestData(Map<String, Object> body,String systemId,String transSeqNo) {
        this.body = body;
        //默认当前时间
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        head.setDateStr(formatter.format(currentTime));
        formatter = new SimpleDateFormat("HHmmssSSS");
        head.setTimeStr(formatter.format(currentTime));
        head.setTransSeqNo(transSeqNo);
        head.setSystemId(systemId);
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return super.toString();
    }

    private RequestHead head = new RequestHead();

    private Map<String,Object> body = new HashMap<>();

    public RequestHead getHead() {
        return head;
    }

    public void setHead(RequestHead head) {
        this.head = head;
    }

    public Map<String,Object> getBody() {
        return body;
    }

    public void setBody(Map<String,Object> body) {
        this.body = body;
    }

    public static class RequestHead implements MessageJSONHead {
        //交易流水号
        @JsonProperty("trans_seq_no")
        private String transSeqNo = "";
        //请求日期
        @JsonProperty("request_date")
        private String dateStr= "";
        //请求日期
        @JsonProperty("request_time")
        private String timeStr= "";
        //系统识别号
        @JsonProperty("system_id")
        private String systemId = "" ;

        public String getTransSeqNo() {
            return transSeqNo;
        }

        public void setTransSeqNo(String transSeqNo) {
            this.transSeqNo = transSeqNo;
        }

        public String getDateStr() {
            return dateStr;
        }

        public void setDateStr(String dateStr) {
            this.dateStr = dateStr;
        }

        public String getTimeStr() {
            return timeStr;
        }

        public void setTimeStr(String timeStr) {
            this.timeStr = timeStr;
        }

        public String getSystemId() {
            return systemId;
        }

        public void setSystemId(String systemId) {
            this.systemId = systemId;
        }
    }

}
