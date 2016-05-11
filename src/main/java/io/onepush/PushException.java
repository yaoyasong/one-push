package io.onepush;

/**
 * 推送服务root exception
 * Created by yaoyasong on 2016/5/6.
 */
public class PushException extends RuntimeException {
    public PushException() {
    }

    public PushException(String s) {
        super(s);
    }

    public PushException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public PushException(Throwable throwable) {
        super(throwable);
    }
}
