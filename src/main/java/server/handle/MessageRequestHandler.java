package server.handle;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.Request.MessageRequestPacket;
import protocol.response.MessageResponsePacket;
import util.Session;
import util.SessionUtil;

import java.util.Date;

@ChannelHandler.Sharable

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    protected MessageRequestHandler(){}


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {

        Session session = SessionUtil.getSession(channelHandlerContext.channel());


        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());

        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        if(toUserChannel != null && SessionUtil.hasLogin(toUserChannel)){
            toUserChannel.writeAndFlush(messageResponsePacket);
        }else{
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
        }

    }
}
