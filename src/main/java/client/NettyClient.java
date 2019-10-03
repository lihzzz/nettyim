package client;

import client.handle.*;
import codec.PacketDecoder;
import codec.PacketEncoder;
import handle.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import protocol.Request.LoginRequestPacket;
import protocol.Request.MessageRequestPacket;
import protocol.command.ConsoleCommandManager;
import protocol.command.LoginConsoleCommand;
import protocol.command.PacketCodeC;
import protocol.response.CreateGroupResponsePacket;
import server.handle.IMIdleStateHandler;
import util.LoginUtil;
import util.SessionUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @author lh
 */
public class NettyClient {
    public final static int MAX_RETRY = 5;
    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ByteBuf delimiter = Unpooled.copiedBuffer("\t".getBytes());
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
//                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE,delimiter))
//                                .addLast(new FirstClientHandler());
                        socketChannel.pipeline()
                                .addLast(new IMIdleStateHandler())
                                .addLast(new HeartBeatTimerHandler())
                                .addLast(new PacketDecoder())
                                                .addLast(new LoginResponseHandler())
                                                .addLast(new AuthHandler())
                                                .addLast(new MessageResponseHandler())
                                .addLast(new GroupMessageResponseHandler())
                                .addLast(new ListGroupMembersResponseHandler())
                                .addLast(new JoinGroupResponseHandler())
                                .addLast(new LogoutResponseHandler())
                                .addLast(new CreateGroupResponseHandler())
                                .addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap,"127.0.0.1",8000 ,MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channle){
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner sc = new Scanner(System.in);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,1,1,TimeUnit.DAYS,new LinkedBlockingQueue<Runnable>(1));

        threadPoolExecutor.execute(() -> {
            while (!Thread.interrupted()){
                if(!SessionUtil.hasLogin(channle)){
                    loginConsoleCommand.exec(sc,channle);
                }else{
                    consoleCommandManager.exec(sc,channle);
                }
            }
        });

    }





}
