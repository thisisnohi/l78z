package nohi.tools.checklist.model;

/**
 * Created by nohi on 2018/5/10.
 */
public class FileItemInfo {
	private String type;//java文件  资源文件
	private String path;//路径 有可能为空 src/aa.java   WebContent/xxx.jsp
	private String fileName;//文件名
	private String filePre;//文件名不包含后缀
	private String suffix;//后缀
	private String fullPath;//全路径

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePre() {
		return filePre;
	}

	public void setFilePre(String filePre) {
		this.filePre = filePre;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public String toString() {
		return "FileItemInfo{" +
				"type='" + type + '\'' +
				", path='" + path + '\'' +
				", fileName='" + fileName + '\'' +
				", filePre='" + filePre + '\'' +
				", suffix='" + suffix + '\'' +
				'}';
	}
}
