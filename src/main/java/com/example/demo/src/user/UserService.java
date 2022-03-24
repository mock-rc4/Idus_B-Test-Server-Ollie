package com.example.demo.src.user;



import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

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
        if(userProvider.checkEmail(postUserReq.getEmail()) ==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        String pwd;
        try{
            //암호화
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            PostUserRes postUserRes = userDao.createUser(postUserReq);
            return postUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetUserRes modifyUserProfile(GetUserRes getUserRes) throws BaseException {
        try{
            GetUserRes userprofile=userDao.getUserProfile(getUserRes.getId()); //아이디 값 가진 user profile정보 불러옴
            getUserRes.setNullProfile(userprofile);
            int result = userDao.modifyUserProfile(getUserRes);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
            GetUserRes patchProfile= userDao.getUserProfile(getUserRes.getId());
            return patchProfile;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
