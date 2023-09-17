package com.woochacha.backend.domain.admin.service.impl;

import com.amazon.ion.*;
import com.amazon.ion.system.IonSystemBuilder;
import com.woochacha.backend.common.ModelMapping;
import com.woochacha.backend.config.QldbConfig;
import com.woochacha.backend.domain.AmazonS3.service.AmazonS3Service;
import com.woochacha.backend.domain.admin.dto.RegisterInputDto;
import com.woochacha.backend.domain.admin.dto.RegisterProductDto;
import com.woochacha.backend.domain.admin.dto.detail.*;
import com.woochacha.backend.domain.admin.exception.CrudDataFromQLDBError;
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
    private static Logger logger = LoggerFactory.getLogger(RegisterProductServiceImpl.class);


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
        try {
            saleForm = saleFormRepository.findById(saleFormId).get();
            String carNum = saleForm.getCarNum();
            getCarOnwerInfo(carNum);
            getAccidentHistroy(carNum);
            getExchangeHistory(carNum);
            getCarInfo(carNum);
            getCarOption(carNum);

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
                    .produdctAccidentInfoList(registerProductAccidentInfos)
                    .productExchangeInfoList(registerProductExchangeInfos)
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
            logger.debug("QLDB에서 차량 데이터 조회");
            return registerProductDto;
        }catch (Exception e){
            throw new CrudDataFromQLDBError();
        }
    }

    private void getCarOnwerInfo(String carNum){
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
        });
    }

    // QLDB car_accident 테이블의 metaId 값을 통해 history 조회
    private void getAccidentHistroy(String carNum){
        qldbDriver.QldbDriver().execute(txn -> {
            // QLDB car_accident 테이블의 history 조회를 위한 metaId값 조회
            String metadataIdCarAccident = qldbService.getMetaIdValue(carNum, "car_accident");
            LOGGER.info("Accident"+metadataIdCarAccident);

            // QLDB car_accident 테이블의 history 조회
            Result resultCarAccident = txn.execute(
                    "SELECT ca.data.accident_type, ca.data.accident_date FROM history(car_accident) AS ca WHERE ca.metadata.id=?", ionSys.newString(metadataIdCarAccident));
            registerProductAccidentInfos = new ArrayList<>();
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
        });
    }
    // QLDB car_exchange 테이블의 metaId값을 통해 history 조회
    private void getExchangeHistory(String carNum){
        // QLDB car_exchange 테이블의 history 조회를 위한 metaId값 조회
        qldbDriver.QldbDriver().execute(txn -> {
            String metaIdCarExchange = qldbService.getMetaIdValue(carNum, "car_exchange");
            LOGGER.info("Exchange" + metaIdCarExchange);

            registerProductExchangeInfos = new ArrayList<>();
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
        });
    }

    // car_info의 데이터를 조회한다(차량 기본 정보)
    private void getCarInfo(String carNum){
        qldbDriver.QldbDriver().execute(txn -> {
            Result resultCarInfo = txn.execute(
                    "SELECT * FROM car_info AS ci WHERE ci.car_num=?", ionSys.newString(carNum));
            IonStruct ionStructCarInfo = ((IonStruct) resultCarInfo.iterator().next());
            carFuelQLDB = ((IonString) ionStructCarInfo.get("car_fuel")).stringValue();
            carTypeQLDB = ((IonString) ionStructCarInfo.get("car_type")).stringValue();
            carTransmissionQLDB = ((IonString) ionStructCarInfo.get("car_transmission")).stringValue();
            carColorQLDB = ((IonString) ionStructCarInfo.get("car_color")).stringValue();
            carModelQLDB = ((IonString) ionStructCarInfo.get("car_model")).stringValue();
            carNameQLDB = ((IonString) ionStructCarInfo.get("car_name")).stringValue();
        });
    }

    // car_option의 데이터를 조회한다.(차량 옵션)
    private void getCarOption(String carNum){
        qldbDriver.QldbDriver().execute(txn -> {
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
    }


    // 관리자가 매물 등로 시 사용되는 메서드
    // QLDB에서 조회한 데이터, 입력받은 차량 가격과 이미지를 RDS 테이블 형식에 맞춰 변환해 저장 (매물 등록)
    @Transactional
    @Override
    public void registerProduct(Long saleFormId, RegisterInputDto registerInputDto) throws IOException, ParseException {
        RegisterProductDto registerProductDto = getRegisterProductInfo(saleFormId); // QLDB 데이터 조회
        registerInputDto.setCarNum(registerProductDto.getRegisterProductBasicInfo().getCarNum());
        saveCarName(registerProductDto);
        saveCarModel(registerProductDto);
        saveCarDetail(registerProductDto);
        saveCarBasicInfo(registerInputDto,saleFormId,registerProductDto);
        saveCarOption(registerProductDto, saleFormId);
        saveCarAccidentInfo(registerProductDto);
        saveCarExchangeInfo(registerProductDto);

        // 사용자가 업로드한 차량 이미지 RDS에 저장
        amazonS3Service.uploadProductImage(registerInputDto);
        logger.debug("carNum:{} 차량 이미지 업로드", carNumQLDB);

    }
    // QLDB에서 조회한 car_name이 RDS의 car_name 테이블에 없으면 insert 하기
    private void saveCarName(RegisterProductDto registerProductDto){
        if (carNameRepository.findByName(registerProductDto.getRegisterProductBasicInfo().getCarName()) == null) {
            CarName carName = CarName.builder()
                    .name(registerProductDto.getRegisterProductBasicInfo().getCarName())
                    .build();
            carNameRepository.save(carName);
            logger.debug("새로운 차량 이름 RDS에 등록 carName:{}", carNameQLDB);
        }
    }

    // QLDB에서 조회한 model이 RDS의 model 테이블에 없으면 insert 하기
    private void saveCarModel(RegisterProductDto registerProductDto){
        if (modelRepository.findByName(ModelList.valueOf(registerProductDto.getRegisterProductDetailInfo().getModel())) == null) {
            Model model = Model.builder()
                    .name(ModelList.valueOf(registerProductDto.getRegisterProductDetailInfo().getModel()))
                    .build();
            modelRepository.save(model);
            logger.debug("새로운 차량 모델 RDS에 등록 model:{}", carModelQLDB);
        }
    }

    // QLDB에서 조회한 데이터를 CarDetail에 저장
    private void saveCarDetail(RegisterProductDto registerProductDto){
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
    }
    // QLDB에서 불러온 내용에 대해 product에 저장
    private void saveCarBasicInfo(RegisterInputDto registerInputDto, Long saleFormId, RegisterProductDto registerProductDto){
        Product product = Product.builder()
                .saleForm(saleFormRepository.findById(saleFormId).get())
                .price(registerInputDto.getPrice())
                .status(carStatusRepository.findById((short) 4).get())
                .carDetail(carDetailRepository.findById(registerProductDto.getRegisterProductBasicInfo().getCarNum()).get())
                .build();
        // 만들어진 Dto 객체 RDS에 insert
        productRepository.save(product);
        logger.debug("saleFormId:{}에 대한 차량 기본 정보 RDS에 저장", saleFormId);
    }
    // QLDB에서 불러온 내용을 차량 옵션에 저장
    private void saveCarOption(RegisterProductDto registerProductDto, Long saleFormId){
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
        logger.debug("saleFormId:{}에 대한 차량 옵션 정보 RDS에 저장", saleFormId);
    }
    //QLDB에서 불러온 내용을 차량 사고 이력에 저장
    private void saveCarAccidentInfo(RegisterProductDto registerProductDto){
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
                        logger.debug("carNum:{}차량에 대한 사고이력 정보 RDS에 저장", carNumQLDB);
                    } catch (ParseException e) {
                        logger.error("차량 사고이력 조회시 날짜가 빈 문자열");
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    // QLDB에서 불러온 내용을 차량 교체 이력에 저장
    private void saveCarExchangeInfo(RegisterProductDto registerProductDto) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        for (int i = 0; i < registerProductDto.getRegisterProductDetailInfo().getProductExchangeInfoList().size(); i++) {
            Date date = formatter.parse(registerProductDto.getRegisterProductDetailInfo().getProductExchangeInfoList().get(i).getDate());
            CarExchangeInfo carExchangeInfo = CarExchangeInfo.builder()
                    .exchangeType(exchangeTypeRepository.findIdByType(ExchangeTypeList.valueOf(registerProductDto.getRegisterProductDetailInfo().getProductExchangeInfoList().get(i).getType())))
                    .createdAt(date)
                    .carDetail(carDetailRepository.findById(registerProductDto.getRegisterProductBasicInfo().getCarNum()).get())
                    .build();
            // 만들어진 Dto 객체 RDS에 insert
            carExchangeInfoRepository.save(carExchangeInfo);
            logger.debug("carNum:{}차량에 대한 교체이력 정보 RDS에 저장", carNumQLDB);
        }
    }
}
