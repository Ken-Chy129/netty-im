package cn.ken.im.client.command;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/1 14:22
 */
public class ClientLoginCommand implements ClientCommand {
    @Override
    public void exec() {
        String input = ClientCommandUtil.readCommand(tip());
        String[] split = input.split(":");
        if (split.length == 2) {
            
        } else {
            printError();
        }
    }

    @Override
    public String tip() {
        return "请输入用户信息(id:password)：";
    }


}
