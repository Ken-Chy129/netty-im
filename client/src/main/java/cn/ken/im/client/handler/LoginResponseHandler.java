package cn.ken.im.client.handler;

import cn.ken.im.bean.MessageProtos;
import cn.ken.im.client.common.ResultCodeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/2 20:39
 */
@Slf4j
public class LoginResponseHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof MessageProtos.Message message) || message.getType() != MessageProtos.HeadType.LOGIN_RESPONSE) {
            return;
        }
        MessageProtos.LoginResponse loginResponse = message.getLoginResponse();
        if (loginResponse.getCode() == ResultCodeEnum.SUCCESS.getCode()) {
            log.info("登录成功");
            // 登录成功后移除登录响应处理器
            ctx.pipeline().remove(this);
        } else {
            log.error("登录失败" + loginResponse.getInfo());
        }
    }
}
