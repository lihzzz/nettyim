package client.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.response.LogoutResponsePacket;
import util.SessionUtil;

public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogoutResponsePacket logoutResponsePacket) throws Exception {
        System.out.println("LogoutResponseHandler");
        SessionUtil.unBindSession(channelHandlerContext.channel());
    }
}
