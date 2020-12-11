package nohi.test.zip;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import nohi.utils.ZipFileUtils;
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
public class TestZip {

    @Test
    public  void zipFile() throws ZipException {
        String path = "/Users/nohi/tmp/zip";
        String zip = path + "/1.zip";

        List<File> srcFiles = new ArrayList<>();
        srcFiles.add(new File(path + "/1.txt"));
        srcFiles.add(new File(path + "/2.txt"));
        srcFiles.add(new File(path + "/2的副本.txt"));
        ZipFileUtils.zipFile(zip, srcFiles);

        List<File> list = ZipFileUtils.upzipFile(zip, path + "/unzip");
        for (File file : list) {
            System.out.println(file.getName());
        }
        // deletefile(zip);
    }



}
