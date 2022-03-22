package com.example.demo.src.work;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.model.*;
import com.example.demo.src.work.model.GetWorkNewRes;
import com.example.demo.utils.AES128;
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
}
