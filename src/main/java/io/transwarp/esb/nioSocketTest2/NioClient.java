package niosocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
 

public class NioClient implements Runnable{
	
	public static void main(String[] args) {
		for(int i=0;i<1;i++){
			Runnable client = new NioClient("127.0.0.1", 23000);
			Thread t = new Thread(client);
			t.start();
		}
	}
	
	private BlockingQueue<String >words;
	private Random random;
	private InetSocketAddress address;
	
	public NioClient (String localname,int port){
		address = new InetSocketAddress(localname, port);
	}
	
	private void init(){
		words = new ArrayBlockingQueue<>(5);
		try {  
            words.put("ni");  
            words.put("hao");  
            words.put("I");  
            words.put("lan");  
            words.put("lu");  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }    
        random = new Random();  
	}
	
	
	 private String getWord(){  
	        return words.poll();  
	 }  
	 
	@Override
	public void run() {
		init();
		SocketChannel channel = null;
		Selector selector = null;
		try {
			selector = Selector.open();
			channel = SocketChannel.open();
			channel.configureBlocking(false);
			channel.connect(address);
			channel.register(selector, SelectionKey.OP_CONNECT);
			boolean isOver = false;
			
			while(!isOver){
				int num = selector.select();
				if(num>0){
					Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
					while(iterator.hasNext()){
						SelectionKey sk = iterator.next();
						
						if(sk.isConnectable()){
							if(channel.isConnectionPending()){
								if(channel.finishConnect()){
									sk.interestOps(SelectionKey.OP_READ);     //只有成功连接，才能注册read事件
									channel.write(Constants.encoder(CharBuffer.wrap(getWord())));  //向服务端写数据
								}else{
									sk.cancel();// 将这个键注销
								}
							}
						}else if(sk.isReadable()){
							ByteBuffer buffer = ByteBuffer.allocate(1024);
							int bnum =  channel.read(buffer);
							if(bnum>0){
								buffer.flip();
								CharBuffer cbuffer = Constants.decoder(buffer);
								System.out.println(Thread.currentThread().getId()+"客户端收到数据:"+cbuffer.toString());
								String word = getWord();  
		                        if(word != null){  
		                            channel.write(Constants.encoder(CharBuffer.wrap(word)));  
		                        }  
		                        else{  
		                            isOver = true;  
		                        }  
		                        try {
									Thread.sleep(5000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
						iterator.remove();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(channel!=null){
					channel.close();
				}
				if(selector!=null){
					selector.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}