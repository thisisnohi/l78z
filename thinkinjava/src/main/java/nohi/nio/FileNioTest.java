package nohi.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

public class FileNioTest {

  @Test
  public void testStringNio() {

	String newData = "New String to write to file..." + System.currentTimeMillis();

	ByteBuffer buf = ByteBuffer.allocate( 48 );
	buf.clear();
	buf.put( newData.getBytes() );

	buf.flip();

	try {
	  RandomAccessFile aFile = new RandomAccessFile( "src/writer.txt", "rw" );
	  FileChannel inChannel = aFile.getChannel();
	  while (buf.hasRemaining()) {
		inChannel.write( buf );
	  }
	  inChannel.truncate( 20 );
	  inChannel.close();
	} catch (Exception e) {
	  e.printStackTrace();
	}

  }

  @Test
  public void testFielExists() {
	File file = new File( "src/main/java/req.txt" );
	System.out.println( "file.exists():" + file.exists() );
	System.out.println( "file.getAbsolutePath():" + file.getAbsolutePath() );
  }


  @Test
  public void testNioReadFile() throws IOException {
	File file = new File( "src/main/java/req.txt" );
	System.out.println( "file.exists():" + file.exists() );

	Assert.assertEquals("文件不存在",true,file.exists());

	RandomAccessFile aFile = new RandomAccessFile( file, "rw" );
	FileChannel inChannel = aFile.getChannel();
	ByteBuffer buf = ByteBuffer.allocate( 1024 );
	int bytesRead = -1;
	StringBuffer sb = new StringBuffer();
	while ((bytesRead = inChannel.read( buf )) != -1) {
	  System.out.println( "bytesRead:" + bytesRead );
	  buf.flip();
	  sb.append( Charset.forName( "UTF-8" ).decode( buf ) );
	  buf.clear();
	  bytesRead = inChannel.read( buf );
	}
	aFile.close();
	System.out.println( "------------------" );
	System.out.println( sb.toString() );

  }

  /**
   * @param args
   */
  public static void main(String[] args) {

  }

}
