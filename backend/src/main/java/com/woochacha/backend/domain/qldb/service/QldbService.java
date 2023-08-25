package com.woochacha.backend.domain.qldb.service;

import org.springframework.data.util.Pair;

public interface QldbService {
    Pair<String, String> inquiryCarOwnerInfo(String carNum);

}
