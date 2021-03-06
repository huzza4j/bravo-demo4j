package com.xlaser4j.common.exception;

import com.xlaser4j.common.enums.Status;
import com.xlaser4j.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @package: com.xlaser4j.common.exception
 * @author: Elijah.D
 * @time: 2020/2/10 18:59
 * @description: Handler Exception
 * @modified: Elijah.D
 */
@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {
    /**
     * Handler
     *
     * @param e
     * @return response
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResponse<Object> handlerException(Exception e) {
        if (e instanceof NoHandlerFoundException) {
            log.error("[全局异常拦截]NoHandlerFoundException: 请求方法 {}, 请求路径 {}", ((NoHandlerFoundException)e).getRequestURL(), ((NoHandlerFoundException)e).getHttpMethod());

            return new ApiResponse<>().ofStatus(Status.REQUEST_NOT_FOUND);
        } else if (e instanceof MethodArgumentNotValidException) {
            log.error("[全局异常拦截]MethodArgumentNotValidException", e);

            return new ApiResponse<>().of(Status.BAD_REQUEST.getCode(), ((MethodArgumentNotValidException)e).getBindingResult().getAllErrors().get(0).getDefaultMessage(), null);
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            log.error("[全局异常拦截]MethodArgumentTypeMismatchException: 参数名 {}, 异常信息 {}", ((MethodArgumentTypeMismatchException)e).getName(), ((MethodArgumentTypeMismatchException)e).getMessage());

            return new ApiResponse<>().ofStatus(Status.PARAM_NOT_MATCH);
        } else if (e instanceof HttpMessageNotReadableException) {
            log.error("[全局异常拦截]HttpMessageNotReadableException: 错误信息 {}", ((HttpMessageNotReadableException)e).getMessage());

            return new ApiResponse<>().ofStatus(Status.PARAM_NOT_NULL);
        } else if (e instanceof ApiException) {
            log.error("[全局异常拦截]ApiException: 状态码 {}, 异常信息 {}", ((ApiException)e).getCode(), e.getMessage());

            return new ApiResponse<>().ofException((ApiException)e);
        }
        log.error("[全局异常拦截]: 异常信息 {} ", e.getMessage());
        return new ApiResponse<>().ofStatus(Status.UNKNOWN_ERROR);
    }
}
