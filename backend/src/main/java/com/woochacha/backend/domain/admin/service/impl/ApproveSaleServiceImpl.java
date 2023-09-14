package com.woochacha.backend.domain.admin.service.impl;

import com.amazon.ion.*;
import com.amazon.ion.system.IonSystemBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.config.QldbConfig;
import com.woochacha.backend.domain.admin.dto.approve.*;
import com.woochacha.backend.domain.admin.exception.CrudDataFromQLDBError;
import com.woochacha.backend.domain.admin.service.ApproveSaleService;
import com.woochacha.backend.domain.car.info.entity.ExchangeType;
import com.woochacha.backend.domain.car.info.repository.ExchangeTypeRepository;
import com.woochacha.backend.domain.qldb.service.QldbService;
import com.woochacha.backend.domain.sale.entity.QSaleForm;
import com.woochacha.backend.domain.sale.entity.SaleForm;
import com.woochacha.backend.domain.sale.repository.SaleFormRepository;
import com.woochacha.backend.domain.sale.service.SaleFormApplyService;
import com.woochacha.backend.domain.status.entity.CarStatusList;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.qldb.Result;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApproveSaleServiceImpl implements ApproveSaleService {

    private final JPAQueryFactory jpaQueryFactory;
    private final QldbConfig qldbDriver;
    private final IonSystem ionSys = IonSystemBuilder.standard().build();
    private CarInspectionInfoResponseDto carInspectionInfoResponseDto;
    private int carDistance;
    private final SaleFormRepository saleFormRepository;
    private final ExchangeTypeRepository exchangeTypeRepository;
    private final SaleFormApplyService saleFormApplyService;
    private final QldbService qldbService;

    private static Logger logger = LoggerFactory.getLogger(ApproveSaleServiceImpl.class);

    @Override
    public Page<ApproveSaleResponseDto> getApproveSaleForm(Pageable pageable) {
        QSaleForm sf = QSaleForm.saleForm;

        QueryResults<ApproveSaleResponseDto> approveSaleResponseDtoList = jpaQueryFactory
                .select(Projections.fields(ApproveSaleResponseDto.class,
                        sf.id,
                        sf.member.name.as("name"),
                        sf.carNum,
                        sf.carStatus.status.as("status")))
                .from(sf)
                .join(sf.member)
                .join(sf.carStatus)
                .where(sf.carStatus.status.eq(CarStatusList.심사중))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        logger.info("관리자가 판매신청폼 리스트를 조회");
        return new PageImpl<>(approveSaleResponseDtoList.getResults(), pageable, approveSaleResponseDtoList.getTotal());
    }

    @Override
    @Transactional
    public Boolean updateSaleFormDenyStatus(Long saleFormId){
        int count = saleFormRepository.updateDenyStatus(saleFormId);
        if(count != 0){
            return true;
        }
        logger.debug("조건에 맞는 신청폼 없음");
        return false;
    }


    // qldb에서 차량 번호에 따른 필요한 차량 정보를 가지고 온다.
    @Override
    public CarInspectionInfoResponseDto getQldbCarInfoList(Long saleFormId) {
        String carNum = saleFormApplyService.findCarNum(saleFormId);
        String accidentMetaId = qldbService.getMetaIdValue(carNum, "car_accident");
        logger.debug("get car_accident metaIdValue from QLDB={}", accidentMetaId);
        String exchangeMetaId = qldbService.getMetaIdValue(carNum, "car_exchange");
        logger.debug("get car_exchange metaIdValue from QLDB={}", exchangeMetaId);

        List<CarAccidentInfoDto> accidentInfoDtoList = new ArrayList<>();
        List<CarExchangeInfoDto> exchangeInfoDtoList = new ArrayList<>();
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result accidentResult = txn.execute(
                        "SELECT ca.data.accident_type, ca.data.accident_desc, ca.data.accident_date " +
                                "FROM history(car_accident) AS ca " +
                                "WHERE ca.metadata.id=?",
                        ionSys.newString(accidentMetaId));
                for (IonValue ionValue : accidentResult) {
                    IonStruct ionStruct = (IonStruct) ionValue;
                    CarAccidentInfoDto accidentInfoDto = CarAccidentInfoDto.builder()
                            .accidentType(((IonString) ionStruct.get("accident_type")).stringValue())
                            .accidentDesc(((IonString) ionStruct.get("accident_desc")).stringValue())
                            .accidentDate(((IonString) ionStruct.get("accident_date")).stringValue())
                            .build();
                    accidentInfoDtoList.add(accidentInfoDto);
                }
                logger.debug("get accident history from QLDB ={}", accidentResult);
                Result exchangeResult = txn.execute(
                        "SELECT ce.data.exchange_type, ce.data.exchange_desc, ce.data.exchange_date " +
                                "FROM history(car_exchange) AS ce " +
                                "WHERE ce.metadata.id=?",
                        ionSys.newString(exchangeMetaId));
                for (IonValue ionValue : exchangeResult) {
                    IonStruct ionStruct = (IonStruct) ionValue;
                    CarExchangeInfoDto exchangeInfoDto = CarExchangeInfoDto.builder()
                            .exchangeType(((IonString) ionStruct.get("exchange_type")).stringValue())
                            .exchangeDesc(((IonString) ionStruct.get("exchange_desc")).stringValue())
                            .exchangeDate(((IonString) ionStruct.get("exchange_date")).stringValue())
                            .build();
                    exchangeInfoDtoList.add(exchangeInfoDto);
                }
                logger.debug("get exchange history from QLDB ={}", exchangeResult);

                Result resultCarInfo = txn.execute(
                        "SELECT r.car_owner_name, r.car_owner_phone, r.car_distance FROM car AS r WHERE r.car_num=?", ionSys.newString(carNum));
                IonStruct ionStruct = (IonStruct) resultCarInfo.iterator().next();
                List<ExchangeType> exchangeTypeList = exchangeTypeRepository.findAll();
                carInspectionInfoResponseDto = CarInspectionInfoResponseDto.builder()
                        .carNum(carNum)
                        .carOwnerName(((IonString) ionStruct.get("car_owner_name")).stringValue())
                        .carOwnerPhone(((IonString) ionStruct.get("car_owner_phone")).stringValue())
                        .carDistance(((IonInt) ionStruct.get("car_distance")).intValue())
                        .carAccidentInfoDtoList(accidentInfoDtoList)
                        .carExchangeInfoDtoList(exchangeInfoDtoList)
                        .exchangeTypeList(exchangeTypeList)
                        .build();
                logger.debug("get carInspectionInfo={}", carInspectionInfoResponseDto);
            });
            return carInspectionInfoResponseDto;
        } catch (Exception e) {
            logger.error("QLDB로부터 차량조회 중 에러 발생");
            throw new CrudDataFromQLDBError();
        }
    }

    @Override
    public Boolean compareCarHistory(CompareRequestDto compareRequestDto, Long saleFormId){
        try{
            String carNum = saleFormApplyService.findCarNum(saleFormId);
        int carDistance = getCarDistance(carNum);
        if(carDistance > compareRequestDto.getDistance()){
            logger.debug("잘못된 주행거리 입력");
            return false;
        }else {
            updateSaleFormStatus(saleFormId);
            updateQldbCarDistance(compareRequestDto.getDistance(), saleFormId);
            if(compareRequestDto.getCarAccidentInfoDto() != null){
                updateQldbAccidentInfo(compareRequestDto.getCarAccidentInfoDto(), saleFormId);
            }
            if(compareRequestDto.getCarExchangeInfoDto() != null){
                updateQldbExchangeInfo(compareRequestDto.getCarExchangeInfoDto(), saleFormId);
            }
            return true;
        }
        } catch (Exception e){
            logger.error("차량 history 조회 중 에러 발생");
            throw new CrudDataFromQLDBError();
        }
    }

    // 차량의 distance를 받았을 때 제대로 된 값인지 나타내는 것
    public int getCarDistance(String carNum) {
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result = txn.execute(
                        "SELECT c.car_distance " +
                                "FROM car AS c "+
                                "WHERE c.car_num=?",
                        ionSys.newString(carNum));
                IonStruct ionStruct = (IonStruct) result.iterator().next();
                carDistance = ((IonInt) ionStruct.get("car_distance")).intValue();
            });
            logger.debug("get car_distance from QLDB={}", carDistance);
            return carDistance;
        } catch (Exception e) {
            logger.error("QLDB에서 차량의 주행거리 조회 중 에러 발생");
            throw new CrudDataFromQLDBError();
        }
    }

    @Transactional
    public void updateSaleFormStatus(Long saleFormId){
        saleFormRepository.updateStatus(saleFormId);
    }

    @Transactional
    public void updateQldbCarDistance(int carDistance, Long saleFormId){
        SaleForm saleForm = saleFormRepository.findById(saleFormId).orElseThrow(() -> new RuntimeException("SaleForm not found"));;
        String carNum = saleForm.getCarNum();
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result = txn.execute(
                        "UPDATE car AS c SET c.car_distance=? WHERE c.car_num=?"
                        ,ionSys.newInt(carDistance),ionSys.newString(carNum));
                logger.debug("QLDB 주행거리 정보 UPDATE");
            });
        } catch (Exception e) {
            logger.error("QLDB에 주행거리 UPDATE 중 에러 발생");
            throw new CrudDataFromQLDBError();
        }
    }

    @Transactional
    public void updateQldbAccidentInfo(CarAccidentInfoDto carAccidentInfoDto, Long saleFormId){
        SaleForm saleForm = saleFormRepository.findById(saleFormId).orElseThrow(() -> new RuntimeException("SaleForm not found"));;
        String carNum = saleForm.getCarNum();
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result1 = txn.execute(
                        "UPDATE car_accident AS ca " +
                                "SET ca.accident_desc = ?, " +
                                "ca.accident_type=?, " +
                                "ca.accident_date=?, " +
                                "ca.accident_inspection_place='우차차 정비소'" +
                                "WHERE ca.car_num=?"
                        ,ionSys.newString(carAccidentInfoDto.getAccidentDesc())
                        ,ionSys.newString(carAccidentInfoDto.getAccidentType())
                        ,ionSys.newString(carAccidentInfoDto.getAccidentDate())
                        ,ionSys.newString(carNum));
            });
            logger.debug("QLDB 차량 사고이력 UPDATE");
        } catch (Exception e) {
            logger.error("QLDB에 사고이력 UPDATE 중 에러 발생");
            throw new CrudDataFromQLDBError();
        }
    }

    @Transactional
    public void updateQldbExchangeInfo(CarExchangeInfoDto carExchangeInfoDto, Long saleFormId){
        SaleForm saleForm = saleFormRepository.findById(saleFormId).orElseThrow(() -> new RuntimeException("SaleForm not found"));
        String carNum = saleForm.getCarNum();
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result2 = txn.execute(
                        "UPDATE car_exchange AS ce " +
                                "SET ce.exchange_desc = ?, " +
                                "ce.exchange_type=?, " +
                                "ce.exchange_date=?, " +
                                "ce.exchange_inspection_place='우차차 정비소'" +
                                "WHERE ce.car_num=?"
                        ,ionSys.newString(carExchangeInfoDto.getExchangeDesc())
                        ,ionSys.newString(carExchangeInfoDto.getExchangeType())
                        ,ionSys.newString(carExchangeInfoDto.getExchangeDate())
                        ,ionSys.newString(carNum));
            });
            logger.debug("QLDB 차량 교체이력 UPDATE");
        } catch (Exception e) {
            logger.error("QLDB에 교체이력 UPDATE 중 에러 발생");
            throw new CrudDataFromQLDBError();
        }
    }
}
