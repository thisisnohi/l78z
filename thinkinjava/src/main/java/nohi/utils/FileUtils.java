package nohi.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.List;

public class FileUtils {
	public static String docDir = "";

	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			//递归删除目录中的子目录下
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	public static void clearPath(String path){
		if (StringUtils.isNotBlank( path )) {
			try {
				File file = new File( path );
				if (file.exists() && file.isDirectory()) {
					deleteDir(file);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 判断路径是否存在
	 * @param path
	 * @return
	 */
	public static boolean exists(String path){
		if (StringUtils.isBlank(path)) {

			return false;
		}
		File file = new File(path);
		return file.exists();
	}

	/**
	 * 创建目录
	 * @param path
	 * @return
	 */
	public static void createDir(String path) throws Exception {
		File file = new File(path);
		if (!file.exists()) {
			if (!file.mkdirs()){
				throw new Exception("创建文件目录[" + path + "]失败!");
			}
		}
	}

	/**
	 * 保存输入流中的数据至文件，指定文件名
	 * 	   默认UTF-8
	 * @param docPath
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String storeMessage(InputStream is,String docPath,String fileName) throws Exception{
		//创建目录
		createDir(docPath);

		File file = new File(docPath + File.separator + fileName);
		if (file.exists()) {
			throw new Exception("文件已经存在");
		}
		if (null == is) {
			throw new Exception("输入流为空!");
		}
		FileOutputStream fos = null ;
		try {
            fos = new FileOutputStream(file); //直接写文件全路径

			byte[] bytes = new byte[2048];
			int length = -1;
			while ((length = is.read(bytes)) > -1){
                fos.write(bytes,0,length);
			}
            fos.flush();
		} catch (Exception e) {
			throw new Exception(e.getMessage(),e);
		}finally{
			try {
				if (null != fos) {
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//返回文件路径和文件名
		return docPath + File.separator + fileName;
	}

	/**
	 * 保存信息至文件，指定文件名
	 * 	   默认UTF-8
	 * @param message
	 * @param docPath
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String storeMessage(String message,String docPath,String fileName) throws Exception {
		return storeMessage(message,"UTF-8", docPath, fileName,false);
	}
		/**
		 * 保存信息至文件，指定文件名
		 * 	   默认UTF-8
		 * @param message
		 * @param docPath
		 * @param fileName
		 * @return
		 * @throws Exception
		 */
	public static String storeMessage(String message,String docPath,String fileName,boolean checkExists) throws Exception{
		return storeMessage(message,"UTF-8", docPath, fileName,checkExists);
	}

	/**
	 * 保存信息至文件，指定文件名、字符集
	 * @param message
	 * @param encode
	 * @param docPath
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String storeMessage(String message,String encode,String docPath,String fileName,boolean checkExists) throws Exception{
		//创建目录
		createDir(docPath);

		File file = new File(docPath + File.separator + fileName);
		if (file.exists() && (file.isDirectory() || checkExists )) { //如果文件存在: 文件是目录/需要检查文件是否存在,则报错
			throw new Exception("文件已经存在");
		}

		if (null == message) {
			message = "";
		}
		OutputStreamWriter writer = null;
		FileOutputStream fos = null ;
		try {
			fos = new FileOutputStream(file); //直接写文件全路径
			writer = new OutputStreamWriter(fos,encode);
			writer.write(message);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}finally{
			try {
				if (null != fos) {
					fos.close();
				}
				if (null != writer) {
					writer.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return docPath + File.separator + fileName;
	}

	/**
	 * 保存信息至文件，指定文件名、字符集
	 * @param message
	 * @param encode
	 * @param docPath
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String storeMessageAppend(String message,String encode,String docPath,String fileName) throws Exception{
		//创建目录
		createDir(docPath);

		File file = new File(docPath + File.separator + fileName);
		if (file.exists() && file.isDirectory()) { //如果文件存在: 文件是目录/需要检查文件是否存在,则报错
			throw new Exception("文件已经存在，且是文件目录");
		}

		if (null == message) {
			message = "";
		}
		OutputStreamWriter writer = null;
		FileOutputStream fos = null ;
		try {
			fos = new FileOutputStream(file,true); //直接写文件全路径
			writer = new OutputStreamWriter(fos,encode);
			writer.write(message);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}finally{
			try {
				if (null != fos) {
					fos.close();
				}
				if (null != writer) {
					writer.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return docPath + File.separator + fileName;
	}

//	public static void downloadFromFilePathRequest(String filePath, String contentType, HttpServletRequest req, HttpServletResponse resp) throws Exception {
//
//		 FileInputStream fis = null;
//		 ByteArrayOutputStream baos = null;
//
//		 try {
//			 baos = new ByteArrayOutputStream();
//			 fis = new FileInputStream(filePath);
//			 byte[] b = new byte[1024];
//			 int i = -1;
//			 while ((i = fis.read(b)) != -1) {
//				 baos.write(b, 0, i);
//			 }
//
//			 String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
//			 download(baos.toByteArray(), fileName, contentType,req,resp);
//		 } catch (Exception e) {
//			 e.printStackTrace();
//			 throw new Exception(e);
//		 }finally{
//			 try {
//				if (null != fis) {
//					fis.close();
//				}
//
//				if (null != baos) {
//					baos.close();
//				}
//			} catch (Exception e2) {
//				// TODO: handle exception
//				e2.printStackTrace();
//			}
//		}
//
//	 }
//
//	private static void download(byte file[], String fileName, String contentType, HttpServletRequest req, HttpServletResponse resp) throws Exception{
//	     log.debug((new StringBuilder()).append("fileName:").append(fileName).toString());
//	     log.debug((new StringBuilder()).append("contentType:").append(contentType).toString());
//	     try{
//            HttpServletResponse response = resp;
//            response.setContentType(contentType);
//            response.setHeader("Content-Disposition", (new StringBuilder()).append("attachment; filename=").append(new String(fileName.getBytes("GBK"), "ISO8859_1")).toString());
//            response.setContentLength(file.length);
//            ServletOutputStream out = response.getOutputStream();
//            out.write(file);
//            out.flush();
//	     }catch(Exception e){
//	    	 log.debug(e.getMessage(),e);
//	         throw new Exception(e);
//	     }
//	 }

    /**
     * 从输入流中获取数据
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

	/**
	 * 从输入流中获取数据
	 * @param inStream 输入流
	 * @return
	 * @throws Exception
	 */
	public static String readStringfromStream(InputStream inStream) throws Exception{
		byte[] buffer = readStream(inStream);
		if (null != buffer) {
			return new String(buffer,"UTF-8");
		}
		return "";
	}

	/**
	 * 从输入流中获取数据
	 */
	public static String readStringfromPath(String path) throws Exception{
		return readStringfromStream(new FileInputStream( path ));
	}

	/**
	 * 获取文件扩展名
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
	    if ((filename != null) && (filename.length() > 0)) {
	        int dot = filename.lastIndexOf('.');
	        if ((dot >-1) && (dot < (filename.length() - 1))) {
	            return filename.substring(dot + 1);
	        }
	    }
	    return filename;
	}

	/**
	 * 递归获取指定目录下的所有version.json文件的内容.
	 * @param path
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static List<String> getAllVersionInfo(File path,List<String> list) throws Exception{
		File[] files = path.listFiles();
		for (File fs : files) {
			if (fs.isDirectory()) {
				getAllVersionInfo(fs,list);
			}else{
				if (fs.getAbsolutePath().endsWith("version.json")) {
					list.add(readStringfromPath(fs.getAbsolutePath()));
				}
			}
		}
		return list;
	}
}
