package com.woochacha.backend.domain.qldb.service;

import org.springframework.data.util.Pair;

public interface QldbService {
    Pair<String, String> inquiryCarOwnerInfo(String carNum);


    String getMetaIdValue(String carNum, String tableName);

    int accidentHistoryInfo(String carNum, String metaId, String accidentType, String accidentDesc);
}