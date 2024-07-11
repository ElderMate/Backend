# ElderMate

Eldermate는 **노인 맞춤형 챗봇 기반 데일리 리포팅 서비스**이다. 이 프로젝트는 노인 사용자가 일상에서 접하는 금융 문자 메시지를 자동으로 분류하고, 추출된 데이터를 챗봇을 통해 전달하여 **금융 사건을 쉽게 파악하고 도움을 받을 수 있는 애플리케이션**을 개발하는 것을 목표로 한다.

 <p align="center"><img width="520" alt="image" src="https://github.com/ElderMate/Backend/assets/110288718/2de31ddd-1d6f-4729-a4ef-f13237978fdc"> </p>

<br><br>

# 필요성
<p align="center"><img width="800" height="400" alt="image" src="https://github.com/ElderMate/Backend/assets/110288718/e2ca8044-98a0-4f24-b3ec-74edb084ce84"></p>

<br>

# 핵심 기능

1.  **문자 수신 및 정보 처리**
  - 시스템은 외부로부터 문자를 수신
  - 분류 모듈을 통해 금융 문자를 분류
  - 주요 정보 추출 모듈을 통해 데이터를 추출 및 DB저장

2. **데일리 리포트를 통한 대화 상호작용** 
  - 저장된 금융 문자 정보를 바탕으로 데일리 리포트를 생성
  - 리포트는 STT(음성 인식)TTS(음성 합성) 모듈을 통해 노인에게 전달
  - 대화 중 이상 금융 거래가 발견될 경우, 해당 문자 정보를 특정하여 DB에 반영

<p align="center"><img width="521" alt="image" src="https://github.com/ElderMate/Backend/assets/110288718/9a28e1b2-fd76-4a1a-9a4a-7a6f7c39c92a"></p>

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
<p align="center"><img width="875" alt="image" src="https://github.com/ElderMate/Backend/assets/110288718/e717783f-8e4d-4f4a-9be8-b927c68d1e61"></p>


<br>

# ERD 
- 설계 목적
  - **사용자**: 노인 사용자 정보를 저장하기 위한 테이블
  - **문자**: 노인 사용자의 문자를 저장하기 위한 테이블
  - **서브 테이블들**: 문자 카테고리별로 주요 정보를 따로 저장하기 위해 서브 타입 테이블로 구성
    
<p align="center"><img width="594" alt="image" src="https://github.com/ElderMate/Backend/assets/110288718/18471fe9-a38e-4900-87c4-06a11d152c89"></p>


<br>

# 구현 화면

| 수신 문자 처리 및 DB 저장 | 금융 상담 진행 과정 | 문제 발생 문자 확인 |
|---------------------------|----------------------|----------------------|
| <img width="517" alt="image" src="https://github.com/ElderMate/Backend/assets/110288718/269ae567-929e-4bf3-abc4-f739cf961312"> | <img width="517" alt="image" src="https://github.com/ElderMate/Backend/assets/110288718/2390772b-286c-475f-bc1b-1cd4f4bb9354"> | <img width="517" alt="image" src="https://github.com/ElderMate/Backend/assets/110288718/5ee19887-d8bb-4cab-92a4-3d32d4c1db3d"> |
| - 문자를 카테고리별 분류됨<br>- 필요한 데이터 추출 후<br>- DB에 가공된 데이터 저장 | - 최초 사용자에게 하루의 금융 리포트(요약)을 제공<br>- 사용자의 질문에 따른 자세한 정보 전달<br>- 대화를 통해 문제가 있는 금융 문자를 파악<br>- STT/TTS 기능을 통해 음성으로도 대화 가능 | - 위의 금융 상담에서 파악된 문자 확인<br>- 이유 및 문자의 정보를 확인 가능 |

<br>

# 참여인원
| 신호현 | 장우진 | 남인경 | 민준영 |
|---------------------------|----------------------|----------------------|----------------------|
| [<img src="https://avatars.githubusercontent.com/seayen" width="130px;" style="max-width: 100%;">](https://github.com/seayen) | [<img src="https://avatars.githubusercontent.com/JangWooJin1" width="130px;" style="max-width: 100%;">](https://github.com/JangWooJin1) | [<img src="https://avatars.githubusercontent.com/Ninky0" width="130px;" style="max-width: 100%;">](https://github.com/Ninky0) | [<img src="https://avatars.githubusercontent.com/Junyoung190198" width="130px;" style="max-width: 100%;">](https://github.com/Junyoung190198) |
| FE, AI | BE, AI | BE | FE |

<br>

