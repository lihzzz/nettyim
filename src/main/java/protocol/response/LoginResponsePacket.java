package protocol.response;

import lombok.Data;
import protocol.command.BasePacket;

import static protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends BasePacket {

    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
