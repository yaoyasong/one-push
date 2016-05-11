package io.onepush;

/**
 * 服务处理错误
 * 如果提供code，参考http code规范进行扩展
 * 如500-服务器出错，可以扩展50001-连接数据库超时
 * Created by yaoyasong on 2016/5/4.
 */
public class PushServiceException extends PushException {
    private Integer code = 5;

    public PushServiceException(String s) {
        super(s);
    }

    public PushServiceException(String s, Integer code) {
        super(s);
        this.code = code;
    }

    public PushServiceException(String s, Integer code, Throwable throwable) {
        super(s, throwable);
        this.code = code;
    }

    public PushServiceException(Integer code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
