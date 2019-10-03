package protocol.response;

import lombok.Data;
import protocol.command.BasePacket;

import static protocol.command.Command.LOGINOUT_RESPONSE;

@Data
public class LogoutResponsePacket extends BasePacket {

    private boolean success;

    private String reason;
    @Override
    public Byte getCommand() {
        return LOGINOUT_RESPONSE;
    }
}
