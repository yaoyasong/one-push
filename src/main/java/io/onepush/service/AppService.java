package io.onepush.service;

import io.onepush.PushParameterException;
import io.onepush.model.Application;
import io.onepush.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * App服务
 * Created by yaoyasong on 2016/5/11.
 */
@Service
public class AppService {

    @Autowired
    private AppRepository appRepository;

    public Boolean validate(String appId, String appKey) {
        Application app = appRepository.findOne(appId);
        if (app == null) {
            throw new PushParameterException("应用不存在，ID：" + appId);
        }
        if (!app.getAppKey().equals(appKey)) {
            throw new PushParameterException("应用密钥不正确");
        }
        return true;
    }
}
