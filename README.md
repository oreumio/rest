# rest

Apache James REST API

메일 서버의 서비스를 RESTful API로 제공하는 것을 목표로 합니다.

사용자의 기능:
 6. 로그인
 로그아웃
 패스워드 변경

 메일 보내기:
  7. 구조화 데이타로 보내기
  MIME으로 보내기
  템플릿으로 보내기

 메일박스:
  8. 생성
  9. 삭제
  10. 개명
  11. 검색

 메일:
  12. 검색: 메일박스, 사용자 플래그로 검색하기, 첨부파일 여부로 검색하기, 별표 여부로 검색하기, 순서 및 순서 기준 변경, 기간, 보낸사람, 받는사람, 제목, 내용, 첨부파일명 등으로 검색
  검색: 방으로 검색하기
  13. 조회: MIME 타입으로 내용을 선택하기, 전달로 발송, 답변으로 발송, 첨부로 발송, 원문, 첨부파일
   구조화 데이타로 조회하기
   MIME 으로 조회하기
  비표준 MIME 대응
  14. 조작: 읽은 상태 변경, 새 상태 변경, 메일박스 변경, 사용자 플래그 탈부착, 삭제 플래그 탈부착, 완전삭제
  조작: 캐릭터세트 변경
  MIME 추가

 발신 추적
  검색
  조회

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

 파일 관리: 업로드, 다운로드, 삭제, 보안메일 패스워드, 대용량 첨부파일화

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

시스템의 관리:
 고객 정지* /system/suspendClient
 고객 복원* /system/resumeClient
 시스템 용량 지정 /system/updateQuota
 시스템 도메인 등록 /system/addDomain
 시스템 도메인 삭제 /system/removeDomain
 시스템 관리자 로그인 /system/login
 시스템 관리자 로그아웃 /system/logout

고객의 관리:
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

그룹관리자의 관리:
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
