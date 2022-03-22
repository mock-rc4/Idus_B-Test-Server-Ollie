package com.example.demo.src.work;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.model.*;
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
}
