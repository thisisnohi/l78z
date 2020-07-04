package nohi.btsw.security.pojo;


/**
 * Created by PengHui on 2017/5/17.
 */

import java.util.HashMap;

public class ResponseData{

    public ResponseData(){
        super();
    }

    private ResponseHead head = new ResponseHead();

    private Object body =  new HashMap<>();

    public ResponseHead getHead() {
        return head;
    }

    public void setHead(ResponseHead head) {
        this.head = head;
    }

    public Object getBody() {
        System.out.println(body);
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }



}
