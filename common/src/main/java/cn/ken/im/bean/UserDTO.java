package cn.ken.im.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/1 16:18
 */
@Slf4j
@Data
public class UserDTO {

    String userId;
    String userName;
    String devId;
    String token;
    String nickName = "nickName";
    PlatformType platform = PlatformType.WINDOWS;

    // windows,mac,android, ios, web , other
    public enum PlatformType {
        WINDOWS, MAC, ANDROID, IOS, WEB, OTHER;
    }

    private String sessionId;


    public void setPlatform(int platform) {
        PlatformType[] values = PlatformType.values();
        for (PlatformType value : values) {
            if (value.ordinal() == platform) {
                this.platform = value;
            }
        }

    }
    
    @Override
    public String toString() {
        return "User{" +
                "uid='" + userId + '\'' +
                ", devId='" + devId + '\'' +
                ", token='" + token + '\'' +
                ", nickName='" + nickName + '\'' +
                ", platform=" + platform +
                '}';
    }

    public static UserDTO fromMsg(MessageProtos.LoginRequest info) {
        UserDTO user = new UserDTO();
        user.userId = info.getUid();
        user.devId = info.getDeviceId();
        user.token = info.getToken();
        user.setPlatform(info.getPlatform());
        log.info("user:{}", user);
        return user;

    }

}
