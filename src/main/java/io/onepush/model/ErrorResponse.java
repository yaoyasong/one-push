package io.onepush.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 错误处理结果
 * Created by yaoyasong on 2016/5/4.
 */
public class ErrorResponse {
    private Integer code;
    private String message;
    private Throwable stackInfo;

    public ErrorResponse() {
    }

    public ErrorResponse(Integer code, String message, Throwable stackInfo) {
        this.code = code;
        this.message = message;
        this.stackInfo = stackInfo;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getStackInfo() {
        return stackInfo;
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "{\"msg\":\"Error occured while writing json string\"}";
        }
    }
}
