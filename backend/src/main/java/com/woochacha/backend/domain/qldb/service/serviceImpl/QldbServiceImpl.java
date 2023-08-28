package com.woochacha.backend.domain.qldb.service.serviceImpl;

import com.amazon.ion.*;
import com.amazon.ion.system.IonSystemBuilder;
import com.woochacha.backend.config.QldbConfig;
import com.woochacha.backend.domain.admin.dto.CarInspectionInfoDto;
import com.woochacha.backend.domain.qldb.service.QldbService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import software.amazon.qldb.Result;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QldbServiceImpl implements QldbService {

    private final QldbConfig qldbDriver;
    private final IonSystem ionSys = IonSystemBuilder.standard().build();
    private String owner;
    private String ownerPhone;
    private String metaId;
    private int countAccidentHistory;


    // QLDB에 저장된 차량 번호와 같은 차량 소유주의 이름과 전화번호를 찾아준다.
    @Override
    public Pair<String, String> getCarOwnerInfo(String carNum) {
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result = txn.execute(
                        "SELECT r.car_owner, r.car_owner_phone FROM car AS r WHERE r.car_num=?", ionSys.newString(carNum));
                IonStruct carOwner = (IonStruct) result.iterator().next();
                IonString carOwnerString = (IonString) carOwner.get("car_owner");
                owner = carOwnerString.stringValue();
                IonString carOwnerPhoneString = (IonString) carOwner.get("car_owner_phone");
                ownerPhone = carOwnerPhoneString.stringValue();
            });
            return Pair.of(owner, ownerPhone);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // QLDB에 저장된 게시글의 history를 볼 수 있는 meta id 값을 가지고 옴
    @Override
    public String getMetaIdValue(String carNum, String tableName) {
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result = txn.execute(
                        "SELECT r_id FROM " + tableName + " AS r BY r_id WHERE r.car_num=?", ionSys.newString(carNum));
                IonString carMetaId = (IonString) result.iterator().next();
                metaId = carMetaId.stringValue();
            });
            return metaId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // QLDB에 저장된 history를 차량 번호에 따른 차량 사고 종류와 사고 내역에 대해서 일치하는 history의 개수를 count한다.
    @Override
    public int accidentHistoryInfo(String metaId, String accidentType, String accidentDesc) {
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result = txn.execute(
                        "SELECT COUNT(*) FROM history(car_accident) AS r WHERE r.accident_type=? AND r.accident_desc =? AND r.metadata.id =?",
                        ionSys.newString(accidentType), ionSys.newString(accidentDesc), ionSys.newString(metaId));
                IonInt countHistory = (IonInt) result.iterator().next();
                countAccidentHistory = countHistory.intValue();
            });
            return countAccidentHistory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CarInspectionInfoDto> getQldbCarInfoList(String carMetaId, String accidentMetaId, String exchangeMetaId) {
        List<CarInspectionInfoDto> inspectionInfoList = new ArrayList<>();
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result = txn.execute(
                        "SELECT c.data.car_distance, ca.data.accident_type, ca.data.accident_desc, ca.data.accident_date, ce.data.exchange_type, ce.data.exchange_desc, ce.data.exchange_date " +
                                "FROM history(car) AS c, history(car_accident) AS ca, history(car_exchange) AS ce " +
                                "WHERE c.metadata.id='?', ca.metadata.id='?', ce.metadata.id='?'",
                        ionSys.newString(carMetaId), ionSys.newString(accidentMetaId), ionSys.newString(exchangeMetaId));
                for (IonValue ionValue : result) {
                    IonStruct ionStruct = (IonStruct) ionValue;

                    CarInspectionInfoDto inspectionInfo = CarInspectionInfoDto.builder()
                            .distance(ionStruct.get("car_distance").toString())
                            .accidentType(ionStruct.get("accident_type").toString())
                            .accidentDesc(ionStruct.get("accident_desc").toString())
                            .accidentDate(ionStruct.get("accident_date").toString())
                            .exchangeType(ionStruct.get("exchange_type").toString())
                            .exchangeDesc(ionStruct.get("exchange_desc").toString())
                            .exchangeDate(ionStruct.get("exchange_date").toString())
                            .build();

                    inspectionInfoList.add(inspectionInfo);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return inspectionInfoList;
    }

}