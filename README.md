
## 📢 프로젝트 소개

아이디어스 팀 프로젝트입니다.   
기간: 3/19 ~ 4/1   
참여 인원 : 프론트엔드 1, 백엔드 1   

## 🛠 기술스택

 - SpringBoot
 - AWS
    - EC2 Linux instance
    - RDS Database - MySQL
 - Kakao 소셜로그인
 - Swift

## erd

URL : https://aquerytool.com/aquerymain/index/?rurl=d895bb08-2c37-48c1-9ff3-8aaadc0872fa&
Password : 3xs6i1


## api명세서

https://docs.google.com/spreadsheets/d/1tHDdcIBoIRphjRwL4aNMx_cqag7Ekw1C_p_jPg0u_ZQ/edit?usp=sharing



## 개발일지


### 2022 - 03 -19 진행상황
  - ec2인스턴스 구축
  - dev/prod 나누기
  - rds구축
  - ssl구축
  
### 2022 - 03 -20 진행상황
  - erd설계 70%
  - git 저장소 연결 - 실패: repo not fount에러

### 2022 -03 -21 진행상황
  - 회원가입/로그인, 회원조회 api구현
  
  - 회원가입/로그인 명세서 작성
  - git 저장소 연결 : git 관련 자격증명 다 삭제하고 다시 git pull하니까 해결됨. 
                      
  - 서버에 git clone, git pull로 반영: 이 과정에서 아이디, 비번 입력하는데 access token필요해서 발급받음. access token 일회용인데 저장안해뒀다가 다시 발급받음.
  
  - prod 에 반영하고 테스트 하는데 로그인, 회원가입은 안되고 회원조회는 됨.
    - 테스트 실패 이유: 인바운드규칙 설정을 안한거였음. 그것도 모르고 포트번호 바꿔보고 서브도메인설정파일 바꿔보고 이상한짓함.
   
  - prod 에 회원가입, 로그인 반영
  
 ### 2022 -03 -22 진행상황
  - 특정 유저 프로필 조회 API구현
  - 특정 유저 프로필 수정 API수현
  - 작품 New탭 게시글 전체 조회 API구현 
  - 서버에 반영

### 2022 -03 -23 진행상황
  - 작품 게시글 상세 페이지 API구현
    - 작품정보, 작품후기, 작품댓글, 작품키워드등 많은 정보가 한 페이지에 들어있어 한방쿼리에 어려움을 겪음
    - 그래서 여러 객체를 따로 가져와서 하나로 합침
  - 앞으로 구현할 api 설계

### 2022 -03 -24 진행상황
  - 실시간 탭 작품 조회 api 구현
    -구매된 작품만 조회되도록 함
  - 작품 검색 api 구현
    -검색한 키워드가 제목에 포함되는 작품만 조회되도록함
    -jdbctemplate like 구문 처리에서 처음에 like '%'?'%' 이렇게 했었는데 잘 되지 않음. 
    -like ? 하고 String keyword='%'+word+'%'로 처리함
  - 투데이 탭 오늘의 작품 조회 api 구현
    -오늘 등록된 작품만 조회되도록 함
  - 작품에 관심 누르기/취소하기 api구현
  - 작품에 댓글 달기 api구현
  - 작품에 후기 쓰기 api구현
    - 작품을 구매한 사람만 후기를 쓸 수 있게 validation처리 해줘야 한다. 

### 2022 -3 -25 진행상황
  - 작품에 단 댓글 삭제 api구현
  - 작품에 단 후기 삭제 api구현
  - erd 설계 거의 완료(온라인클래스, 오프라인클래스 추가)
  - 온라인 전체 클래스 목록 (정렬 방식 3가지-최신순, 관심많은순, 후기많은순) API 구현
    - 정렬방식 나누기를 어떻게 표현할까 고민했는데 query params 방식으로 받기로 함. 의미를 잘 파악할 수 있을것 같았기 때문임. 
  - 온라인 클래스 상세 페이지 api구현
  
### 2022 -3 -26 진행상황
  - erd설계 - 구매테이블, 이미지테이블 추가
  - 데이터베이스에 데이터 채워넣기
  - 온라인 클래스 검색 api구현
  - 온라인 클래스 관심 누르기/해제하기 api구현
  - 온라인 클래스 댓글 달기/삭제하기 api구현
  - 온라인 클래스 후기 쓰기/삭제하기 api구현
  - 작품 구매하기 api구현
  - 온라인 클래스 구매하기 api구현
  - 작품, 온라인id가 다 리턴되도록 api들 수정(삭제하거나 관심 누를때 id값이 필요한데 간과하고 있었음)
  - 구매한 유저만 후기 쓸수있도록 validation처리 함
  관심누르기/해제하기
  - 관심 누르기/해제하기 api를 전에는 json body값으로 {workid, status}를 받아서 status=0이면 해제하고 1이면 누르기로 구현했었는데 비효율적인 것 같아서 방식을 변경했다. 
  - path variabel로 workid를 받고 workid와 status가1인 레코드가 존재하는지 아닌지 검사해서 workid만 받고도 관심 누르기/해제하기를 자연스럽게 할 수 있게 했다.

### 2022 -03 -27 진행상황
  - 카카오 소셜 로그인 구현
    - 프론트분이랑 상의 없이 임의로 구현해봄. 상의해야함
  
### 2022 -03 -28 진행상황
  - 작품 찜(관심) 목록 조회 api구현
  - 프론트 분이 로그인 안한 버전의 목록 조회 만들어 달라고 하셔서 jwt토큰 받는 부분 수정해서 구현함
  - 오프라인 클래스 api구현(전체 조회, 상세 조회, 관심누르기, 댓글, 후기)

### 2022 -03 -29 진행상황
  - 작품 카테고리 목록 api구현
  - 오프라인 클래스 카테고리 목록 api구현
  - 작품 카테고리별 조회 api구현
  - 오프라인 클래스 카레고리별 조회 api구현
  - 내일 추가할 api
    - 작품 상세 페이지의 같은 카테고리 인기작품 추천 
    - 내가 쓴 구매후기

### 2022 -03 -30 진행상황
  - 같은 카테고리 작품 추천 api 구현
  - 온라인 클래스 정렬 api수정
    - 정렬방식을 int로 받았다가 string으로 수정함. string으로 받으면 의미가 명확하여 불필요한 중간과정을 없앨 수 있다.
  - validation 추가 
    - 댓글, 후기 내용이 비었는지 확인
    - 별점이 0~5사이인지 확인
  - 소셜로그인 수정
    - 인증코드를 받는게 아니라 accesstoken을 받는 형식으로 수정함
  - 위도, 경도값을 받고 전체 지번 주소를 리턴해내는 api 구현
    - 카카오맵 로컬 api사용
    - header로 restapi를 전달하는 과정에서 "KakaoAK"+APPKEY(restapi값 상수) 를 전달했는데 에러 나서 "KakaoAK restapi값"으로 바꿔줬더니 성공함
    - json 파싱 위해 json-simple 이용
    
