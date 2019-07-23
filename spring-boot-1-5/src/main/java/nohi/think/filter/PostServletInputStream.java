/**
 * 
 */
package nohi.think.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

/**
 *类名称：PostServletInputStream
 *创建人：yujianyong
 *创建日期：2018年5月16日  上午10:25:47
 *修改人：yujianyong
 *修改时间：2018年5月16日 上午10:25:47
 *修改备注：
 *
 */
public class PostServletInputStream  extends ServletInputStream {
    private InputStream inputStream;
    private String body ;//解析json之后的文本

    public PostServletInputStream(String body) throws IOException {
        this.body=body;
        inputStream = null;
    }


    private InputStream acquireInputStream() throws IOException {
        if(inputStream == null) {
            inputStream = new ByteArrayInputStream(body.getBytes());//通过解析之后传入的文本生成inputStream以便后面control调用
        }

        return inputStream;
    }


    public void close() throws IOException {
        try {
            if(inputStream != null) {
                inputStream.close();
            }
        }
        catch(IOException e) {
            throw e;
        }
        finally {
            inputStream = null;
        }
    }


    public int read() throws IOException {
        return acquireInputStream().read();
    }


    public boolean markSupported() {
        return false;
    }


    public synchronized void mark(int i) {
        throw new UnsupportedOperationException("mark not supported");
    }


    public synchronized void reset() throws IOException {
        throw new IOException(new UnsupportedOperationException("reset not supported"));
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }
}

