package niosocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
 
 
public class Constants {
	public static int readCapacity = 1024;
	public static Charset charset = Charset.forName("UTF-8");
	public static CharsetEncoder encoder = charset.newEncoder();
	public static CharsetDecoder decoder = charset.newDecoder();
	public static ByteBuffer encoder(CharBuffer charbuffer) throws IOException{
		return encoder.encode(charbuffer);
	}
	public static CharBuffer decoder(ByteBuffer buffer) throws IOException{
		return decoder.decode(buffer);
	}
}