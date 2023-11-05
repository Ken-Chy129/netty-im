package cn.ken.im.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
    String devId;
    String password;
    String nickName;
    PlatformType platform = PlatformType.WINDOWS;

    public UserDTO() {
        this.devId = "1111";
        this.nickName = "test";
    }

    public UserDTO(String userId, String password) {
        this();
        this.userId = userId;
        this.password = password;
    }

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
    
    public static UserDTO fromMsg(MessageProtos.LoginRequest info) {
        UserDTO user = new UserDTO();
        user.userId = info.getUid();
        user.devId = info.getDeviceId();
        user.password = info.getPassword();
        user.setPlatform(info.getPlatform());
        return user;
    }

}
