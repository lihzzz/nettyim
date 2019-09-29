package protocol.Request;

import lombok.Data;
import protocol.command.BasePacket;

import static protocol.command.Command.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends BasePacket {
    private String message;

    public MessageRequestPacket(String message) {
        this.message = message;
    }
    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
