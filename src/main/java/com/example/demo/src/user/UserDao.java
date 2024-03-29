package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import com.example.demo.src.work.model.GetWorkSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select * from user";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getDate("birthday"),
                        rs.getInt("gender"),
                        rs.getString("phone"),
                        rs.getString("recipient"),
                        rs.getString("recipient_phone"),
                        rs.getString("address"),
                        rs.getString("profile_img"))
                );
    }

    public List<GetUserRes> getUsersByEmail(String email){
        String getUsersByEmailQuery = "select * from user where email =?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getDate("birthday"),
                        rs.getInt("gender"),
                        rs.getString("phone"),
                        rs.getString("recipient"),
                        rs.getString("recipient_phone"),
                        rs.getString("address"),
                        rs.getString("profile_img")),
                getUsersByEmailParams);
    }

    public GetUserRes getUserProfile(int userIdx){
        String getUserQuery = "select * from user where id = ?";
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getDate("birthday"),
                        rs.getInt("gender"),
                        rs.getString("phone"),
                        rs.getString("recipient"),
                        rs.getString("recipient_phone"),
                        rs.getString("address"),
                        rs.getString("profile_img")),
                getUserParams);
    }
    public List<GetWorkSearch> getUserInterest(int userIdx){
        String getUserQuery = "select w.id, author_id, title,img,\n" +
                            "      interestStatus,\n" +
                            "       w.created_at,\n" +
                            "       wr.content as workReview,\n" +
                            "       price,\n" +
                            "       count(star) as starCnt,\n" +
                            "       AVG(star) as star\n" +
                            "from work w\n" +
                            "left join (\n" +
                            "        select w.id, img,\n" +
                            "           max( case\n" +
                            "               when wi.user_id =? then wi.status\n" +
                            "               when wi.user_id!=? then 0\n" +
                            "               when wi.user_id is null then 0\n" +
                            "           end ) as interestStatus\n" +
                            "        from work w\n" +
                            "        left join work_interest wi\n" +
                            "        on w.id = wi.work_id\n" +
                            "        left join (\n" +
                            "            select work_id,img\n" +
                            "            from work_image\n" +
                            "            group by work_id\n" +
                            "        ) image\n" +
                            "        on w.id=image.work_id\n" +
                            "        group by w.id\n" +
                            ") win\n" +
                            "on w.id = win.id\n" +
                            "left join work_review wr\n" +
                            "on w.id = wr.work_id && wr.status=1\n" +
                            "where interestStatus=1\n" +
                            "group by w.id";
        int getUserParams = userIdx;
        return this.jdbcTemplate.query(getUserQuery,
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
                getUserParams, getUserParams
        );
    }

    public PostUserRes createUser(PostUserReq postUserReq){

        if(postUserReq.getSocial()==null) {
            System.out.println("소셜아님");
            String createUserQuery = "insert into user (email, name, password, phone,recommended_code,receiving_consent) VALUES (?,?,?,?,?,?)";

            Object[] createUserParams = new Object[]{postUserReq.getEmail(), postUserReq.getName(), postUserReq.getPassword(), postUserReq.getPhone(),
                    postUserReq.getRecommendedCode(), postUserReq.getReceivingConsent()};
            this.jdbcTemplate.update(createUserQuery, createUserParams);
        }else if(postUserReq.getSocial()=="kakao"){
            String createUserQuery = "insert into user (email, name, password, phone,recommended_code,receiving_consent,social,social_id) VALUES (?,?,?,?,?,?,?,?)";

            Object[] createUserParams = new Object[]{postUserReq.getEmail(), postUserReq.getName(), postUserReq.getPassword(), postUserReq.getPhone(),
                    postUserReq.getRecommendedCode(), postUserReq.getReceivingConsent(),postUserReq.getSocial(),postUserReq.getSocialId()};
            this.jdbcTemplate.update(createUserQuery, createUserParams);
        }

        String lastInserIdQuery = "select last_insert_id()";
        String getNameQuery="select id, name from user where id=?";
        int getNameParams=this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
        return this.jdbcTemplate.queryForObject(getNameQuery,
                (rs, rowNum) -> new PostUserRes(
                        rs.getInt("id"),
                        rs.getString("name")
                ),
                getNameParams);

    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from user where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int modifyUserProfile(GetUserRes getUserRes){
        String modifyUserNameQuery = "update user set email=?, name=?, birthday=?, gender=?, phone=?, recipient=?, recipient_phone=?, address=?, profile_img=? where id = ? ";
        Object[] modifyUserNameParams = new Object[]{getUserRes.getEmail(),getUserRes.getName(),getUserRes.getBirthday(),
        getUserRes.getGender(),getUserRes.getPhone(),getUserRes.getRecipient(),getUserRes.getRecipientPhone(),getUserRes.getAddress(),
        getUserRes.getProfileImg(),getUserRes.getId()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);


    }

    public User getUser(PostLoginReq postLoginReq){
        String getPwdQuery = "select * from user where email = ?";
        String getPwdParams = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("recommended_code"),
                        rs.getInt("receiving_consent"),
                        rs.getInt("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getString("social"),
                        rs.getString("social_id"),
                        rs.getDate("birthday"),
                        rs.getInt("gender"),
                        rs.getString("recipient"),
                        rs.getString("recipient_phone"),
                        rs.getString("address"),
                        rs.getString("profile_img")
                ),
                getPwdParams
                );

    }

    public User kakaoUser(String email){
        String retrieveUserQuery = ""
                + "SELECT * "
                + "FROM   user "
                + "WHERE  email = ?;";
        String retrieveUserParams = email;
        return this.jdbcTemplate.queryForObject(retrieveUserQuery,
                ((rs, rowNum) -> new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("recommended_code"),
                        rs.getInt("receiving_consent"),
                        rs.getInt("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getString("social"),
                        rs.getString("social_id"),
                        rs.getDate("birthday"),
                        rs.getInt("gender"),
                        rs.getString("recipient"),
                        rs.getString("recipient_phone"),
                        rs.getString("address"),
                        rs.getString("profile_img")
                )), retrieveUserParams);
    }


}
