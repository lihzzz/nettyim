package protocol.Request;

import protocol.command.BasePacket;

import static protocol.command.Command.HEARTBEAT_REQUEST;

public  class HeartBeatRequestPacket extends BasePacket {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
