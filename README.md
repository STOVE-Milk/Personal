# Personal
  
# 아키텍처
  ![image](https://user-images.githubusercontent.com/20275668/146684658-c584c165-cd1d-4b28-80e0-1d8c0e7814a8.png)
  - 모두 Spring Boot 서버로 구현
  - View 서버
    - Thymeleaf
    - 요청에 대해 HTML 파일과 Ajax로 API를 요청할 수 있는 JS 파일 전송
      - 서버 내부에서 RestTemplate를 이용해 처리하려다가 React를 모방해서 비동기적 요청을 가능한 선에서 하고싶어서 이런 방식을 선택
      - 쿠키와 헤더 설정 시 서버에서 Null로 뜨는 문제를 겪는 중 (Query string으로 토큰을 날려 임시적으로 해결)
        - Wireshark로 요청 패킷을 봤을 때, 쿠키 값은 제대로 들어있음
      - 쿠키를 받을 시 바로 삭제되는 문제 발생(json으로 토큰 string을 받아서 클라이언트에서 쿠키를 생성하도록 해결)
        - 삭제되면서 전송이 되는 것이 아니고, 새로고침 후 삭제되는 것을 보면 특정 조건에 의해 삭제를 하거나 유지자체를 안할 것
        - 보안적으로 HttpOnly 설정을 했을 경우 클라이언트에서 어떻게 쿠키에 접근해서 헤더에 넣어주는지 모르겠음
        - 쿠키가 삭제되는 원인은 CORS로 추정
          - 기본적인 CORS 설정은 했기 때문에, 내가 모르는 쿠키의 특징이 문제인 것 같음
          - 쿠키의 domain이 다를 시 브라우저가 유지를 하지 않는다는 정보 획득 (설정을 해보았지만 실패)
          - domain이 여러개 설정이 불가능한데 모든 서버가 주고받을 수 있나?
          - path 설정도 해보았지만 실패
      - 로그인이 되어있을 시 라우팅, 토큰이 만료되었을 시 토큰 삭제, 에러 페이지 처리가 필요함
  - API Gateway
    - 요청에 대해 JWT 변조 체크, 권한 체크를 진행
      - Spring Filter를 이용해 구현
      - 예외 처리 관련해서 부족함을 느낌
        - AOP만을 사용해서 Exception Handler를 해보려 했지만 실패
    - 요청은 RestTemplate를 이용해 단순히 전달하는 역할 수행
      - Spring Cloud Gateway를 따라해보고 싶어서 진행
      - 동기적인 방식이라 문제가 있음
      - Netty나 Webflux를 공부하려 했지만 시간 부족
  - Auth 서버
    - JWT 방식, 토큰 생성 후 쿠키설정 및 전송
    - 비밀번호 암호화는 Bcrypt 단방향 해쉬 사용
      - Bcrypt의 경우 salt값이 해쉬된 비밀번호 앞 29자리에 들어가 있어서 salt값을 따로 저장할 필요가 없음
    - Exception Handler로 예외 처리
  - Management 서버
    - 유저 리스트 GET, 유저 삭제, 유저 권한 변경 기능 구현
    - 뒤에 두 기능은 아직 미연결
    - 유저 리스트는 특정 인원만큼 페이지를 나누려고 했음
      - 쿼리를 순수하게 작성하려다가 JPA의 Page를 이용해 쉽게 구현
      - 20명씩으로 해놨지만, 1000만명이라고 했을 때에는 다른 필터링이 필요할 것 같음
        - 검색기능, 권한별 필터링, 인증여부별 필터링
    - Exception Handler로 예외 처리


# DB Schema
![image](https://user-images.githubusercontent.com/20275668/146685302-f1babce9-378e-4075-8e7d-12fba64c0110.png)

# API

