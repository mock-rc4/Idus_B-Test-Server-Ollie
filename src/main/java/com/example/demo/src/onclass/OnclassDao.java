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
}
