package cn.ken.im.bean;

import cn.ken.im.common.util.Snowflake;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/2 1:10
 */
@Data
public class ClientSession {
    
    private String sessionId = String.valueOf(Snowflake.nextId());
    
    private UserDTO userDTO;
    
    private Channel channel;
    
    private boolean isConnected;

    public static void loginSuccess(ChannelHandlerContext ctx, MessageProtos.Message pkg) {
        Channel channel = ctx.channel();
    }
}
