package nohi.test.zip;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import org.junit.Test;

import java.util.List;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-08-19 11:25
 **/
public class TestZip4JUnzip {

    @Test
    public void unzip() throws Exception {
        // 要打包的文件夹
        String path = "/Users/nohi/tmp/zip";
        String zip = path + "/target/1.zip";

        // 生成的压缩文件
        ZipFile zipFile = new ZipFile(zip);
        System.out.println("zipFile.isEncrypted():" + zipFile.isEncrypted());

        //方法一
        zipFile.extractAll(path + "/unzip");

        //方法二
        if (zipFile.isEncrypted()) {
            zipFile.setPassword("123456");
        }
        List fileHeaderList = zipFile.getFileHeaders();
        for (int i = 0; i < fileHeaderList.size(); i++) {
            FileHeader fileHeader = (FileHeader)fileHeaderList.get(i);
            zipFile.extractFile(fileHeader, "c:\\ZipTest2\\");
        }

    }

    @Test
    public void unzip多文件() throws Exception {
        // 要打包的文件夹
        // 要打包的文件夹
        String path = "/Users/nohi/Downloads";
        String zip = path + "/unzip/1.zip";

        // 生成的压缩文件
        ZipFile zipFile = new ZipFile(zip);
        System.out.println("zipFile.isEncrypted():" + zipFile.isEncrypted());

        //方法一
        zipFile.extractAll(path + "/unzip");

    }
}
