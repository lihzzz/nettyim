package client.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.response.GroupMessageResponsePacket;
import server.handle.GroupMessageRequestHandler;
import util.Session;

public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupMessageResponsePacket groupMessageResponsePacket) throws Exception {
        String fromGroupId = groupMessageResponsePacket.getFromGroupId();
        Session fromUser = groupMessageResponsePacket.getFromUser();
        System.out.println("收到群[" + fromGroupId + "]中[" + fromUser + "]发来的消息：" + groupMessageResponsePacket.getMessage());

    }
}
