package io.onepush.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.onepush.enums.PlatformType;
import io.onepush.model.PushCertification;
import io.onepush.model.PushMsg;
import io.onepush.model.PushRequest;
import io.onepush.model.UserDevice;
import io.onepush.repository.CertificationRepository;
import io.onepush.repository.PushMsgRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息推送处理
 * Created by yaoyasong on 2016/5/4.
 */
@Service
public class PushMsgHandler {
    private static final Log log = LogFactory.getLog(PushMsgHandler.class);

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private ApnsHandler apnsHandler;

    @Autowired
    private CertificationRepository certRepository;

    @Autowired
    private PushMsgRepository pushMsgRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 推送消息
     * @param pushRequest
     */
    public synchronized void pushMsg(PushRequest pushRequest, Iterable<UserDevice> devices) {
        PushMsg pushMsg = new PushMsg(pushRequest);

        List<String> offlineIosTokens = new ArrayList();
        for (UserDevice device : devices) {
            setPushMsgExpire(pushRequest,pushMsg);
            pushMsg.setAudienceDeviceId(device.getId());
            pushMsg.setCreatedAt(new Date());
            saveMsgForConfirm(pushRequest, pushMsg);

            //发送到客户端的消息不需要audienceDeviceId和expireAt
            pushMsg.setAudienceDeviceId(null);
            pushMsg.setExpireAt(null);
            pushMsg.setCreatedAt(null);
            String jsonMsg = null;
            try {
                jsonMsg = objectMapper.writeValueAsString(pushMsg);
            } catch (JsonProcessingException e) {
                log.error("parse msg error: " + e.getMessage(),e);
                return;
            }

            WebSocketSession wsSession = sessionManager.getSessions().get(device.getId());
            if (wsSession != null) {
                try {
                    wsSession.sendMessage(new TextMessage(jsonMsg));
                    log.info("sent msg to : " + device.getId());
                } catch (IOException e) {
                    log.error("send msg error, will try again: " + e.getMessage(),e);
                }
            } else {//off line
                if (PlatformType.IOS == device.getPlatform()) {
                    offlineIosTokens.add(device.getNativeToken());
                    //通过apns发送消息后，确认消息收到
                    if (pushMsg.getId() != null) {
                        pushMsgRepository.delete(pushMsg.getId());
                    }
                    return;
                }
            }
        }
        if (!CollectionUtils.isEmpty(offlineIosTokens)) {
            String appId = pushRequest.getAppId();
            PushCertification cert = certRepository.findByAppId(appId);
            if (cert != null) {
                apnsHandler.pushMsg(cert.getCertFile(),cert.getCertPassword(),offlineIosTokens,
                        pushRequest.getAlert(),pushRequest.getSound(), pushRequest.getBadge());
            } else {
                log.error("no cert file found for app: " + appId);
            }
        }
    }

    private void setPushMsgExpire(PushRequest pushRequest, PushMsg pushMsg) {
        //save offline msg
        if (pushMsg.getNeedConfirm() && pushRequest.getDuration() >= pushRequest.MSG_MIN_TTL) {
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime expiredTime = time.plusSeconds(pushRequest.getDuration());
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = expiredTime.atZone(zone).toInstant();
            Date date = Date.from(instant);
            pushMsg.setExpireAt(date);
        }
    }

    private void saveMsgForConfirm(PushRequest pushRequest, PushMsg pushMsg) {
        //save offline msg
        if (pushMsg.getNeedConfirm() && pushRequest.getDuration() >= pushRequest.MSG_MIN_TTL) {
            pushMsgRepository.save(pushMsg);
        }
    }

}
