package com.example.demo.src.work;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.model.*;
import com.example.demo.src.work.model.GetWorkComment;
import com.example.demo.src.work.model.GetWorkReviewRes;
import com.example.demo.src.work.model.WorkCommentReview;
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
public class WorkService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WorkDao workDao;
    private final WorkProvider workProvider;
    private final JwtService jwtService;


    @Autowired
    public WorkService(WorkDao workDao, WorkProvider workProvider, JwtService jwtService) {
        this.workDao = workDao;
        this.workProvider = workProvider;
        this.jwtService = jwtService;

    }
    /**
     * 작품에 관심 누르기 API*/
    public UserInterest createWorkInterest(UserInterest userInterest,int userId) throws BaseException {
        //중복
        try{
            UserInterest interest;
            if(userInterest.getStatus()==0){
                interest = workDao.clearWorkInterest(userInterest,userId);
            }
            else{
                interest = workDao.createWorkInterest(userInterest,userId);
            }
            return interest;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 작품에 댓글 달기 API*/
    public GetWorkComment createWorkComment(WorkCommentReview workCommentReview, int userId) throws BaseException {
        //중복
        try{
            GetWorkComment getWorkComment = workDao.createWorkComment(workCommentReview,userId);
            return getWorkComment;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 작품에 후기 쓰기 API*/
    public GetWorkReviewRes createWorkReview(WorkCommentReview workCommentReview, int userId) throws BaseException {
        //중복
        try{
            System.out.println("서비스");
            GetWorkReviewRes getWorkReviewRes = workDao.createWorkReview(workCommentReview,userId);
            return getWorkReviewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
