# Switchwon
스위치온 과제

## spec.
- Spring boot 3.2.3
- jdk 17
- JPA
- H2 DB ( http://localhost:8080/h2-console )
- Swagger ( http://localhost:8080/swagger-ui/index.html#/payment-controller )

## 설계에 앞서서 문제 상황에 대한 개인 의견으로 처리 상황 설명
1. 해당 스위치온페이먼츠라는 개념이 네이버페이, 토스페이와 같은 일정 금액을 충전을 하는 방식이라는 출제 의도로 이해하였습니다.
2. 1번에 대한 상황으로 충전된 금액을 넘을 경우 요청된 결제수단에서 해당 금액을 충전해서 제외하는 방식으로 진행하겠습니다.
3. 결제 예상 결과에 수수료 부분은 policy_fees 테이블로 따로 정책을 관리하게 하였습니다.
4. 결제 예상 결과를 본 후 결제 승인 요청이 오기 때문에 결제 승인 요청에 amount값은 수수료를 포함한 최종 금액으로 보겠습니다.

## 구조 설명
```
domain : controller, service 같은 비지니스 로직
 ㄴ {도메인}
    ㄴ controller : 컨트롤러
        ㄴ request : 컨트롤러에 들어오는 Request Valid 같은 값들 정의
        ㄴ response : 컨트롤러에서 클라이언트 단에 나갈떄 보여지는 특수한 모양이 있으면 Response        
    ㄴ service : 서비스
global : 전체적인 설정, 유틸 등 설정
 ㄴ config : 설정 파일
 ㄴ support : 서비스를 사용중에 도움이되는 컴포넌트
model : Entity, Repository 와 같은 DB 관련
 ㄴ {도메인} : Entity
    ㄴ enums : ENUM 묶음
    ㄴ mapper : Entity <-> DTO , Entity <-> Request, Response 같은 변경하는 로직
    ㄴ repository : Entity Repository
        ㄴ querydsl : QueryDsl를 사용시 모아두는 Repository
```
1. fetchJoin이 필요한 경우 Querydsl을 사용하였습니다. ( JPQL로도 가능하지만 복잡한 쿼리를 써야 될 경우 QueryDsl로 할 수 밖에 없으므로 QueryDsl 사용 )
2. Entity 를 DTO 와 같은 객체로 변경시 mapstruct 라이브러리를 사용
3. DB에 대소문자 구분을 위해 LowerUnderScoreNamingStrategy 정의 되어있습니다.
4. 현재 문제에서는 DTO 와 같이 Entity 모양과 유사하게 내려주는 케이스가 없어서 DTO 대신 Response로 선언해서 화면에 핏하게 내려주게하였습니다. 기본 원칙은 DTO 로 내려주는게 맞다고 봅니다.

## API 설명
1. 잔액 조회
- users Table(사용자 테이블)에서 연결된 payment_accounts Table(계좌 테이블) 을 fetchJoin을 해서 내려주는 방식으로 구현
2. 결제 예상 결과 조회
- 예상 조회를 하는건데 POST로 선언되어 있어서 어딘가에 데이터가 남아야 될걸로 판단해서 payment_estimate_logs Table ( 결제 예상 로그 정보) 에 쌓아두었습니다.
- 수수료 규칙은 policy_fees Table (수수료 정책 테이블) 에서 가져오며 기간을 주어서 현재 날짜에 제일 가까운 시작시간을 가지는 정책을 가져옵니다.
  - 수수료 규칙을 가져오는데 추가 가능 사항은 Redis 같은 메모리 DB를 사용해서 DB 조회를 줄이는 방식도 있습니다.
3. 결제 승인 요청
- 결제 승인 요청시 payment_accounts Table(계좌 테이블) 에 금액이 남아있다면 남은 금액 소모 후 필요 금액 충전해서 결제하는 로직으로 진행
- payments Table (결제 테이블) 은 결제한건당 묶음 단위
- payment_logs Table (결제 로그 테이블) payments에 결제를 이루어 질 때 충전, 결제하는 관계를 기록하는 테이블
- 은행과 연결이 따로 없어서 try catch로 실패, 성공 정도만 처리하였지만, 실제 프로그램으로 만들어질 경우 Queue를 달아서 몇번에 재시도 등을 이루어서 결제가 완료되어야 한다고 생각합니다.
