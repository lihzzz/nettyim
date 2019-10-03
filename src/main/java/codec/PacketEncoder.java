package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import protocol.command.BasePacket;
import protocol.command.PacketCodeC;

public class PacketEncoder extends MessageToByteEncoder<BasePacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, BasePacket packet, ByteBuf out) {
        PacketCodeC.INSTANCE.encode(out, packet);
    }
}
