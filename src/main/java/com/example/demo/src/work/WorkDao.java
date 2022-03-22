package com.example.demo.src.work;

import com.example.demo.src.user.model.*;
import com.example.demo.src.work.model.GetWorkNewRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class WorkDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetWorkNewRes> getWorksNew(int userIdxByJwt){
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
                (rs,rowNum) -> new GetWorkNewRes(
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getString("img"),
                        rs.getInt("interestStatus")
                ),
                getWorksNewParams,getWorksNewParams
        );
    }
}
