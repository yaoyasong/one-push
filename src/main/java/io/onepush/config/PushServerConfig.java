package io.onepush.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 服务器配置参数
 * Created by yaoyasong on 2016/5/6.
 */
@ConfigurationProperties(prefix="pushserver")
public class PushServerConfig {

    private String mode;
    private String env;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public Boolean isProd() {
        return getEnv().equals("PROD");
    }
}
