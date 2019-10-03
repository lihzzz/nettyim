package protocol.command;

import io.netty.channel.Channel;
import protocol.Request.LoginRequestPacket;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand{

    LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

    @Override
    public void exec(Scanner sc, Channel channel) {
        System.out.print("输入用户名登录: ");
        String username = sc.nextLine();
        loginRequestPacket.setUsername(username);
        // 密码使用默认的
        loginRequestPacket.setPassword("pwd");
        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }
    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
