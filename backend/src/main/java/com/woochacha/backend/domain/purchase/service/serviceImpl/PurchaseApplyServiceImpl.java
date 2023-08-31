package com.woochacha.backend.domain.purchase.service.serviceImpl;

import com.woochacha.backend.domain.purchase.service.PurchaseApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseApplyServiceImpl implements PurchaseApplyService {

}
