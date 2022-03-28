package com.example.demo.src.offclass;

import com.example.demo.config.BaseException;
import com.example.demo.src.offclass.model.OffClassDetailBase;
import com.example.demo.src.offclass.model.OffclassList;
import com.example.demo.src.onclass.model.OnClassDetailBase;
import com.example.demo.src.onclass.model.OnclassList;
import com.example.demo.src.user.model.UserInterest;
import com.example.demo.src.work.model.GetWorkComment;
import com.example.demo.src.work.model.GetWorkReviewRes;
import com.example.demo.src.work.model.WorkCommentReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.USERS_INVALID_ONLINE_REVIEW;

@Repository
public class OffclassDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<String> getOfflineImg(int offlineId){
        String getWorkQuery =
                "select img\n" +
                "from offline_class_image\n" +
                "where offclass_id=?";
        int getWorkParams = offlineId;
        return this.jdbcTemplate.query(getWorkQuery,
                (rs, rowNum) -> new String(
                        rs.getString("img")
                ),
                getWorkParams);
    }

    public List<OffclassList> getOfflinesNew() {
        String getClassesNewQuery =
                "select *\n" +
                "from(\n" +
                "select oc.id,\n" +
                "       author_id,\n" +
                "       img,\n" +
                "       city,\n" +
                "       town,\n" +
                "       category,\n" +
                "       title,\n" +
                "       star,\n" +
                "       starCnt,\n" +
                "       oc.created_at\n" +
                "from offline_class oc\n" +
                "join offline_class_category occ\n" +
                "on oc.category_id = occ.id\n" +
                "left join offline_class_review ocr\n" +
                "on oc.id = ocr.offclass_id && ocr.status=1\n" +
                "left join(\n" +
                "    select offclass_id,count(star) as starCnt\n" +
                "    from offline_class_review ocr\n" +
                "        where ocr.status=1\n" +
                "    group by offclass_id\n" +
                ") offline_star\n" +
                "on oc.id=offline_star.offclass_id\n" +
                "left join offline_class_image\n" +
                "on oc.id = offline_class_image.offclass_id\n" +
                "left join offline_class_location ocl\n" +
                "on oc.id = ocl.offclass_id\n" +
                "group by oc.id) oc\n" +
                "order by oc.created_at desc";
        return this.jdbcTemplate.query(getClassesNewQuery,
                (rs, rowNum) -> new OffclassList(
                        rs.getInt("id"),
                        rs.getInt("author_id"),
                        rs.getString("img"),
                        rs.getString("city"),
                        rs.getString("town"),
                        rs.getString("category"),
                        rs.getString("title"),
                        rs.getFloat("star"),
                        rs.getInt("starCnt")
                )
        );
    }


    public OffClassDetailBase getOffline(int offlineId, int userId) {
        String getOnlineQuery =
                "select oc.id,\n" +
                "       author_id,\n" +
                "       category,\n" +
                "       title,\n" +
                "       price,\n" +
                "       course_time,\n" +
                "       difficulty,\n" +
                "       people_max,\n" +
                "       city,\n" +
                "       town,\n" +
                "       content,\n" +
                "       ifnull(oci.status,0) as interestStatus\n" +
                "from offline_class oc\n" +
                "join offline_class_category occ\n" +
                "on oc.category_id = occ.id\n" +
                "left join offline_class_interest oci\n" +
                "on oc.id = oci.offclass_id && oci.user_id=?\n" +
                "left join offline_class_location ocl\n" +
                "on oc.id = ocl.offclass_id\n" +
                "where oc.id=?";
        int getOnlineParams = offlineId;
        int getOnlineParams2 = userId;
        return this.jdbcTemplate.queryForObject(getOnlineQuery,
                (rs, rowNum) -> new OffClassDetailBase(
                        rs.getInt("id"),
                        rs.getInt("author_id"),
                        rs.getString("category"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getString("course_time"),
                        rs.getString("difficulty"),
                        rs.getString("people_max"),
                        rs.getString("city"),
                        rs.getString("town"),
                        rs.getString("content"),
                        rs.getInt("interestStatus")
                ),
                getOnlineParams2, getOnlineParams);
    }

    public List<GetWorkReviewRes> getOfflineReview(int offlineId){
        String getWorkQuery =
                "select ocr.id, name, star, ocr.created_at, content, img\n" +
                "from offline_class_review ocr\n" +
                "join idusB.user u\n" +
                "on ocr.user_id = u.id\n" +
                "left join offline_class_review_img ocri\n" +
                "on ocr.id = ocri.offline_class_review_id\n" +
                "where offclass_id=? && ocr.status=1\n" +
                "group by ocr.id";
        int getWorkParams = offlineId;
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy년 MM월 dd일");
        return this.jdbcTemplate.query(getWorkQuery,
                (rs, rowNum) -> new GetWorkReviewRes(
                        rs.getInt("ocr.id"),
                        rs.getString("name"),
                        rs.getFloat("star"),
                        dateFormat.format(rs.getTimestamp("ocr.created_at")),
                        rs.getString("content"),
                        rs.getString("img")
                ),
                getWorkParams);
    }

    public List<GetWorkComment> getOfflineComment(int offlineId){
        String getWorkQuery =
                "select occ.id, name, content\n" +
                "from offline_class_comment occ\n" +
                "join idusB.user u\n" +
                "on occ.user_id = u.id\n" +
                "where occ.offclass_id=? && occ.status=1";
        int getWorkParams = offlineId;
        return this.jdbcTemplate.query(getWorkQuery,
                (rs, rowNum) -> new GetWorkComment(
                        rs.getInt("occ.id"),
                        rs.getString("name"),
                        rs.getString("content")
                ),
                getWorkParams);
    }


//    /*온클에 관심누르기*/
//    public UserInterest createOfflineInterest(int offlineId,int userId){
//
//        String checkInterestQuery = "select exists(select * from offline_class_interest where offclass_id=? && user_id=? && status = 1)";
//        Object[] checkInterestParams = new Object[]{offlineId,userId};
//        int result= this.jdbcTemplate.queryForObject(checkInterestQuery,
//                int.class,
//                checkInterestParams);
//
//        if(result==1){
//            String createWorkInterestQuery = "delete from offline_class_interest where offclass_id=? && user_id=?";
//            Object[] createWorkInterestParams = new Object[]{offlineId,userId};
//            this.jdbcTemplate.update(createWorkInterestQuery, createWorkInterestParams);
//        }
//        else{
//
//            String createWorkInterestQuery = "insert into offline_class_interest(offclass_id,user_id) VALUES (?,?)";
//            Object[] createWorkInterestParams = new Object[]{offlineId,userId};
//            this.jdbcTemplate.update(createWorkInterestQuery, createWorkInterestParams);
//            String lastInserIdQuery = "select last_insert_id()";
//            int getInterestparams=this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
//            String getInterestQuery="select * from offline_class_interest where id=?";
//            return this.jdbcTemplate.queryForObject(getInterestQuery,
//                    (rs, rowNum) -> new UserInterest(
//                            rs.getInt("offclass_id"),
//                            rs.getInt("user_id"),
//                            rs.getInt("status")
//                    ),
//                    getInterestparams);
//        }
//        return new UserInterest(offlineId,userId,0);
//    }
//
//    /*온클 댓글 달기*/
//    public GetWorkComment createOnlineComment(WorkCommentReview workCommentReview, int userId){
//
//        String createOnlineCommentQuery = "insert into online_class_comment(onclass_id,user_id,content) VALUES (?,?,?)";
//        Object[] createOnlineCommentParams = new Object[]{workCommentReview.getWorkId(),userId,workCommentReview.getContent()};
//        this.jdbcTemplate.update(createOnlineCommentQuery, createOnlineCommentParams);
//
//        String lastInserIdQuery = "select last_insert_id()";
//        int getCommentparams=this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
//        System.out.println(getCommentparams);
//        String getCommentReviewQuery="" +
//                "select occ.id, name, content\n" +
//                "from online_class_comment occ\n" +
//                "join idusB.user u\n" +
//                "on occ.user_id = u.id\n" +
//                "where occ.id=? && occ.status=1";
//
//        return this.jdbcTemplate.queryForObject(getCommentReviewQuery,
//                (rs, rowNum) -> new GetWorkComment(
//                        rs.getInt("occ.id"),
//                        rs.getString("name"),
//                        rs.getString("content")
//                ),
//                getCommentparams);
//    }
//
//    /*온클 댓글 삭제*/
//    public int clearOnlineComment(int onlineCommentId,int userId){
//
//        String createWorkInterestQuery = "update online_class_comment set status=? where id=? && user_id=?";
//        Object[] createWorkInterestParams = new Object[]{0,onlineCommentId,userId};
//        return this.jdbcTemplate.update(createWorkInterestQuery, createWorkInterestParams);
//    }
//
//    /*온클 후기 쓰기*/
//    public GetWorkReviewRes createOnlineReview(WorkCommentReview workCommentReview, int userId) throws BaseException {
//
//        if(checkOnlinePurchase(workCommentReview.getWorkId(),userId)==0){
//            throw new BaseException(USERS_INVALID_ONLINE_REVIEW);
//        }
//
//        String createOnlineReviewQuery = "insert into online_class_review(onclass_id,user_id,content,star) VALUES (?,?,?,?)";
//        Object[] createOnlineReviewParams = new Object[]{workCommentReview.getWorkId(),userId,workCommentReview.getContent(),workCommentReview.getStar()};
//        this.jdbcTemplate.update(createOnlineReviewQuery, createOnlineReviewParams);
//
//        String lastInserIdQuery = "select last_insert_id()";
//        int getOnlineReviewparams=this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
//
//        for(int i=0;i<workCommentReview.getReviewImg().size();i++){
//            Object[] createOnlineReviewImgParams = new Object[]{getOnlineReviewparams,workCommentReview.getReviewImg().get(i)};
//            this.jdbcTemplate.update("insert into online_class_review_img(online_class_review_id, img) VALUES (?,?)",createOnlineReviewImgParams);
//        }
//
//        String getInterestQuery="" +
//                "select ocr.id,name, star, ocr.created_at, content, img\n" +
//                "from online_class_review ocr\n" +
//                "join idusB.user u\n" +
//                "on ocr.user_id = u.id\n" +
//                "left join online_class_review_img ocri\n" +
//                "on ocr.id = ocri.online_class_review_id\n" +
//                "where ocr.id=?\n" +
//                "group by ocr.id;";
//        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy년 MM월 dd일");
//        return this.jdbcTemplate.queryForObject(getInterestQuery,
//                (rs, rowNum) -> new GetWorkReviewRes(
//                        rs.getInt("ocr.id"),
//                        rs.getString("name"),
//                        rs.getFloat("star"),
//                        dateFormat.format(rs.getTimestamp("ocr.created_at")),
//                        rs.getString("content"),
//                        rs.getString("img")
//                ),
//                getOnlineReviewparams);
//    }
//    /*온클 후기 삭제*/
//    public int clearOnlineReview(int onlineReviewId,int userId){
//
//        String createOnlineReviewQuery = "update online_class_review set status=? where id=? && user_id=?";
//        Object[] createOnlineReviewParams = new Object[]{0,onlineReviewId,userId};
//        return this.jdbcTemplate.update(createOnlineReviewQuery, createOnlineReviewParams);
//    }
//
//    public int checkOnlinePurchase(int onlineId,int userId){
//        String checkInterestQuery = "select exists(select * from online_class_purchase where onclass_id=? && user_id=? && status = 1)";
//        Object[] checkInterestParams = new Object[]{onlineId,userId};
//        int result= this.jdbcTemplate.queryForObject(checkInterestQuery,
//                int.class,
//                checkInterestParams);
//        return result;
//    }
//    /*온클 구매*/
//    public UserInterest createOnlinePurchase(int onlineId, int userId){
//
//        String createOnlinePurchaseQuery = "insert into online_class_purchase(onclass_id,user_id) VALUES (?,?)";
//        Object[] createOnlinePurchaseParams = new Object[]{onlineId,userId};
//        this.jdbcTemplate.update(createOnlinePurchaseQuery, createOnlinePurchaseParams);
//        String lastInserIdQuery = "select last_insert_id()";
//        int getInterestparams=this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
//        String getInterestQuery="select * from online_class_purchase where id=?";
//        return this.jdbcTemplate.queryForObject(getInterestQuery,
//                (rs, rowNum) -> new UserInterest(
//                        rs.getInt("onclass_id"),
//                        rs.getInt("user_id"),
//                        rs.getInt("status")
//                ),
//                getInterestparams);
//    }
}
