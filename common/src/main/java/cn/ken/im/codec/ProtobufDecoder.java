package cn.ken.im.codec;

import cn.ken.im.bean.MessageProtos;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/10/31 18:59
 */
public class ProtobufDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 标记当前的读指针的位置
        in.markReaderIndex();
        // 判断包头的长度
        if (in.readableBytes() < 2) {
            return; // 不够包头
        }
        int length = in.readShort();
        if (length < 0) {
            ctx.close(); // 非法数据
        }
        if (length > in.readableBytes()) {
            // 如果读到的消息体长度小于消息的该有的长度，则重置读取位置
            in.resetReaderIndex();
            return;
        }
        // 省略魔数版本号的数据的读取和校验
        byte[] array;
        if (in.hasArray()) {
            // 堆缓冲
            ByteBuf slice = in.slice(in.readerIndex(), length);
            array = slice.array();

        } else {
            //直接缓冲
            array = new byte[length];
            in.readBytes(array, 0, length);
        }
        MessageProtos.Message message = MessageProtos.Message.parseFrom(array);
        out.add(message);
    }
}
