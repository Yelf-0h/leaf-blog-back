package com.yecheng.leafblogback.exception;

/**
 * @author Yelf
 * @create 2023-02-22-1:20
 */

public class UnAuthenticationException extends RuntimeException {
    private String msg;

    public UnAuthenticationException() {
        super();
    }

    public UnAuthenticationException(String msg) {
        super(msg);
    }
}
