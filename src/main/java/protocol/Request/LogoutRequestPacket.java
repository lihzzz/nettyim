package protocol.Request;

import lombok.Data;
import protocol.command.BasePacket;

import static protocol.command.Command.LOGINOUT_REQUEST;

@Data
public class LogoutRequestPacket extends BasePacket {

    @Override
    public Byte getCommand() {
        return LOGINOUT_REQUEST;
    }
}
