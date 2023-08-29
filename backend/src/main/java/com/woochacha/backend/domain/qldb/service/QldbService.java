package com.woochacha.backend.domain.qldb.service;

import com.woochacha.backend.domain.admin.dto.CarInspectionInfoDto;
import org.springframework.data.util.Pair;

import java.util.List;

public interface QldbService {
    Pair<String, String> getCarOwnerInfo(String carNum);

    String getMetaIdValue(String carNum, String tableName);

    // QLDB에 저장된 history를 차량 번호에 따른 차량 사고 종류와 사고 내역에 대해서 일치하는 history의 개수를 count한다.
    int accidentHistoryInfo(String metaId, String accidentDesc);

    List<CarInspectionInfoDto> getQldbCarInfoList(String carMetaId, String accidentMetaId, String exchangeMetaId);

//    CarInspectionInfoDto getQldbCarInfo(String carNum);

    int getCarDistance(String carNum);
}
