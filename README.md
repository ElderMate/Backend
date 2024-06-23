# ElderMate

Eldermate는 **노인 맞춤형 챗봇 기반 데일리 리포팅 서비스**이다. 이 프로젝트는 노인 사용자가 일상에서 접하는 금융 문자 메시지를 자동으로 분류하고, 추출된 데이터를 챗봇을 통해 전달하여 **금융 사건을 쉽게 파악하고 도움을 받을 수 있는 애플리케이션**을 개발하는 것을 목표로 한다.
<img width="520" alt="image" src="https://github.com/ElderMate/Backend/assets/110288718/2de31ddd-1d6f-4729-a4ef-f13237978fdc">

<br><br>


# 필요성
<img width="506" alt="image" src="https://github.com/ElderMate/Backend/assets/110288718/e2ca8044-98a0-4f24-b3ec-74edb084ce84">

<br>

# 핵심 기능

1.  문자 수신 및 정보 처리
  - 시스템은 외부로부터 문자를 수신
  - 분류 모듈을 통해 금융 문자를 분류
  - 주요 정보 추출 모듈을 통해 데이터를 추출 및 DB저장

2. 데일리 리포트를 통한 대화 상호작용 
  - 저장된 금융 문자 정보를 바탕으로 데일리 리포트를 생성
  - 리포트는 STT(음성 인식)TTS(음성 합성) 모듈을 통해 노인에게 전달
  - 대화 중 이상 금융 거래가 발견될 경우, 해당 문자 정보를 특정하여 DB에 반영

<img width="521" alt="image" src="https://github.com/ElderMate/Backend/assets/110288718/9a28e1b2-fd76-4a1a-9a4a-7a6f7c39c92a">


<br>


# 기술 스택
-   **백엔드:**
    -   **언어:** Java
    -   **프레임워크:** Spring Boot, Spring Security, Spring Data JPA, Swagger
    -   **라이브러리:** RestTemplate
    -   **서버:** AWS EC2
-   **인공지능:**
    -   **언어:** Python
    -   **프레임워크:** FastAPI, Swagger
    -   **라이브러리:** KoBERT, GPT
    -   **서버:** AWS EC2
-   **데이터베이스(DB):** AWS RDS MySQL
-   **툴:** Git, Notion
<br>


# 시스템 아키텍처
![image](https://github.com/ElderMate/Backend/assets/110288718/74e1ef46-1883-4c04-9e3b-ddce3fae8b14)

<br>

# ERD 
<img width="517" alt="image" src="https://github.com/ElderMate/Backend/assets/110288718/a0f4eac5-62a5-4115-a47b-12b545c1f5b4">

<br>

# 구현 화면
![image](https://github.com/ElderMate/Backend/assets/110288718/269ae567-929e-4bf3-abc4-f739cf961312)

![image](https://github.com/ElderMate/Backend/assets/110288718/2390772b-286c-475f-bc1b-1cd4f4bb9354)

![image](https://github.com/ElderMate/Backend/assets/110288718/5ee19887-d8bb-4cab-92a4-3d32d4c1db3d)

<br>

# API 명세서 

<br>

# 역할
| 분야 | 이름 | 포지션 |
| --- | --- | --- |
| 서버 개발 |  |   |
| 서버 개발 |  |   |
| 서버 개발 |  |   |
| 서버 개발 | 홍길동 | 소비자 데이터 분석 |


