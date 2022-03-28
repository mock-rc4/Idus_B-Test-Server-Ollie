package com.example.demo.src.offclass;
import com.example.demo.src.offclass.model.OffClassDetail;
import com.example.demo.src.offclass.model.OffclassList;

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

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;



import java.util.List;

@RestController
@RequestMapping("/app/offlines")
public class OffclassController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OffclassProvider offclassProvider;
    @Autowired
    private final OffclassService offclassService;
    @Autowired
    private final JwtService jwtService;


    public OffclassController(OffclassProvider offclassProvider, OffclassService offclassService, JwtService jwtService) {
        this.offclassProvider = offclassProvider;
        this.offclassService = offclassService;
        this.jwtService = jwtService;
    }

    /**
     * 온라인 전체 클래스 목록(정렬 방식 3가지-최신순, 관심많은순, 후기많은순) api
     * [Get] /offlines
     */
    @ResponseBody
    @GetMapping("/new")
    public BaseResponse<List<OffclassList>> getOfflines(){
        try {
            List<OffclassList> offclassList = offclassProvider.getOfflinesNew();
            return new BaseResponse<>(offclassList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 작품 상세 페이지(기본화면) api
     * [Get] /offlines/:onlineId */
    @ResponseBody
    @GetMapping("/{offlineId}") // (GET) 127.0.0.1:9000/app/products/:id 해도 됨...
    public BaseResponse<OffClassDetail> getOffline(@PathVariable("offlineId") int offlineId) {
        // Get Users
        try{
            int userIdx = jwtService.getUserIdx();
            OffClassDetail offClassDetail = offclassProvider.getOffline(offlineId,userIdx);
            return new BaseResponse<>(offClassDetail);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


//    /**
//     * 온클에 관심누르기 API
//     * [GET]} */
//    @ResponseBody
//    @GetMapping("/interest/{onlineId}")
//    public BaseResponse<UserInterest> createOnlineInterest(@PathVariable("onlineId") int onlineId) {
//
//        try{
//            int userId = jwtService.getUserIdx();
//            UserInterest interest = offclassService.createOnlineInterest(onlineId,userId);
//            return new BaseResponse<>(interest);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
//    /**
//     * 온클에 댓글달기 API
//     * [POST} */
//    @ResponseBody
//    @PostMapping("/comment")
//    public BaseResponse<GetWorkComment> createOnlineComment(@RequestBody WorkCommentReview workCommentReview) {
//
//        try{
//            int userId = jwtService.getUserIdx();
//            GetWorkComment getWorkComment = onclassService.createOnlineComment(workCommentReview,userId);
//            return new BaseResponse<>(getWorkComment);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
//    /**
//     * 온클에 단 댓글 삭제 API
//     * [Get} */
//    @ResponseBody
//    @GetMapping("/comment/{onlineCommentId}")
//    public BaseResponse<DeleteResult> clearOnlineComment(@PathVariable("onlineCommentId") int onlineCommentId) {
//
//        try{
//            int userId = jwtService.getUserIdx();
//            DeleteResult deleteResult = onclassService.clearOnlineComment(onlineCommentId,userId);
//            return new BaseResponse<>(deleteResult);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
//    /**
//     * 온클에 후기 쓰기 API
//     * [POST} */
//    @ResponseBody
//    @PostMapping("/review")
//    public BaseResponse<GetWorkReviewRes> createOnlineReview(@RequestBody WorkCommentReview workCommentReview) {
//
//        try{
//            int userId = jwtService.getUserIdx();
//            GetWorkReviewRes getWorkReviewRes = onclassService.createOnlineReview(workCommentReview,userId);
//            return new BaseResponse<>(getWorkReviewRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//    /**
//     * 온클에 단 후기 삭제 API
//     * [Get} */
//    @ResponseBody
//    @GetMapping("/review/{onlineReviewId}")
//    public BaseResponse<DeleteResult> clearOnlineReview(@PathVariable("onlineReviewId") int onlineReviewId) {
//
//        try{
//            int userId = jwtService.getUserIdx();
//            DeleteResult deleteResult = onclassService.clearOnlineReview(onlineReviewId,userId);
//            return new BaseResponse<>(deleteResult);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
//    /**
//     * 온라인클래스 구매하기 API
//     * [POST} */
//    @ResponseBody
//    @GetMapping("/purchase/{onlineId}")
//    public BaseResponse<UserInterest> createOnlinePurchase(@PathVariable("onlineId") int onlineId) {
//
//        try{
//            int userId = jwtService.getUserIdx();
//            UserInterest interest = onclassService.createOnlinePurchase(onlineId,userId);
//            return new BaseResponse<>(interest);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
}
