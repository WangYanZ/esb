package niosocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
 
public class NioServer extends Thread{
	
	public static void main(String[] args) {
		NioServer server = new NioServer("127.0.0.1", 23000);
		server.start();
	}
	
	private InetSocketAddress address;
	private ByteBuffer readBuffer;
	private Selector selector;
	public NioServer(){}
	public NioServer(String localhost,int port){
		address = new InetSocketAddress(localhost, port);
	}
	
	private void init(){
		readBuffer = ByteBuffer.allocate(Constants.readCapacity);
		 try {
			selector = Selector.open();
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			serverChannel.bind(address);
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);  	//注册到信道上
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void run() {
		try {
			init();
			while(true){
				int num = selector.select();
				if(num>0){
					Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
					while(iterator.hasNext()){
						SelectionKey sk = iterator.next();
						handlerKey(sk);
						iterator.remove();    //注意将键移除
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void handlerKey(SelectionKey sk) {
		
		if(sk.isAcceptable()){
			accept(sk);
		}else if(sk.isReadable()){
			readMsg(sk);
		}
		
	}
	public void accept(SelectionKey sk) {
		try {
			ServerSocketChannel serverChannel	= (ServerSocketChannel)sk.channel();
			SocketChannel channel = serverChannel.accept();
			if(channel!=null){
				channel.configureBlocking(false);  //将信道一定注册为非阻塞的
				channel.register(sk.selector(), SelectionKey.OP_READ);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void readMsg(SelectionKey sk) {
		SocketChannel channel = null;
		try {
			channel = (SocketChannel) sk.channel();
			readBuffer.clear();
			int num = channel.read(readBuffer);
			if(num>0){
				readBuffer.flip(); //将数据取出来
				//System.out.println(" 服务器接受到的数据:"+num+"字节");
				CharBuffer buffer =  Constants.decoder(readBuffer);
				String recevieMsg = buffer.toString();
				System.out.println("服务器收到数据 ："+recevieMsg);
				String answer = getAnswer(recevieMsg);
				
				CharBuffer cbuffer = CharBuffer.wrap(answer.toCharArray());
				channel.write(Constants.encoder(cbuffer));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void writeMsg(SelectionKey sk) throws IOException{
		
	}
	
	private String getAnswer(String question){  
        String answer = null;  
        switch(question){  
        case "ni":  
            answer = "你";  
            break;  
        case "hao":  
            answer = "好";  
            break;  
        case "I":  
            answer = "我是";  
            break;  
        case "lan":  
            answer = "懒";  
            break;  
        case "lu":  
            answer = "马户";  
            break;  
        default:  
                answer = "请输入 who， 或者what， 或者where";  
        }  
           
        return answer;  
    }  
}