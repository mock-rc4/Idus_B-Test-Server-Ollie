package com.example.demo.src.work;

import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.*;
import com.example.demo.src.work.model.*;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Repository
public class WorkDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetWorkNewRes> getWorksNew(int userIdxByJwt) {
        String getWorksNewQuery =
                "select *\n" +
                "from (\n" +
                "select w.id,author_id, title,img,\n" +
                "       max( case\n" +
                "           when user_id =? then wi.status\n" +
                "           when user_id!=? then 0\n" +
                "           when user_id is null then 0\n" +
                "       end ) as interestStatus,\n" +
                "       w.created_at\n" +
                "from work w\n" +
                "left join work_interest wi\n" +
                "on w.id = wi.work_id\n" +
                "left join (\n" +
                "    select work_id,img\n" +
                "    from work_image\n" +
                "    group by work_id\n" +
                ") image\n" +
                "on w.id=image.work_id\n" +
                "group by w.id) orderWork\n" +
                "order by orderWork.created_at desc";
        int getWorksNewParams = userIdxByJwt;
        return this.jdbcTemplate.query(getWorksNewQuery,
                (rs, rowNum) -> new GetWorkNewRes(
                        rs.getInt("id"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getString("img"),
                        rs.getInt("interestStatus")
                ),
                getWorksNewParams, getWorksNewParams
        );
    }

