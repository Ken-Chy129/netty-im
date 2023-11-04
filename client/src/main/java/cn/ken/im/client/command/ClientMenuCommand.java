package cn.ken.im.client.command;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/1 14:21
 */
public class ClientCommandMenu implements ClientCommand {
    
    @Override
    public void exec() {
        String command = ClientCommandUtil.readCommand(tip());
        if (command.equals("1")) {
            .exec();
        } else if (command.equals("2")) {
            ClientCommandChat.exec();
        } else if (command.equals("3")) {
            ClientCommandExit.exec();
        } else {
            System.out.println("输入错误，请重新输入！");
        }
    }

    @Override
    public String tip() {
        return "[menu] " +
                "1->登录 | " +
                "2->聊天 | " +
                "3->退出 | ";
    }
}
