package com.example.demo.src.offclass;

import com.example.demo.config.BaseException;
import com.example.demo.src.onclass.OnclassDao;
import com.example.demo.src.onclass.OnclassProvider;
import com.example.demo.src.user.model.UserInterest;
import com.example.demo.src.work.model.DeleteResult;
import com.example.demo.src.work.model.GetWorkComment;
import com.example.demo.src.work.model.GetWorkReviewRes;
import com.example.demo.src.work.model.WorkCommentReview;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.USERS_INVALID_ONLINE_REVIEW;

// Service Create, Update, Delete 의 로직 처리
@Service
public class OffclassService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OffclassDao offclassDao;
    private final OffclassProvider offclassProvider;
    private final JwtService jwtService;


    @Autowired
    public OffclassService(OffclassDao offclassDao, OffclassProvider offclassProvider, JwtService jwtService) {
        this.offclassDao = offclassDao;
        this.offclassProvider = offclassProvider;
        this.jwtService = jwtService;

    }

//    /**
//     * 작품에 관심 누르기 API*/
//    public UserInterest createOnlineInterest(int onlineId,int userId) throws BaseException {
//        //중복
//        try{
//            UserInterest interest;
//            interest = onclassDao.createOnlineInterest(onlineId,userId);
//
//            return interest;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    /**
//     * 온클에 댓글 달기 API*/
//    public GetWorkComment createOnlineComment(WorkCommentReview workCommentReview, int userId) throws BaseException {
//        //중복
//        try{
//            GetWorkComment getWorkComment = onclassDao.createOnlineComment(workCommentReview,userId);
//            return getWorkComment;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    /**
//     * 온클에 단 댓글 삭제 API*/
//    public DeleteResult clearOnlineComment(int onlineCommentId, int userId) throws BaseException {
//        //중복
//        try{
//            int result = onclassDao.clearOnlineComment(onlineCommentId,userId);
//            DeleteResult deleteResult;
//            if(result==0){
//                deleteResult=new DeleteResult(onlineCommentId,"삭제가 불가능합니다.");
//            }
//            else{
//                deleteResult=new DeleteResult(onlineCommentId,"삭제 완료");
//            }
//            return deleteResult;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    /**
//     * 온클에 후기 쓰기 API*/
//    public GetWorkReviewRes createOnlineReview(WorkCommentReview workCommentReview, int userId) throws BaseException {
//        //중복
//        try{
//            GetWorkReviewRes getWorkReviewRes = onclassDao.createOnlineReview(workCommentReview,userId);
//            return getWorkReviewRes;
//        } catch (Exception exception) {
//            throw new BaseException(USERS_INVALID_ONLINE_REVIEW);
//        }
//    }
//    /**
//     * 온클에 단 후기 삭제 API*/
//    public DeleteResult clearOnlineReview(int onlineReviewId, int userId) throws BaseException {
//        //중복
//        try{
//            int result = onclassDao.clearOnlineReview(onlineReviewId,userId);
//            DeleteResult deleteResult;
//            if(result==0){
//                deleteResult=new DeleteResult(onlineReviewId,"후기 삭제가 불가능합니다.");
//            }
//            else{
//                deleteResult=new DeleteResult(onlineReviewId,"삭제 완료");
//            }
//            return deleteResult;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    /**
//     * 온클 구매하기 API*/
//    public UserInterest createOnlinePurchase(int onlineId,int userId) throws BaseException {
//        //중복
//        try{
//            UserInterest interest;
//            interest = onclassDao.createOnlinePurchase(onlineId,userId);
//            return interest;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
}
