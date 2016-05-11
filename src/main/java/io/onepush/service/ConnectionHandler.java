package io.onepush.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.onepush.model.PushMsg;
import io.onepush.model.UserDevice;
import io.onepush.repository.DeviceRepository;
import io.onepush.repository.PushMsgRepository;
import io.onepush.util.QueryStringParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在线连接管理
 * Created by yaoyasong on 2016/4/28.
 */
@Service
public class ConnectionHandler extends TextWebSocketHandler {

    private final static Log log = LogFactory.getLog(ConnectionHandler.class);

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private PushMsgRepository pushMsgRepository;
    @Autowired
    private ObjectMapper objectMapper;


    private final static String DEVICE_ID = "deviceId";

    private ConcurrentHashMap<String,WebSocketSession> getSessions() {
        return sessionManager.getSessions();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String query = session.getUri().getQuery();
        String deviceId = QueryStringParser.getParameterValue(query, DEVICE_ID);
        if (!StringUtils.hasText(deviceId)) {
            log.warn("Unauthorized connection without device_id");
            session.close(new CloseStatus(404,"no device id"));
        }
        UserDevice userDevice = deviceRepository.findOne(deviceId);
        if (userDevice != null && "1".equals(userDevice.getStatus())) {
            session.getAttributes().put(DEVICE_ID,deviceId);
            getSessions().put(deviceId,session);
            log.info("Connected to push server : " + deviceId);
            receivedOfflineMsgs(deviceId);

        } else {
            log.warn("No user device found with device_id: " + deviceId);
            session.close(new CloseStatus(404,"no user device found"));
        }
    }

    private void receivedOfflineMsgs(String deviceId) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC,"createdAt"));
        List<PushMsg> offlineMsgs = pushMsgRepository.findByAudienceDeviceId(deviceId, sort);
        for (PushMsg pushMsg : offlineMsgs) {
            //发送到客户端的消息不需要audienceDeviceId和expireAt
            pushMsg.setAudienceDeviceId(null);
            pushMsg.setExpireAt(null);
            pushMsg.setCreatedAt(null);
            String jsonMsg = null;
            try {
                jsonMsg = objectMapper.writeValueAsString(pushMsg);
            } catch (JsonProcessingException e) {
                log.error("parse msg error: " + e.getMessage(), e);
                return;
            }

            WebSocketSession wsSession = sessionManager.getSessions().get(deviceId);
            if (wsSession != null) {
                try {
                    wsSession.sendMessage(new TextMessage(jsonMsg));
                    log.info("sent msg to : " + deviceId);
                } catch (IOException e) {
                    log.error("send msg error, will try again: " + e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Received msg: " + message);
        String receivedInfo = message.getPayload();
        if (receivedInfo.startsWith("HBT")) {//心跳
            session.sendMessage(new TextMessage("ACK1"));
            return;
        }

        if (receivedInfo.startsWith("CFM:")) {//确认消息
            String msgId = receivedInfo.substring(4);
            pushMsgRepository.delete(msgId);
            log.info("Send message is confirmed, confirm info is :" + receivedInfo);
            return;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        removeSession(session);
    }

    public void removeSession(WebSocketSession session) {
        log.info("remove session.");
        Object deviceId = session.getAttributes().get(DEVICE_ID);
        if (deviceId != null) {
            getSessions().remove(deviceId);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        removeSession(session);
    }
}
