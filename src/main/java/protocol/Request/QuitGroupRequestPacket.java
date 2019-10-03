package protocol.Request;

import lombok.Data;
import protocol.command.BasePacket;

import static protocol.command.Command.QUIT_GROUP_REQUEST;

@Data
public class QuitGroupRequestPacket extends BasePacket {
    private String groupId;
    @Override
    public Byte getCommand() {
        return QUIT_GROUP_REQUEST;
    }
}
