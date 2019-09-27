package io.transwarp.esb.nettysocket;

/**
 * @author wy
 * @description
 * @date 2019/9/24 19:25
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.transwarp.esb.service.CallSophonService;

//业务逻辑处理
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    //每个信息入站都会调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        //将消息记录到控制台
        String xmlString = in.toString(CharsetUtil.UTF_8);
        System.out.println("server received:" + xmlString);
        CallSophonService callSophonService = new CallSophonService();
        xmlString = new StringBuffer(xmlString).substring(6);
        String xmlStringToEsb = callSophonService.toEsb(xmlString);
        ctx.write(Unpooled.buffer().writeBytes(xmlStringToEsb.getBytes()));  //将接受到的消息写给发送者
        System.out.println("send finish");

    }

    //通知处理器最后的channelread是当前批处理中的最后一条信息调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将未决消息冲刷到远程节点，并关闭该Channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();  //打印异常栈追踪
        ctx.close(); //关闭该channel
    }
}
