package nohi.btsw.security.pojo;

/**
 * Created by PengHui on 2017/5/24.
 */
public interface MessageJSONHead {

    public String getTransSeqNo();

    public void setTransSeqNo(String transSeqNo);

    public String getDateStr();

    public void setDateStr(String dateStr);

    public String getTimeStr();

    public void setTimeStr(String timeStr);
}
