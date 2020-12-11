package nohi.utils;


import org.apache.commons.io.IOUtils;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-08-19 10:20
 **/
public class ZipFileUtils {

    private static final String ENCODE_UTF_8 = "UTF-8";

    /**
     * @param zip      压缩的目的地址 例如：D://zipTest.zip
     * @param srcFiles 压缩的源文件
     * @description: 压缩文件或路径
     * @version: 1.0
     */


    public static void zipFile(String zip, List<File> srcFiles) {
        try {
            // 判断是否为压缩后的文件后缀是否为.zip结尾
            if (zip.endsWith(".zip") || zip.endsWith(".ZIP")) {
                ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(new File(zip)));
                zipOut.setEncoding(ENCODE_UTF_8);// 设置编码
                for (File file : srcFiles) {
                    zipFile(zip, zipOut, file, "");
                }
                zipOut.close();
            } else {
                throw new RuntimeException("target file[zip] is not .zip type file");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @param zip     压缩的目的地址 例如：D://zipTest.zip
     * @param zipOut
     * @param srcFile 被压缩的文件
     * @param path    在zip中的相对路径
     * @version: 1.0
     */
    private static void zipFile(String zip, ZipOutputStream zipOut, File srcFile, String path) throws IOException {
        System.out.println(" 开始压缩文件[" + srcFile.getName() + "]");
        if (!"".equals(path) && !path.endsWith(File.separator)) {
            path += File.separator;
        }
        if (!srcFile.exists()) {// 测试此抽象路径名定义的文件或目录是否存在
            throw new RuntimeException("压缩失败，文件或目录 " + srcFile + " 不存在!");
        } else {
            if (!srcFile.getPath().equals(zip)) {
                if (srcFile.isDirectory()) {
                    // listFiles能够获取当前文件夹下的所有文件和文件夹，如果文件夹A下还有文件D，那么D也在childs里。
                    File[] files = srcFile.listFiles();
                    if (files.length == 0) {
                        zipOut.putNextEntry(new ZipEntry(path + srcFile.getName() + File.separator));
                        zipOut.closeEntry();
                    } else {
                        for (File file : files) {
                            zipFile(zip, zipOut, file, path + srcFile.getName());
                        }
                    }
                } else {
                    FileInputStream in = new FileInputStream(srcFile);
                    zipOut.putNextEntry(new ZipEntry(path + srcFile.getName()));
                    IOUtils.copy(in, zipOut);
                    in.close();
                    zipOut.closeEntry();
                }
            }
        }
    }

    /**
     * @param zipPath 待解压缩的ZIP文件路径
     * @param descDir 目标目录
     * @return List<File>
     * @description: 解压缩ZIP文件，将ZIP文件里的内容解压到targetDIR目录下
     * @version: 1.0
     */
    public static List<File> upzipFile(String zipPath, String descDir) {
        return upzipFile(new File(zipPath), descDir);
    }

    /**
     * @param zipFile 解压缩文件
     * @param descDir 压缩的目标地址，如：D:\\测试 或 /mnt/d/测试
     * @description: 对.zip文件进行解压缩
     * @version: 1.0
     */
    public static List<File> upzipFile(File zipFile, String descDir) {
        List<File> list = new ArrayList<>();
        // 防止文件名中有中文时出错
        System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding"));
        try {
            if (!zipFile.exists()) {
                throw new RuntimeException("解压失败，文件 " + zipFile + " 不存在!");
            }
            ZipFile zFile = new ZipFile(zipFile, "GBK");
            InputStream in = null;
            OutputStream out = null;
            for (Enumeration entries = zFile.getEntries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                File file = new File(descDir + File.separator + entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    in = zFile.getInputStream(entry);
                    out = new FileOutputStream(file);
                    IOUtils.copy(in, out);
                    out.flush();
                    list.add(file);
                }
            }
            zFile.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    /**
     * @description: 对临时生成的文件夹和文件夹下的文件进行删除
     * @param: delpath
     * @version: 1.0
     */
    public static void deletefile(String delpath) {
        try {
            File file = new File(delpath);
            if (!file.isDirectory()) {// 判断是不是一个目录
                file.delete();
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + File.separator + filelist[i]);
                    if (!delfile.isDirectory()) {
                        delfile.delete();
                    } else if (delfile.isDirectory()) {
                        deletefile(delpath + File.separator + filelist[i]);// 递归删除
                    }
                }
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @description: 测试方法
     * @version: 1.0
     */
    public static void main(String[] args) {
        String path = "/Users/nohi/tmp/zip";
        String zip = path + "/1.zip";
        List<File> srcFiles = new ArrayList<>();
        srcFiles.add(new File(path + "/1.txt"));
        srcFiles.add(new File(path + "/2.txt"));
        srcFiles.add(new File(path + "/2的副本.txt"));
        zipFile(zip, srcFiles);
        List<File> list = upzipFile(zip, path + "/unzip");
        for (File file : list) {
            System.out.println(file.getName());
        }
        // deletefile(zip);
    }
}
