package cn.ken.im.client.command;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/1 14:18
 */
public interface ClientCommand {
    
    void exec();
    
    String tip();
    
    default void printError() {
        System.out.println("输入有误，请重新输入");
    }
}
