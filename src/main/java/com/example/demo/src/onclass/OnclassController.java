package com.example.demo.src.onclass;
import com.example.demo.src.onclass.model.OnClassDetail;
import com.example.demo.src.onclass.model.OnclassList;
import com.example.demo.src.user.model.UserInterest;
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

    /**
     * 온라인 클래스 검색 api
     * [Get] /onlines */
    @ResponseBody
    @GetMapping("/")
    public BaseResponse<List<OnclassList>> getOnlinesSearch(@RequestParam(required = false) String word) {
        try{
            if(word==null){
                System.out.println("null입니다.");
            }
            int userId = jwtService.getUserIdx();
            List<OnclassList>  onclassList = onclassProvider.getOnlinesSearch(word,userId);
            return new BaseResponse<>(onclassList);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 온클에 관심누르기 API
     * [GET]} */
    @ResponseBody
    @GetMapping("/interest")
    public BaseResponse<UserInterest> createOnlineInterest(@RequestParam(required = false) int workId) {

        try{
            int userId = jwtService.getUserIdx();
            UserInterest interest = onclassService.createOnlineInterest(workId,userId);
            return new BaseResponse<>(interest);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 온클에 댓글달기 API
     * [POST} */
    @ResponseBody
    @PostMapping("/comment")
    public BaseResponse<GetWorkComment> createOnlineComment(@RequestBody WorkCommentReview workCommentReview) {

        try{
            int userId = jwtService.getUserIdx();
            GetWorkComment getWorkComment = onclassService.createOnlineComment(workCommentReview,userId);
            return new BaseResponse<>(getWorkComment);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 온클에 단 댓글 삭제 API
     * [Get} */
    @ResponseBody
    @GetMapping("/comment/{onlineCommentId}")
    public BaseResponse<DeleteResult> clearOnlineComment(@PathVariable("onlineCommentId") int onlineCommentId) {

        try{
            int userId = jwtService.getUserIdx();
            DeleteResult deleteResult = onclassService.clearOnlineComment(onlineCommentId,userId);
            return new BaseResponse<>(deleteResult);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 온클에 후기 쓰기 API
     * [POST} */
    @ResponseBody
    @PostMapping("/review")
    public BaseResponse<GetWorkReviewRes> createOnlineReview(@RequestBody WorkCommentReview workCommentReview) {

        try{
            int userId = jwtService.getUserIdx();
            GetWorkReviewRes getWorkReviewRes = onclassService.createOnlineReview(workCommentReview,userId);
            return new BaseResponse<>(getWorkReviewRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 온클에 단 후기 삭제 API
     * [Get} */
    @ResponseBody
    @GetMapping("/review/{onlineReviewId}")
    public BaseResponse<DeleteResult> clearOnlineReview(@PathVariable("onlineReviewId") int onlineReviewId) {

        try{
            int userId = jwtService.getUserIdx();
            DeleteResult deleteResult = onclassService.clearOnlineReview(onlineReviewId,userId);
            return new BaseResponse<>(deleteResult);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
