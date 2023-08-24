package com.woochacha.backend.domain.qldb.service.serviceImpl;

import com.amazon.ion.IonString;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSystem;
import com.amazon.ion.system.IonSystemBuilder;
import com.woochacha.backend.config.QldbConfig;
import com.woochacha.backend.domain.qldb.service.QldbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.qldb.Result;

@Service
@RequiredArgsConstructor
public class QldbServiceImpl implements QldbService {

    private final QldbConfig qldbDriver;
    private String owner;
    private String ownerPhone;
    private String metaId;

    @Override
    public void inquiryCarOwnerInfo(String carNum) {
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
