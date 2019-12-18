package com.woowacourse.caffeine.application.dto;

public class OwnerResponse {
    private final long id;
    private final String email;

    public OwnerResponse(long id, String email) {
        this.id = id;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
