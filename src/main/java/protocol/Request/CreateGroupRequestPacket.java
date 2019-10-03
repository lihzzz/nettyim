package protocol.Request;

import lombok.Data;
import protocol.command.BasePacket;

import java.util.List;

import static protocol.command.Command.CREATE_GROUP_REQUEST;
import static protocol.command.Command.CREATE_GROUP_RESPONSE;

@Data
public class CreateGroupRequestPacket extends BasePacket {
    private List<String> userIdList;
    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
