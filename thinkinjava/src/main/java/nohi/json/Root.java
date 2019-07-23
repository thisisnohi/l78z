package nohi.json;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by nohi on 2019-6-14.
 */
public class Root<H,B> {
  private String code;
  private String msg;

  @JSONField(name = "head")
  private H h ;

  @JSONField(name = "body")
  private B b ;

  public String getCode() {
	return code;
  }

  public void setCode(String code) {
	this.code = code;
  }

  public String getMsg() {
	return msg;
  }

  public void setMsg(String msg) {
	this.msg = msg;
  }

  public H getH() {
	return h;
  }

  public void setH(H h) {
	this.h = h;
  }

  public B getB() {
	return b;
  }

  public void setB(B b) {
	this.b = b;
  }
}
