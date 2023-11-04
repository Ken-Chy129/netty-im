package cn.ken.im.bean;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/2 1:10
 */
public class ClientSession {
    
    private String sessionId;
    
    private UserDTO userDTO;
    
    private Channel channel;

    public static void loginSuccess(ChannelHandlerContext ctx, MessageProtos.Message pkg) {
        Channel channel = ctx.channel();
    }
}
