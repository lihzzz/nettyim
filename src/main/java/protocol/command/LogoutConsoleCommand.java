package protocol.command;

import io.netty.channel.Channel;
import protocol.Request.LogoutRequestPacket;

import java.util.Scanner;

public class LogoutConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("LogoutConsoleCommand");
        LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(logoutRequestPacket);
    }
}
