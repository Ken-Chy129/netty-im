package cn.ken.im;

import cn.ken.im.codec.ProtobufDecoder;
import cn.ken.im.codec.ProtobufEncoder;
import cn.ken.im.handler.LoginRequestHandler;
import cn.ken.im.handler.MessageRedirectHandler;
import cn.ken.im.handler.ServerExceptionHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/4 14:08
 */
@Slf4j
public class NettyServer {
    
    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ChannelFuture future = bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(8080)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            log.info("客户端{}:{}已连接",  
                                    ch.remoteAddress().getAddress(),
                                    ch.remoteAddress().getPort());
                            ch.pipeline().addLast(new ProtobufDecoder())
                                    .addLast(new ProtobufEncoder())
                                    .addLast(new LoginRequestHandler())
                                    .addLast(new MessageRedirectHandler())
                                    .addLast(new ServerExceptionHandler());
                        }
                    })
                    .bind()
                    .sync();
            log.info("服务端启动成功");
            future.channel().closeFuture().sync();
        } catch (Exception exception) {
            log.info("服务端启动异常:{}", exception.getMessage());
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
