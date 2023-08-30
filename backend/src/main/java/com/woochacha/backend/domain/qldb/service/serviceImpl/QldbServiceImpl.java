package com.woochacha.backend.domain.qldb.service.serviceImpl;

import com.amazon.ion.*;
import com.amazon.ion.system.IonSystemBuilder;
import com.woochacha.backend.config.QldbConfig;
import com.woochacha.backend.domain.qldb.service.QldbService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import software.amazon.qldb.Result;


@Service
@RequiredArgsConstructor
public class QldbServiceImpl implements QldbService {

    private final QldbConfig qldbDriver;
    private final IonSystem ionSys = IonSystemBuilder.standard().build();
    private String ownerName;
    private String ownerPhone;
    private String metaId;
    private int countAccidentHistory;
    int carDistance;


    // QLDB에 저장된 차량 번호와 같은 차량 소유주의 이름과 전화번호를 찾아준다.
    @Override
    public Pair<String, String> getCarOwnerInfo(String carNum) {
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result = txn.execute(
                        "SELECT r.car_owner_name, r.car_owner_phone FROM car AS r WHERE r.car_num=?", ionSys.newString(carNum));
                IonStruct ionStruct = (IonStruct) result.iterator().next();
                ownerName = ((IonString) ionStruct.get("car_owner_name")).stringValue();
                ownerPhone = ((IonString) ionStruct.get("car_owner_phone")).stringValue();
            });
            return Pair.of(ownerName, ownerPhone);
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
                IonStruct ionStruct = (IonStruct) result.iterator().next();
                metaId = ((IonString) ionStruct.get("r_id")).stringValue();
            });
            return metaId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // QLDB에 저장된 history를 차량 번호에 따른 차량 사고 종류와 사고 내역에 대해서 일치하는 history의 개수를 count한다.
    @Override
    public int accidentHistoryInfo(String metaId, String accidentDesc) {
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result = txn.execute(
                        "SELECT COUNT(*) FROM history(car_accident) AS r WHERE r.metadata.id =? AND r.data.accident_desc =?",
                        ionSys.newString(metaId), ionSys.newString(accidentDesc));
                IonStruct ionStruct = (IonStruct) result.iterator().next();
                countAccidentHistory = ((IonInt) ionStruct.get("_1")).intValue();
            });
            return countAccidentHistory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}