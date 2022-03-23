# Idus_B-Test-Server-Ollie

## 1주차 해야할일
  - EC2 인스턴스 구축
  - rds데이터베이스 구축
  - erd설계
  - dev/prod서버 구축
  - ssl구축
  - api명세서 설계
  - 회원가입/로그인 api개발 -new api 개발

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

erd
URL : https://aquerytool.com/aquerymain/index/?rurl=d895bb08-2c37-48c1-9ff3-8aaadc0872fa&
Password : 3xs6i1

api명세서:https://docs.google.com/spreadsheets/d/1tHDdcIBoIRphjRwL4aNMx_cqag7Ekw1C_p_jPg0u_ZQ/edit?usp=sharing

### 2022 -03 -23 진행상황
  - 작품 게시글 상세 페이지 API구현
    - 작품정보, 작품후기, 작품댓글, 작품키워드등 많은 정보가 한 페이지에 들어있어 한방쿼리에 어려움을 겪음
    - 그래서 여러 객체를 따로 가져와서 하나로 합침
  - 앞으로 구현할 api 설계
