package server.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.command.BasePacket;

import java.util.HashMap;
import java.util.Map;

import static protocol.command.Command.*;

public class IMHandler extends SimpleChannelInboundHandler<BasePacket> {
    public static final IMHandler INSTANCE = new IMHandler();
    private Map<Byte, SimpleChannelInboundHandler<? extends BasePacket>> handlerMap;

    private IMHandler() {
        handlerMap = new HashMap<>();

        handlerMap.put(MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        handlerMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(JOINGROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
        handlerMap.put(SEND_TO_GROUP_REQUEST, GroupMessageRequestHandler.INSTANCE);
        handlerMap.put(LOGINOUT_REQUEST, LogoutRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BasePacket basePacket) throws Exception {
        handlerMap.get(basePacket.getCommand()).channelRead(channelHandlerContext, basePacket);
    }
}
