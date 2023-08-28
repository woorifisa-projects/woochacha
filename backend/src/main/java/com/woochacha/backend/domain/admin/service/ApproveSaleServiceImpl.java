package com.woochacha.backend.domain.admin.service;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.domain.admin.dto.ApproveSaleResponseDto;
import com.woochacha.backend.domain.sale.entity.QSaleForm;
import com.woochacha.backend.domain.status.entity.CarStatusList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApproveSaleServiceImpl implements ApproveSaleService {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public QueryResults<ApproveSaleResponseDto> getApproveSaleForm(Pageable pageable) {
        QSaleForm sf = QSaleForm.saleForm;

        QueryResults<ApproveSaleResponseDto> results = jpaQueryFactory
                .select(Projections.fields(ApproveSaleResponseDto.class,
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

        return results;
    }

}
