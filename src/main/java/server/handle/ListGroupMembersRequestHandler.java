package server.handle;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.Request.ListGroupMembersRequestPacket;
import protocol.response.ListGroupMembersResponsePacket;
import util.Session;
import util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable

public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    protected ListGroupMembersRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ListGroupMembersRequestPacket listGroupMembersRequestPacket) throws Exception {
        String groupId = listGroupMembersRequestPacket.getGroupId();
        ChannelGroup channels = SessionUtil.getChannelGroup(groupId);

        List<Session> sessionList = new ArrayList<>();

        for(Channel channel : channels){
            Session session = SessionUtil.getSession(channel);
            sessionList.add(session);
        }

        ListGroupMembersResponsePacket listGroupMembersResponsePacket = new ListGroupMembersResponsePacket();

        listGroupMembersResponsePacket.setGroupId(groupId);
        listGroupMembersResponsePacket.setSessionList(sessionList);

        channelHandlerContext.channel().writeAndFlush(listGroupMembersResponsePacket);
    }
}
