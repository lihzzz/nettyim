package protocol.response;

import lombok.Data;
import protocol.command.BasePacket;
import util.Session;

import static protocol.command.Command.SEND_TO_GROUP_RESPONSE;
@Data
public class GroupMessageResponsePacket extends BasePacket {

    String fromGroupId;
    String message;
    Session fromUser;
    @Override
    public Byte getCommand() {
        return SEND_TO_GROUP_RESPONSE;
    }
}
