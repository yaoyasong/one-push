package io.onepush.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * IOS 推送证书
 * Created by yaoyasong on 2016/5/6.
 */
@Document
public class PushCertification {

    private String id;
    private String appId;
    private String certFile;
    private String certPassword;

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

    public String getCertFile() {
        return certFile;
    }

    public void setCertFile(String certFile) {
        this.certFile = certFile;
    }

    public String getCertPassword() {
        return certPassword;
    }

    public void setCertPassword(String certPassword) {
        this.certPassword = certPassword;
    }
}
