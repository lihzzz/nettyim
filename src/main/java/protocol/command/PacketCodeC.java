package protocol.command;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import protocol.Request.*;
import protocol.response.*;
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
        packetTypeMap.put(LOGINOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(LOGINOUT_RESPONSE, LogoutResponsePacket.class);
        packetTypeMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(JOINGROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(JOINGROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(SEND_TO_GROUP_REQUEST, GroupMessageRequestPacket.class);
        packetTypeMap.put(SEND_TO_GROUP_RESPONSE, GroupMessageResponsePacket.class);
        packetTypeMap.put(HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);

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
        byteBuf.writeBytes("\t".getBytes());

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
        byte[] deli = new byte["\t".getBytes().length];
        byteBuf.readBytes(deli);

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
