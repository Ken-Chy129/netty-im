package cn.ken.im.handler;

import cn.ken.im.bean.MessageProtos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/6 1:28
 */
public class MessageRedirectHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof MessageProtos.Message message)) {
            return;
        }
        if (message.getType() != MessageProtos.HeadType.MESSAGE_REQUEST) {
            return;
        }
        MessageProtos.MessageRequest messageRequest = message.getMessageRequest();
        System.out.printf("收到来自%s的消息:%s\n", messageRequest.getFromNick(), messageRequest.getContent());
    }
}
