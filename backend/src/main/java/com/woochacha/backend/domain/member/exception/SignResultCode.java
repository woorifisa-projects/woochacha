package com.woochacha.backend.domain.member.exception;

public enum SignResultCode {

    SUCCESS(0, "Success"),
    FAIL(-1, "Fail"),
    DUPLICATE_PHONE(1, "Duplicate Phone Exception"),
    DUPLICATE_EMAIL(2, "Duplicate Email Exception"),
    SUSPENDED_ACCOUNT(3, "Suspended Account"),
    ACCESS_DENIED(403, "Access Denied")
    ;

    int code;
    String msg;

    SignResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
