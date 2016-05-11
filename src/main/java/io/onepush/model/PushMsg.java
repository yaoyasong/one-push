package io.onepush.model;

import io.onepush.enums.PushMsgType;

import java.io.Serializable;
import java.util.Date;

/**
 * 推送到客户端的提醒消息
 * Created by yaoyasong on 2016/4/29.
 */
public class PushMsg implements Serializable{
    /**
     * 唯一标志
     */
    private String id;
    private String appId;
    private String tenantCode;
    /**
     * 提醒标题
     */
    private String alert;
    /**
     * 消息内容
     */
    private Object extra;
    /**
     * 消息类型
     */
    private PushMsgType pushMsgType;
    /**
     * 客户端监听标志
     */
    private String listenFlag;
    /**
     * 是否需要确认
     */
    private Boolean needConfirm;
    /**
     * 到期时间
     */
    private Date expireAt;
    /**
     * 接收的设备ID
     */
    private String audienceDeviceId;

    /**
     * 创建时间
     * @param pushRequest
     */
    private Date createdAt;

    public PushMsg() {
    }

    public PushMsg(PushRequest pushRequest) {

        this.appId = pushRequest.getAppId();
        this.tenantCode = pushRequest.getTenantCode();
        this.alert = pushRequest.getAlert();
        this.extra = pushRequest.getExtra();
        this.pushMsgType = pushRequest.getPushMsgType();
        this.listenFlag = pushRequest.getListenFlag();
        this.needConfirm = pushRequest.getNeedConfirm();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    public PushMsgType getPushMsgType() {
        return pushMsgType;
    }

    public void setPushMsgType(PushMsgType pushMsgType) {
        this.pushMsgType = pushMsgType;
    }

    public String getListenFlag() {
        return listenFlag;
    }

    public void setListenFlag(String listenFlag) {
        this.listenFlag = listenFlag;
    }

    public Boolean getNeedConfirm() {
        return needConfirm;
    }

    public void setNeedConfirm(Boolean needConfirm) {
        this.needConfirm = needConfirm;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }

    public String getAudienceDeviceId() {
        return audienceDeviceId;
    }

    public void setAudienceDeviceId(String audienceDeviceId) {
        this.audienceDeviceId = audienceDeviceId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
