package com.woochacha.backend.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode{
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "입력한 값이 옳지 않습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "정보를 찾을 수 없습니다."),
    LOGIN_DO_NOT_MATCH(HttpStatus.UNAUTHORIZED, "로그인 정보가 일치하지 않습니다."),
    AUTHORIZATION_EXCEPTION(HttpStatus.FORBIDDEN,"접근할 수 있는 권한이 없습니다."),
    USER_IS_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 이용자는 존재하지 않습니다."),
    PRODUCT_IS_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR,"해당 상품이 존재 하지 않습니다."),
    CAR_IS_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 차량이 존재 하지 않습니다."),
    LOG_IS_NOT_FOUND(HttpStatus.NOT_FOUND,"로그가 존재 하지 않습니다."),
    SALE_IS_NOT_FOUND(HttpStatus.NOT_FOUND,"판매 정보가 존재하지 않습니다."),
    PURCHASE_IS_NOT_FOUND(HttpStatus.NOT_FOUND,"요청하신 구매 정보가 존재하지 않습니다."),
    STATUS_IS_NOT_FOUND(HttpStatus.NOT_FOUND,"잘못된 상태 요청입니다."),
    TRANSACTION_IS_NOT_FOUND(HttpStatus.NOT_FOUND,"거래 완료 내역이 존재하지 않습니다."),
    CRUDE_QLDB_ERROR(HttpStatus.NOT_FOUND, "QLDB 조회 중 문제가 발생했습니다."),
    SELLER_BUYER_SAME(HttpStatus.BAD_REQUEST, "본인이 등록한 매물은 구매할 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
