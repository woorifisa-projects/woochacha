package com.woochacha.backend.domain.qldb.service.serviceImpl;

import com.amazon.ion.*;
import com.amazon.ion.system.IonSystemBuilder;
import com.woochacha.backend.config.QldbConfig;
import com.woochacha.backend.domain.admin.dto.CarInspectionInfoDto;
import com.woochacha.backend.domain.qldb.service.QldbService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
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
    private CarInspectionInfoDto inspectionInfo;
    int carDistance;


    // QLDB에 저장된 차량 번호와 같은 차량 소유주의 이름과 전화번호를 찾아준다.
    @Override
    public Pair<String, String> getCarOwnerInfo(String carNum) {
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result = txn.execute(
                        "SELECT r.car_owner_name, r.car_owner_phone FROM car AS r WHERE r.car_num=?", ionSys.newString(carNum));
                System.out.println(result);
                for (IonValue carOwner : result) {
                    IonStruct carOwnerInfo = (IonStruct) carOwner;
                    IonString carOwnerString = (IonString) carOwnerInfo.get("car_owner_name");
                    owner = StringEscapeUtils.unescapeJava(carOwnerString.stringValue());
                    IonString carOwnerPhoneString = (IonString) carOwnerInfo.get("car_owner_phone");
                    ownerPhone = StringEscapeUtils.unescapeJava(carOwnerPhoneString.stringValue());
                }
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


//    public CarInspectionInfoDto updateQldbCarInfo(String carNum) {
//        try {
//            qldbDriver.QldbDriver().execute(txn -> {
//                Result result = txn.execute(
//                        "SELECT c.car_distance, ca.accident_type, ca.accident_desc, ca.accident_date, ce.data.exchange_type, ce.data.exchange_desc, ce.data.exchange_date " +
//                                "FROM car AS c, car_accident AS ca, car_exchange AS ce " +
//                                "WHERE c.car_num='?', ca.car_num='?', ce.car_num='?'",
//                        ionSys.newString(carNum), ionSys.newString(carNum), ionSys.newString(carNum));
//                IonStruct ionStruct = (IonStruct) result.iterator().next();
//
//                inspectionInfo = CarInspectionInfoDto.builder()
//                        .distance(ionStruct.get("car_distance").toInt())
//                        .accidentType(ionStruct.get("accident_type").toString())
//                        .accidentDesc(ionStruct.get("accident_desc").toString())
//                        .accidentDate(ionStruct.get("accident_date").toString())
//                        .exchangeType(ionStruct.get("exchange_type").toString())
//                        .exchangeDesc(ionStruct.get("exchange_desc").toString())
//                        .exchangeDate(ionStruct.get("exchange_date").toString())
//                        .build();
//            });
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return inspectionInfo;
//    }
    @Override
    public int getCarDistance(String carNum) {
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result = txn.execute(
                        "SELECT c.car_distance " +
                                "FROM car AS c "+
                                "WHERE c.car_num='?'",
                        ionSys.newString(carNum));
                IonInt distance = (IonInt) result.iterator().next();
                carDistance = distance.intValue();
            });
            return carDistance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}