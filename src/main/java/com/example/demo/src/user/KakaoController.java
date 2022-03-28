package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/oauth")
public class KakaoController {
    //    @Autowired
    private UserService userService;

    public KakaoController(UserService userService) {
        this.userService = userService;
    }

//
//    @RequestMapping("/kakao/login")
//    public String home(@RequestParam(value = "code", required = false) String code) throws Exception{
//        System.out.println("#########" + code);
//        String access_Token = kakaoService.getAccessToken(code);
//        System.out.println("###access_Token#### : " + access_Token);
//        return "testPage";
//    }

    /**
     * 카카오 로그인 API
     */
    @ResponseBody
    @GetMapping("/kakao")
    public BaseResponse<PostLoginRes> loginKakao(@RequestParam(required = false) String code, String phone) {
        try {
            PostLoginRes postLoginRes = userService.loginKakao(code, phone);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
}
