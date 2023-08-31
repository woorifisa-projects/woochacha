package com.woochacha.backend.domain.admin.dto.QLDBtoRDS;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarOptionDto {

    private String carDetailCarNum;
    private Byte heatedSeat;
    private Byte smartKey;
    private Byte blackbox;
    private Byte navigation;
    private Byte airbag;
    private Byte sunroof;
    private Byte highPass;
    private Byte rearviewCamera;
}
