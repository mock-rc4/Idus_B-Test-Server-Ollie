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

    /**
     * 작품에 관심 누르기 API*/
    public UserInterest createOfflineInterest(int offlineId,int userId) throws BaseException {
        //중복
        try{
            UserInterest interest;
            interest = offclassDao.createOfflineInterest(offlineId,userId);

            return interest;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 온클에 댓글 달기 API*/
    public GetWorkComment createOfflineComment(WorkCommentReview workCommentReview, int userId) throws BaseException {
        //중복
        try{
            GetWorkComment getWorkComment = offclassDao.createOfflineComment(workCommentReview,userId);
            return getWorkComment;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 온클에 단 댓글 삭제 API*/
    public DeleteResult clearOfflineComment(int offlineCommentId, int userId) throws BaseException {
        //중복
        try{
            int result = offclassDao.clearOfflineComment(offlineCommentId,userId);
            DeleteResult deleteResult;
            if(result==0){
                deleteResult=new DeleteResult(offlineCommentId,"삭제가 불가능합니다.");
            }
            else{
                deleteResult=new DeleteResult(offlineCommentId,"삭제 완료");
            }
            return deleteResult;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 온클에 후기 쓰기 API*/
    public GetWorkReviewRes createOfflineReview(WorkCommentReview workCommentReview, int userId) throws BaseException {
        //중복
        try{
            GetWorkReviewRes getWorkReviewRes = offclassDao.createOfflineReview(workCommentReview,userId);
            return getWorkReviewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /**
     * 온클에 단 후기 삭제 API*/
    public DeleteResult clearOfflineReview(int offlineReviewId, int userId) throws BaseException {
        //중복
        try{
            int result = offclassDao.clearOfflineReview(offlineReviewId,userId);
            DeleteResult deleteResult;
            if(result==0){
                deleteResult=new DeleteResult(offlineReviewId,"후기 삭제가 불가능합니다.");
            }
            else{
                deleteResult=new DeleteResult(offlineReviewId,"삭제 완료");
            }
            return deleteResult;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

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
