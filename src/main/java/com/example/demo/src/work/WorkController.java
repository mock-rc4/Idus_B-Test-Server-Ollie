package com.example.demo.src.work;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.work.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 작품 상세 페이지(기본화면) api
     * [Get] /works/detail */
    @ResponseBody
    @GetMapping("/detail/{workId}") // (GET) 127.0.0.1:9000/app/products/:id 해도 됨...
    public BaseResponse<GetWorkDetail> getWork(@PathVariable("workId") int workId) {
        // Get Users
        try{
            int userIdx = jwtService.getUserIdx();
            GetWorkDetail getWorkDetail = workProvider.getWork(workId,userIdx);
            return new BaseResponse<>(getWorkDetail);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 실시간 탭 작품 목록 api
     * [Get] /works */
    @ResponseBody
    @GetMapping("/real-time")
    public BaseResponse<List<GetWorkRealTime>> getWorksRealTime() {
        try{
            int userId = jwtService.getUserIdx();
            List<GetWorkRealTime>  getWorkRealTime = workProvider.getWorksRealTime(userId);
            return new BaseResponse<>(getWorkRealTime);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 작품 검색 api
     * [Get] /works */
    @ResponseBody
    @GetMapping("/search")
    public BaseResponse<List<GetWorkSearch>> getWorksSearch(@RequestParam(required = false) String word) {
        try{
            if(word == null){ //params에 Email도 들어오면 안됨
              word="캠핑";
            }
            int userId = jwtService.getUserIdx();
            List<GetWorkSearch>  getWorkSearch = workProvider.getWorksSearch(word,userId);
            return new BaseResponse<>(getWorkSearch);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 실시간 탭 작품 목록 api
     * [Get] /works */
    @ResponseBody
    @GetMapping("/today")
    public BaseResponse<List<GetWorkSearch>> getWorksToday() {
        try{
            int userId = jwtService.getUserIdx();
            List<GetWorkSearch>  getWorkSearch = workProvider.getWorksToday(userId);
            return new BaseResponse<>(getWorkSearch);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
