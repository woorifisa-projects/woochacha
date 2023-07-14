package dev.market.spring_market.service;

import dev.market.spring_market.dto.LoginReq;
import dev.market.spring_market.dto.LoginRes;
import dev.market.spring_market.dto.UserRequest;
import dev.market.spring_market.dto.UserResponse;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    UserResponse findById(Long userId);

    UserResponse deleteUser(Long userId);

    UserResponse registerUser(UserRequest userRequest);


    LoginRes login(LoginReq loginReq);

    UserResponse updateUser(Long userId,UserRequest userRequest);

}

