package com.example.demo.src.onclass;
import com.example.demo.src.onclass.model.OnClassDetailBase;
import com.example.demo.src.onclass.model.OnclassList;
import com.example.demo.src.user.model.*;
import com.example.demo.src.work.model.*;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public class OnclassDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<OnclassList> getOnlinesNew(int userId) {
        String getClassesNewQuery =
                "select *\n" +
                "from(\n" +
                "select author_id,\n" +
                        "video, \n" +
                "       category,\n" +
                "       title,\n" +
                "       price,\n" +
                "       star,\n" +
                "       ocr.content as review,\n" +
                "       name,\n" +
                "        max( case\n" +
                "       when oci.user_id =? then oci.status\n" +
                "       when oci.user_id!=? then 0\n" +
                "       when oci.user_id is null then 0\n" +
                "       end ) as interestStatus,\n" +
                "       oneline_class.created_at\n" +
                "from oneline_class\n" +
                "join online_class_category occ\n" +
                "on oneline_class.category_id = occ.id\n" +
                "left join online_class_review ocr\n" +
                "on oneline_class.id = ocr.onclass_id && ocr.status=1\n" +
                "left join online_class_interest oci\n" +
                "on oneline_class.id = oci.onclass_id\n" +
                "left join idusB.user u\n" +
                "on ocr.user_id = u.id\n" +
                "group by oneline_class.id) oc\n" +
                "order by oc.created_at desc";
        int getClassesNewParams = userId;
        return this.jdbcTemplate.query(getClassesNewQuery,
                (rs, rowNum) -> new OnclassList(
                        rs.getInt("author_id"),
                        rs.getString("video"),
                        rs.getString("category"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getFloat("star"),
                        rs.getString("review"),
                        rs.getString("name"),
                        rs.getInt("interestStatus")
                ),
                getClassesNewParams, getClassesNewParams
        );
    }

    public List<OnclassList> getOnlinesInterest(int userId) {
        String getClassesInterestQuery =
                "select *\n" +
                "from(\n" +
                "select author_id,\n" +
                        "video, \n" +
                "       category,\n" +
                "       title,\n" +
                "       price,\n" +
                "       star,\n" +
                "       ocr.content as review,\n" +
                "       name,\n" +
                "        max( case\n" +
                "       when oci.user_id =? then oci.status\n" +
                "       when oci.user_id!=? then 0\n" +
                "       when oci.user_id is null then 0\n" +
                "       end ) as interestStatus,\n" +
                "       ifnull(interestCnt,0) as interestCnt\n" +
                "from oneline_class\n" +
                "join online_class_category occ\n" +
                "on oneline_class.category_id = occ.id\n" +
                "left join online_class_review ocr\n" +
                "on oneline_class.id = ocr.onclass_id\n" +
                "left join online_class_interest oci\n" +
                "on oneline_class.id = oci.onclass_id\n" +
                "left join(\n" +
                "    select onclass_id, count(status) as interestCnt\n" +
                "    from online_class_interest\n" +
                "    group by onclass_id\n" +
                ") incnt on oneline_class.id=incnt.onclass_id\n" +
                "left join idusB.user u\n" +
                "on ocr.user_id = u.id\n" +
                "group by oneline_class.id\n" +
                ") oc\n" +
                "order by oc.interestCnt desc";
        int getClassesInterestParams = userId;
        return this.jdbcTemplate.query(getClassesInterestQuery,
                (rs, rowNum) -> new OnclassList(
                        rs.getInt("author_id"),
                        rs.getString("video"),
                        rs.getString("category"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getFloat("star"),
                        rs.getString("review"),
                        rs.getString("name"),
                        rs.getInt("interestStatus")
                ),
                getClassesInterestParams, getClassesInterestParams
        );
    }

    public List<OnclassList> getOnlinesReview(int userId) {
        String getClassesReviewQuery =
                "select *\n" +
                "from(\n" +
                "select author_id,\n" +
                        "video, \n" +
                "       category,\n" +
                "       title,\n" +
                "       price,\n" +
                "       star,\n" +
                "       ocr.content as review,\n" +
                "       name,\n" +
                "        max( case\n" +
                "       when oci.user_id =? then oci.status\n" +
                "       when oci.user_id!=? then 0\n" +
                "       when oci.user_id is null then 0\n" +
                "       end ) as interestStatus,\n" +
                "       ifnull(reviewCnt,0) as reviewCnt\n" +
                "from oneline_class\n" +
                "join online_class_category occ\n" +
                "on oneline_class.category_id = occ.id\n" +
                "left join online_class_review ocr\n" +
                "on oneline_class.id = ocr.onclass_id\n" +
                "left join online_class_interest oci\n" +
                "on oneline_class.id = oci.onclass_id\n" +
                "left join(\n" +
                "    select onclass_id, count(if(status=1,status,null)) as reviewCnt\n" +
                "    from online_class_review\n" +
                "    group by onclass_id\n" +
                ") incnt on oneline_class.id=incnt.onclass_id\n" +
                "left join idusB.user u\n" +
                "on ocr.user_id = u.id\n" +
                "group by oneline_class.id\n" +
                ") oc\n" +
                "order by oc.reviewCnt desc";
        int getClassesReviewParams = userId;
        return this.jdbcTemplate.query(getClassesReviewQuery,
                (rs, rowNum) -> new OnclassList(
                        rs.getInt("author_id"),
                        rs.getString("video"),
                        rs.getString("category"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getFloat("star"),
                        rs.getString("review"),
                        rs.getString("name"),
                        rs.getInt("interestStatus")
                ),
                getClassesReviewParams, getClassesReviewParams
        );
    }

    public List<OnclassList> getOnlinesRandom(int userId) {
        String getOnlinesRandomQuery =
                "select *\n" +
                "from(\n" +
                "select author_id,\n" +
                        "video, \n" +
                "       category,\n" +
                "       title,\n" +
                "       price,\n" +
                "       star,\n" +
                "       ocr.content as review,\n" +
                "       name,\n" +
                "        max( case\n" +
                "       when oci.user_id =? then oci.status\n" +
                "       when oci.user_id!=? then 0\n" +
                "       when oci.user_id is null then 0\n" +
                "       end ) as interestStatus,\n" +
                "       oneline_class.created_at\n" +
                "from oneline_class\n" +
                "join online_class_category occ\n" +
                "on oneline_class.category_id = occ.id\n" +
                "left join online_class_review ocr\n" +
                "on oneline_class.id = ocr.onclass_id && ocr.status=1\n" +
                "left join online_class_interest oci\n" +
                "on oneline_class.id = oci.onclass_id\n" +
                "left join idusB.user u\n" +
                "on ocr.user_id = u.id\n" +
                "group by oneline_class.id) oc\n" +
                "order by rand()";
        int getOnlinesRandomParams = userId;
        return this.jdbcTemplate.query(getOnlinesRandomQuery,
                (rs, rowNum) -> new OnclassList(
                        rs.getInt("author_id"),
                        rs.getString("video"),
                        rs.getString("category"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getFloat("star"),
                        rs.getString("review"),
                        rs.getString("name"),
                        rs.getInt("interestStatus")
                ),
                getOnlinesRandomParams, getOnlinesRandomParams
        );
    }

    public OnClassDetailBase getOnline(int onlineId, int userId) {
        String getOnlineQuery =
                "select author_id,\n" +
                        "video, \n" +
                "       category,\n" +
                "       title,\n" +
                "       price,\n" +
                "       course_start,\n" +
                "       difficulty,\n" +
                "       material_explain,\n" +
                "       content,\n" +
                "       ifnull(oci.status,0) as interestStatus,\n" +
                "       starCnt,\n" +
                "       star\n" +
                "from oneline_class oc\n" +
                "join online_class_category occ\n" +
                "on oc.category_id = occ.id\n" +
                "left join online_class_interest oci\n" +
                "on oc.id = oci.onclass_id && oci.user_id=?\n" +
                "left join(\n" +
                "    select onclass_id,count(star) as starCnt,AVG(star) as star\n" +
                "    from online_class_review ocr\n" +
                "        where ocr.status=1\n" +
                "    group by onclass_id\n" +
                ") online_star\n" +
                "on oc.id=online_star.onclass_id\n" +
                "where oc.id=?";
        int getOnlineParams = onlineId;
        int getOnlineParams2 = userId;
        return this.jdbcTemplate.queryForObject(getOnlineQuery,
                (rs, rowNum) -> new OnClassDetailBase(
                        rs.getInt("author_id"),
                        rs.getString("video"),
                        rs.getString("category"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getString("course_start"),
                        rs.getString("difficulty"),
                        rs.getString("material_explain"),
                        rs.getString("content"),
                        rs.getInt("interestStatus"),
                        rs.getInt("starCnt"),
                        rs.getFloat("star")
                ),
                getOnlineParams2, getOnlineParams);
    }

    public List<GetWorkReviewRes> getOnlineReview(int onlineId){
        String getWorkQuery =
                "select name, star, ocr.created_at, content, img\n" +
                "from online_class_review ocr\n" +
                "join idusB.user u\n" +
                "on ocr.user_id = u.id\n" +
                "left join online_class_review_img ocri\n" +
                "on ocr.id = ocri.online_class_review_id\n" +
                "where onclass_id=? && ocr.status=1\n" +
                "group by ocr.id;";
        int getWorkParams = onlineId;
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy년 MM월 dd일");
        return this.jdbcTemplate.query(getWorkQuery,
                (rs, rowNum) -> new GetWorkReviewRes(
                        rs.getString("name"),
                        rs.getFloat("star"),
                        dateFormat.format(rs.getTimestamp("ocr.created_at")),
                        rs.getString("content"),
                        rs.getString("img")
                ),
                getWorkParams);
    }

    public List<GetWorkComment> getOnlineComment(int onlineId){
        String getWorkQuery =
                "select name, content\n" +
                "from online_class_comment occ\n" +
                "join idusB.user u\n" +
                "on occ.user_id = u.id\n" +
                "where occ.onclass_id=? && occ.status=1";
        int getWorkParams = onlineId;
        return this.jdbcTemplate.query(getWorkQuery,
                (rs, rowNum) -> new GetWorkComment(
                        rs.getString("name"),
                        rs.getString("content")
                ),
                getWorkParams);
    }

    public List<OnclassList> getOnlinesSearch(String word, int userId) {
        String getClassesSearchQuery =
                "select author_id,\n" +
                "       video,\n" +
                "       category,\n" +
                "       title,\n" +
                "       price,\n" +
                "       star,\n" +
                "       ocr.content as review,\n" +
                "       name,\n" +
                "        max( case\n" +
                "       when oci.user_id =? then oci.status\n" +
                "       when oci.user_id!=? then 0\n" +
                "       when oci.user_id is null then 0\n" +
                "       end ) as interestStatus\n" +
                "from oneline_class\n" +
                "join online_class_category occ\n" +
                "on oneline_class.category_id = occ.id\n" +
                "left join online_class_review ocr\n" +
                "on oneline_class.id = ocr.onclass_id && ocr.status=1\n" +
                "left join online_class_interest oci\n" +
                "on oneline_class.id = oci.onclass_id\n" +
                "left join idusB.user u\n" +
                "on ocr.user_id = u.id\n" +
                "where oneline_class.title\n" +
                "like ? \n" +
                "group by oneline_class.id";
        int getClassesSearchParams = userId;
        String keyword='%'+word+'%';
        return this.jdbcTemplate.query(getClassesSearchQuery,
                (rs, rowNum) -> new OnclassList(
                        rs.getInt("author_id"),
                        rs.getString("video"),
                        rs.getString("category"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getFloat("star"),
                        rs.getString("review"),
                        rs.getString("name"),
                        rs.getInt("interestStatus")
                ),
                getClassesSearchParams,getClassesSearchParams, keyword
        );
    }

    /*온클에 관심누르기*/
    public UserInterest createOnlineInterest(int workId,int userId){

        String checkInterestQuery = "select exists(select * from online_class_interest where onclass_id=? && user_id=? && status = 1)";
        Object[] checkInterestParams = new Object[]{workId,userId};
        int result= this.jdbcTemplate.queryForObject(checkInterestQuery,
                int.class,
                checkInterestParams);

        if(result==1){
            System.out.println("이미 관심 눌러서 해제됨");
            String createWorkInterestQuery = "delete from online_class_interest where onclass_id=? && user_id=?";
            Object[] createWorkInterestParams = new Object[]{workId,userId};
            this.jdbcTemplate.update(createWorkInterestQuery, createWorkInterestParams);
        }
        else{
            System.out.println("관심 누름");
            String createWorkInterestQuery = "insert into online_class_interest(onclass_id,user_id) VALUES (?,?)";
            Object[] createWorkInterestParams = new Object[]{workId,userId};
            this.jdbcTemplate.update(createWorkInterestQuery, createWorkInterestParams);
            String lastInserIdQuery = "select last_insert_id()";
            int getInterestparams=this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
            String getInterestQuery="select * from online_class_interest where id=?";
            return this.jdbcTemplate.queryForObject(getInterestQuery,
                    (rs, rowNum) -> new UserInterest(
                            rs.getInt("onclass_id"),
                            rs.getInt("user_id"),
                            rs.getInt("status")
                    ),
                    getInterestparams);
        }
        return new UserInterest(workId,userId,0);
    }

    /*온클 댓글 달기*/
    public GetWorkComment createOnlineComment(WorkCommentReview workCommentReview, int userId){

        String createOnlineCommentQuery = "insert into online_class_comment(onclass_id,user_id,content) VALUES (?,?,?)";
        Object[] createOnlineCommentParams = new Object[]{workCommentReview.getWorkId(),userId,workCommentReview.getContent()};
        this.jdbcTemplate.update(createOnlineCommentQuery, createOnlineCommentParams);

        String lastInserIdQuery = "select last_insert_id()";
        int getCommentparams=this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
        System.out.println(getCommentparams);
        String getCommentReviewQuery="" +
                "select name, content\n" +
                "from online_class_comment occ\n" +
                "join idusB.user u\n" +
                "on occ.user_id = u.id\n" +
                "where occ.id=? && occ.status=1";

        return this.jdbcTemplate.queryForObject(getCommentReviewQuery,
                (rs, rowNum) -> new GetWorkComment(
                        rs.getString("name"),
                        rs.getString("content")
                ),
                getCommentparams);
    }

    /*온클 댓글 삭제*/
    public int clearOnlineComment(int onlineCommentId,int userId){

        String createWorkInterestQuery = "update online_class_comment set status=? where id=? && user_id=?";
        Object[] createWorkInterestParams = new Object[]{0,onlineCommentId,userId};
        return this.jdbcTemplate.update(createWorkInterestQuery, createWorkInterestParams);
    }

    /*온클 후기 쓰기*/
    public GetWorkReviewRes createOnlineReview(WorkCommentReview workCommentReview, int userId){

        String createOnlineReviewQuery = "insert into online_class_review(onclass_id,user_id,content,star) VALUES (?,?,?,?)";
        Object[] createOnlineReviewParams = new Object[]{workCommentReview.getWorkId(),userId,workCommentReview.getContent(),workCommentReview.getStar()};
        this.jdbcTemplate.update(createOnlineReviewQuery, createOnlineReviewParams);

        String lastInserIdQuery = "select last_insert_id()";
        int getOnlineReviewparams=this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);

        for(int i=0;i<workCommentReview.getReviewImg().size();i++){
            Object[] createOnlineReviewImgParams = new Object[]{getOnlineReviewparams,workCommentReview.getReviewImg().get(i)};
            this.jdbcTemplate.update("insert into online_class_review_img(online_class_review_id, img) VALUES (?,?)",createOnlineReviewImgParams);
        }

        String getInterestQuery="" +
                "select name, star, ocr.created_at, content, img\n" +
                "from online_class_review ocr\n" +
                "join idusB.user u\n" +
                "on ocr.user_id = u.id\n" +
                "left join online_class_review_img ocri\n" +
                "on ocr.id = ocri.online_class_review_id\n" +
                "where ocr.id=?\n" +
                "group by ocr.id;";
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy년 MM월 dd일");
        return this.jdbcTemplate.queryForObject(getInterestQuery,
                (rs, rowNum) -> new GetWorkReviewRes(
                        rs.getString("name"),
                        rs.getFloat("star"),
                        dateFormat.format(rs.getTimestamp("ocr.created_at")),
                        rs.getString("content"),
                        rs.getString("img")
                ),
                getOnlineReviewparams);
    }
    /*온클 후기 삭제*/
    public int clearOnlineReview(int onlineReviewId,int userId){

        String createOnlineReviewQuery = "update online_class_review set status=? where id=? && user_id=?";
        Object[] createOnlineReviewParams = new Object[]{0,onlineReviewId,userId};
        return this.jdbcTemplate.update(createOnlineReviewQuery, createOnlineReviewParams);
    }
}
