package protocol.response;

import lombok.Data;
import protocol.command.BasePacket;

import static protocol.command.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends BasePacket {

    private String fromUserId;
    private String fromUserName;
    private String message;
    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
