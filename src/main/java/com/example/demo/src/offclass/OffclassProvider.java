package com.example.demo.src.offclass;

import com.example.demo.config.BaseException;
import com.example.demo.src.offclass.model.OffClassDetail;
import com.example.demo.src.offclass.model.OffClassDetailBase;
import com.example.demo.src.offclass.model.OffclassList;
import com.example.demo.src.onclass.OnclassDao;
import com.example.demo.src.onclass.model.OnClassDetail;
import com.example.demo.src.onclass.model.OnClassDetailBase;
import com.example.demo.src.onclass.model.OnclassList;
import com.example.demo.src.work.model.GetWorkComment;
import com.example.demo.src.work.model.GetWorkReviewRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

//Provider : Read의 비즈니스 로직 처리
@Service
public class OffclassProvider {
    private final OffclassDao offclassDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OffclassProvider(OffclassDao offclassDao, JwtService jwtService) {
        this.offclassDao = offclassDao;
        this.jwtService = jwtService;
    }

    public List<OffclassList> getOfflinesNew() throws BaseException{
        try{
            List<OffclassList> offclassList = offclassDao.getOfflinesNew();
            return offclassList;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }



    public OffClassDetail getOffline(int offlineId, int userId) throws BaseException {
        try {

            OffClassDetailBase offClassDetailBase = offclassDao.getOffline(offlineId,userId);
            List<String> imgs=offclassDao.getOfflineImg(offlineId);
            List<GetWorkReviewRes> offlineReviews = offclassDao.getOfflineReview(offlineId);
            List<GetWorkComment> offlineComments = offclassDao.getOfflineComment(offlineId);

            OffClassDetail offClassDetail=new OffClassDetail();

            offClassDetail.setOfflineDetail(offClassDetailBase);
            offClassDetail.setImgs(imgs);
            offClassDetail.setOfflineReview(offlineReviews);
            offClassDetail.setOfflineComment(offlineComments);

            return offClassDetail;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
