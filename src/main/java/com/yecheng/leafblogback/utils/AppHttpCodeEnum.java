package com.yecheng.leafblogback.utils;

/**
 * 应用程序http代码枚举
 *
 * @author Yelf
 */
public enum AppHttpCodeEnum {
    /**
     * 成功标识
     */
    SUCCESS(200,"成功"),
    /**
     * 错误标识
     */
    ERROR(500,"出现异常"),
    PARAMS_ERROR(5003,"请求有误，参数异常！"),
    LOGIN_ERROR(5004,"账号或密码错误！"),

    ARTICLE_ERROR(5001,"请求有误，找不到该文章！"),
    REVIEW_ERROR(5002,"请求有误，获取游览量失败！");


    /**
     * 响应码
     */
    private final Integer code;
    /**
     * 响应信息
     */
    private final String msg;

    /**
     * 响应类返回值枚举
     *
     * @param code 代码
     * @param msg  味精
     */
    AppHttpCodeEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取响应码
     *
     * @return {@link Integer}
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取消息
     *
     * @return {@link String}
     */
    public String getMsg() {
        return msg;
    }
}
