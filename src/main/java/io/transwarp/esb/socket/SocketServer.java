package io.transwarp.esb.socket;

import io.transwarp.esb.config.ApplicationProperties;
import io.transwarp.esb.resttemplate.RestTemplateCallSophon;
import io.transwarp.esb.service.DataFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author wangyan
 * @description 服务端socket
 * @date 2019/7/30 14:57
 */
@Service
public class SocketServer {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Value("${port}")
    private Integer port;
    private boolean started;
    private ServerSocket serverSocket;
    //使用多线程，需要线程池，防止并发过高时创建过多线程耗尽资源
    private ExecutorService threadPool = Executors.newCachedThreadPool();
    @Autowired
    ApplicationProperties properties;

    public void start(){
        start(properties.getSocketPort());
    }
    private void start(Integer port){
        try {
            serverSocket =  new ServerSocket(port == null ? this.port : port);
            started = true;
            logger.info("Socket服务已启动，占用端口： {}", serverSocket.getLocalPort());
        }catch (IOException e){
            logger.error("端口异常信息",e);
            System.exit(0);
        }
        while (started){
            try {
                Socket socket = serverSocket.accept();
                Runnable runnable = () -> {
                    try {
                        StringBuilder xmlString = onMessage(socket);
                        System.out.println(xmlString);     //需要注意这里返回的数据是	"\t<a>12</a>"
                        String xmlStringNew = DataFormatUtil.XmlReplaceBlank(xmlString.deleteCharAt(1).toString().trim());
                        //上传的xml转为json
                        String jsonStringToSophon = DataFormatUtil.XmlToJson(xmlStringNew);
                        System.out.println(jsonStringToSophon);
                       //调用resttemplate处理并返回json
                        String jsonStringFromSophon = RestTemplateCallSophon.callSophonUrl(jsonStringToSophon);
                        System.out.println(jsonStringFromSophon);
                        // 再转为xml
                        String xmlStringToEsb =DataFormatUtil.JsonToXml(jsonStringFromSophon);
                        //返回客户端
                        sendMessage(socket,xmlStringToEsb);
                        socket.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                };
                //可以接收线程处理结果
                Future future = threadPool.submit(runnable);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static StringBuilder onMessage(Socket socket){
        byte[] bytes = new byte[1024];
        int len;
        try{
            // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
            InputStream inputStream = socket.getInputStream();
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            socket.shutdownInput();
            return sb;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    private static void sendMessage(Socket socket,String message){
        try {
            //向客户端返回数据
            OutputStream outputStream = socket.getOutputStream();
            //首先需要计算得知消息的长度
            byte[] sendBytes = message.getBytes("UTF-8");
            //然后将消息的长度优先发送出去
            outputStream.write(sendBytes.length >> 8);
            outputStream.write(sendBytes.length);
            //然后将消息再次发送出去
            outputStream.write(sendBytes);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
