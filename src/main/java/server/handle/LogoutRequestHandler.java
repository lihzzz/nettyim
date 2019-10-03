package server.handle;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.Request.LogoutRequestPacket;
import protocol.response.LogoutResponsePacket;
import util.SessionUtil;

@ChannelHandler.Sharable

public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    protected LogoutRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogoutRequestPacket logoutRequestPacket) throws Exception {
        SessionUtil.unBindSession(channelHandlerContext.channel());
        System.out.println("LogoutRequestHandler");

        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        channelHandlerContext.channel().writeAndFlush(logoutResponsePacket);
    }
}
