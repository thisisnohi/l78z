package nohi.test.ftp;

import nohi.test.ftp.old.FTPUtil;
import nohi.test.ftp.old.SSLSessionReuseFTPSClient;
import org.junit.Test;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-09-21 16:11
 **/
public class Testftp {
    private FtpClient ftpClient;

   public Testftp(){
         /*
        使用默认的端口号、用户名、密码以及根目录连接FTP服务器
         */
//        this.connectServer("192.168.56.1", 22, "sftp", "sftp123", "/Users/sftp/ftp_files");
//        this.connectServer("192.168.56.101", 21, "xetl", "xetl", "/home/xetl");
    }

    public void connectServer(String ip, int port, String user, String password, String path) {
        try {
            /* ******连接服务器的两种方法*******/
            System.out.println("====before create====");
            ftpClient = FtpClient.create();
            System.out.println("====after create====");
            try {
                SocketAddress addr = new InetSocketAddress(ip, port);
                System.out.println("====before connect====");
                ftpClient.setConnectTimeout(10*1000); // 10s，如果超过就判定超时了
                ftpClient.connect(addr);
                System.out.println("====after connect====");
                ftpClient.login(user, password.toCharArray());
                System.out.println("login success!");
                if (path.length() != 0) {
                    //把远程系统上的目录切换到参数path所指定的目录
                    ftpClient.changeDirectory(path);
                }
            } catch (FtpProtocolException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /**
     * 关闭连接
     */
    public void closeConnect() {
        try {
            ftpClient.close();
            System.out.println("disconnect success");
        } catch (IOException ex) {
            System.out.println("not disconnect");
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /**
     * 上传文件
     * @param localFile 本地文件
     * @param remoteFile 远程文件
     */
    public void upload(String localFile, String remoteFile) {
        File file_in = new File(localFile);
        System.out.println("====upload====");
        try(OutputStream os = ftpClient.putFileStream(remoteFile);
            FileInputStream is = new FileInputStream(file_in)) {
            byte[] bytes = new byte[1024];
            int c;
            while ((c = is.read(bytes)) != -1) {
                os.write(bytes, 0, c);
            }
            System.out.println("upload success");
        } catch (IOException ex) {
            System.out.println("not upload");
            ex.printStackTrace();
        } catch (FtpProtocolException e) {
            e.printStackTrace();
        }
        System.out.println("====upload.over====");
    }

    /**
     * 下载文件。获取远程机器上的文件filename，借助TelnetInputStream把该文件传送到本地。
     * @param remoteFile 远程文件路径(服务器端)
     * @param localFile 本地文件路径(客户端)
     */
    public void download(String remoteFile, String localFile) {
        File file_in = new File(localFile);
        try(InputStream is = ftpClient.getFileStream(remoteFile);
            FileOutputStream os = new FileOutputStream(file_in)){

            byte[] bytes = new byte[1024];
            int c;
            while ((c = is.read(bytes)) != -1) {
                os.write(bytes, 0, c);
            }
            System.out.println("download success");
        } catch (IOException ex) {
            System.out.println("not download");
            ex.printStackTrace();
        }catch (FtpProtocolException e) {
            e.printStackTrace();
        }
    }

    public void main11(String agrs[]) {
        Testftp fu = new Testftp();

        System.out.println("====1====");

        //上传测试
        String localfile = "/Users/nohi/tmp/sftp/1.txt";
        String remotefile = "nohi_1.txt";                //上传
        fu.upload(localfile, remotefile);
        fu.closeConnect();
        System.out.println("====closeConnect====");
    }


    @Test
    public void testSftpCleint() throws IOException {
        String directory= "/home/xetl";
        String keyWord = "";

        String host = "192.168.56.101";
        int port = 21;
        String userName = "xetl";
        String password = "xetl";

        List<File>  list = FTPUtil.getFiles(host, port, userName, password, directory, keyWord);
        System.out.println("list:" + (null == list ? "is null" : list.size()));
        for (File file : list) {
            System.out.println(file.getName());
        }
    }
}
