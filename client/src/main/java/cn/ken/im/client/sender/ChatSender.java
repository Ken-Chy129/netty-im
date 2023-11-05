package cn.ken.im.client.sender;

import cn.ken.im.bean.ClientSession;
import cn.ken.im.bean.MessageProtos;
import cn.ken.im.bean.UserDTO;
import cn.ken.im.common.enums.MessageType;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/5 15:32
 */
public class ChatSender extends BaseSender {

    public ChatSender() {
    }

    public ChatSender(UserDTO user, ClientSession session) {
        super(user, session);
    }

    public void sendChatMsg(String content, String receiver) {
        MessageProtos.MessageRequest messageRequest = MessageProtos.MessageRequest.newBuilder()
                .setMsgId(System.currentTimeMillis())
                .setMsgType(MessageType.TEXT.ordinal())
                .setFrom(getUser().getUserId())
                .setTo(receiver)
                .setContent(content)
                .setFromNick(getUser().getNickName())
                .build();
        MessageProtos.Message message = MessageProtos.Message.newBuilder()
                .setType(MessageProtos.HeadType.MESSAGE_REQUEST)
                .setSessionId(getSession().getSessionId())
                .setMessageRequest(messageRequest)
                .build();
        sendMsg(message);
    }
}
