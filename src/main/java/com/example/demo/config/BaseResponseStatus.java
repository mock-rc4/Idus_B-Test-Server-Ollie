package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),

    POST_USERS_EMPTY_NAME(false, 2018, "이름을 입력해주세요."),

    POST_USERS_EMPTY_PHONE(false, 2019, "전화번호를 입력해주세요."),

    POST_USERS_EMPTY_PASSWORD(false, 2020, "비밀번호를 입력해주세요."),
    POST_USERS_EMPTY_PASSWORD2(false, 2021, "비밀번호 확인을 입력해주세요."),
    POST_USERS_INVALID_PASSWORD(false, 2022, "비밀번호 형식을 확인해주세요."),
    POST_USERS_ANOTHER_PASSWORD(false, 2023, "비밀번호가 같지 않습니다."),

    USERS_INVALID_WORK_REVIEW(false, 2030, "작품을 구매한 적이 없는 유저는 후기를 남길 수 없습니다."),
    USERS_INVALID_ONLINE_REVIEW(false, 2031, "온라인클래스를 이용한 적이 없는 유저는 후기를 남길 수 없습니다."),
    USERS_INVALID_OFFLINE_REVIEW(false, 2032, "오프라인클래스를 이용한 적이 없는 유저는 후기를 남길 수 없습니다."),

    POST_KAKAO_INVALID_TOKEN(false,2050,"유효하지않은 카카오 토큰입니다."),
    POST_KAKAO_LOGIN_FAIL(false,2051,"카카오 로그인실패"),
    POST_KAKAO_LOGIN_EXISTS(false,2052,"중복된 카카오아이디입니다."),

    ONLINE_SORT_ERROR(false,2060,"정렬 방식이 잘못되었습니다."),

    POST_REVIEW_STAR_MAX(false,2070,"최대 별점은 5점입니다. 다시 입력해주세요."),
    POST_REVIEW_STAR_MIN(false,2072,"별점은 0점 밑으로 내려가면 안됩니다. 다시 입력해주세요."),
    POST_REVIEW_EMPTY_CONTENT(false,2071,"내용을 입력해주세요"),
    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
