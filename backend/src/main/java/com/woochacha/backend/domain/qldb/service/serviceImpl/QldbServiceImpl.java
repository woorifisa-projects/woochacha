package com.woochacha.backend.domain.qldb.service.serviceImpl;

import com.amazon.ion.IonInt;
import com.amazon.ion.IonString;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSystem;
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
    private String owner;
    private String ownerPhone;
    private String metaId;
    private int countAccidentHistory;

    // QLDB에 저장된 차량 번호와 같은 차량 소유주의 이름과 전화번호를 찾아준다.
    @Override
    public Pair<String, String> inquiryCarOwnerInfo(String carNum) {
        IonSystem ionSys = IonSystemBuilder.standard().build();
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
            return Pair.of(owner,ownerPhone);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // QLDB에 저장된 게시글의 history를 볼 수 있는 meta id 값을 가지고 옴
    @Override
    public String getMetaIdValue(String carNum, String tableName) {
        IonSystem ionSys = IonSystemBuilder.standard().build();
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result = txn.execute(
                        "SELECT r_id FROM " + tableName + " AS r BY r_id WHERE r.car_num=?", ionSys.newString(carNum));
                IonStruct carOwner = (IonStruct) result.iterator().next();
                IonString carMetaId = (IonString) carOwner.get("r_id");
                metaId = carMetaId.stringValue();
            });
            return metaId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // QLDB에 저장된 history를 차량 번호에 따른 차량 사고 종류와 사고 내역에 대해서 일치하는 history의 개수를 count한다.
    @Override
    public int accidentHistoryInfo(String carNum, String metaId, String accidentType, String accidentDesc) {
        IonSystem ionSys = IonSystemBuilder.standard().build();
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result = txn.execute(
                        "SELECT COUNT(*) FROM car AS r WHERE r.car_num=? AND r.accident_type=? AND r.accident_desc =? AND r.metadata.id =?",
                        ionSys.newString(carNum), ionSys.newString(accidentType), ionSys.newString(accidentDesc), ionSys.newString(metaId));
                IonStruct countHistory = (IonStruct) result.iterator().next();
                IonInt countHistoryInt = (IonInt) countHistory.get("_1");
                countAccidentHistory = countHistoryInt.intValue();
            });
            return countAccidentHistory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
