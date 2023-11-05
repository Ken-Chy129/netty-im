package cn.ken.im.client.sender;

import cn.ken.im.bean.ClientSession;
import cn.ken.im.bean.MessageProtos;
import cn.ken.im.bean.UserDTO;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/2 1:16
 */
@Data
@Slf4j
public class BaseSender {
    
    private UserDTO user;
    
    private ClientSession session;

    public BaseSender() {
    }

    public BaseSender(UserDTO user, ClientSession session) {
        this.user = user;
        this.session = session;
    }

    public void sendMsg(MessageProtos.Message message) {
        Channel channel = session.getChannel();
        ChannelFuture channelFuture = channel.writeAndFlush(message);
        channelFuture.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                log.info(message.getType() == MessageProtos.HeadType.LOGIN_REQUEST ? 
                        "登录成功":
                        "发送成功");
            } else {
                log.warn("发送失败");
            }
        });
    }
    
}
