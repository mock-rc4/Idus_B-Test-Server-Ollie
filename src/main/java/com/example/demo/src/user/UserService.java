package com.example.demo.src.user;



import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;

    }

    //    /**
//     * 작품에 관심 누르기 API*/
//    public UserInterest clearWorkInterest(UserInterest userInterest,int userId) throws BaseException {
//        //중복
//        try{
//            UserInterest interest = userDao.clearWorkInterest(userInterest,userId);
//            return interest;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
        if (userProvider.checkEmail(postUserReq.getEmail()) == 1) {
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        String pwd;
        try {
            //암호화
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            PostUserRes postUserRes = userDao.createUser(postUserReq);
            return postUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetUserRes modifyUserProfile(GetUserRes getUserRes) throws BaseException {
        try {
            GetUserRes userprofile = userDao.getUserProfile(getUserRes.getId()); //아이디 값 가진 user profile정보 불러옴
            getUserRes.setNullProfile(userprofile);
            int result = userDao.modifyUserProfile(getUserRes);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
            GetUserRes patchProfile = userDao.getUserProfile(getUserRes.getId());
            return patchProfile;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostLoginRes loginKakao(String accessToken, String phone) throws BaseException {
        try {
            //String accessToken=getAccessToken(code);
            KakaoGetUser kakaoGetUser = getUserInfoByToken(accessToken);
            String kakaoid = "kakao" + kakaoGetUser.getKakaoId();
            String email = kakaoGetUser.getEmail();
            int id;
            if (userProvider.checkEmail(email) == 1) {
                User user = userDao.kakaoUser(email);
                id = user.getId();
            } else {
                PostUserReq postUserReq = new PostUserReq();
                postUserReq.setSocial("kakao");
                postUserReq.setSocialId(kakaoid);
                postUserReq.setEmail(email);
                postUserReq.setName(kakaoGetUser.getName());
                postUserReq.setPhone(phone);
                postUserReq.setPassword("");
                PostUserRes postUserRes = createUser(postUserReq);
                id = postUserRes.getUserIdx();
            }
            String jwt = jwtService.createJwt(id);
            return new PostLoginRes(id, jwt);
        } catch (Exception e) {
            throw new BaseException(POST_KAKAO_LOGIN_FAIL);
        }

    }

    public String getAccessToken(String authorizedCode) throws JsonProcessingException {
        System.out.println("getAccessToken 호출");
        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "9adca47b25d38d5f1826188403e6caca");
        params.add("redirect_uri", "http://localhost:9000/oauth/kakao");
        params.add("code", authorizedCode);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청하기, Post방식으로, 그리고 response 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // JSON -> 액세스 토큰 파싱
        String accessToken = "";
        String tokenJson = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();

        KakaoAccessTokenRes kakaoAccessTokenRes = objectMapper.readValue(tokenJson, KakaoAccessTokenRes.class);
        System.out.println("액세스 토큰임 : " + kakaoAccessTokenRes.getAccess_token());
        accessToken = kakaoAccessTokenRes.getAccess_token();
        return accessToken;
    }

    public KakaoGetUser getUserInfoByToken(String accessToken) throws JsonProcessingException {

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        String result = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(result);
        KakaoUserRes kakaoUserRes = objectMapper.readValue(result, KakaoUserRes.class);
        System.out.println("카카오 유저 Idx : " + kakaoUserRes.getId());
        System.out.println("카카오 유저 닉넴 : " + kakaoUserRes.getProperties().getNickname());
        System.out.println("카카오 유저 이메일 : " + kakaoUserRes.getKakao_account().getEmail());
        KakaoGetUser kakaoGetUser = new KakaoGetUser(kakaoUserRes.getId(), kakaoUserRes.getProperties().getNickname(), kakaoUserRes.getKakao_account().getEmail());
        //가져온 사용자 정보를 객체로 만들어서 반환
        return kakaoGetUser;
    }

    public String getAddress(String lati, String longi){
        try{
            final String APPKEY="9adca47b25d38d5f1826188403e6caca";
            final String API_URL="https://dapi.kakao.com/v2/local/geo/coord2address.json?x="+longi+"&y="+lati+"&input_coord=WGS84";

            HttpHeaders headers=new HttpHeaders();
            headers.add("Authorization","KakaoAK 9adca47b25d38d5f1826188403e6caca");

            MultiValueMap<String,String> parameters=new LinkedMultiValueMap<String,String>();
            parameters.add("x",longi);
            parameters.add("y",lati);
            parameters.add("input_coord","WGS84");

            RestTemplate restTemplate=new RestTemplate();
            ResponseEntity<String> result=restTemplate.exchange(API_URL,HttpMethod.GET,new HttpEntity<>(headers),String.class);

            JSONParser jsonParser=new JSONParser();
            JSONObject jsonObject=(JSONObject)jsonParser.parse(result.getBody());
            System.out.println(result.getBody());
            JSONArray jsonArray=(JSONArray)jsonObject.get("documents");

            JSONObject local=(JSONObject)jsonArray.get(0);
            JSONObject jsonArray1=(JSONObject)local.get("address");
            String localAddress=(String)jsonArray1.get("address_name");

            return localAddress;
        }
        catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
}



