package com.woochacha.backend.domain.admin.service.impl;

import com.amazon.ion.*;
import com.amazon.ion.system.IonSystemBuilder;
import com.woochacha.backend.config.QldbConfig;
import com.woochacha.backend.domain.admin.dto.RegisterProductDto;
import com.woochacha.backend.domain.admin.dto.detail.*;
import com.woochacha.backend.domain.admin.service.RegisterProductService;
import com.woochacha.backend.domain.car.detail.entity.CarOptionList;
import com.woochacha.backend.domain.jwt.JwtAuthenticationFilter;
import com.woochacha.backend.domain.qldb.service.QldbService;
import com.woochacha.backend.domain.sale.entity.SaleForm;
import com.woochacha.backend.domain.sale.repository.SaleFormRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.qldb.Result;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterProductServiceImpl implements RegisterProductService {

    private final QldbConfig qldbDriver;
    private final IonSystem ionSys = IonSystemBuilder.standard().build();
    private final QldbService qldbService;
    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final SaleFormRepository saleFormRepository;

    // TODO: 외부에 변수 선언을 하지 않아도 되는 방향으로 향후 리팩토링 예정
    private String carModelQLDB;
    private String carNumQLDB;
    private String ownerNameQLDB;
    private String ownerPhoneQLDB;
    private Integer distanceQLDB;
    private int yearQLDB;
    private int capacityQLDB;
    private String carTypeQLDB;
    private String carFuelQLDB;
    private String carColorQLDB;
    private String carTransmissionQLDB;
    private List<RegisterProductAccidentInfo> registerProductAccidentInfos = new ArrayList<>();
    private List<RegisterProductExchangeInfo> registerProductExchangeInfos = new ArrayList<>();
    private SaleForm saleForm;
    private String carNameQLDB;
    private boolean heatSeatQLDB;
    private boolean smartKeyQLDB;
    private boolean blackboxQLDB;
    private boolean navigationQLDB;
    private boolean airbagQLDB;
    private boolean sunroofQLDB;
    private boolean highPassQLDB;
    private boolean rearviewCameraQLDB;
    private String type;
    private String date;

    public static Byte boolToInt(boolean b) {
        return b ? (byte) 1 : 0;
    }

    // 매물 등록 전 보여줄 데이터를 QLDB에서 조회한다.
    @Override
    public RegisterProductDto getRegisterProductInfo(Long saleFormId){

        SaleForm saleForm = saleFormRepository.findById(saleFormId).get();
        String carNum = saleForm.getCarNum();
        qldbDriver.QldbDriver().execute(txn -> {
            // QLDB car 테이블 정보 조회
            Result resultCar = txn.execute(
                    "SELECT * FROM car AS c WHERE c.car_num=?", ionSys.newString(carNum));
            IonStruct ionStructCar = (IonStruct) resultCar.iterator().next();
            carNumQLDB = ((IonString) ionStructCar.get("car_num")).stringValue();
            ownerNameQLDB = ((IonString) ionStructCar.get("car_owner_name")).stringValue();
            ownerPhoneQLDB = ((IonString) ionStructCar.get("car_owner_phone")).stringValue();
            yearQLDB = ((IonInt) ionStructCar.get("car_year")).intValue();
            distanceQLDB = ((IonInt) ionStructCar.get("car_distance")).intValue();
            capacityQLDB = ((IonInt) ionStructCar.get("car_capacity")).intValue();

            // QLDB car_accident 테이블의 history 조회를 위한 metaId값 조회
            String metadataIdCarAccident = qldbService.getMetaIdValue(carNum, "car_accident");

            // QLDB car_accident 테이블의 history 조회
            Result resultCarAccident = txn.execute(
                    "SELECT ca.data.accident_type, ca.data.accident_date FROM history(car_accident) AS ca WHERE ca.metadata.id=?", ionSys.newString(metadataIdCarAccident));
            for (IonValue ionValue : resultCarAccident) {
                IonStruct ionStruct = (IonStruct) ionValue;

                type = ((IonString) ionStruct.get("accident_type")).stringValue();
                date = ((IonString) ionStruct.get("accident_date")).stringValue();
                RegisterProductAccidentInfo registerProductAccidentInfo = RegisterProductAccidentInfo.builder()
                        .type(type)
                        .date(date)
                        .build();
                registerProductAccidentInfos.add(registerProductAccidentInfo);
            }

            // QLDB car_exchange 테이블의 history 조회를 위한 metaId값 조회
            String metaIdCarExchange = qldbService.getMetaIdValue(carNum, "car_exchange");
            // QLDB car_exchange 테이블의 history 조회
            Result resultCarExchange = txn.execute(
                    "SELECT ce.data.exchange_type, ce.data.exchange_date FROM history(car_exchange) AS ce WHERE ce.metadata.id=?", ionSys.newString(metaIdCarExchange));
            for (IonValue ionValue : resultCarExchange){
                IonStruct ionStruct = (IonStruct) ionValue;

                type = ((IonString) ionStruct.get("exchange_type")).stringValue();
                date = ((IonString) ionStruct.get("exchange_date")).stringValue();
                RegisterProductExchangeInfo registerProductExchangeInfo = RegisterProductExchangeInfo.builder()
                        .type(type)
                        .date(date)
                        .build();
                registerProductExchangeInfos.add(registerProductExchangeInfo);
            }

            // QLDB car_info 테이블 정보 조회
            Result resultCarInfo = txn.execute(
                    "SELECT * FROM car_info AS ci WHERE ci.car_num=?", ionSys.newString(carNum));
            IonStruct ionStructCarInfo = ((IonStruct) resultCarInfo.iterator().next());
            carFuelQLDB = ((IonString) ionStructCarInfo.get("car_fuel")).stringValue();
            carTypeQLDB = ((IonString) ionStructCarInfo.get("car_type")).stringValue();
            carTransmissionQLDB = ((IonString) ionStructCarInfo.get("car_transmission")).stringValue();
            carColorQLDB = ((IonString) ionStructCarInfo.get("car_color")).stringValue();
            carModelQLDB = ((IonString) ionStructCarInfo.get("car_model")).stringValue();
            carNameQLDB = ((IonString) ionStructCarInfo.get("car_name")).stringValue();

            // QLDB car_option 테이블 정보 조회
            Result resultCarOption = txn.execute(
                    "SELECT * FROM car_option AS co WHERE co.car_num=?", ionSys.newString(carNum));
            IonStruct ionStructCarOption = ((IonStruct) resultCarOption.iterator().next());
            heatSeatQLDB = Boolean.parseBoolean(((IonBool) ionStructCarOption.get("heat_seat")).toString());
            smartKeyQLDB = Boolean.parseBoolean(((IonBool) ionStructCarOption.get("smart_key")).toString());
            blackboxQLDB = Boolean.parseBoolean(((IonBool) ionStructCarOption.get("blackbox")).toString());
            navigationQLDB = Boolean.parseBoolean(((IonBool) ionStructCarOption.get("navigation")).toString());
            airbagQLDB = Boolean.parseBoolean(((IonBool) ionStructCarOption.get("airbag")).toString());
            sunroofQLDB = Boolean.parseBoolean(((IonBool) ionStructCarOption.get("sunroof")).toString());
            highPassQLDB = Boolean.parseBoolean(((IonBool) ionStructCarOption.get("high_pass")).toString());
            rearviewCameraQLDB = Boolean.parseBoolean(((IonBool) ionStructCarOption.get("rearview_camera")).toString());
        });
        // TODO: 코드 간략하게 리팩토링
        RegisterProductBasicInfo registerProductBasicInfo = RegisterProductBasicInfo.builder()
                .title(carModelQLDB+carNameQLDB+yearQLDB)
                .carNum(carNumQLDB)
                .branch(saleForm.getBranch().getName().name())
                .build();
        RegisterProductDetailInfo registerProductDetailInfo = RegisterProductDetailInfo.builder()
                .capacity(capacityQLDB)
                .distance(distanceQLDB)
                .carType(carTypeQLDB)
                .fuelName(carFuelQLDB)
                .transmissionName(carTransmissionQLDB)
                .produdctAccidentInfoList((List) registerProductAccidentInfos)
                .productExchangeInfoList((List) registerProductExchangeInfos)
                .build();

        List<RegisterProductOptionInfo> options = new ArrayList<>();
        options.add(new RegisterProductOptionInfo(CarOptionList.열선시트.name(), boolToInt(heatSeatQLDB)));
        options.add(new RegisterProductOptionInfo(CarOptionList.스마트키.name(), boolToInt(smartKeyQLDB)));
        options.add(new RegisterProductOptionInfo(CarOptionList.블랙박스.name(), boolToInt(blackboxQLDB)));
        options.add(new RegisterProductOptionInfo(CarOptionList.네비게이션.name(), boolToInt(navigationQLDB)));
        options.add(new RegisterProductOptionInfo(CarOptionList.에어백.name(), boolToInt(airbagQLDB)));
        options.add(new RegisterProductOptionInfo(CarOptionList.썬루프.name(), boolToInt(sunroofQLDB)));
        options.add(new RegisterProductOptionInfo(CarOptionList.하이패스.name(), boolToInt(highPassQLDB)));
        options.add(new RegisterProductOptionInfo(CarOptionList.후방카메라.name(), boolToInt(rearviewCameraQLDB)));

        RegisterProductDto registerProductDto = RegisterProductDto.builder()
                .registerProductBasicInfo(registerProductBasicInfo)
                .registerProductDetailInfo(registerProductDetailInfo)
                .registerProductOptionInfos(options)
                .build();
        return registerProductDto;
    }
}