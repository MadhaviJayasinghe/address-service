package com.dexlk.address.VO;

public class ValidationResponseTemplateVO {
    private ValidationResponse validationResponse;

    public ValidationResponseTemplateVO() {
    }

    public ValidationResponseTemplateVO(ValidationResponse validationResponse) {
        this.validationResponse = validationResponse;
    }

    public ValidationResponse getValidationResponse() {
        return validationResponse;
    }

    public void setValidationResponse(ValidationResponse validationResponse) {
        this.validationResponse = validationResponse;
    }
}