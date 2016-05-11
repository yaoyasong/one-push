package io.onepush.model;

/**
 * IOS提醒消息
 * Created by yaoyasong on 2016/5/4.
 */
public class IosPushMsg extends PushMsg {
    private String sound;
    private Integer badge;

    public IosPushMsg(PushRequest pushRequest) {
        super(pushRequest);
        this.badge = pushRequest.getBadge();
        this.sound = pushRequest.getSound();
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public Integer getBadge() {
        return badge;
    }

    public void setBadge(Integer badge) {
        this.badge = badge;
    }

}
