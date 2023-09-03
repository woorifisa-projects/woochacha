package com.woochacha.backend.domain.member.exception;

public enum SignResultCode {

    SUCCESS(0, "Success"), FAIL(-1, "Fail"),
    DUPLICATE_EMAIL_EXCEPTION(1, "Duplicate Email Exception"), DUPLICATE_PHONE_EXCEPTION(2, "Duplicate Phone Exception");

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
