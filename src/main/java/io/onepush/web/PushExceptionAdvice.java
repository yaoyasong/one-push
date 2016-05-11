package io.onepush.web;

import io.onepush.PushParameterException;
import io.onepush.PushServiceException;
import io.onepush.config.PushServerConfig;
import io.onepush.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 统一异常处理类
 *
 * Created by yaoyasong on 2016/5/10.
 */
@ControllerAdvice
public class PushExceptionAdvice extends ResponseEntityExceptionHandler{

    @Autowired
    private PushServerConfig pushServerConfig;

    @ExceptionHandler(PushParameterException.class)
    public ResponseEntity<ErrorResponse> handleParameterException(PushParameterException e) {
        ErrorResponse er = null;
        if (pushServerConfig.isProd()) {
            er = new ErrorResponse(e.getCode(),e.getMessage(),null);
        } else {
            er = new ErrorResponse(e.getCode(),e.getMessage(),e.getCause());
        }
        return new ResponseEntity<ErrorResponse>(er, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PushServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(PushServiceException e) {
        ErrorResponse er = null;
        if (pushServerConfig.isProd()) {
            er = new ErrorResponse(e.getCode(),e.getMessage(),null);
        } else {
            er = new ErrorResponse(e.getCode(),e.getMessage(),e.getCause());
        }
        return new ResponseEntity<ErrorResponse>(er, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
