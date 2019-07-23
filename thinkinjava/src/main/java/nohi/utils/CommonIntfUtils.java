package nohi.utils;

public class CommonIntfUtils {

	/**
	 * 截取指定节点内的字符串
	 * @param msg
	 * @param startNode
	 * @param endNode
	 * @return
	 */
	public static String cutString(String msg,String startNode,String endNode){
		int index = msg.indexOf(startNode);
		int length = startNode.length();
		return msg.substring(index + length, msg.indexOf(endNode));
	}
	
	public static String getStringWithLength(String str,int length){
		return getStringWithLength(str,length,"0");
	}
	
	public static String getStringWithLength(String str,int length,String appendStr){
		
		if (null == str) {
			str = "";
		}
		if (str.length() > length) {
			return str.substring(str.length() - length);
		}else if (str.length() == length) {
			return str;
		}
		
		for (int i = str.length() ; i < length ; i++) {
			str = appendStr + str;
		}
		
		return str;
	}
	
	public  static void main(String[] args){
		System.out.println(getStringWithLength("123",8));
	}
}
