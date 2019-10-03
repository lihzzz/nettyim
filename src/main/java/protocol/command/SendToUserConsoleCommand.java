package protocol.command;

import io.netty.channel.Channel;
import protocol.Request.MessageRequestPacket;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个某个用户：");
        String userId = scanner.next();
        String message = scanner.next();

        channel.writeAndFlush(new MessageRequestPacket(userId,message));
    }
}
