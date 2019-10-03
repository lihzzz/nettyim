package protocol.Request;

import lombok.Data;
import protocol.command.BasePacket;

import static protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;

@Data
public class ListGroupMembersRequestPacket extends BasePacket {
    private String groupId;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
