package server.handle;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.Request.QuitGroupRequestPacket;
import protocol.response.QuitGroupResponsePacket;
import util.SessionUtil;

@ChannelHandler.Sharable

public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    protected QuitGroupRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, QuitGroupRequestPacket quitGroupRequestPacket) throws Exception {
        // 1. 获取群对应的 channelGroup，然后将当前用户的 channel 移除
        String groupId = quitGroupRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(channelHandlerContext.channel());

        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        responsePacket.setGroupId(quitGroupRequestPacket.getGroupId());
        responsePacket.setSuccess(true);
        channelHandlerContext.channel().writeAndFlush(responsePacket);

    }
}
