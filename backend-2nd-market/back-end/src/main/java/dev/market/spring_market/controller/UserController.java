package dev.market.spring_market.controller;

import dev.market.spring_market.dto.LoginReq;
import dev.market.spring_market.dto.LoginRes;
import dev.market.spring_market.dto.UserRequest;
import dev.market.spring_market.dto.UserResponse;
import dev.market.spring_market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    //회원 마이페이지 정보 반환API
    @GetMapping("/{userId}")
    public UserResponse getUserInfo(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @PostMapping("/login")
    public String Login(@RequestBody LoginReq loginReq, HttpSession httpSession){
        System.out.println("이거 실행됐음");
        LoginRes loginRes = userService.login(loginReq);
        httpSession.setAttribute("user",loginRes);
        return loginRes.getNickname();
    }
    @GetMapping("/test")
    public void test(HttpSession httpSession){
        LoginRes loginRes = (LoginRes) httpSession.getAttribute("user");
        System.out.println(loginRes.getUserId());
    }


    @PatchMapping("/delete")
    public UserResponse deleteUserInfo(HttpSession httpSession) {
        LoginRes loginRes = (LoginRes) httpSession.getAttribute("user");

        return userService.deleteUser(loginRes.getUserId());
    }

    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.registerUser(userRequest);
        return  userResponse;

   }

   @PatchMapping("/update")
    public UserResponse updateUserInfo(HttpSession httpSession,@RequestBody UserRequest userRequest) {
        LoginRes loginRes = (LoginRes) httpSession.getAttribute("user");
       return userService.updateUser(loginRes.getUserId(), userRequest);
   }

}

