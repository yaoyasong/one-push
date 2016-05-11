package io.onepush;

import io.onepush.config.PushServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Push server启动类
 * Created by yaoyasong on 2016/4/28.
 */
@RestController
@SpringBootApplication
@EnableConfigurationProperties(PushServerConfig.class)
public class Server {
    @RequestMapping("")
    String home() {
        return "one push server";
    }

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}
