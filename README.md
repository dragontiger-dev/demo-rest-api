# Spring Boot REST API demo

인프런 백기선님의 강의를 보며 따라했던 프로젝트 입니다. 

- 기간 : 2021-03-26 ~ 04-26
- 버전 : Spring Boot 2.4.5

## 개요

- 간단한 이벤트 CRUD REST API 제공
- 주요 기술
  - Spring Data JPA
  - Spring HATEOAS
  - Spring Rest Docs
  - Spring Security OAuth2
- Spring Security를 이용하여 사용자별 권한 설정
- JUnit5 테스트 코드 작성
- 강의 직후 프로젝트 완성도 70% (Spring Rest Docs 미완성, 사용자 인증 기능 추가하면서 기존 테스트코드 업데이트 안함, )
  - 완성도 높이는 것은 반복적인 일이 더 많다고 생각되어 복습 겸 새로 다시 만들어볼 계획

## 수강 후기

1. REST API 에 대한 이해

   물론 여러 조건들이 있지만 빈번하게 어겨지고 있는 규칙 2가지에 대해서만 

   - self-descrive message
     - REST API 사용자들은 응답 메시지를 보고 이해를 할 수 있도록 해야 한다.
     - 이 프로젝트에서는 API에 대한 설명을 Spring Rest Docs에 추가하여 해당 Docs에 링크를 추가하여 메시지를 설명하는 방법을 사용했다.
   - HATEOAS(Hypermedia as the engine of application state)
     - 링크를 통해서 애플리케이션 상태 변화가 가능해야 한다. 
     - 이벤트를 CRUD 하기 위해서 필요한건 HTTP 요청 방법과 링크만 있으면 되도록 API를 개발했다. (물론 CRUD에 필요한 권한 까지)
     
2. 실전 TDD

   - TDD 이론을 배우고 이제 프로젝트에 써먹으려고 시도했으나 조금 막막했다. 하지만 이 강의를 통해서 TDD를 진행하기 위한 흐름을 제대로 익혔다.
   - TDD를 안하고 과거에 불편하게 개발했던 경험들이 떠올랐다. 회원가입 테스트를 하기 위해서 직접 회원가입 양식들을 채워가며 테스트 했었는데 너무 왜 이제야 배웠을까 싶다
   
3. 종합적인 느낌

   - 고수의 의식의 흐름을 따라가며 배우는 것이 재미있었다. 보면 계속 느끼지만 역시 API 문서에 대해서 깊게 보아야 무언가 막혔을 때 쉽게 해쳐나갈 수 있는 것 같다.
