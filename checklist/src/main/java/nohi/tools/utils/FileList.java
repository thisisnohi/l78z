package nohi.tools.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by nohi on 2018/2/2.
 */
public class FileList {
	public static final String separator = "/";

	/**
	 * @param source 需要压缩的文件
	 * @return File 返回压缩后的文件
	 * @throws
	 * @Title: compress
	 * @Description: 将文件用tar压缩
	 */
	public static File compress(File source) {
		File target = new File( source.getName() + ".gz" );
		FileInputStream in = null;
		GZIPOutputStream out = null;
		try {
			in = new FileInputStream( source );
			out = new GZIPOutputStream( new FileOutputStream( target ) );
			byte[] array = new byte[1024];
			int number = -1;
			while ((number = in.read( array, 0, array.length )) != -1) {
				out.write( array, 0, number );
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}

			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return target;
	}

	/***
	 * 指定文件夹下的所有文件
	 * @param path
	 * @return
	 */
	public static List<File> getDirectFiles(String path) {
		File root = new File( path );
		return Arrays.asList( root.listFiles() );
	}
	/***
	 * 指定文件夹下的所有文件
	 * @param path
	 * @return
	 */
	public static List<File> getAllFiles(String path) {
		File root = new File( path );
		List<File> files = new ArrayList<File>();
		if (!root.isDirectory()) {
			files.add( root );
		} else {
			File[] subFiles = root.listFiles();
			for (File f : subFiles) {
				files.addAll( getAllFiles( f.getAbsolutePath() ) );
			}
		}
		return files;
	}

	/**
	 * 拼接文件路径
	 * @return
	 */
	public static String addFilePath(String path,String ...filePath){
		StringBuffer sb = new StringBuffer(  );
		if (StringUtils.isBlank( path )) {
			path = "";
		}
		sb.append( repalceFilePath(path) );
		if (null != filePath && filePath.length > 0) {
			for (String p : filePath) {
				if (StringUtils.isNotBlank( p )) {
					if (sb.length() > 0) {
						sb.append(separator).append( p );
					}else {
						sb.append( p );
					}
				}
			}
		}

		return sb.toString();
	}
	/**
	 * 获取父目录名称
	 * @return
	 */
	public static String getParentDirName(String file){
		if (StringUtils.isBlank( file )) {
			return "";
		}
		file = repalceFilePath(file);
		int index = file.lastIndexOf( "/" );
		if (index > 0) {
			return file.substring( index + 1 );
		}
		return "";
	}

	public static String repalceFilePath(String path){
		return path.replaceAll( "\\\\" , "/" ).replaceAll( "/*$" ,"" );
	}
}
