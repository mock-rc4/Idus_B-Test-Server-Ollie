package com.example.demo.src.work;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
import com.example.demo.src.work.model.GetWorkNewRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.config.BaseResponseStatus.POST_USERS_INVALID_PASSWORD;
import static com.example.demo.utils.ValidationRegex.*;

@RestController
@RequestMapping("/app/works")
public class WorkController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final WorkProvider workProvider;
    @Autowired
    private final WorkService workService;
    @Autowired
    private final JwtService jwtService;


    public WorkController(WorkProvider workProvider, WorkService workService, JwtService jwtService){
        this.workProvider = workProvider;
        this.workService = workService;
        this.jwtService = jwtService;
    }

    /**
     * new탭 작품 목록 api
     * [Get] /works */
    @ResponseBody
    @GetMapping("/new") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetWorkNewRes>> getWorksNew() {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            List<GetWorkNewRes> getWorkNewRes = workProvider.getWorksNew(userIdxByJwt);
            return new BaseResponse<>(getWorkNewRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
