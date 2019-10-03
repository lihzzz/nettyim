package server;

import codec.PacketCodecHandler;
import codec.PacketDecoder;
import codec.PacketEncoder;
import handle.FirstServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import server.handle.*;
import util.Spliter;

import java.util.concurrent.atomic.AtomicInteger;

public class NettyServer {
    public static void main(String[] args) {
        AtomicInteger connectNum = new AtomicInteger(0);

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ByteBuf delimiter = Unpooled.copiedBuffer("\t".getBytes());


        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline()
                                .addLast(new IMIdleStateHandler())
                                .addLast(PacketCodecHandler.INSTANCE)
                                .addLast(LoginRequestHandler.INSTANCE)
                                .addLast(HeartBeatRequestHandler.INSTANCE)
                                .addLast(LogoutRequestHandler.INSTANCE)
                                .addLast(IMHandler.INSTANCE);

                        //nioSocketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE,delimiter)).addLast(new FirstServerHandler(connectNum));
                    }
                });
        bind(serverBootstrap,8000);
    }


    private static void bind(final ServerBootstrap serverBootstrap,final int port){
        serverBootstrap.bind(port).addListener(future -> {
            if(future.isSuccess()){
                System.out.println("端口[" + port + "]绑定成功!");
            }else{
                System.err.println("端口[" + port + "]绑定失败!");
                bind(serverBootstrap, port+1);
            }
        });
    }
}
