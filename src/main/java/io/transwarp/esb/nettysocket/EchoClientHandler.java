package io.transwarp.esb.nettysocket;

/**
 * @author wy
 * @description
 * @date 2019/9/24 19:24
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 *客户端逻辑
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //当前通知Channel是活跃的时候，发送一条信息
        ctx.writeAndFlush(Unpooled.copiedBuffer("000532000526V10   NFTS  NFTS0022557240         2019092317200001010100GNFTS201909230325565000ITGS  1P033000911  01pw0130       030000                                                                          <?xml version=\\\"1.0\\\" encoding=\\\"GBK\\\"?>\" +\n" +
                "                    \"<root><head><TelrNum></TelrNum><TermiId></TermiId><OrgNum></OrgNum><AuthOrgNum></AuthOrgNum><ITGSHead><winame></winame><encysd>0000000000000000</encysd><keynum></keynum></ITGSHead><AccFlg>0</AccFlg><AuthTelrNum></AuthTelrNum><BusiCode>NFTS</BusiCode></head><body><costyp>0</costyp></body></root>",CharsetUtil.UTF_8));
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        //记录已接受消息的转储
        System.out.println("Client received :" +
                byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
