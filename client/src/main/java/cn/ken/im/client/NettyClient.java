package cn.ken.im.client;

import cn.ken.im.bean.ClientSession;
import cn.ken.im.bean.UserDTO;
import cn.ken.im.client.handler.ChatMsgHandler;
import cn.ken.im.client.handler.ExceptionHandler;
import cn.ken.im.client.handler.LoginResponseHandler;
import cn.ken.im.client.sender.ChatSender;
import cn.ken.im.client.sender.LoginSender;
import cn.ken.im.codec.ProtobufDecoder;
import cn.ken.im.codec.ProtobufEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/2 20:28
 */
@Slf4j
@Data
public class NettyClient {

    private String host;

    private int port;

    private Bootstrap bootstrap;

    private EventLoopGroup group;

    private UserDTO userDTO;

    private ClientSession session;

    private LoginSender loginSender;

    private ChatSender chatSender;

    private int retryCnt;

    private boolean isLogin;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
        group = new NioEventLoopGroup();
    }

    public void connect() {
        try {
            bootstrap = new Bootstrap();
            bootstrap.remoteAddress(new InetSocketAddress(host, port));
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProtobufDecoder())
                            .addLast(new ProtobufEncoder())
                            .addLast(new LoginResponseHandler())
                            .addLast(new ChatMsgHandler())
                            .addLast(new ExceptionHandler());
                }
            });
            log.info("欢迎来到Netty-IM聊天平台，正在尝试连接服务器...");
            ChannelFuture connect = bootstrap.connect();
            connect.addListener((ChannelFuture future) -> {
                Channel channel = future.channel();
                if (future.isSuccess()) {
                    log.info("连接成功");
                    session = new ClientSession();
                    session.setChannel(channel);
                    session.setConnected(true);
                    channel.closeFuture().addListener((ChannelFuture future1) -> {
                        log.info("客户端关闭");
                        session.setConnected(false);
                    });
                } else {
                    if (retryCnt++ < 3) {
                        log.warn("连接失败，五秒后尝试重连");
                        channel.eventLoop().schedule(
                                this::connect,
                                5000,
                                java.util.concurrent.TimeUnit.MILLISECONDS
                        );
                    } else {
                        log.warn("多次链接服务端失败");
                    }

                }
            });
        } catch (Exception ex) {
            log.error("客户端连接失败！" + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        NettyClient client = new NettyClient("127.0.0.1", 8080);
        client.connect();
        while (true) {
            if (!client.isLogin) {
                client.login();
            } else {
                client.chat();
            }
        }
    }

    private void chat() {
        System.out.println("请输入要发送的消息(内容@用户名):");
        Scanner in = new Scanner(System.in);
        String input = in.next();
        if ("exit".equals(input)) {
            logout();
            return;
        }
        String[] split = input.split("@");
        if (split.length == 2) {
            chatSender.sendChatMsg(split[0], split[1]);
        } else {
            System.out.println("消息格式异常");
        }
    }

    private void logout() {
        isLogin = false;
    }

    private void login() {
        System.out.print("请登录(账号@密码):");
        Scanner in = new Scanner(System.in);
        while (!isLogin) {
            String input = in.next();
            String[] split = input.split("@");
            if (split.length == 2) {
                isLogin = true;
                userDTO = new UserDTO(split[0], split[1]);
                loginSender = new LoginSender(userDTO, session);
                chatSender = new ChatSender(userDTO, session);
                loginSender.sendLoginMsg();
            } else {
                System.out.print("输入格式有误，请重新输入");
            }
        }
    }
}
