package protocol.response;

import lombok.Data;
import protocol.command.BasePacket;

import static protocol.command.Command.JOINGROUP_RESPONSE;

@Data
public class JoinGroupResponsePacket extends BasePacket{
    private boolean success;
    private String groupId;
    private String reason;
    private String name;
    private String sessionId;

    @Override
    public Byte getCommand() {
        return JOINGROUP_RESPONSE;
    }
}
