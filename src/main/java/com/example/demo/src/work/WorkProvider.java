package com.example.demo.src.work;

import com.example.demo.config.BaseException;
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
public class WorkProvider {

    private final WorkDao workDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public WorkProvider(WorkDao workDao, JwtService jwtService) {
        this.workDao = workDao;
        this.jwtService = jwtService;
    }

    public List<GetWorkNewRes> getWorksNew(int userIdxByJwt) throws BaseException{
        try{
            List<GetWorkNewRes> getWorkNewRes = workDao.getWorksNew(userIdxByJwt);
            return getWorkNewRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public GetWorkDetail getWork(int workId, int userId) throws BaseException {
        try {

            GetWorkDetailRes getWorkDetailRes = workDao.getWork(workId,userId);
            List<String> keywords=workDao.getWorkKeyword(workId);
            List<String> imgs=workDao.getWorkImg(workId);

            List<GetWorkReviewRes> workReviews = workDao.getWorkReview(workId);
            List<GetWorkComment> workComments = workDao.getWorkComment(workId);

            GetWorkDetail getWorkDetail=new GetWorkDetail();

            getWorkDetail.setWorkDetail(getWorkDetailRes);
            getWorkDetail.setKeyword(keywords);
            getWorkDetail.setImgs(imgs);
            getWorkDetail.setWorkReview(workReviews);
            getWorkDetail.setWorkComment(workComments);
            return getWorkDetail;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetWorkRealTime> getWorksRealTime(int userId) throws BaseException{
        try{
            List<GetWorkRealTime> getWorkRealTime = workDao.getWorksRealTime(userId);
            return getWorkRealTime;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetWorkSearch> getWorksSearch(String word,int userId) throws BaseException{
        try{
            List<GetWorkSearch> getWorkSearch = workDao.getWorksSearch(word,userId);
            return getWorkSearch;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetWorkRealTime> getWorksToday(int userId) throws BaseException{
        try{
            List<GetWorkRealTime> getWorkRealTime = workDao.getWorksToday(userId);
            return getWorkRealTime;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
