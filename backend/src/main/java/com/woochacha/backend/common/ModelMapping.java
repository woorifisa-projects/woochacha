package com.woochacha.backend.common;


import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

public class ModelMapping {
    private static ModelMapper modelMapper = null;

    private ModelMapping() {
        modelMapper = new ModelMapper();

        /*
            ModelMapper 라이브러리 사용 시, Entity에 @Setter가 꼭 필요한데
            이 @Setter을 사용하지 않기 위해 AccessLevel을 PRIVATE으로 변경
         */
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
    }

    public static ModelMapper getInstance() {
        return modelMapper;
    }

}
