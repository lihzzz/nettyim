package protocol.response;

import lombok.Data;
import protocol.command.BasePacket;
import protocol.command.ConsoleCommand;

import static protocol.command.Command.QUIT_GROUP_RESPONSE;
@Data
public class QuitGroupResponsePacket extends BasePacket {
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_RESPONSE;
    }
}
