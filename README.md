# intern_auth

---

# 바로인턴12기 안중건 
- Java 백엔드 과제

---

## 프로젝트 개요
회원가입, 로그인, JWT 인증, 권한 변경 API 구현했습니다.

---

# SwaggerAPI 주소
* http://43.201.97.154:8080/swagger-ui/index.html

---
# AWS EC2 에서 실행 중인 엔드포인트 URL
* 회원가입
  * username, password, nickname을 입력해야 합니다
  * http://43.201.97.154:8080/signup
* 로그인
  * 회원 가입 후 로그인 가능 합니다
  * http://43.201.97.154:8080/login
* 관리자 권한 부여
  * 관리자로 로그인 후 생성되는 토큰 입력 후 사용해야 합니다
  * 관리자 ID : admin , 비밀번호 : 1234 로 미리 하나 설정해두었습니다
  * http://43.201.97.154:8080/admin/users/{userId}/roles


