package cn.ken.im.client.handler;

import cn.ken.im.bean.ClientSession;
import cn.ken.im.bean.MessageProtos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/8 0:09
 */
@Slf4j
public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {
    
    // 心跳的时间间隔，单位为秒
    private static final int HEART_BEAT_INTERVAL = 50;

    // 在Handler业务处理器被加入到流水线时开始发送心跳数据包
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ClientSession session = ctx.channel().attr(ClientSession.CLIENT_SESSION).get();
        MessageProtos.MessageHeartBeat heartBeat =
                MessageProtos.MessageHeartBeat.newBuilder()
                        .setSeq(0)
                        .setJson("{\"from\":\"client\"}")
                        .setUid(session.getUserDTO().getUserId())
                        .build();
        MessageProtos.Message message = MessageProtos.Message.newBuilder()
                .setType(MessageProtos.HeadType.HEART_BEAT)
                .setSessionId(session.getSessionId())
                .setMessageHeartBeat(heartBeat)
                .build();
        heartBeat(ctx, message);
        super.handlerAdded(ctx);
    }

    private void heartBeat(ChannelHandlerContext ctx, MessageProtos.Message message) {
        // 提交在给定延迟后启用的一次性任务。
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                log.info("发送心跳消息给服务端");
                ctx.writeAndFlush(message);
                // 递归调用，发送下一次的心跳
                heartBeat(ctx, message);
            }
        }, HEART_BEAT_INTERVAL, TimeUnit.SECONDS);
    }

    // 接收到服务器的心跳回写
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof MessageProtos.Message message)) {
            super.channelRead(ctx, msg);
            return;
        }
        if (message.getType() == MessageProtos.HeadType.HEART_BEAT) {
            log.info("收到会写的心跳信息");
        } else {
            super.channelRead(ctx, msg);
        } 
    }
}
