package dev.market.spring_market.dto;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Getter
@Builder
@AllArgsConstructor

public class UserRequest {


    private String userEmail;

    private String password;

    private String nickname;


    private char gender;

    public UserRequest() {

    }
}
