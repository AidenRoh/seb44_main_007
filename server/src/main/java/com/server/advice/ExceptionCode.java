package com.server.advice;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    WISHLIST_NOT_FOUND(404, "Wishlist not found"),
    TRADE_NOT_FOUND(404, "Trade not found"),
    FIXED_NOT_FOUND(404, "fixed not found"),
    TOTAL_NOT_FOUND(404, "Total not found"),
    TRADE_MEMBER_NOT_MATCH(404,"Trade memberId not match"),
    FIXED_MEMBER_NOT_MATCH(404,"Fixed memberId not match"),
    WISHLIST_MEMBER_NOT_MATCH(404, "Wishlist memberId not match");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}