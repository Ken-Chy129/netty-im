package cn.ken.im.handler;

import cn.ken.im.bean.ClientSession;
import cn.ken.im.bean.MessageProtos;
import cn.ken.im.bean.UserDTO;
import cn.ken.im.common.SessionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/4 15:28
 */
@Slf4j
public class LoginRequestHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof MessageProtos.Message message)) {
            return;
        }
        if (message.getType() == MessageProtos.HeadType.LOGIN_REQUEST) {
            MessageProtos.LoginRequest loginRequest = message.getLoginRequest();
            log.info("用户{}登录", loginRequest.getUid());
            ClientSession clientSession = new ClientSession();
            clientSession.setConnected(true);
            clientSession.setSessionId(message.getSessionId());
            clientSession.setChannel(ctx.channel());
            clientSession.setUserDTO(UserDTO.fromMsg(loginRequest));
            SessionManager.addSession(clientSession);
        }
        super.channelRead(ctx, msg);
    }
}
