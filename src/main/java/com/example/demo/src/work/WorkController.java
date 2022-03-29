package com.example.demo.src.work;
import com.example.demo.src.user.model.GetUserRes;
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
    @GetMapping("/{workId}") // (GET) 127.0.0.1:9000/app/products/:id 해도 됨...
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
    @GetMapping("")
    public BaseResponse<List<GetWorkSearch>> getWorksSearch(@RequestParam(required = false) String word) {
        try{
            if(word==null){
                System.out.println("null입니다.");
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
    public BaseResponse<List<GetWorkRealTime>> getWorksToday() {
        try{
            int userId = jwtService.getUserIdx();
            List<GetWorkRealTime> getWorkRealTime = workProvider.getWorksToday(userId);
            return new BaseResponse<>(getWorkRealTime);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 작품 카테고리 목록 api
     * [Get] /works */
    @ResponseBody
    @GetMapping("/category")
    public BaseResponse<List<WorkCategory>> getWorksCategory() {
        try{
            List<WorkCategory> workCategory = workProvider.getWorksCategory();
            return new BaseResponse<>(workCategory);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 작품 상세 페이지(기본화면) api
     * [Get] /works/detail */
    @ResponseBody
    @GetMapping("/category/{categoryId}") // (GET) 127.0.0.1:9000/app/products/:id 해도 됨...
    public BaseResponse<List<GetWorkSearch>> getWorksbyCategory(@PathVariable("categoryId") int categoryId) {
        // Get Users
        try{
            int userIdx = jwtService.getUserIdx();
            List<GetWorkSearch> getWorkSearch = workProvider.getWorksbyCategory(categoryId,userIdx);
            return new BaseResponse<>(getWorkSearch);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 작품에 관심누르기 API
     * [POST} */
    @ResponseBody
    @GetMapping("/interest/{workId}")
    public BaseResponse<UserInterest> createWorkInterest(@PathVariable("workId") int workId) {

        try{
            int userId = jwtService.getUserIdx();
            UserInterest interest = workService.createWorkInterest(workId,userId);
            return new BaseResponse<>(interest);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 작품에 댓글달기 API
     * [POST} */
    @ResponseBody
    @PostMapping("/comment")
    public BaseResponse<GetWorkComment> createWorkComment(@RequestBody WorkCommentReview workCommentReview) {

        try{
            int userId = jwtService.getUserIdx();
            GetWorkComment getWorkComment = workService.createWorkComment(workCommentReview,userId);
            return new BaseResponse<>(getWorkComment);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 작품에 단 댓글 삭제 API
     * [Get} */
    @ResponseBody
    @GetMapping("/comment/{workCommentId}")
    public BaseResponse<DeleteResult> clearWorkComment(@PathVariable("workCommentId") int workCommentId) {

        try{
            int userId = jwtService.getUserIdx();
            DeleteResult deleteResult = workService.clearWorkComment(workCommentId,userId);
            return new BaseResponse<>(deleteResult);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 작품에 후기 쓰기 API
     * [POST} */
    @ResponseBody
    @PostMapping("/review")
    public BaseResponse<GetWorkReviewRes> createWorkReview(@RequestBody WorkCommentReview workCommentReview) {

        try{
            int userId = jwtService.getUserIdx();
            GetWorkReviewRes getWorkReviewRes = workService.createWorkReview(workCommentReview,userId);
            return new BaseResponse<>(getWorkReviewRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 작품에 단 후기 삭제 API
     * [Get} */
    @ResponseBody
    @GetMapping("/review/{workReviewId}")
    public BaseResponse<DeleteResult> clearWorkReview(@PathVariable("workReviewId") int workReviewId) {

        try{
            int userId = jwtService.getUserIdx();
            DeleteResult deleteResult = workService.clearWorkReview(workReviewId,userId);
            return new BaseResponse<>(deleteResult);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 작품 구매하기 API
     * [POST} */
    @ResponseBody
    @GetMapping("/purchase/{workId}")
    public BaseResponse<UserInterest> createWorkPurchase(@PathVariable("workId") int workId) {

        try{
            int userId = jwtService.getUserIdx();
            UserInterest interest = workService.createWorkPurchase(workId,userId);
            return new BaseResponse<>(interest);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
