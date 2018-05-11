package nohi.tools.checklist;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nohi.doc.utils.ExcelUtils;
import nohi.tools.checklist.model.FileItemInfo;
import nohi.tools.checklist.properties.CheckListProperties;
import nohi.tools.utils.FileList;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nohi on 2018/5/10.
 */
@Service
public class CheckListService {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CheckListProperties ckCheckListProperties;

	public void doIt(){
		try {

			//step 1 读取文件
			Set<String> set = readFile();
			log.debug( String.valueOf( set ) );

			//step 2 解决路径，获取文件信息
			List<FileItemInfo> fileInfoList = parase(set);
			log.debug( String.valueOf( fileInfoList ) );

			for (FileItemInfo fileItemInfo : fileInfoList) {
				copyFile(fileItemInfo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void copyFile(FileItemInfo fileItemInfo) throws Exception {
		if ("src".equalsIgnoreCase( fileItemInfo.getType() )) {
			copyFileSrc(fileItemInfo);
		}else {
			copyFileResource(fileItemInfo);
		}
	}

	/**
	 * 拷贝java文件
	 * @param fileItemInfo
	 */
	public void copyFileSrc(final FileItemInfo fileItemInfo) throws Exception {
		//拷贝源文件
		String fromPath = FileList.addFilePath(ckCheckListProperties.getWorkspaceDir(),fileItemInfo.getFullPath());
		String toPath = FileList.addFilePath(ckCheckListProperties.getTargetDir(),ckCheckListProperties.getTargetSrcFolder(),fileItemInfo.getType(),fileItemInfo.getPath());
		this.copyFile(fromPath,toPath , fileItemInfo.getFileName() );

		//拷贝编译后文件
		fromPath = FileList.addFilePath(ckCheckListProperties.getWorkspaceDir(),ckCheckListProperties.getWebRoot(),ckCheckListProperties.getWebPath(),fileItemInfo.getPath());
		toPath = FileList.addFilePath(ckCheckListProperties.getTargetDir(),ckCheckListProperties.getTargetCompileFolder(),ckCheckListProperties.getWebPath(),fileItemInfo.getPath());
		File files = new File(fromPath);

		final Pattern p = Pattern.compile("^" + fileItemInfo.getFilePre() + "(\\$.*)?(\\.class)$");
		File[] fileList = files.listFiles( new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if ("java".equalsIgnoreCase( fileItemInfo.getSuffix() )) {
					Matcher m = p.matcher(name);
					return m.matches();
				}else if (name.equalsIgnoreCase( fileItemInfo.getFileName())) {
					return true;
				}
				return false;
			}
		} );
		if (null != fileList && fileList.length > 0) {
			for (File file : fileList) {
				this.copyFile(file.getAbsolutePath(),toPath , file.getName() );
			}
		}

	}

	/**
	 * 拷贝资源文件
	 * @param fileItemInfo
	 */
	public void copyFileResource(FileItemInfo fileItemInfo) throws Exception {
		String fromPath = FileList.addFilePath(ckCheckListProperties.getWorkspaceDir(),fileItemInfo.getFullPath());
		String toPath = FileList.addFilePath(ckCheckListProperties.getTargetDir(),ckCheckListProperties.getTargetSrcFolder(),fileItemInfo.getType(),fileItemInfo.getPath());
		this.copyFile(fromPath,toPath , fileItemInfo.getFileName() );

		fromPath = FileList.addFilePath(ckCheckListProperties.getWorkspaceDir(),fileItemInfo.getFullPath());
		toPath = FileList.addFilePath(ckCheckListProperties.getTargetDir(),ckCheckListProperties.getTargetCompileFolder(),fileItemInfo.getPath());
		this.copyFile(fromPath,toPath , fileItemInfo.getFileName() );
	}

	public void copyFile(String fromPath,String toPath,String fileName) throws Exception {
		File fromFile = new File(fromPath);
		if (null == fromFile || !fromFile.exists()) {
			throw new Exception( "源文件不存在:" + fromFile );
		}
		File toPathFile = new File(toPath);
		//如果目录文件路径不存在，则创建目录
		if (null == toPathFile || !toPathFile.exists()) {
			toPathFile.mkdirs();
		}

		try (InputStream inStream = new FileInputStream(fromFile)) { ; //读入原文件
			log.info( "拷贝文件[{}]到目录[{}]" , fromFile , toPath );
			FileOutputStream fs = new FileOutputStream( FileList.addFilePath( toPath , fileName ));
			byte[] buffer = new byte[2048];
			int length;
			int bytesum = 0;
			int byteread = 0;
			while ( (byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread; //字节数 文件大小
				fs.write(buffer, 0, byteread);
			}
			inStream.close();
		}catch (Exception e){
			log.error( "拷贝文件[{}]至目录[{}]异常" ,fromFile, toPath);
			log.error( e.getMessage(),e);
			throw e;
		}
	}

	public List<FileItemInfo> parase(Set<String> list){
		List<FileItemInfo> fileItemList = new ArrayList<>(  );
		if (null != list && !list.isEmpty()) {
			for (String s : list) {
				FileItemInfo fi = parase( s );
				if (null != fi) {
					fileItemList.add( fi );
				}
			}
		}
		return fileItemList;
	}

	public FileItemInfo parase(String line){
		if (StringUtils.isNotBlank( line )) {
			String fullPath = line;
			line = line.replaceAll( "\\\\"  , "/" ); //把 \转换为/
			if (line.startsWith( "/" )) {
				line = line.substring( 1 );
			}
			String type = line.substring( 0 , line.indexOf( "/" ) );
			String path = null;
			String fileName = null;
			String prefix = null;
			String suffix = null;
			//除去 src 或 WebContent 后的路径
			line = line.substring( line.indexOf( "/" ) + 1 );
			//如果还有/,说明还有路径
			if (line.indexOf( "/" ) > 0 ) {
				path = line.substring( 0 , line.lastIndexOf( "/" ) );
				fileName = line.substring( line.lastIndexOf( "/" ) + 1);
			}else {
				fileName = line;
			}

			suffix = fileName.substring( fileName.lastIndexOf( "." ) + 1 );
			prefix = fileName.replaceAll( "\\." + suffix + "$","" );
			FileItemInfo info = new FileItemInfo();
			info.setFileName( fileName );
			info.setType( type );
			info.setFilePre( prefix );
			info.setSuffix( suffix );;
			info.setPath( path );
			info.setFullPath( fullPath );
			return info;
		}
		return null;
	}
	/**
	 * 读取文件路径
	 * @return
	 * @throws Exception
	 */
	public Set<String> readFile() throws Exception {
		Set<String> set = new HashSet<>(  );
		Workbook hwb = null;
		Row row = null;//行
		Cell cell = null;//列

		//1,读取Excel
		try ( FileInputStream fi = new FileInputStream(ckCheckListProperties.getCkFile()) ) {
			hwb = WorkbookFactory.create(fi);

			//2,取得第一个Sheet
			Sheet sheet = hwb.getSheetAt(0);
			int rows = sheet.getLastRowNum();//行数, base 0
			int cols = -1;
			for (int i = 1 ; i <= rows ; i++) {//遍历行
				row = sheet.getRow( i );//取得行
				cols = row.getLastCellNum();//取得最后一列有序号 base 1

				if (cols >= 1) {
					String tmp = ExcelUtils.getCellValue( 	row.getCell( 1 ) );
					if (StringUtils.isBlank( tmp )) {
						log.debug( "checkList line[{}] cell is null" , (i+1));
						break;
					}
					set.add( tmp.trim() ); //去除前后空格
				}
			}
		}catch (Exception e){
			throw e;
		}
		return set;
	}

}
