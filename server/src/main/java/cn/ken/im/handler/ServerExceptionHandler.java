package cn.ken.im.handler;

import cn.ken.im.common.exception.BusinessException;
import cn.ken.im.common.exception.InvalidFrameException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/4 15:29
 */
@Slf4j
public class ServerExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof BusinessException) {
            //业务异常，通知客户端

        } else if (cause instanceof InvalidFrameException) {
            //报文异常
            log.error(cause.getMessage());
            //服务器做适当处理
        } else {
            //其他异常
            //捕捉异常信息
            log.error(cause.getMessage());
            ctx.close();

            //开始重连
        }
    }
}
