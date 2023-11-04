package cn.ken.im.codec;

import cn.ken.im.bean.MessageProtos;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/10/31 15:15
 */
public class ProtobufEncoder extends MessageToByteEncoder<MessageProtos.Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtos.Message msg, ByteBuf out) throws Exception {
        byte[] bytes = msg.toByteArray();
        int length = bytes.length;
        out.writeShort(length);
        // 省略魔数、版本号的写入
        out.writeBytes(bytes);
    }
}
