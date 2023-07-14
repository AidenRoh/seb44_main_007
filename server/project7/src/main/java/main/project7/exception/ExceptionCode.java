package main.project7.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    PASSWORD_INCORRECT(403, "Password Incorrect"),
    QUESTION_NOT_FOUND(404, "Question Not Found"),
    ALREADY_VOTE_MEMBER(400, "Already Vote Member"),
    VOTE_QUESTION_NOT_FOUND(404, "VoteQuestion Not Found"),
    INVALID_USER(400, "Invalid User"),
    ANSWER_NOT_FOUND(404, "Answer Not Found"),
    VOTE_ANSWER_NOT_FOUND(404, "VoteAnswer Not Found"),
    TAG_NOT_FOUND(404, "Tag Not Found");
    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
