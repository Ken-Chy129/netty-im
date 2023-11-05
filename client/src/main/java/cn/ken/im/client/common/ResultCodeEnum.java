package cn.ken.im.client.common;

import lombok.Getter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/2 22:43
 */
@Getter
public enum ResultCodeEnum {
    
    SUCCESS(200, "成功"),
    FAILED(500, "失败"),
    PARAM_ERROR(400, "参数错误"),
    NOT_FOUND(404, "资源不存在"),
    UNAUTHORIZED(401, "没有权限"),
    FORBIDDEN(403, "没有权限"),
    SERVER_ERROR(500, "服务器异常"),
    TIMEOUT(504, "请求超时"),
    
    ;
    private final int code;
    private final String message;
    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