    public List<GetWorkNewRes> getWorksNewNotLogin() {
        String getWorksNewQuery =
                "select *\n" +
                        "from (\n" +
                        "select w.id,author_id, title,img,\n" +
                        "       0 as interestStatus,\n" +
                        "       w.created_at\n" +
                        "from work w\n" +
                        "left join work_interest wi\n" +
                        "on w.id = wi.work_id\n" +
                        "left join (\n" +
                        "    select work_id,img\n" +
                        "    from work_image\n" +
                        "    group by work_id\n" +
                        ") image\n" +
                        "on w.id=image.work_id\n" +
                        "group by w.id) orderWork\n" +
                        "order by orderWork.created_at desc";
        return this.jdbcTemplate.query(getWorksNewQuery,
                (rs, rowNum) -> new GetWorkNewRes(
                        rs.getInt("id"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getString("img"),
                        rs.getInt("interestStatus")
                )
        );
    }

    public List<GetWorkRealTime> getWorksRealTime(int userId) {
        String getWorksNewQuery =
                "select *\n" +
                "from (\n" +
                "select w.id,author_id, title,img,\n" +
                "       max( case\n" +
                "           when wi.user_id =? then wi.status\n" +
                "           when wi.user_id!=? then 0\n" +
                "           when wi.user_id is null then 0\n" +
                "       end ) as interestStatus,\n" +
                "       wp.created_at,\n" +
                "       wr.content as workReview,\n" +
                "       count(star) as starCnt,\n" +
                "       AVG(star) as star\n" +
                "from work w\n" +
                "left join work_interest wi\n" +
                "on w.id = wi.work_id\n" +
                "left join (\n" +
                "    select work_id,img\n" +
                "    from work_image\n" +
                "    group by work_id\n" +
                ") image\n" +
                "on w.id=image.work_id\n" +
                "left join work_review wr\n" +
                "on w.id = wr.work_id && wr.status=1\n" +
                "join work_purchase wp\n" +
                "on w.id = wp.work_id\n" +
                "group by w.id) orderWork\n" +
                "order by orderWork.created_at desc";
        int getWorksNewParams = userId;
        return this.jdbcTemplate.query(getWorksNewQuery,
                (rs, rowNum) -> new GetWorkRealTime(
                        rs.getInt("id"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getString("img"),
                        rs.getInt("interestStatus"),
                        rs.getString("workReview"),
                        rs.getInt("starCnt"),
                        rs.getFloat("star")
                ),
                getWorksNewParams, getWorksNewParams
        );
    }

    public List<GetWorkSearch> getWorksSearch(String word,int userId) {
        String getWorksNewQuery =
                "select w.id,author_id, title,img,\n" +
                "       max( case\n" +
                "           when wi.user_id =? then wi.status\n" +
                "           when wi.user_id!=? then 0\n" +
                "           when wi.user_id is null then 0\n" +
                "       end ) as interestStatus,\n" +
                "       w.created_at,\n" +
                "       wr.content as workReview,\n" +
                        "price,\n" +
                "       count(star) as starCnt,\n" +
                "       AVG(star) as star\n" +
                "from work w\n" +
                "left join work_interest wi\n" +
                "on w.id = wi.work_id\n" +
                "left join (\n" +
                "    select work_id,img\n" +
                "    from work_image\n" +
                "    group by work_id\n" +
                ") image\n" +
                "on w.id=image.work_id\n" +
                "left join work_review wr\n" +
                "on w.id = wr.work_id && wr.status=1\n" +
                "where w.title\n" +
                "like ? \n" +
                "group by w.id";
        int getWorksNewParams = userId;
        String keyword='%'+word+'%';
        return this.jdbcTemplate.query(getWorksNewQuery,
                (rs, rowNum) -> new GetWorkSearch(
                        rs.getInt("id"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getString("img"),
                        rs.getInt("interestStatus"),
                        rs.getString("workReview"),
                        rs.getInt("price"),
                        rs.getInt("starCnt"),
                        rs.getFloat("star")
                ),
                getWorksNewParams, getWorksNewParams,keyword
        );
    }

    public List<GetWorkRealTime> getWorksToday(int userId) {
        String getWorksNewQuery =
                "select w.id,author_id,title,img,\n" +
                "       max( case\n" +
                "           when wi.user_id =? then wi.status\n" +
                "           when wi.user_id!=? then 0\n" +
                "           when wi.user_id is null then 0\n" +
                "       end ) as interestStatus,\n" +
                "       w.created_at,\n" +
                "       wr.content as workReview,\n" +
                "       count(star) as starCnt,\n" +
                "       AVG(star) as star\n" +
                "from work w\n" +
                "left join work_interest wi\n" +
                "on w.id = wi.work_id\n" +
                "left join (\n" +
                "    select work_id,img\n" +
                "    from work_image\n" +
                "    group by work_id\n" +
                ") image\n" +
                "on w.id=image.work_id\n" +
                "left join work_review wr\n" +
                "on w.id = wr.work_id && wr.status=1\n" +
                "where DATE(w.created_at) = DATE(NOW())\n" +
                "group by w.id";
        int getWorksNewParams = userId;
        return this.jdbcTemplate.query(getWorksNewQuery,
                (rs, rowNum) -> new GetWorkRealTime(
                        rs.getInt("id"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getString("img"),
                        rs.getInt("interestStatus"),
                        rs.getString("workReview"),
                        rs.getInt("starCnt"),
                        rs.getFloat("star")
                ),
                getWorksNewParams, getWorksNewParams
        );
    }

    public GetWorkDetailRes getWork(int workId, int userId) {
        String getWorkQuery =
                "select w.id,author_id, category, title, price, delivery_price, delivery_start,quantity,content, ifnull(wi.status,0) as interestStatus, starCnt, star\n" +
                        "from work w\n" +
                        "left join work_interest wi\n" +
                        "on w.id = wi.work_id && wi.user_id= ?\n" +
                        "left join(\n" +
                        "    select work_id,count(star) as starCnt,AVG(star) as star\n" +
                        "    from work_review\n" +
                        "    where work_review.status=1" +
                        "    group by work_id\n" +
                        ") work_star\n" +
                        "on w.id=work_star.work_id\n" +
                        "join work_category wc\n" +
                        "on w.category_id = wc.id\n" +
                        "where w.id= ?";
        int getWorkParams = workId;
        int getWorkParams2 = userId;
        return this.jdbcTemplate.queryForObject(getWorkQuery,
                (rs, rowNum) -> new GetWorkDetailRes(
                        rs.getInt("id"),
                        rs.getInt("author_id"),
                        rs.getString("category"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getInt("delivery_price"),
                        rs.getString("delivery_start"),
                        rs.getString("quantity"),
                        rs.getString("content"),
                        rs.getInt("interestStatus"),
                        rs.getInt("starCnt"),
                        rs.getFloat("star")
                ),
                getWorkParams2, getWorkParams);
    }

    public GetWorkDetailRes getWorkNotLogin(int workId) {
        String getWorkQuery =
                "select w.id,author_id, category, title, price, delivery_price, delivery_start,quantity,content, 0 as interestStatus, starCnt, star\n" +
                        "from work w\n" +
                        "left join(\n" +
                        "    select work_id,count(star) as starCnt,AVG(star) as star\n" +
                        "    from work_review\n" +
                        "    where work_review.status=1" +
                        "    group by work_id\n" +
                        ") work_star\n" +
                        "on w.id=work_star.work_id\n" +
                        "join work_category wc\n" +
                        "on w.category_id = wc.id\n" +
                        "where w.id= ?";
        int getWorkParams = workId;
        return this.jdbcTemplate.queryForObject(getWorkQuery,
                (rs, rowNum) -> new GetWorkDetailRes(
                        rs.getInt("id"),
                        rs.getInt("author_id"),
                        rs.getString("category"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getInt("delivery_price"),
                        rs.getString("delivery_start"),
                        rs.getString("quantity"),
                        rs.getString("content"),
                        rs.getInt("interestStatus"),
                        rs.getInt("starCnt"),
                        rs.getFloat("star")
                ),
                 getWorkParams);
    }

    public List<GetWorkReviewRes> getWorkReview(int workId){
        String getWorkQuery =
                "select wr.id, name, star, wr.created_at, content, img\n" +
                "from work_review wr\n" +
                "join idusB.user u\n" +
                "on wr.user_id = u.id\n" +
                "left join work_review_image wri\n" +
                "on wr.id = wri.work_review_id\n" +
                "where work_id=? && wr.status=1\n" +
                "group by wr.id";
        int getWorkParams = workId;
//        int getWorkParams2 = userId;
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy년 MM월 dd일");
        return this.jdbcTemplate.query(getWorkQuery,
                (rs, rowNum) -> new GetWorkReviewRes(
                        rs.getInt("wr.id"),
                        rs.getString("name"),
                        rs.getFloat("star"),
                        dateFormat.format(rs.getTimestamp("wr.created_at")),
                        rs.getString("content"),
                        rs.getString("img")
                ),
                getWorkParams);
    }

    public List<GetWorkComment> getWorkComment(int workId){
        String getWorkQuery =
                "select work_comment.id, name, content\n" +
                "from work_comment\n" +
                "join idusB.user u\n" +
                "on work_comment.user_id = u.id\n" +
                "where work_comment.work_id=? && work_comment.status=1";
        int getWorkParams = workId;
        return this.jdbcTemplate.query(getWorkQuery,
                (rs, rowNum) -> new GetWorkComment(
                        rs.getInt("work_comment.id"),
                        rs.getString("name"),
                        rs.getString("content")
                ),
                getWorkParams);
    }
    public List<String> getWorkKeyword(int workId){
        String getWorkQuery =
                "select keyword\n" +
                "from work_keyword\n" +
                "join work w on work_keyword.work_id = w.id\n" +
                "where work_id=?";
        int getWorkParams = workId;
        return this.jdbcTemplate.query(getWorkQuery,
                (rs, rowNum) -> new String(
                        rs.getString("keyword")
                ),
                getWorkParams);
    }
    public List<String> getWorkImg(int workId){
        String getWorkQuery =
                "select img\n" +
                "from work_image\n" +
                "where work_id=?";
        int getWorkParams = workId;
        return this.jdbcTemplate.query(getWorkQuery,
                (rs, rowNum) -> new String(
                        rs.getString("img")
                ),
                getWorkParams);
    }
    public List<WorkCategory> getWorksCategory(){
        String getWorkQuery =
                "select id,category\n" +
                "from work_category";

        return this.jdbcTemplate.query(getWorkQuery,
                (rs, rowNum) -> new WorkCategory(
                        rs.getInt("id"),
                        rs.getString("category")
                ));
    }
    /*작품 추천*/
    public List<WorkRecommend> getWorksRecommend(int workId){
        String getWorkCategory="" +
                "select category_id\n" +
                "from work\n" +
                "where work.id=?";
        int getworkId=workId;
        int categoryId= this.jdbcTemplate.queryForObject(getWorkCategory,
                        int.class,
                        getworkId);

        String getWorkQuery =
                "select w.id, title, img\n" +
                "from work w\n" +
                "left join work_image wi\n" +
                "on w.id = wi.work_id\n" +
                "where w.category_id=? && w.id not in (?)\n" +
                "group by w.id;";

        return this.jdbcTemplate.query(getWorkQuery,
                (rs, rowNum) -> new WorkRecommend(
                        rs.getInt("id"),
                        rs.getString("img"),
                        rs.getString("title")
                ),categoryId,getworkId);
    }

    /*작품 카테고리 별 목록 api*/
    public List<GetWorkSearch> getWorksbyCategory(int categoryId,int userId) {
        String getWorksNewQuery =
                "select w.id, author_id, title,img,\n" +
                "       max( case\n" +
                "           when wi.user_id =? then wi.status\n" +
                "           when wi.user_id!=? then 0\n" +
                "           when wi.user_id is null then 0\n" +
                "       end ) as interestStatus,\n" +
                "       w.created_at,\n" +
                "       wr.content as workReview,\n" +
                "       price,\n" +
                "       count(star) as starCnt,\n" +
                "       AVG(star) as star\n" +
                "from work w\n" +
                "left join work_interest wi\n" +
                "on w.id = wi.work_id\n" +
                "left join (\n" +
                "    select work_id,img\n" +
                "    from work_image\n" +
                "    group by work_id\n" +
                ") image\n" +
                "on w.id=image.work_id\n" +
                "left join work_review wr\n" +
                "on w.id = wr.work_id && wr.status=1\n" +
                "where category_id=?\n" +
                "group by w.id;";
        int getWorksNewParams = userId;
        int getWorksNewParams2=categoryId;
        return this.jdbcTemplate.query(getWorksNewQuery,
                (rs, rowNum) -> new GetWorkSearch(
                        rs.getInt("id"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getString("img"),
                        rs.getInt("interestStatus"),
                        rs.getString("workReview"),
                        rs.getInt("price"),
                        rs.getInt("starCnt"),
                        rs.getFloat("star")
                ),
                getWorksNewParams, getWorksNewParams,getWorksNewParams2
        );
    }
    /*작품 관심 누르기*/
    public UserInterest createWorkInterest(int workId,int userId){

        String checkInterestQuery = "select exists(select * from work_interest where work_id=? && user_id=? && status = 1)";
        Object[] checkInterestParams = new Object[]{workId,userId};
        int result= this.jdbcTemplate.queryForObject(checkInterestQuery,
                int.class,
                checkInterestParams);

        if(result==1){
            String createWorkInterestQuery = "delete from work_interest where work_id=? && user_id=?";
            Object[] createWorkInterestParams = new Object[]{workId,userId};
            this.jdbcTemplate.update(createWorkInterestQuery, createWorkInterestParams);
        }
        else{
            String createWorkInterestQuery = "insert into work_interest(work_id,user_id) VALUES (?,?)";
            Object[] createWorkInterestParams = new Object[]{workId,userId};
            this.jdbcTemplate.update(createWorkInterestQuery, createWorkInterestParams);
            String lastInserIdQuery = "select last_insert_id()";
            int getInterestparams=this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
            String getInterestQuery="select * from work_interest where id=?";
            return this.jdbcTemplate.queryForObject(getInterestQuery,
                    (rs, rowNum) -> new UserInterest(
                            rs.getInt("work_id"),
                            rs.getInt("user_id"),
                            rs.getInt("status")
                    ),
                    getInterestparams);
        }
        return new UserInterest(workId,userId,0);
    }

    /*작품 관심 해제*/
    public UserInterest clearWorkInterest(UserInterest userInterest,int userId){

        String createWorkInterestQuery = "delete from work_interest where work_id=? && user_id=?";
        Object[] createWorkInterestParams = new Object[]{userInterest.getWorkId(),userId};
        this.jdbcTemplate.update(createWorkInterestQuery, createWorkInterestParams);

        return new UserInterest(userInterest.getWorkId(),userId,0);
    }
    /*작품 댓글 달기*/
    public GetWorkComment createWorkComment(WorkCommentReview workCommentReview, int userId){

        String createWorkInterestQuery = "insert into work_comment(work_id,user_id,content) VALUES (?,?,?)";
        Object[] createWorkInterestParams = new Object[]{workCommentReview.getWorkId(),userId,workCommentReview.getContent()};
        this.jdbcTemplate.update(createWorkInterestQuery, createWorkInterestParams);

        String lastInserIdQuery = "select last_insert_id()";
        int getInterestparams=this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
        String getInterestQuery="" +
                "select work_comment.id, name,content\n" +
                "from work_comment\n" +
                "join idusB.user\n" +
                "on work_comment.user_id = user.id\n" +
                "where work_comment.id=?";
        return this.jdbcTemplate.queryForObject(getInterestQuery,
                (rs, rowNum) -> new GetWorkComment(
                        rs.getInt("work_comment.id"),
                        rs.getString("name"),
                        rs.getString("content")
                ),
                getInterestparams);
    }
    /*작품 댓글 삭제*/
    public int clearWorkComment(int workCommentId,int userId){

        String createWorkInterestQuery = "update work_comment set status=? where id=? && user_id=?";
        Object[] createWorkInterestParams = new Object[]{0,workCommentId,userId};
        return this.jdbcTemplate.update(createWorkInterestQuery, createWorkInterestParams);
    }
    /*작품 후기 쓰기*/
    public GetWorkReviewRes createWorkReview(WorkCommentReview workCommentReview, int userId) throws BaseException {

        if(checkWorkPurchase(workCommentReview.getWorkId(),userId)==0){
            throw new BaseException(USERS_INVALID_WORK_REVIEW);
        }

        String createWorkReviewQuery = "insert into work_review(work_id,user_id,content,star) VALUES (?,?,?,?)";
        Object[] createWorkReviewParams = new Object[]{workCommentReview.getWorkId(),userId,workCommentReview.getContent(),workCommentReview.getStar()};
        this.jdbcTemplate.update(createWorkReviewQuery, createWorkReviewParams);

        String lastInserIdQuery = "select last_insert_id()";
        int getWorkReviewparams=this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);

        for(int i=0;i<workCommentReview.getReviewImg().size();i++){
            Object[] createWorkReviewImgParams = new Object[]{getWorkReviewparams,workCommentReview.getReviewImg().get(i)};
            this.jdbcTemplate.update("insert into work_review_image(work_review_id, img) VALUES (?,?)",createWorkReviewImgParams);
        }

        String getInterestQuery="" +
                "select wr.id,name, star, wr.created_at, content,img\n" +
                "from work_review wr\n" +
                "join idusB.user u\n" +
                "on wr.user_id = u.id\n" +
                "left join work_review_image wri\n" +
                "on wr.id = wri.work_review_id\n" +
                "where wr.id= ?\n" +
                "group by wr.id";
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy년 MM월 dd일");
        return this.jdbcTemplate.queryForObject(getInterestQuery,
                (rs, rowNum) -> new GetWorkReviewRes(
                        rs.getInt("wr.id"),
                        rs.getString("name"),
                        rs.getFloat("star"),
                        dateFormat.format(rs.getTimestamp("wr.created_at")),
                        rs.getString("content"),
                        rs.getString("img")
                ),
                getWorkReviewparams);
    }
    /*작품 후기 삭제*/
    public int clearWorkReview(int workReviewId,int userId){

        String createWorkInterestQuery = "update work_review set status=? where id=? && user_id=?";
        Object[] createWorkInterestParams = new Object[]{0,workReviewId,userId};
        return this.jdbcTemplate.update(createWorkInterestQuery, createWorkInterestParams);
    }

    public int checkWorkPurchase(int workId,int userId){
        String checkInterestQuery = "select exists(select * from work_purchase where work_id=? && user_id=? && status = 1)";
        Object[] checkInterestParams = new Object[]{workId,userId};
        int result= this.jdbcTemplate.queryForObject(checkInterestQuery,
                int.class,
                checkInterestParams);
        return result;
    }

    /*작품 구매하기*/
    public UserInterest createWorkPurchase(int workId, int userId){

        String createWorkPurchaseQuery = "insert into work_purchase(work_id,user_id) VALUES (?,?)";
        Object[] createWorkPurchaseParams = new Object[]{workId,userId};
        this.jdbcTemplate.update(createWorkPurchaseQuery, createWorkPurchaseParams);
        String lastInserIdQuery = "select last_insert_id()";
        int getInterestparams=this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
        String getInterestQuery="select * from work_purchase where id=?";
        return this.jdbcTemplate.queryForObject(getInterestQuery,
                (rs, rowNum) -> new UserInterest(
                        rs.getInt("work_id"),
                        rs.getInt("user_id"),
                        rs.getInt("status")
                ),
                getInterestparams);
    }
}
