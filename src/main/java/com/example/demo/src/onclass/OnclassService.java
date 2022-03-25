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
}
