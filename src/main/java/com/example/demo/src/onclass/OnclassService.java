package com.example.demo.src.onclass;
import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.model.*;
import com.example.demo.src.work.WorkDao;
import com.example.demo.src.work.WorkProvider;
import com.example.demo.src.work.model.DeleteResult;
import com.example.demo.src.work.model.GetWorkComment;
import com.example.demo.src.work.model.GetWorkReviewRes;
import com.example.demo.src.work.model.WorkCommentReview;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.hibernate.sql.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class OnclassService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OnclassDao onclassDao;
    private final OnclassProvider onclassProvider;
    private final JwtService jwtService;


    @Autowired
    public OnclassService(OnclassDao onclassDao, OnclassProvider onclassProvider, JwtService jwtService) {
        this.onclassDao = onclassDao;
        this.onclassProvider = onclassProvider;
        this.jwtService = jwtService;

    }

    /**
     * 작품에 관심 누르기 API*/
    public UserInterest createOnlineInterest(int onlineId,int userId) throws BaseException {
        //중복
        try{
            UserInterest interest;
            interest = onclassDao.createOnlineInterest(onlineId,userId);

            return interest;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 온클에 댓글 달기 API*/
    public GetWorkComment createOnlineComment(WorkCommentReview workCommentReview, int userId) throws BaseException {
        //중복
        try{
            GetWorkComment getWorkComment = onclassDao.createOnlineComment(workCommentReview,userId);
            return getWorkComment;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 온클에 단 댓글 삭제 API*/
    public DeleteResult clearOnlineComment(int onlineCommentId, int userId) throws BaseException {
        //중복
        try{
            int result = onclassDao.clearOnlineComment(onlineCommentId,userId);
            DeleteResult deleteResult;
            if(result==0){
                deleteResult=new DeleteResult(onlineCommentId,"삭제가 불가능합니다.");
            }
            else{
                deleteResult=new DeleteResult(onlineCommentId,"삭제 완료");
            }
            return deleteResult;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 작품에 후기 쓰기 API*/
    public GetWorkReviewRes createOnlineReview(WorkCommentReview workCommentReview, int userId) throws BaseException {
        //중복
        try{
            GetWorkReviewRes getWorkReviewRes = onclassDao.createOnlineReview(workCommentReview,userId);
            return getWorkReviewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /**
     * 작품에 단 후기 삭제 API*/
    public DeleteResult clearOnlineReview(int onlineReviewId, int userId) throws BaseException {
        //중복
        try{
            int result = onclassDao.clearOnlineReview(onlineReviewId,userId);
            DeleteResult deleteResult;
            if(result==0){
                deleteResult=new DeleteResult(onlineReviewId,"후기 삭제가 불가능합니다.");
            }
            else{
                deleteResult=new DeleteResult(onlineReviewId,"삭제 완료");
            }
            return deleteResult;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
