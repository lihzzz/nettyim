package server;

import codec.PacketDecoder;
import codec.PacketEncoder;
import handle.FirstServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import server.handle.LoginRequestHandler;
import server.handle.MessageRequestHandler;
import util.Spliter;

public class NettyServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
//                        nioSocketChannel.pipeline().addLast(new PacketDecoder())
//                                                   .addLast(new LoginRequestHandler())
//                                                   .addLast(new MessageRequestHandler())
//                                                   .addLast(new PacketEncoder());
                        nioSocketChannel.pipeline().addLast(new Spliter()).addLast(new FirstServerHandler());
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
