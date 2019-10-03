package protocol.Request;

import lombok.Data;
import protocol.command.BasePacket;

import static protocol.command.Command.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends BasePacket {
    private String message;
    private String toUserId;

    public MessageRequestPacket(String toUserId,String message) {
        this.toUserId = toUserId;
        this.message = message;
    }
    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
