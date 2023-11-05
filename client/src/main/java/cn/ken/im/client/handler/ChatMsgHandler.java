package cn.ken.im.client.handler;

import cn.ken.im.bean.MessageProtos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/2 20:50
 */
public class ChatMsgHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageProtos.Message messageProtos = (MessageProtos.Message) msg;
        // 登录请求
        if (messageProtos.getType() == MessageProtos.HeadType.LOGIN_REQUEST) {
            super.channelRead(ctx, msg);
            return;
        }
        // 聊天消息
        MessageProtos.MessageRequest messageRequest = messageProtos.getMessageRequest();
        System.out.printf("收到消息(来自%s):%s\n", 
                messageRequest.getFrom(), 
                messageRequest.getContent());
    }
}
