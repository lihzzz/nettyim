package protocol.command;

public interface Command {
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE = 4;
    Byte LOGINOUT_REQUEST  = 5;
    Byte LOGINOUT_RESPONSE = 6;
    Byte CREATE_GROUP_REQUEST = 7;
    Byte CREATE_GROUP_RESPONSE = 8;

    Byte JOINGROUP_REQUEST = 9;
    Byte JOINGROUP_RESPONSE = 10;

    Byte QUIT_GROUP_REQUEST = 11;
    Byte QUIT_GROUP_RESPONSE = 12;


    Byte LIST_GROUP_MEMBERS_REQUEST = 13;
    Byte LIST_GROUP_MEMBERS_RESPONSE = 14;


    Byte SEND_TO_GROUP_REQUEST = 15;
    Byte SEND_TO_GROUP_RESPONSE = 16;

    Byte HEARTBEAT_REQUEST = 17;
    Byte HEARTBEAT_RESPONSE = 18;
}
