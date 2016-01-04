# rest

OreumIO James REST API

메일 서버의 서비스를 RESTful API로 제공하는 것을 목표로 합니다.

사용자에는 시스템 관리자, 고객, 그룹 관리자, 보통 사용자가 있습니다.

일반적인 클라이언트는 그룹관리자와 보통사용자를 위한 API 만 사용합니다.
시스템 관리자와 고객은 개발용으로 제공합니다.

- 서버의 실행 방법

War 로 패키징 되어 있는 파일을 사용하여 실행하면 됩니다.

실행환경을 반영하기 위해서 설정을 변경할 필요가 있으면, 다운로드한 war 파일을 풀어서, 변경이 필요한 파일을 손봅니다.

※ 전제조건으로 RDBMS와 MQ(ActiveMQ) 를 실행시켜야 합니다.

-- 참고: Apache ActiveMQ(v 5.12.0) 실행

activemq.apache.org 에서 실행파일을 다운받아서 실행합니다.

$ bin/activemq start

-- 데이터베이스 설정

WEB-INF/classes/james-database.properties: 데이터베이스 접속 정보

-- 데이터베이스 초기화

WEB-INF/classes/META-INF/persistence.xml: JPA 영속성 설정

create 로 되어 있는 hibernate.hbm2ddl.auto 속성을 살려서 기동하면 DDL 실행에 의해서 데이터베이스를 초기화할 수 있습니다.

※ 두 번째 실행할 때는 hibernate.hbm2ddl.auto 속성을 코멘트로 만들어야 데이터베이스 초기화를 막을 수 있습니다.

-- 서버 로깅 설정

WEB-INF/classes/logback.xml

- 사용자 API 스펙

-- 시스템 사용자의 API: swagger-system.yaml 참조
-- 고객 사용자의 API: swagger-client.yaml 참조
-- 그룹 관리자 사용자의 API: swagger-groupadmin.yaml 참조
-- 보통 사용자의 API: swagger-user.yaml 참조

- 시스템 사용자 설정

META-INF/spring/security-spring.xml

user-service 엘러먼트의 user 엘러먼트의 내용을 변경하거나, 엘러먼트를 추가 또는 삭제하면 됩니다.

- 고객, 그룹, 보통 사용자 추가

샘플인 index.jsp 의 내용을 참고

- 메일 보내기

샘플인 send.jsp 의 내용을 참고

- 파일 업로드

샘플인 upload.jsp 의 내용을 참고


보통 사용자의 기능:
 패스워드 변경

 메일 보내기:
  3. 구조화 데이타로 보내기

 메일박스:
  4. 생성
  5. 삭제
  6. 개명
  7. 검색

 메일:
  8. 검색: 메일박스, 사용자 플래그로 검색하기, 첨부파일 여부로 검색하기, 별표 여부로 검색하기, 순서 및 순서 기준 변경, 기간, 보낸사람, 받는사람, 제목, 내용, 첨부파일명 등으로 검색
  검색: 방으로 검색하기
  조회: TEXT/HTML 내용을 선택하기, 전달로 발송, 답변으로 발송, 첨부로 발송, 원문, 첨부파일
  9. 구조화 데이타로 조회하기
  MIME 으로 조회하기
  기본 문자세트 변경 - 비표준 MIME 대응
  10. 조작: 읽은 상태 변경, 새 상태 변경, 메일박스 변경, 사용자 플래그 탈부착, 삭제 플래그 탈부착, 완전삭제

 발신 추적
  검색
  조회

 예약 메일
  생성
  조회
  삭제
  변경:시각
  검색

 초안 메일
  생성
  조회
  삭제
  검색

 파일
  업로드
  다운로드
  조회
  삭제

 웹버그
  조회

 보안메일
  조회

 대용량메일
  다운로드

 설정:
  메일 스팸 처리를 위한 설정 관리 (주소 기준)
  메일 박스 분류 설정을 위한 관리 (판별 기준)
  메일 레이블 분류 설정을 위한 관리 (판별 기준)
  사용자 서명 관리
  외부에서 메일 가져오기 관리
  부재 관리
  포워딩 관리 (판별 기준)
  메일 백업 및 복원
  사용자 단말 설정 관리

 사용자 조직 검색 및 조회

 15. 그룹 설정 조회

 16. 용량 계획 및 실적 조회

 기타:
  주소록*
  일정 달력*
  게시판*
  문서함*
  업무*
  결재*

그룹 관리자의 관리:
 사용자: 도메인 네임스페이스에서 아이디 생성
  아이디 중복 체크
  등록
  정지
  복원
  해지(삭제)
  용량 관리
  포워딩 설정*

 설정 관리:
  기본 설정:
   조회
   변경
  POP3/IMAP 사용 대상자 관리
   추가
   삭제
  대표 메일 관리
   생성
   변경
   삭제
  그룹 서명 관리
   생성
   변경
   삭제
  수발신 집계 조회
   검색
  내부메일 회수
   검색
   회수
  대용량 메일 관리
   검색
   기한 변경
  보안 메일 관리
   검색
   기한 변경
   비밀번호 변경
  외부 발송 관리 (판별 기준)
  메일 도착에 대한 통지 관리

 사용자 조직 관리:
  회사
  부서
  직위
  직무
  직책
  그룹 정의 조직

 사용자 조직:
  조직 부여
  조직 박탈


고객의 기능
 고객 등록 /client/register
 고객 해지* /client/unregister
 고객 용량 변경* /client/changeQuota
 고객 상태 변경* /client/changeState
 고객 조회* /client/get
 고객 로그인 /client/login
 고객 로그아웃 /client/logout

 고객 도메인:
  3. 등록 /client/addDomain
  삭제 /client/removeDomain
  목록 /client/listDomains

 사용자 그룹:
  2. 생성 /group/add
  삭제 /group/remove
  목록 /group/list
  사용자 그룹 용량 변경 /group/changeQuota
  사용자 그룹 상태 변경 /group/changeState

 그룹 도메인
  4. 사용자 그룹 도메인 변경: 주 도메인이란 아이디 생성에 기준으로 사용하는 도메인. 고객 도메인 풀에서 꺼내서 사용하는 개념. 주 도메인이든, 부 도메인이든 한 번 사용되면 다시 사용할 수 없다.
               주 도메인이 같을 경우는 사용된 부 도메인이나 풀의 도메인을 부 도메인으로 사용할 수 있다.
               주 도메인이 다를 경우는 풀의 도메인만 부 도메인으로 사용할 수 있다.
  주 도메인 추가 /group/addDomain
  주 도메인 제거 /group/removeDomain
  부 도메인 추가 /group/addSecDomain
  부 도메인 제거 /group/removeSecDomain

 사용자:
  5. 그룹 관리자 등록: 도메인 네임스페이스에서 아이디 생성, 그룹 지정(그룹 변경은 고려할 요소가 많아 아주 신중하게 해야함. 메일박스의 위치, 용량, 주소 등)
   아이디 중복 체크

시스템 관리자의 기능
 고객 정지* /system/suspendClient
 고객 복원* /system/resumeClient
 시스템 용량 지정 /system/updateQuota
 시스템 도메인 등록 /system/addDomain
 시스템 도메인 삭제 /system/removeDomain
 시스템 관리자 로그인 /system/login
 시스템 관리자 로그아웃 /system/logout
