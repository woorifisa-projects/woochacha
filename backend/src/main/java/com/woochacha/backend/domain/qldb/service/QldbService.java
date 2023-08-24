package com.woochacha.backend.domain.qldb.service;

public interface QldbService {
    void inquiryCarOwnerInfo(String carNum);


    void getMetaIdValue(String carNum, String tableName);
}
