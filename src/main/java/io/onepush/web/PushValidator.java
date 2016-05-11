package io.onepush.web;

import io.onepush.model.PushRequest;
import io.onepush.model.UserDevice;
import io.onepush.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * 推送服务的数据校验
 *
 * Created by yaoyasong on 2016/5/9.
 */
@Component
public class PushValidator implements Validator{

    @Autowired
    private AppService appService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDevice.class.equals(clazz) || PushRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "appId", "field.required", new String[]{"appId"});
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "platform", "field.required", new String[]{"platform"});

        if (obj instanceof UserDevice) {
            ValidationUtils.rejectIfEmptyOrWhitespace(e, "alias", "field.required", new String[]{"alias"});
            ValidationUtils.rejectIfEmptyOrWhitespace(e, "nativeToken", "field.required", new String[]{"nativeToken"});
            if (!e.hasErrors()) {
                UserDevice device = (UserDevice) obj;
                String appId = device.getAppId();
                appService.validate(appId, null);
            }
        } else if (obj instanceof PushRequest) {
            ValidationUtils.rejectIfEmptyOrWhitespace(e, "appKey", "field.required", new String[]{"appKey"});
            ValidationUtils.rejectIfEmptyOrWhitespace(e, "audienceType", "field.required", new String[]{"audienceType"});
            ValidationUtils.rejectIfEmptyOrWhitespace(e, "audiences", "field.required", new String[]{"audiences"});
            ValidationUtils.rejectIfEmptyOrWhitespace(e, "alert", "field.required", new String[]{"alert"});
            if (!e.hasErrors()) {
                PushRequest req = (PushRequest)obj;
                String appId = req.getAppId();
                String appKey = req.getAppKey();
                appService.validate(appId,appKey);
            }
        }
    }
}
