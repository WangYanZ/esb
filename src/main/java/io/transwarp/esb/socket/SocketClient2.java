package io.transwarp.esb.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author wangyan
 * @description
 * @date 2019/7/30 18:56
 */
public class SocketClient2 {
    public static void main(String[] args) throws IOException {
        socketClient2F();
    }
    public static void socketClient2F() throws IOException{
        final Logger logger = LoggerFactory.getLogger(SocketClient2.class);
        String host = "127.0.0.1";
        int port = 8091;
        Socket socket = new Socket(host, port);
        // 建立连接后获得输出流
        OutputStream outputStream = socket.getOutputStream();
        String message = "<root>" +
                "<a>http://localhost:8090/base/testRest</a>" +
                "<s>abc</s>" +
                "</root>";
        //首先需要计算得知消息的长度
        byte[] sendBytes = message.getBytes("UTF-8");
        //然后将消息的长度优先发送出去
        outputStream.write(sendBytes.length >>8);
        outputStream.write(sendBytes.length);
        //然后将消息再次发送出去
        outputStream.write(sendBytes);
        outputStream.flush();
        socket.shutdownOutput();


        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
            sb.append(new String(bytes, 0, len, "UTF-8"));
        }
        System.out.println(sb);
        outputStream.close();
        inputStream.close();
        socket.close();
    }

}
