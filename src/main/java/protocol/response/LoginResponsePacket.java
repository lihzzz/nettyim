package protocol.response;

import lombok.Data;
import protocol.command.BasePacket;

import static protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends BasePacket {

    private boolean success;
    private String reason;
    private String userId;
    private String userName;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
