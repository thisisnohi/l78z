package nohi.tools.checklist.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by nohi on 2018/5/10.
 */
@Component
@ConfigurationProperties(prefix = "ck")
public class CheckListProperties {

	private String workspaceDir;
	private String targetDir;
	private String fileSperator;
	private String targetSrcFolder;
	private String targetCompileFolder;
	private String ckFile;

	private String webPath;
	private String webRoot;

	public String getWebPath() {
		return webPath;
	}

	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}

	public String getWebRoot() {
		return webRoot;
	}

	public void setWebRoot(String webRoot) {
		this.webRoot = webRoot;
	}

	public String getWorkspaceDir() {
		return workspaceDir;
	}

	public void setWorkspaceDir(String workspaceDir) {
		this.workspaceDir = workspaceDir;
	}

	public String getTargetDir() {
		return targetDir;
	}

	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}

	public String getFileSperator() {
		return fileSperator;
	}

	public void setFileSperator(String fileSperator) {
		this.fileSperator = fileSperator;
	}

	public String getTargetSrcFolder() {
		return targetSrcFolder;
	}

	public void setTargetSrcFolder(String targetSrcFolder) {
		this.targetSrcFolder = targetSrcFolder;
	}

	public String getTargetCompileFolder() {
		return targetCompileFolder;
	}

	public void setTargetCompileFolder(String targetCompileFolder) {
		this.targetCompileFolder = targetCompileFolder;
	}

	public String getCkFile() {
		return ckFile;
	}

	public void setCkFile(String ckFile) {
		this.ckFile = ckFile;
	}

	@Override
	public String toString() {
		return "CheckListProperties{" +
				"workspaceDir='" + workspaceDir + '\'' +
				", targetDir='" + targetDir + '\'' +
				", fileSperator='" + fileSperator + '\'' +
				", targetSrcFolder='" + targetSrcFolder + '\'' +
				", targetCompileFolder='" + targetCompileFolder + '\'' +
				", ckFile='" + ckFile + '\'' +
				", webPath='" + webPath + '\'' +
				", webRoot='" + webRoot + '\'' +
				'}';
	}
}
