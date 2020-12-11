package nohi.test.zip;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-08-19 11:25
 **/
public class TestZip4J {

    public ZipParameters zipParameters(){
        ZipParameters parameters = new ZipParameters();
        /*
            压缩方式
                COMP_STORE = 0;（仅打包，不压缩）
                COMP_DEFLATE = 8;（默认）
                COMP_AES_ENC = 99; 加密压缩
            压缩级别
                DEFLATE_LEVEL_FASTEST = 1; (速度最快，压缩比最小)
                DEFLATE_LEVEL_FAST = 3; (速度快，压缩比小)
                DEFLATE_LEVEL_NORMAL = 5; (一般)
                DEFLATE_LEVEL_MAXIMUM = 7;
                DEFLATE_LEVEL_ULTRA = 9;
        */
        // 压缩方式
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        //设置密码
        parameters.setEncryptFiles(true);
        //        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 标准加密
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        parameters.setPassword("123456");
        return parameters;
    }
    @Test
    public  void zipFile() throws ZipException {
        // 要打包的文件夹
        String path = "/Users/nohi/tmp/zip";
        String zip = path + "/target/1.zip";

        // 生成的压缩文件
        ZipFile zipFile = new ZipFile(zip);

        // 生成zip参数
        ZipParameters parameters = this.zipParameters();
//        File currentFile = new File("D:\\test");
//        File[] fs = currentFile.listFiles();
//        // 遍历test文件夹下所有的文件、文件夹
//        for (File f : fs) {
//            if (f.isDirectory()) {
//                zipFile.addFolder(f.getPath(), parameters);
//            } else {
//                zipFile.addFile(f, parameters);
//            }
//        }
        zipFile.addFolder(path + "/unzip", parameters);
        zipFile.addFile(new File(path + "/1.txt"), parameters);
        zipFile.addFile(new File(path + "/2的副本.txt"), parameters);


        // 添加文件至指定目录
        ArrayList filesToAdd = new ArrayList();
        filesToAdd.add(new File(path + "/2的副本3.txt"));
        parameters.setRootFolderInZip("test2/");
        zipFile.addFiles(filesToAdd, parameters);
    }


    /**
     * 添加文件
     * @throws IOException
     * @throws ZipException
     */
    @Test
    public void appendFile() throws IOException, ZipException {
        // 要打包的文件夹
        String path = "/Users/nohi/tmp/zip";
        String zip = path + "/target/1.zip";

        // 生成的压缩文件
        ZipFile zipFile = new ZipFile(zip);
        // 生成zip参数
        ZipParameters parameters = this.zipParameters();
        // 添加文件
        zipFile.addFile(new File(path + "/2的副本3.txt"), parameters);
    }

    /**
     * 添加文件
     * @throws IOException
     * @throws ZipException
     */
    @Test
    public void appendFile2() throws IOException, ZipException {
        // 要打包的文件夹
        String path = "/Users/nohi/tmp/zip";
        String zip = path + "/target/1.zip";

        // 生成的压缩文件
        ZipFile zipFile = new ZipFile(zip);
        System.out.println("zipFile.isEncrypted():" + zipFile.isEncrypted());

        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setFileNameInZip("文件2.txt");
        parameters.setSourceExternalStream(true);

        InputStream is = new FileInputStream(path + "/1111.txt");
        zipFile.addStream(is, parameters);
        is.close();

        System.out.println("zipFile.isEncrypted():" + zipFile.isEncrypted());
    }

    /**
     * zip流写入
     * @throws IOException
     * @throws ZipException
     */
    @Test
    public void testZipOutputStream() throws IOException, ZipException {
        // 要打包的文件夹
        String path = "/Users/nohi/tmp/zip";
        String zip = path + "/target/1.zip";

        // 添加文件
        ArrayList filesToAdd = new ArrayList();
        filesToAdd.add(new File(path + "/2的副本2.txt"));

        ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(new File(zip)));

        ZipParameters parameters = this.zipParameters();

        for (int i = 0; i < filesToAdd.size(); i++) {
            File file = (File)filesToAdd.get(i);
            outputStream.putNextEntry(file,parameters);

            if (file.isDirectory()) {
                outputStream.closeEntry();
                continue;
            }

            InputStream inputStream = new FileInputStream(file);
            byte[] readBuff = new byte[4096];
            int readLen = -1;
            while ((readLen = inputStream.read(readBuff)) != -1) {
                outputStream.write(readBuff, 0, readLen);
            }
            outputStream.closeEntry();
            inputStream.close();
        }
        outputStream.finish();
        outputStream.close();
    }

    @Test
    public void showFiles() throws Exception {
        // 要打包的文件夹
        String path = "/Users/nohi/tmp/zip";
        String zip = path + "/target/1.zip";

        // 生成的压缩文件
        ZipFile zipFile = new ZipFile(zip);
        System.out.println("zipFile.isEncrypted():" + zipFile.isEncrypted());
        List fileHeaderList = zipFile.getFileHeaders();

        for (int i = 0; i < fileHeaderList.size(); i++) {
            FileHeader fileHeader = (FileHeader)fileHeaderList.get(i);
            System.out.println("************************************************************");
            System.out.println("****File Details for: " + fileHeader.getFileName() + "*****");
            System.out.println("Name: " + fileHeader.getFileName());
            System.out.println("Compressed Size: " + fileHeader.getCompressedSize());
            System.out.println("Uncompressed Size: " + fileHeader.getUncompressedSize());
            System.out.println("CRC: " + fileHeader.getCrc32());
            System.out.println("************************************************************");
        }
    }

    @Test
    public void _分隔文件() throws ZipException {
        // 要打包的文件夹
        String path = "/Users/nohi/Downloads";
        String zip = path + "/1.zip";

        ZipFile zipFile = new ZipFile(zip);
        ArrayList filesToAdd = new ArrayList();
        filesToAdd.add(new File("/Users/nohi/Downloads/testfile.rmvb"));

        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

//        zipFile.createZipFile(filesToAdd, parameters, true, 65536);
        zipFile.createZipFile(filesToAdd, parameters, true, 1 * 1024 * 1024 * 100);
    }

}
