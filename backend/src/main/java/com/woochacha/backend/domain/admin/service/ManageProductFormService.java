package com.woochacha.backend.domain.admin.service;

import com.woochacha.backend.domain.admin.dto.ManageProductFormDto;
import org.springframework.data.domain.Page;

public interface ManageProductFormService {

    Page<ManageProductFormDto> findDeleteEditForm(int pageNumber, int pageSize);
}
