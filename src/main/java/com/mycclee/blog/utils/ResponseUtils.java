package com.mycclee.blog.utils;

/**
 * @author mycclee
 * @createTime 2019/7/13 21:27
 */
public class ResponseUtils {

    private Boolean success;

    private String message;

    private Object body;

    public ResponseUtils(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseUtils(Boolean success, String message, Object body) {
        this.success = success;
        this.message = message;
        this.body = body;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
