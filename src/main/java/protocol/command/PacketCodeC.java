package protocol.command;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import protocol.Request.LoginRequestPacket;
import protocol.Request.MessageRequestPacket;
import protocol.response.LoginResponsePacket;
import protocol.response.MessageResponsePacket;
import serialize.Serializer;
import serialize.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

import static protocol.command.Command.*;

public class PacketCodeC {
    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private static final Map<Byte,Class<? extends BasePacket>> packetTypeMap;
    private static final Map<Byte,Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<Byte, Class<? extends BasePacket>>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<Byte, Serializer>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(),serializer);
    }

    public ByteBuf encode(ByteBuf byteBuf, BasePacket packet) {
        // 1. 创建 ByteBuf 对象
//        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        // 2. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public BasePacket decode(ByteBuf byteBuf){
        byteBuf.skipBytes(4);

        byteBuf.skipBytes(1);

        byte serializeAlgorithm = byteBuf.readByte();
        byte command = byteBuf.readByte();

        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends BasePacket> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if(requestType != null && serializer != null){
            return serializer.deserialize(requestType,bytes);
        }
        return null;
    }



    private Serializer getSerializer(byte serializeAlgorithm){
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends BasePacket> getRequestType(byte command){
        return packetTypeMap.get(command);
    }
}
