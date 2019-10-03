package protocol.Request;

import lombok.Data;
import protocol.command.BasePacket;

import static protocol.command.Command.SEND_TO_GROUP_REQUEST;

@Data
public class GroupMessageRequestPacket extends BasePacket {

    String toGroupId;
    String message;

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }


    @Override
    public Byte getCommand() {
        return SEND_TO_GROUP_REQUEST;
    }
}
