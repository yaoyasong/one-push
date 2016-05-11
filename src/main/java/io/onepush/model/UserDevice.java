package io.onepush.model;

import io.onepush.enums.PlatformType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户设备信息
 * Created by yaoyasong on 2016/4/28.
 */
@Document
public class UserDevice implements Serializable {
    private String id; //推送内部设备编号
    private String alias;//别名，一个别名可以对应多个deviceId
    private String appId;
    private String thirdpartId;
    private List<String> tag;
    private PlatformType platform;
    private String nativeToken;//设备本地编号
    private String listenFlag = "NOTF";//监听的信息
    private Date lastUpdateTime;
    private String mobile;
    private String status = "1";//状态 1-激活

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getThirdpartId() {
        return thirdpartId;
    }

    public void setThirdpartId(String thirdpartId) {
        this.thirdpartId = thirdpartId;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public PlatformType getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformType platform) {
        this.platform = platform;
    }

    public String getNativeToken() {
        return nativeToken;
    }

    public void setNativeToken(String nativeToken) {
        this.nativeToken = nativeToken;
    }

    public String getListenFlag() {
        return listenFlag;
    }

    public void setListenFlag(String listenFlag) {
        this.listenFlag = listenFlag;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
