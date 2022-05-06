package com.dexlk.address.VO;

public class ResponseTemplateVO {
    private Fund fund;

    public ResponseTemplateVO() {
    }

    public ResponseTemplateVO(Fund fund) {
        this.fund = fund;
    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }
}