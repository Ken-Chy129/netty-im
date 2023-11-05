package cn.ken.im.common;

import cn.ken.im.bean.ClientSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/6 0:58
 */
public class SessionManager {
    
    private static ConcurrentHashMap<String, ClientSession> sessionMap = new ConcurrentHashMap<>();
    
    public static void addSession(ClientSession clientSession) {
        sessionMap.put(clientSession.getSessionId(), clientSession);
    }
}
