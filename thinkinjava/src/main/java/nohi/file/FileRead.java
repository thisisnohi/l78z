package nohi.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileRead {

	public InputStream getResource(String name){
		return FileRead.class.getClassLoader().getResourceAsStream(name);
	}
	/**
	 * 用字符流方式读取内中数据
	 * 
	 * @param is
	 * @return
	 */
	public String readBuffer(InputStream is) {
		return readBuffer(is, null);
	}

	/**
	 * 根据指定字符集按字符流方式读取流中内容 默认字符集UTF-8
	 * 
	 * @param is
	 * @param charSet
	 * @return
	 */
	public String readBuffer(InputStream is, String charSet) {
		int i = 0;
		StringBuffer sb = new StringBuffer();
		char[] c = new char[1024];
		try {

			if (null == charSet || "".equals(charSet.trim())) {
				charSet = "UTF-8";
			}
			InputStreamReader br = new InputStreamReader(is, charSet);
			while ((i = br.read(c)) != -1) {
				String s = new String(c, 0, i);
				sb.append(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return sb.toString();
	}

	public String read(InputStream is) {
		return read(is, 0);
	}

	public String read(InputStream is, int length) {
		int i = 0;
		byte[] b = new byte[1024];
		StringBuffer sb = new StringBuffer();

		try {
			if (length > 0) {
				b = new byte[length];
				i = is.read(b);
				if (i > -1) {
					sb.append(new String(b, 0, i));
				}
			} else {
				while ((i = is.read(b)) > -1) {
					sb.append(new String(b, 0, i));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		return sb.toString();
	}
}
