package com.woochacha.backend.domain.product.dto.all;

import com.querydsl.core.Tuple;
import com.woochacha.backend.domain.car.detail.dto.CarNameDto;
import com.woochacha.backend.domain.car.detail.entity.CarName;
import com.woochacha.backend.domain.car.type.dto.*;
import com.woochacha.backend.domain.car.type.entity.*;
import com.woochacha.backend.domain.sale.dto.BranchDto;
import com.woochacha.backend.domain.sale.entity.Branch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductFilterInfo {
    List<TypeDto> typeList;
    List<ModelDto> modelList;
    List<CarNameDto> carNameList;
    List<FuelDto> fuelList;
    List<ColorDto> colorList;
    List<TrasmissionDto> transmissionList;
    List<BranchDto> branchList;
}
