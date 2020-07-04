package nohi.btsw.security.pojo;

import java.util.Map;

/**
 * Created by PengHui on 2017/5/24.d
 */
public interface MessageJSONData<T extends MessageJSONHead> {

    public T getHead();

    public void setHead(T head);

    public Map<String,Object> getBody();

    public void setBody(Map<String, Object> body);
}
