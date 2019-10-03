package server.handle;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.Request.LoginRequestPacket;
import protocol.response.LoginResponsePacket;
import util.LoginUtil;
import util.Session;
import util.SessionUtil;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    //private AtomicInteger countNum;
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();
    protected LoginRequestHandler() {
    }

//    public LoginRequestHandler(AtomicInteger countNum){
//        this.countNum = countNum;
//    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(new Date() + ": 收到客户端登录请求……");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setSuccess(true);
        loginResponsePacket.setUserName(loginRequestPacket.getUsername());
        String userId = randomUserId();
        loginResponsePacket.setUserId(userId);
        SessionUtil.bindSession(new Session(userId,loginRequestPacket.getUsername()),channelHandlerContext.channel());
        if(SessionUtil.hasLogin(channelHandlerContext.channel())){
///            this.countNum.incrementAndGet();
            System.out.println(new Date() + ": 登录成功!");
//            System.out.println("current connected " + this.countNum.get());
        }else{
            System.out.println(new Date() + ": 登录失败!");
        }
        // 登录响应
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception{
        super.channelUnregistered(ctx);
//        this.countNum.decrementAndGet();
//        System.out.println("current connected" + this.countNum.get());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        SessionUtil.unBindSession(ctx.channel());
    }
    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }


}
