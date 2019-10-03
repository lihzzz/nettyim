package server.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.Request.HeartBeatRequestPacket;
import protocol.response.HeartBeatResponsePacket;

public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {

    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HeartBeatRequestPacket heartBeatRequestPacket) throws Exception {
        System.out.println("HeartBeat---server");
        channelHandlerContext.writeAndFlush(new HeartBeatResponsePacket());
    }
}
