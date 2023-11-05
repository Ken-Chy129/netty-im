package cn.ken.im.client.command;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/1 14:21
 */
public class ClientMenuCommand implements ClientCommand {
    
    @Override
    public void exec() {
        String input = ClientCommandUtil.readCommand(tip());
        try {
            ClientCommand clientCommand = ClientCommandUtil.getCommand(Integer.parseInt(input));
            clientCommand.exec();
        } catch (Exception e) {
            printError();
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
