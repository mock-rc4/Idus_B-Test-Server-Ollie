package com.example.demo.src.user;

import com.example.demo.src.work.model.GetWorkSearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.config.BaseResponseStatus.POST_USERS_INVALID_PASSWORD;
import static com.example.demo.utils.ValidationRegex.*;

@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;



    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 회원 조회 API
     * [GET] /users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /users? Email=
     * @return BaseResponse<List<GetUserRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String Email) {
        try{
            if(Email == null){ //params에 Email도 들어오면 안됨
                List<GetUserRes> getUsersRes = userProvider.getUsers();
                return new BaseResponse<>(getUsersRes);
            }
            // Get Users
            List<GetUserRes> getUsersRes = userProvider.getUsersByEmail(Email);
            return new BaseResponse<>(getUsersRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원 1명 프로필 조회 API
     * [GET] /users/profile
     * @return BaseResponse<GetUserRes>
     */
    @ResponseBody
    @GetMapping("/profile") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<GetUserRes> getUserProfile() {
        // Get Users
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            GetUserRes getUserRes = userProvider.getUserProfile(userIdxByJwt);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 회원 1명 작품 관심목록 조회 API
     * [GET] /users/profile
     * @return BaseResponse<GetUserRes>
     */
    @ResponseBody
    @GetMapping("/interest/work") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetWorkSearch>> getUserInterest() {
        // Get Users
        try{
            int userId = jwtService.getUserIdx();
            List<GetWorkSearch> getWorkSearch = userProvider.getUserInterest(userId);
            return new BaseResponse<>(getWorkSearch);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

//    /**
//     * 작품에 관심해제 API
//     * [POST} */
//    @ResponseBody
//    @PostMapping("/interest-clear")
//    public BaseResponse<UserInterest> clearWorkInterest(@RequestBody UserInterest userInterest) {
//
//        try{
//            int userId = jwtService.getUserIdx();
//            UserInterest interest = userService.clearWorkInterest(userInterest,userId);
//            return new BaseResponse<>(interest);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        if(postUserReq.getEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        if(postUserReq.getName()==null){
            return new BaseResponse<>(POST_USERS_EMPTY_NAME);
        }
        if(postUserReq.getPhone() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_PHONE);
        }
        if(postUserReq.getPassword()==null){
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        if(!isRegexPassword(postUserReq.getPassword())){
            return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
        }
        if(postUserReq.getPassword2()==null){
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD2);
        }
        if(!isRegexPassword(postUserReq.getPassword2())){
            return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
        }

        if(!postUserReq.getPassword().equals(postUserReq.getPassword2())){
            return new BaseResponse<>(POST_USERS_ANOTHER_PASSWORD);
        }

        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 로그인 API
     * [POST] /users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            if(postLoginReq.getEmail()==null){
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }
            if(!isRegexEmail(postLoginReq.getEmail())){
                return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
            }
            if(postLoginReq.getPassword() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
            }
            if(!isRegexPassword(postLoginReq.getPassword())){
                return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
            }
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
//    /**
//     * 카카오 로그인 API*/
//    @ResponseBody
//    @GetMapping("/kakaologIn")
//    public BaseResponse<PostLoginRes> loginKakao(@RequestParam(required = false) String code, String phone){
//        try{
//          PostLoginRes postLoginRes=userService.loginKakao(code,phone);
//          return new BaseResponse<>(postLoginRes);
//        }catch (BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//
//    }

    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<GetUserRes> modifyUserProfile(@RequestBody GetUserRes getUserRes){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            getUserRes.setId(userIdxByJwt);
            GetUserRes patchProfile=userService.modifyUserProfile(getUserRes);

        return new BaseResponse<>(patchProfile);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*좌표변경*/
    @ResponseBody
    @GetMapping("/address") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<String> getUsers(@RequestParam(required = false) String longi, String lati) {
        String add = userService.getAddress(lati,longi);
        return new BaseResponse<>(add);
    }
    /*동 검색*/
    @ResponseBody
    @GetMapping("/search") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<UserAddressList> getUserLocation(@RequestParam(required = false) String word) {
        UserAddressList add = userService.getLocation(word);
        return new BaseResponse<>(add);
    }

}
