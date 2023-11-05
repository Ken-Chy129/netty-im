package cn.ken.im.client.command;

import java.util.Map;
import java.util.Scanner;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/1 15:01
 */
public class ClientCommandUtil {
    
    private static final Scanner SCANNER = new Scanner(System.in);
    
    private static final Map<Integer, ClientCommand> COMMAND_MAP = Map.of(
            0, new ClientMenuCommand(),
            1, new ClientLoginCommand(),
            2, new ClientChatCommand(),
            3, new ClientExitCommand());
    
    public static String readCommand(String tips) {
        System.out.print(tips);
        return SCANNER.nextLine();
    }
    
    public static ClientCommand getCommand(int index) {
        ClientCommand res = COMMAND_MAP.get(index);
        if (res == null) {
            throw new RuntimeException();
        }
        return res;
    }
}
