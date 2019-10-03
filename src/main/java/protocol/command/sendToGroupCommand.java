package protocol.command;

import io.netty.channel.Channel;
import protocol.Request.GroupMessageRequestPacket;

import java.util.Scanner;

public class sendToGroupCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个某个群组：");
        String toGroupId = scanner.next();
        String message = scanner.next();

        channel.writeAndFlush(new GroupMessageRequestPacket(toGroupId,message));

    }
}
