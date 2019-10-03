package protocol.response;

import lombok.Data;
import protocol.command.BasePacket;
import util.Session;

import java.util.List;

import static protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMembersResponsePacket extends BasePacket {
    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
