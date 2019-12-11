package com.woowacourse.caffeine.application.dto;

public class OrderChangeRequest {

    private String changeStatus;

    public OrderChangeRequest() {
    }

    public OrderChangeRequest(final String changeStatus) {
        this.changeStatus = changeStatus;
    }

    public String getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(final String changeStatus) {
        this.changeStatus = changeStatus;
    }
}
