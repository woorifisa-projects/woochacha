package com.woochacha.backend.domain.admin.service.impl;

import com.amazon.ion.*;
import com.amazon.ion.system.IonSystemBuilder;
import com.woochacha.backend.common.ModelMapping;
import com.woochacha.backend.config.QldbConfig;
import com.woochacha.backend.domain.AmazonS3.service.AmazonS3Service;
import com.woochacha.backend.domain.admin.dto.RegisterInputDto;
import com.woochacha.backend.domain.admin.dto.RegisterProductDto;
import com.woochacha.backend.domain.admin.dto.detail.*;
import com.woochacha.backend.domain.admin.service.RegisterProductService;
import com.woochacha.backend.domain.car.detail.entity.CarDetail;
import com.woochacha.backend.domain.car.detail.entity.CarName;
import com.woochacha.backend.domain.car.detail.entity.CarOption;
import com.woochacha.backend.domain.car.detail.entity.CarOptionList;
import com.woochacha.backend.domain.car.detail.repository.CarDetailRepository;
import com.woochacha.backend.domain.car.detail.repository.CarNameRepository;
import com.woochacha.backend.domain.car.detail.repository.CarOptionRepository;
import com.woochacha.backend.domain.car.info.entity.*;
import com.woochacha.backend.domain.car.info.repository.AccidentTypeRepository;
import com.woochacha.backend.domain.car.info.repository.CarAccidentInfoRepository;
import com.woochacha.backend.domain.car.info.repository.CarExchangeInfoRepository;
import com.woochacha.backend.domain.car.info.repository.ExchangeTypeRepository;
import com.woochacha.backend.domain.car.type.entity.*;
import com.woochacha.backend.domain.car.type.repository.*;
import com.woochacha.backend.domain.jwt.JwtAuthenticationFilter;
import com.woochacha.backend.domain.product.entity.Product;
import com.woochacha.backend.domain.product.repository.CarImageRepository;
import com.woochacha.backend.domain.product.repository.ProductRepository;
import com.woochacha.backend.domain.qldb.service.QldbService;
import com.woochacha.backend.domain.sale.entity.SaleForm;
import com.woochacha.backend.domain.sale.repository.SaleFormRepository;
import com.woochacha.backend.domain.status.repository.CarStatusRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.qldb.Result;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegisterProductServiceImpl implements RegisterProductService {

    private final QldbConfig qldbDriver;
    private final IonSystem ionSys = IonSystemBuilder.standard().build();
    private final QldbService qldbService;
    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final SaleFormRepository saleFormRepository;
    private final AmazonS3Service amazonS3Service;
    private final ColorRepository colorRepository;
    private final FuelRepository fuelRepository;
    private final ModelRepository modelRepository;
    private final TransmissionRepository transmissionRepository;
    private final TypeRepository typeRepository;
    private final CarNameRepository carNameRepository;
    private final AccidentTypeRepository accidentTypeRepository;
    private final ExchangeTypeRepository exchangeTypeRepository;
    private final CarDetailRepository carDetailRepository;
    private final ProductRepository productRepository;
    private final CarOptionRepository carOptionRepository;
    private final CarExchangeInfoRepository carExchangeInfoRepository;
    private final CarAccidentInfoRepository carAccidentInfoRepository;
    private final CarStatusRepository carStatusRepository;
    private final CarImageRepository carImageRepository;
    private final ModelMapper modelMapper = ModelMapping.getInstance();


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
    public RegisterProductDto getRegisterProductInfo(Long saleFormId) {
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
            LOGGER.info("Exchange"+metaIdCarExchange);
            LOGGER.info("Accident"+metadataIdCarAccident);

            // QLDB car_exchange 테이블의 history 조회
            Result resultCarExchange = txn.execute(
                    "SELECT ce.data.exchange_type, ce.data.exchange_date FROM history(car_exchange) AS ce WHERE ce.metadata.id=?", ionSys.newString(metaIdCarExchange));
            for (IonValue ionValue : resultCarExchange) {
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
            // TODO: rearview_camera로 수정. 현재는 QLDB에 cameara로 오타나있음
            rearviewCameraQLDB = Boolean.parseBoolean(((IonBool) ionStructCarOption.get("rearview_cameara")).toString());
        });
        // TODO: 코드 간략하게 리팩토링
        RegisterProductBasicInfo registerProductBasicInfo = RegisterProductBasicInfo.builder()
                .title(carModelQLDB + ' ' + carNameQLDB + ' ' + yearQLDB)
                .carNum(carNumQLDB)
                .branch(saleForm.getBranch().getName().name())
                .carName(carNameQLDB)
                .build();
        RegisterProductDetailInfo registerProductDetailInfo = RegisterProductDetailInfo.builder()
                .model(carModelQLDB)
                .color(carColorQLDB)
                .year((short) yearQLDB)
                .capacity((short) capacityQLDB)
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

    // 관리자가 매물 등로 시 사용되는 메서드
    // QLDB에서 조회한 데이터, 입력받은 차량 가격과 이미지를 RDS 테이블 형식에 맞춰 변환해 저장 (매물 등록)
    @Transactional
    @Override
    public void registerProduct(Long saleFormId, RegisterInputDto registerInputDto) throws IOException, ParseException {
        RegisterProductDto registerProductDto = getRegisterProductInfo(saleFormId); // QLDB 데이터 조회
        registerInputDto.setCarNum(registerProductDto.getRegisterProductBasicInfo().getCarNum());
        Integer price = registerInputDto.getPrice();

        // RDS에 저장할 DTO객체 만들기
        SaleForm saleForm = saleFormRepository.findById(saleFormId).get();

        // QLDB에서 조회한 car_name이 RDS의 car_name 테이블에 없으면 insert 하기
        if (carNameRepository.findByName(registerProductDto.getRegisterProductBasicInfo().getCarName()) == null) {
            CarName carName = CarName.builder()
                    .name(registerProductDto.getRegisterProductBasicInfo().getCarName())
                    .build();
            carNameRepository.save(carName);
        }

        // QLDB에서 조회한 model이 RDS의 model 테이블에 없으면 insert 하기
        if (modelRepository.findByName(ModelList.valueOf(registerProductDto.getRegisterProductDetailInfo().getModel())) == null) {
            Model model = Model.builder()
                    .name(ModelList.valueOf(registerProductDto.getRegisterProductDetailInfo().getModel()))
                    .build();
            modelRepository.save(model);
        }

        // QLDB에서 조회한 데이터를 RDS 테이블 형식에 맞춰 Dto 객체 만들기
        CarDetail carDetail = CarDetail.builder()
                .carNum(registerProductDto.getRegisterProductBasicInfo().getCarNum())
                .owner(saleForm.getMember().getName())
                .phone(saleForm.getMember().getPhone())
                .distance(registerProductDto.getRegisterProductDetailInfo().getDistance())
                .year(registerProductDto.getRegisterProductDetailInfo().getYear())
                .capacity(registerProductDto.getRegisterProductDetailInfo().getCapacity())
                .fuel(fuelRepository.findByName((String) registerProductDto.getRegisterProductDetailInfo().getFuelName()))
                .type(typeRepository.findByName(TypeList.valueOf(registerProductDto.getRegisterProductDetailInfo().getCarType())))
                .transmission(transmissionRepository.findByName(TransmissionList.valueOf(registerProductDto.getRegisterProductDetailInfo().getTransmissionName())))
                .color(colorRepository.findByName(ColorList.valueOf(registerProductDto.getRegisterProductDetailInfo().getColor())))
                .model(modelRepository.findByName(ModelList.valueOf(registerProductDto.getRegisterProductDetailInfo().getModel())))
                .carName(carNameRepository.findByName(registerProductDto.getRegisterProductBasicInfo().getCarName()))
                .build();
        // 만들어진 Dto 객체 RDS에 insert
        carDetailRepository.save(carDetail);

        Product product = Product.builder()
                .saleForm(saleFormRepository.findById(saleFormId).get())
                .price(registerInputDto.getPrice())
                .status(carStatusRepository.findById((short) 4).get())
                .carDetail(carDetailRepository.findById(registerProductDto.getRegisterProductBasicInfo().getCarNum()).get())
                .build();
        // 만들어진 Dto 객체 RDS에 insert
        productRepository.save(product);

        // 사용자가 업로드한 차량 이미지 RDS에 저장
        boolean isRegistered = amazonS3Service.uploadProductImage(registerInputDto);

        CarOption carOption = CarOption.builder()
                .carDetail(carDetailRepository.findById(registerProductDto.getRegisterProductBasicInfo().getCarNum()).get())
                .heatedSeat(registerProductDto.getRegisterProductOptionInfos().get(0).getWhether())
                .smartKey(registerProductDto.getRegisterProductOptionInfos().get(1).getWhether())
                .blackbox(registerProductDto.getRegisterProductOptionInfos().get(2).getWhether())
                .navigation(registerProductDto.getRegisterProductOptionInfos().get(3).getWhether())
                .airbag(registerProductDto.getRegisterProductOptionInfos().get(4).getWhether())
                .sunroof(registerProductDto.getRegisterProductOptionInfos().get(5).getWhether())
                .highPass(registerProductDto.getRegisterProductOptionInfos().get(6).getWhether())
                .rearviewCamera(registerProductDto.getRegisterProductOptionInfos().get(7).getWhether())
                .build();
        // 만들어진 Dto 객체 RDS에 insert
        carOptionRepository.save(carOption);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        if ("".equals(registerProductDto.getRegisterProductDetailInfo().getProdudctAccidentInfoList())) {
            return;
        } else {
            for (int i = 0; i < registerProductDto.getRegisterProductDetailInfo().getProdudctAccidentInfoList().size(); i++) {
                String dateStr = registerProductDto.getRegisterProductDetailInfo().getProdudctAccidentInfoList().get(i).getDate();
                if (dateStr != null && !dateStr.isEmpty()) { // 비어있지 않은 문자열인 경우에만 파싱을 시도
                    try {
                        Date date = formatter.parse(dateStr);
                        CarAccidentInfo carAccidentInfo = CarAccidentInfo.builder()
                                .accidentType(accidentTypeRepository.findIdByType(AccidentTypeList.valueOf(registerProductDto.getRegisterProductDetailInfo().getProdudctAccidentInfoList().get(i).getType())))
                                .carDetail(carDetailRepository.findById(registerProductDto.getRegisterProductBasicInfo().getCarNum()).get())
                                .createdAt(date)
                                .build();
                        // 만들어진 Dto 객체 RDS에 insert
                        carAccidentInfoRepository.save(carAccidentInfo);
                    } catch (ParseException e) {
                        // 날짜가 유효하지 않을 경우 처리 (예외 처리)
                        e.printStackTrace(); // 또는 적절한 예외 처리 로직을 추가합니다.
                    }
                }
            }
        }

        for (int i = 0; i < registerProductDto.getRegisterProductDetailInfo().getProductExchangeInfoList().size(); i++) {
            Date date = formatter.parse(registerProductDto.getRegisterProductDetailInfo().getProductExchangeInfoList().get(i).getDate());
            CarExchangeInfo carExchangeInfo = CarExchangeInfo.builder()
                    .exchangeType(exchangeTypeRepository.findIdByType(ExchangeTypeList.valueOf(registerProductDto.getRegisterProductDetailInfo().getProductExchangeInfoList().get(i).getType())))
                    .createdAt(date)
                    .carDetail(carDetailRepository.findById(registerProductDto.getRegisterProductBasicInfo().getCarNum()).get())
                    .build();
            // 만들어진 Dto 객체 RDS에 insert
            carExchangeInfoRepository.save(carExchangeInfo);
        }
    }
}


