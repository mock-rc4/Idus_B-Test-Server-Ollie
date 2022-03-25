package com.example.demo.src.onclass;
import com.example.demo.src.onclass.model.OnClassDetail;
import com.example.demo.src.onclass.model.OnclassList;
import com.example.demo.src.work.model.GetWorkDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/onlines")
public class OnclassController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OnclassProvider onclassProvider;
    @Autowired
    private final OnclassService onclassService;
    @Autowired
    private final JwtService jwtService;


    public OnclassController(OnclassProvider onclassProvider, OnclassService onclassService, JwtService jwtService) {
        this.onclassProvider = onclassProvider;
        this.onclassService = onclassService;
        this.jwtService = jwtService;
    }

    /**
     * 온라인 전체 클래스 목록(정렬 방식 3가지-최신순, 관심많은순, 후기많은순) api
     * [Get] /onlines
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<OnclassList>> getOnlines(@RequestParam(required = false) Integer sort){
        try {
            int userId = jwtService.getUserIdx();
            System.out.println(sort);
            if (sort == null) {
                List<OnclassList> onclassList = onclassProvider.getOnlinesNew(userId);
                return new BaseResponse<>(onclassList);
            } else if (sort == 1) {
                List<OnclassList> onclassList = onclassProvider.getOnlinesInterest(userId);
                return new BaseResponse<>(onclassList);
            }else if(sort == 2){
                List<OnclassList> onclassList = onclassProvider.getOnlinesReview(userId);
                return new BaseResponse<>(onclassList);
            }
            List<OnclassList> onclassList = onclassProvider.getOnlinesRandom(userId);
            return new BaseResponse<>(onclassList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 작품 상세 페이지(기본화면) api
     * [Get] /works/detail */
    @ResponseBody
    @GetMapping("/{onlineId}") // (GET) 127.0.0.1:9000/app/products/:id 해도 됨...
    public BaseResponse<OnClassDetail> getOnline(@PathVariable("onlineId") int onlineId) {
        // Get Users
        try{
            int userIdx = jwtService.getUserIdx();
            OnClassDetail onClassDetail = onclassProvider.getOnline(onlineId,userIdx);
            return new BaseResponse<>(onClassDetail);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }



}
