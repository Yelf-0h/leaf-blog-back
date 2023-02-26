package com.yecheng.leafblogback.interceptor;

import com.google.protobuf.ServiceException;
import com.yecheng.leafblogback.exception.UnAuthenticationException;
import com.yecheng.leafblogback.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理程序
 *
 * @author CHEN
 * @date 2023/01/28
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(BindException.class)
    public ResponseResult handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return ResponseResult.errorResult(500,message);
    }


    /**
     * 处理http请求方法不支持
     * 请求方法不支持
     *
     * @param e       e
     * @param request 请求
     * @return {@link ResponseResult}<{@link Object}>
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                          HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestUri, e.getMethod());
        return ResponseResult.errorResult(500,"请求地址'"+requestUri+"',不支持'"+e.getMethod()+"'请求");
    }

    /**
     * 处理服务异常
     * 业务异常
     *
     * @param e e
     * @return {@link ResponseResult}<{@link Object}>
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseResult<Object> handleServiceException(ServiceException e) {
        String message = e.getMessage();
        log.error(message, e);
        return ResponseResult.errorResult(500,message);
    }

    /**
     * 处理联合国身份验证异常
     * 业务异常
     *
     * @param e e
     * @return {@link ResponseResult}<{@link Object}>
     */
    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseResult<Object> handleUnAuthenticationException(UnAuthenticationException e) {
        log.error("未登录异常",e);
        return new ResponseResult<>(500,"尚未登录，请登录");
    }


    /**
     * 处理运行时异常
     * 拦截未知的运行时异常
     *
     * @param e       e
     * @param request 请求
     * @return {@link ResponseResult}<{@link Object}>
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseResult<Object> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestUri, e);
        return ResponseResult.errorResult(500,"请求地址'" + requestUri + "',发生未知异常");
    }


    /**
     * 处理异常
     * 系统异常
     *
     * @param e       e
     * @param request 请求
     * @return {@link ResponseResult}<{@link Object}>
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<Object> handleException(Exception e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestUri, e);
        return ResponseResult.errorResult(500,"请求地址'" + requestUri + "',发生未知异常");
    }
}