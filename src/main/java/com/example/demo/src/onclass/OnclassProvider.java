package com.example.demo.src.onclass;
import com.example.demo.config.BaseException;
import com.example.demo.src.onclass.model.OnClassDetail;
import com.example.demo.src.onclass.model.OnClassDetailBase;
import com.example.demo.src.onclass.model.OnclassList;
import com.example.demo.src.work.WorkDao;
import com.example.demo.src.work.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class OnclassProvider {
    private final OnclassDao onclassDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OnclassProvider(OnclassDao onclassDao, JwtService jwtService) {
        this.onclassDao = onclassDao;
        this.jwtService = jwtService;
    }

    public List<OnclassList> getOnlinesNew(int userId) throws BaseException{
        try{
            List<OnclassList> onclassList = onclassDao.getOnlinesNew(userId);
            return onclassList;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<OnclassList> getOnlinesInterest(int userId) throws BaseException{
        try{
            List<OnclassList> onclassList = onclassDao.getOnlinesInterest(userId);
            return onclassList;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<OnclassList> getOnlinesReview(int userId) throws BaseException{
        try{
            List<OnclassList> onclassList = onclassDao.getOnlinesReview(userId);
            return onclassList;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<OnclassList> getOnlinesRandom(int userId) throws BaseException{
        try{
            List<OnclassList> onclassList = onclassDao.getOnlinesRandom(userId);
            return onclassList;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public OnClassDetail getOnline(int onlineId, int userId) throws BaseException {
        try {

            OnClassDetailBase onClassDetailBase = onclassDao.getOnline(onlineId,userId);

            List<GetWorkReviewRes> onlineReviews = onclassDao.getOnlineReview(onlineId);
            List<GetWorkComment> onlineComments = onclassDao.getOnlineComment(onlineId);

            OnClassDetail onClassDetail=new OnClassDetail();

            onClassDetail.setOnlineDetail(onClassDetailBase);
            onClassDetail.setOnlineReview(onlineReviews);
            onClassDetail.setOnlineComment(onlineComments);

            return onClassDetail;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<OnclassList> getOnlinesSearch(String word,int userId) throws BaseException{
        try{
            List<OnclassList> onclassList = onclassDao.getOnlinesSearch(word,userId);
            return onclassList;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<WorkCategory> getOnlinesCategory() throws BaseException{
        try{
            List<WorkCategory> workCategory = onclassDao.getOnlinesCategory();
            return workCategory;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
//    public List<OnclassList> getOnlinesbyCategory(int categoryId,int userId) throws BaseException{
//        try{
//            List<OnclassList> onclassList = onclassDao.getOnlinesbyCategory(categoryId,userId);
//            return onclassList;
//        }
//        catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
}
