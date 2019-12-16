package com.szp.mall.inventory.vo;

public class MyResponse {
    private String status;

    private Object result;

    private String message;

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    public MyResponse(String status, Object result, String message) {
        this.status = status;
        this.result = result;
        this.message = message;
    }

    public MyResponse(String status, Object result) {
        this.status = status;
        this.result = result;
    }

    public MyResponse(Object result) {
        this.result = result;
        this.status = SUCCESS;
    }
    public MyResponse() {
        this.status = SUCCESS;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MyResponse{" +
                "status='" + status + '\'' +
                ", result=" + result +
                ", message='" + message + '\'' +
                '}';
    }
}
