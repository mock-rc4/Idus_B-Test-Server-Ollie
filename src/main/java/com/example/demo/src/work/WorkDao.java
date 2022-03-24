package com.example.demo.src.work;

import com.example.demo.src.user.model.*;
import com.example.demo.src.work.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.List;

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
                        "select author_id, title,img,\n" +
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
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getString("img"),
                        rs.getInt("interestStatus")
                ),
                getWorksNewParams, getWorksNewParams
        );
    }

    public List<GetWorkRealTime> getWorksRealTime(int userId) {
        String getWorksNewQuery =
                "select *\n" +
                "from (\n" +
                "select author_id, title,img,\n" +
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
                "on w.id = wr.work_id\n" +
                "join work_purchase wp\n" +
                "on w.id = wp.work_id\n" +
                "group by w.id) orderWork\n" +
                "order by orderWork.created_at desc";
        int getWorksNewParams = userId;
        return this.jdbcTemplate.query(getWorksNewQuery,
                (rs, rowNum) -> new GetWorkRealTime(
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
                "select author_id, title,img,\n" +
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
                "on w.id = wr.work_id\n" +
                "where w.title\n" +
                "like ? \n" +
                "group by w.id";
        int getWorksNewParams = userId;
        String keyword='%'+word+'%';
        return this.jdbcTemplate.query(getWorksNewQuery,
                (rs, rowNum) -> new GetWorkSearch(
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
                "select author_id,title,img,\n" +
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
                "on w.id = wr.work_id\n" +
                "where DATE(w.created_at) = DATE(NOW())\n" +
                "group by w.id";
        int getWorksNewParams = userId;
        return this.jdbcTemplate.query(getWorksNewQuery,
                (rs, rowNum) -> new GetWorkRealTime(
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
                "select author_id, category, title, price, delivery_price, delivery_start,quantity,content, ifnull(wi.status,0) as interestStatus, starCnt, star\n" +
                        "from work w\n" +
                        "left join work_interest wi\n" +
                        "on w.id = wi.work_id && wi.user_id= ?\n" +
                        "left join(\n" +
                        "    select work_id,count(star) as starCnt,AVG(star) as star\n" +
                        "    from work_review\n" +
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

    public List<GetWorkReviewRes> getWorkReview(int workId){
        String getWorkQuery =
                "select name, star, wr.created_at, content, img\n" +
                "from work_review wr\n" +
                "join idusB.user u\n" +
                "on wr.user_id = u.id\n" +
                "join work_review_image wri\n" +
                "on wr.id = wri.work_review_id\n" +
                "where work_id= ?\n" +
                "group by wri.work_review_id";
        int getWorkParams = workId;
//        int getWorkParams2 = userId;
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy년 MM월 dd일");
        return this.jdbcTemplate.query(getWorkQuery,
                (rs, rowNum) -> new GetWorkReviewRes(
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
                "select name, content\n" +
                "from work_comment\n" +
                "join idusB.user u\n" +
                "on work_comment.user_id = u.id\n" +
                "where work_comment.work_id=?";
        int getWorkParams = workId;
        return this.jdbcTemplate.query(getWorkQuery,
                (rs, rowNum) -> new GetWorkComment(
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
}
