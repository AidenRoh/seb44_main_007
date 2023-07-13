package main.reproject.advice;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    PASSWORD_INCORRECT(405, "Password incorrect"),
    COFFEE_CODE_EXISTS(409, "Coffee Code exists"),
    WISHLIST_NOT_FOUND(404, "Wishlist not found"),
    CANNOT_CHANGE_ORDER(403, "Order can not change"),
    QNA_QUESTION_NOT_FOUND(404, "Q&A Question not found"),
    CANNOT_CHANGE_QNA_QUESTION(403, "Q&A Question can not change"),
    CANNOT_READ_QNA_QUESTION(403, "Q&A Question can not read"),
    TRADE_NOT_FOUND(404, "Trade not found"),
    FIXED_NOT_FOUND(404, "fixed not found"),
    TOTAL_NOT_FOUND(404, "Total not found"),
    EMAIL_NOT_FOUND(404, "Email not found");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}