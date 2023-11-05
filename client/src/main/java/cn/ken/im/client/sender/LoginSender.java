package cn.ken.im.client.sender;

import cn.ken.im.bean.ClientSession;
import cn.ken.im.bean.MessageProtos;
import cn.ken.im.bean.UserDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/2 1:07
 */
@Slf4j
public class LoginSender extends BaseSender {


    public LoginSender(UserDTO user, ClientSession session) {
        super(user, session);
    }
    
    public void sendLoginMsg() {
        UserDTO user = getUser();
        ClientSession session = getSession();
        MessageProtos.LoginRequest loginRequest = 
                MessageProtos.LoginRequest.newBuilder()
                        .setUid(user.getUserId())
                        .setDeviceId(user.getDevId())
                        .setPlatform(user.getPlatform().ordinal())
                        .setPassword(user.getPassword())
                        .build();
        MessageProtos.Message message = 
                MessageProtos.Message.newBuilder()
                        .setType(MessageProtos.HeadType.LOGIN_REQUEST)
                        .setSessionId(session.getSessionId())
                        .setLoginRequest(loginRequest)
                        .build();
        sendMsg(message);
    }
}
