package com.woochacha.backend.domain.product.dto.all;

import com.woochacha.backend.domain.car.detail.dto.CarNameDto;
import com.woochacha.backend.domain.car.type.dto.*;
import com.woochacha.backend.domain.sale.dto.BranchDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterInfo {
    List<TypeDto> typeList;
    List<ModelDto> modelList;
    List<CarNameDto> carNameList;
    List<FuelDto> fuelList;
    List<ColorDto> colorList;
    List<TrasmissionDto> transmissionList;
    List<BranchDto> branchList;
}
