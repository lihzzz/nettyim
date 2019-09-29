package protocol.command;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lh
 */
@Setter
@Getter
public abstract class BasePacket {
    private Byte version = 1;
    public abstract Byte getCommand();
}
