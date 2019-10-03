package client.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.response.LoginResponsePacket;
import util.LoginUtil;
import util.Session;
import util.SessionUtil;

import java.util.Date;
import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    String userId;
    String userName;

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        // 创建登录对象
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//        loginRequestPacket.setUserId(UUID.randomUUID().toString());
//        loginRequestPacket.setUsername("flash");
//        loginRequestPacket.setPassword("pwd");
//
//        // 写数据
//        ctx.channel().writeAndFlush(loginRequestPacket);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {
        String userId = loginResponsePacket.getUserId();
        String userName = loginResponsePacket.getUserName();
        this.userId = userId;
        this.userName = userName;

        if(loginResponsePacket.isSuccess()){
            System.out.println("[" + userName + "]登录成功，userId 为: " + loginResponsePacket.getUserId());
            SessionUtil.bindSession(new Session(userId, userName), channelHandlerContext.channel());
        }else {
            System.out.println("[" + userName + "]登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
    }
}
