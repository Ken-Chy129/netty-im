package cn.ken.im.handler;

import cn.ken.im.bean.MessageProtos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/7 23:26
 */
@Slf4j
public class HeartBeatServerHandler extends IdleStateHandler {

    private static final int READ_IDLE_GAP = 150; // 最大空闲时间(s)

    public HeartBeatServerHandler() {
        super(READ_IDLE_GAP, 0, 0, TimeUnit.SECONDS);
    }
    
    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        log.info("{}秒内未读到数据，关闭连接", READ_IDLE_GAP);
        // 其他处理，如关闭会话
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 判断消息实例
        if (!(msg instanceof MessageProtos.Message message)) {
            super.channelRead(ctx, msg);
            return;
        }
        if (message.getType() == MessageProtos.HeadType.HEART_BEAT) {
            if (ctx.channel().isActive()) {
                // 将心跳数据包直接回给客户端
                ctx.writeAndFlush(msg);
            }
        }
        super.channelRead(ctx, msg);
    }
}
