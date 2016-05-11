package io.onepush.model;

/**
 * Application信息
 * Created by yaoyasong on 2016/5/9.
 */
public class Application {

    private String id;
    private String name;
    private String appKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
