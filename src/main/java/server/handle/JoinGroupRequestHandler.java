package server.handle;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.Request.JoinGroupRequestPacket;
import protocol.response.JoinGroupResponsePacket;
import util.Session;
import util.SessionUtil;

@ChannelHandler.Sharable

public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    protected JoinGroupRequestHandler(){}


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupRequestPacket joinGroupRequestPacket) throws Exception {
        String groupId = joinGroupRequestPacket.getGroupId();
        ChannelGroup channels = SessionUtil.getChannelGroup(groupId);
        channels.add(channelHandlerContext.channel());
        SessionUtil.bindChannelGroup(groupId,channels);
        Session session = SessionUtil.getSession(channelHandlerContext.channel());

        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        joinGroupResponsePacket.setSuccess(true);
        joinGroupResponsePacket.setGroupId(groupId);
        joinGroupResponsePacket.setName(session.getUserName());
        joinGroupResponsePacket.setSessionId(session.getUserId());
        System.out.print("[" + session.getUserId() + "],加入群聊"+"["+groupId + "]成功");

        channels.writeAndFlush(joinGroupResponsePacket);
        //channelHandlerContext.channel().writeAndFlush(joinGroupResponsePacket);


    }
}
