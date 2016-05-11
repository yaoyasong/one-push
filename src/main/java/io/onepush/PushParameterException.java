package io.onepush;

/**
 * 参数错误
 * 如果提供code，参考http code规范进行扩展
 * 如404-找不到资源，可以扩展40401-找不到用户信息
 * Created by yaoyasong on 2016/5/4.
 */
public class PushParameterException extends PushException {

    private Integer code = 4;

    public PushParameterException(String s) {
        super(s);
    }

    public PushParameterException(String s, Integer code) {
        super(s);
        this.code = code;
    }

    public PushParameterException(String s, Integer code, Throwable throwable) {
        super(s, throwable);
        this.code = code;
    }

    public PushParameterException(Integer code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
