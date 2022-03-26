package com.example.demo.src.work;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.model.*;
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
    public UserInterest createWorkInterest(int workId,int userId) throws BaseException {
        //중복
        try{
            UserInterest interest;
            interest = workDao.createWorkInterest(workId,userId);
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
     * 작품에 단 댓글 삭제 API*/
    public DeleteResult clearWorkComment(int workCommentId, int userId) throws BaseException {
        //중복
        try{
            int result = workDao.clearWorkComment(workCommentId,userId);
            DeleteResult deleteResult;
            if(result==0){
                deleteResult=new DeleteResult(workCommentId,"삭제가 불가능합니다.");
            }
            else{
                deleteResult=new DeleteResult(workCommentId,"삭제 완료");
            }
            return deleteResult;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 작품에 후기 쓰기 API*/
    public GetWorkReviewRes createWorkReview(WorkCommentReview workCommentReview, int userId) throws BaseException {
        //중복
        try{
            GetWorkReviewRes getWorkReviewRes = workDao.createWorkReview(workCommentReview,userId);
            return getWorkReviewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /**
     * 작품에 단 후기 삭제 API*/
    public DeleteResult clearWorkReview(int workReviewId, int userId) throws BaseException {
        //중복
        try{
            int result = workDao.clearWorkReview(workReviewId,userId);
            DeleteResult deleteResult;
            if(result==0){
                deleteResult=new DeleteResult(workReviewId,"삭제가 불가능합니다.");
            }
            else{
                deleteResult=new DeleteResult(workReviewId,"삭제 완료");
            }
            return deleteResult;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
