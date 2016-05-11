package io.onepush.web;

import io.onepush.model.Application;
import io.onepush.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Application管理web接口
 * Created by yaoyasong on 2016/5/11.
 */
@RestController
@RequestMapping("/apps")
public class AppController {

    @Autowired
    private AppRepository appRepository;

    /**
     * 创建app
     * @return
     */
    @RequestMapping(value="/", method= RequestMethod.POST)
    public Application addApp(@RequestBody String appName) {
        Application app = new Application();
        app.setName(appName);
        app.setAppKey(generateRandomKey());
        appRepository.save(app);
        return app;
    }

    /**
     * 获取app
     * @return
     */
    @RequestMapping(value="/{appId}", method= RequestMethod.GET)
    public Application getApp(@PathVariable String appId) {
        Application app = appRepository.findOne(appId);
        return app;
    }

    private String generateRandomKey() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
