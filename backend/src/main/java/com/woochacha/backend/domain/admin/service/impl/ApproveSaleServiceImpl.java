package com.woochacha.backend.domain.admin.service.impl;

import com.amazon.ion.*;
import com.amazon.ion.system.IonSystemBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.config.QldbConfig;
import com.woochacha.backend.domain.admin.dto.approve.ApproveSaleResponseDto;
import com.woochacha.backend.domain.admin.dto.approve.CarAccidentInfoDto;
import com.woochacha.backend.domain.admin.dto.approve.CarExchangeInfoDto;
import com.woochacha.backend.domain.admin.dto.approve.CarInspectionInfoResponseDto;
import com.woochacha.backend.domain.admin.service.ApproveSaleService;
import com.woochacha.backend.domain.sale.entity.QSaleForm;
import com.woochacha.backend.domain.sale.entity.SaleForm;
import com.woochacha.backend.domain.sale.repository.SaleFormRepository;
import com.woochacha.backend.domain.status.entity.CarStatusList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.qldb.Result;

import java.util.ArrayList;
import java.util.List;


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

    @Override
    public QueryResults<ApproveSaleResponseDto> getApproveSaleForm(Pageable pageable) {
        QSaleForm sf = QSaleForm.saleForm;

        return jpaQueryFactory
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
    }

    // qldb에서 차량 번호에 따른 필요한 차량 정보를 가지고 온다.
    @Override
    public CarInspectionInfoResponseDto getQldbCarInfoList(String carNum, String accidentMetaId, String exchangeMetaId) {
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
                Result exchangeResult = txn.execute(
                        "SELECT ce.data.exchange_type, ce.data.exchange_info, ce.data.exchange_date " +
                                "FROM history(car_exchange) AS ce " +
                                "WHERE ce.metadata.id=?",
                        ionSys.newString(exchangeMetaId));
                for (IonValue ionValue : exchangeResult) {
                    IonStruct ionStruct = (IonStruct) ionValue;
                    CarExchangeInfoDto exchangeInfoDto = CarExchangeInfoDto.builder()
                            .exchangeType(((IonString) ionStruct.get("exchange_type")).stringValue())
                            .exchangeDesc(((IonString) ionStruct.get("exchange_info")).stringValue())
                            .exchangeDate(((IonString) ionStruct.get("exchange_date")).stringValue())
                            .build();
                    exchangeInfoDtoList.add(exchangeInfoDto);
                }
                Result resultCarInfo = txn.execute(
                        "SELECT r.car_owner_name, r.car_owner_phone, r.car_distance FROM car AS r WHERE r.car_num=?", ionSys.newString(carNum));
                IonStruct ionStruct = (IonStruct) resultCarInfo.iterator().next();
                carInspectionInfoResponseDto = CarInspectionInfoResponseDto.builder()
                        .carNum(carNum)
                        .carOwnerName(((IonString) ionStruct.get("car_owner_name")).stringValue())
                        .carOwnerPhone(((IonString) ionStruct.get("car_owner_phone")).stringValue())
                        .carDistance(((IonInt) ionStruct.get("car_distance")).intValue())
                        .carAccidentInfoDtoList(accidentInfoDtoList)
                        .carExchangeInfoDtoList(exchangeInfoDtoList)
                        .build();
            });
            return carInspectionInfoResponseDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 차량의 distance를 받았을 때 제대로 된 값인지 나타내는 것
    @Override
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
            return carDistance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void updateSaleFormStatus(Long saleFormId){
        saleFormRepository.updateStatus(saleFormId);
    }

    @Override
    @Transactional
    public void updateQldbCarDistance(int carDistance, Long saleFormId){
        SaleForm saleForm = saleFormRepository.findById(saleFormId).orElseThrow(() -> new RuntimeException("SaleForm not found"));;
        String carNum = saleForm.getCarNum();
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result = txn.execute(
                        "UPDATE car AS c SET c.car_distance=? WHERE c.car_num=?"
                        ,ionSys.newInt(carDistance),ionSys.newString(carNum));
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void updateQldbExchangeInfo(CarExchangeInfoDto carExchangeInfoDto, Long saleFormId){
        SaleForm saleForm = saleFormRepository.findById(saleFormId).orElseThrow(() -> new RuntimeException("SaleForm not found"));
        String carNum = saleForm.getCarNum();
        try {
            qldbDriver.QldbDriver().execute(txn -> {
                Result result2 = txn.execute(
                        "UPDATE car_exchange AS ce " +
                                "SET ce.exchange_info = ?, " +
                                "ce.exchange_type=?, " +
                                "ce.exchange_date=?, " +
                                "ce.exchange_inspection_place='우차차 정비소'" +
                                "WHERE ce.car_num=?"
                        ,ionSys.newString(carExchangeInfoDto.getExchangeDesc())
                        ,ionSys.newString(carExchangeInfoDto.getExchangeType())
                        ,ionSys.newString(carExchangeInfoDto.getExchangeDate())
                        ,ionSys.newString(carNum));
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
