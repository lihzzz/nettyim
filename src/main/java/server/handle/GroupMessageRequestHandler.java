package server.handle;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.Request.GroupMessageRequestPacket;
import protocol.response.GroupMessageResponsePacket;
import util.SessionUtil;

@ChannelHandler.Sharable

public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    protected GroupMessageRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupMessageRequestPacket groupMessageRequestPacket) throws Exception {
        String groupId = groupMessageRequestPacket.getToGroupId();

        String message = groupMessageRequestPacket.getMessage();
        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setFromGroupId(groupId);
        groupMessageResponsePacket.setMessage(message);
        groupMessageResponsePacket.setFromUser(SessionUtil.getSession(channelHandlerContext.channel()));

        ChannelGroup channels = SessionUtil.getChannelGroup(groupId);
        channels.writeAndFlush(groupMessageResponsePacket);
    }
}
