package protocol.Request;

import lombok.Data;
import protocol.command.BasePacket;
import protocol.command.Command;


/**
 * @author lh
 */
@Data
public class LoginRequestPacket extends BasePacket {
    private String userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
