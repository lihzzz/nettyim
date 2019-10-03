package protocol.response;

import protocol.command.BasePacket;

import static protocol.command.Command.HEARTBEAT_RESPONSE;

public class HeartBeatResponsePacket extends BasePacket {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
