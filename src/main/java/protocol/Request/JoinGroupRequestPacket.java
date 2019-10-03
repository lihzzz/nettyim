package protocol.Request;

import lombok.Data;
import protocol.command.BasePacket;

import static protocol.command.Command.JOINGROUP_REQUEST;
@Data
public class JoinGroupRequestPacket extends BasePacket {

    String groupId;
    @Override
    public Byte getCommand() {
        return JOINGROUP_REQUEST;
    }
}
