package dev.market.spring_market.service;

import dev.market.spring_market.dto.LoginReq;
import dev.market.spring_market.dto.LoginRes;
import dev.market.spring_market.dto.UserRequest;
import dev.market.spring_market.dto.UserResponse;
import dev.market.spring_market.entity.User;
import dev.market.spring_market.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepo userRepo;

    @Override
    public UserResponse findById(Long userId) {
        System.out.println("서비스 실행");
        Optional<User> user = userRepo.findById(userId);
        if(!user.isPresent()){
            throw new IllegalArgumentException();
        }
        User getUser = user.get();
        UserResponse userResponse = UserResponse.builder().userEmail(getUser.getUserEmail()).nickname(getUser.getNickname()).gender(getUser.getGender()).build();
        return userResponse;

    }

    @Override

    public UserResponse deleteUser(Long userId) {
        User user = userRepo.getReferenceById(userId);

        User user1 = new User(user.getUserId(), user.getUserEmail(), user.getPassword(), user.getNickname(), user.getGender(), 0,user.getCreatedAt());
        System.out.println(user1.getStatus());
        User user2 =userRepo.save(user1);
        System.out.println(user2.getStatus());

        UserResponse userResponse = UserResponse.builder().userEmail(user1.getUserEmail()).nickname(user1.getNickname()).gender(user1.getGender()).build();
        return userResponse;

    }

    @Override
    public UserResponse registerUser(UserRequest userRequest) {


        User user = new User(userRequest.getUserEmail(),userRequest.getPassword(),userRequest.getNickname(), userRequest.getGender());
        User savedUser = userRepo.save(user);
        UserResponse userResponse = UserResponse.builder().userEmail(savedUser.getUserEmail()).nickname(savedUser.getNickname()).gender(savedUser.getGender()).build();
        return userResponse;
    }

    @Override
    public LoginRes login(LoginReq loginReq) {
        User user = userRepo.findByUserEmail(loginReq.getUserEmail());
        LoginRes loginRes = LoginRes.builder().userEmail(user.getUserEmail()).userId(user.getUserId()).nickname(user.getNickname()).gender(user.getGender()).build();
        return loginRes;

    }

    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        User user1 = userRepo.getReferenceById(userId);
       User user = new User(userId,userRequest.getUserEmail(),userRequest.getPassword(),userRequest.getNickname(), userRequest.getGender(),1,user1.getCreatedAt());
        User updateUser = userRepo.save(user);
        UserResponse userResponse = UserResponse.builder().userEmail(updateUser.getUserEmail()).nickname(updateUser.getNickname()).gender(updateUser.getGender()).build();

        return userResponse;

    }
}
