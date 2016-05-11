package io.onepush.service;

import io.onepush.repository.DeviceRepository;
import io.onepush.model.UserDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户设备服务
 * Created by yaoyasong on 2016/5/3.
 */
@Service
public class UserDeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * 注册设备信息
     * 根据mupAppId + nativeToken确定唯一
     * @param userDevice
     */
    public void register(UserDevice userDevice) {
        UserDevice existDevice = deviceRepository.findByAppIdAndNativeToken(userDevice.getAppId(),userDevice.getNativeToken());
        if (existDevice != null) {
            userDevice.setId(existDevice.getId());
        }
        userDevice.setLastUpdateTime(new Date());
        userDevice.setStatus("1");
        deviceRepository.save(userDevice);
    }

    public void unregister(String deviceId) {
        UserDevice device = deviceRepository.findOne(deviceId);
        if (device != null) {
            device.setStatus("0");
        }
        deviceRepository.save(device);
//        deviceRepository.delete(deviceId);
    }
}
