package client;

import client.handle.LoginResponseHandler;
import client.handle.MessageResponseHandler;
import codec.PacketDecoder;
import codec.PacketEncoder;
import handle.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import protocol.Request.MessageRequestPacket;
import protocol.command.PacketCodeC;
import util.LoginUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author lh
 */
public class NettyClient {
    public final static int MAX_RETRY = 5;
    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new FirstClientHandler());
//                        socketChannel.pipeline().addLast(new PacketDecoder())
//                                                .addLast(new LoginResponseHandler())
//                                                .addLast(new MessageResponseHandler())
//                                                .addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap,"127.0.0.1",8000 ,MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry){
        bootstrap.connect(host,port).addListener(future -> {
            if(future.isSuccess()){
                System.out.println("连接成功!");
                startConsoleThread(((ChannelFuture)future).channel());
            }else if(retry == 0){
                System.err.println("重试次数已用完，放弃连接！");
            }else{
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(
                        ()->connect(bootstrap,host,port,retry-1),delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channle){
        new Thread(()->{
            while (!Thread.interrupted()){
                if(LoginUtil.hasLogin(channle)){
                    System.out.println("输入消息发送至服务端: ");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();


                    channle.writeAndFlush(new MessageRequestPacket(line));
                }
            }
        }).start();
    }


}
