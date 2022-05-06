package com.dexlk.address.VO;

public class Fund {
    private String id;
    private Number fundAmount;

    public Fund() {
    }

    public Fund(String id, Number fundAmount) {
        this.id = id;
        this.fundAmount = fundAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Number getFundAmount() {
        return fundAmount;
    }

    public void setFundAmount(Number fundAmount) {
        this.fundAmount = fundAmount;
    }
}
