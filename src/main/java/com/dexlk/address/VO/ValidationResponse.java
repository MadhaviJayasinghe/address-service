package com.dexlk.address.VO;

public class ValidationResponse {
    private String response;
    private String userId;

    public ValidationResponse() {
    }

    public ValidationResponse(String response, String userId) {
        this.response = response;
        this.userId = userId;
    }

    public ValidationResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
