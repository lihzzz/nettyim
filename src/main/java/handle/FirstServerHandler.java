package handle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    private AtomicInteger connectNum;

    public FirstServerHandler(AtomicInteger connectNum){
        this.connectNum = connectNum;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception{
        super.channelRegistered(ctx);
        connectNum.incrementAndGet();
        System.out.println("current connected " + connectNum.get());
    }
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception{
        super.channelUnregistered(ctx);
        connectNum.decrementAndGet();
        System.out.println("current connected" + connectNum.get());
    }
}