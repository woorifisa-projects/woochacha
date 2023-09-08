package com.woochacha.backend.domain.sale.service;

import com.woochacha.backend.common.ModelMapping;
import com.woochacha.backend.domain.log.service.LogService;
import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.member.repository.MemberRepository;
import com.woochacha.backend.domain.product.repository.ProductRepository;
import com.woochacha.backend.domain.qldb.service.QldbService;
import com.woochacha.backend.domain.sale.dto.BranchDto;
import com.woochacha.backend.domain.sale.entity.Branch;
import com.woochacha.backend.domain.sale.entity.SaleForm;
import com.woochacha.backend.domain.sale.repository.BranchRepository;
import com.woochacha.backend.domain.sale.repository.SaleFormRepository;
import com.woochacha.backend.domain.status.entity.CarStatus;
import com.woochacha.backend.domain.status.repository.CarStatusRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaleFormApplyServiceImpl implements SaleFormApplyService{

    private final SaleFormRepository saleFormRepository;
    private final CarStatusRepository carStatusRepository;
    private final QldbService qldbService;
    private final MemberRepository memberRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    private final LogService logService;
    private final ModelMapper modelMapper = ModelMapping.getInstance();

    //차량 신청 폼을 요청할 때, 전체 branch 리스트를 넘겨줌 (Get)
    @Override
    public List<BranchDto> getBranchList() {
        List<Branch> branchList = branchRepository.findAll();
        return branchList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private BranchDto convertToDto(Branch branch) {
        return modelMapper.map(branch, BranchDto.class);
    }

    // 차량 신청 폼을 작성하고 제출 했을 때 데이터가 맞는지 유효성 검사를 한다.(Post)
    @Override
    @Transactional
    public String submitCarSaleForm(String carNum, Long memberId, Long branchId) {
        int countAccident;
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        String memberName = member.getName();
        String memberPhone = member.getPhone();
        Pair<String, String> ownerInfo = qldbService.getCarOwnerInfo(carNum);
        String historyId = qldbService.getMetaIdValue(carNum, "car_accident");
        if(historyId.isEmpty()){
            countAccident = 0;
        }else {
            countAccident = qldbService.accidentHistoryInfo(historyId, "전손침수");
        }
        String owner = ownerInfo.getFirst();
        String ownerPhone = ownerInfo.getSecond();
        int checkCar = productRepository.checkCarNum(carNum, (short) 4);
        if(memberName.equals(owner) && memberPhone.equals(ownerPhone) && countAccident == 0 && checkCar == 0){
            saveSaleForm(carNum, memberId, branchId);
            logService.savedMemberLogWithType(memberId, "판매 신청폼 제출");
            return "차량 판매 신청이 성공적으로 완료되었습니다.";
        } else if (!memberName.equals(owner)) {
            return "차량 소유주가 일치하지 않습니다.";
        } else if (!memberPhone.equals(ownerPhone)) {
            return "차량 소유주가 일치하지 않습니다.";
        } else if (countAccident != 0) {
            return "침수사고가 존재하는 차량입니다.";
        } else if (checkCar !=0){
            return "이미 등록된 차량입니다.";
        } else{
            return "차량 조회 오류입니다";
        }
    }

    @Override
    public String findCarNum(Long saleFormId) {
        SaleForm saleForm = saleFormRepository.findById(saleFormId).orElseThrow(() -> new RuntimeException("SaleForm not found"));
        return saleForm.getCarNum();
    }

    // 차량 등록을 위한 sale 폼을 작성한다.
    @Override
    @Transactional
    public void saveSaleForm(String carNum, Long memberId, Long branchId) {
        CarStatus carStatus = carStatusRepository.findById((short) 2)
                                .orElseThrow(() -> new RuntimeException("CarStatus not found"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found")); // 예시로 RuntimeException 처리
        SaleForm saleForm = SaleForm.builder()
                .carNum(carNum)
                .member(member)
                .branch(branch)
                .carStatus(carStatus)
                .build();
        saleFormRepository.save(saleForm);
    }
}
