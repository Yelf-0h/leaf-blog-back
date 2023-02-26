package com.yecheng.leafblogback.controller;

import com.yecheng.leafblogback.bean.dto.*;
import com.yecheng.leafblogback.bean.entity.User;
import com.yecheng.leafblogback.bean.vo.LoginVo;
import com.yecheng.leafblogback.service.UserService;
import com.yecheng.leafblogback.utils.ResponseResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Yelf
 * @create 2023-02-02-19:28
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("login")
    public ResponseResult<LoginVo> login(@RequestBody LoginDto user){
        return userService.login(user);
    }
    @GetMapping("/logout")
    public ResponseResult<String> logout(){
        return userService.logout();
    }

    @PostMapping("register")
    public ResponseResult<String> register(@RequestBody @Validated RegisterDto registerDto){
        return userService.register(registerDto);
    }

    @GetMapping("checkUserName")
    public ResponseResult<String> checkUserName(@Validated UserNameDto userNameDto){
        return userService.checkUserName(userNameDto);
    }

    @GetMapping("checkEmail")
    public ResponseResult<String> checkEmail(@Validated EmailDto emailDto){
        return userService.checkEmail(emailDto.getEmail());
    }

    @PostMapping("forgetPassword")
    public ResponseResult<String> forgetPassword(@RequestBody @Validated ForgetPasswordDto forgetPasswordDto){
        return userService.forgetPassword(forgetPasswordDto);
    }

    @PostMapping("updateNameAndPhone")
    public ResponseResult<LoginVo> updateNameAndPhone(@RequestBody @Validated UpdateNameAndPhoneDto updateNameAndPhoneDto){
        return userService.updateNameAndPhone(updateNameAndPhoneDto);
    }

}
