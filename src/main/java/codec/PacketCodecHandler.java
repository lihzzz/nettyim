package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageCodec;
import protocol.command.BasePacket;
import protocol.command.PacketCodeC;

import java.util.List;
@ChannelHandler.Sharable

public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, BasePacket> {

    public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();

    private PacketCodecHandler() {

    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, BasePacket basePacket, List<Object> list) throws Exception {
        ByteBuf byteBuf = channelHandlerContext.channel().alloc().ioBuffer();
        PacketCodeC.INSTANCE.encode(byteBuf, basePacket);
        list.add(byteBuf);


    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(PacketCodeC.INSTANCE.decode(byteBuf));


    }
}
