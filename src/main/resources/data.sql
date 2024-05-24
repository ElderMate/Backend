INSERT INTO user(id, birthdate, gender, name, password, phoneNumber) VALUES ('1','1960-05-02','0','홍길동','$2a$10$V9LeNOSrAeJq1xUcfX.j3utpTLw3FWNof53yh3/S9ktClDNldKFBu','01012341234');

INSERT INTO Message(id, confirm, time, userId, category, msg, pNum) VALUES ('1', 0, '2024-04-12T12:23:30.327959','1','결제 승인','[Web발신]우리(1169)승인 홍*동님 4,900원 일시불 04/12 12:23 맥도날드판교테크노 누적342,500원','1588-9955');
INSERT INTO Message(id, confirm, time, userId, category, msg, pNum) VALUES ('2', 0, '2024-04-12T21:19:28.124235','1','결제 승인','[Web발신] [신한체크승인] 홍*동(4384) 04/12 19:28 (금액)1,500원 태평할인마트','1544-7200');

INSERT INTO Message(id, confirm, time, userId, category, msg, pNum) VALUES ('3', 0, '2024-04-12T21:24:14.478235','1','결제 취소','[Web발신] [KB국민카드] 3*5* 홍*동님 이마트천호점 이용건 04월12일 취소완료(-30,000원)','1588-1688');

INSERT INTO Message(id, confirm, time, userId, category, msg, pNum) VALUES ('4', 0, '2024-04-12T16:08:38.471905','1','납부 예정','[Web발신] [KB국민체크]홍길동님 교통대금 60,400원 04/13 체크결제계좌에서 출금예정(04/12기준) 2024년 4월 12일 (금) 오후 4시 08분','1588-1688');

INSERT INTO Message(id, confirm, time, userId, category, msg, pNum) VALUES ('5', 0, '2024-04-12T13:48:21.882589','1','계좌 개설','[Web발신] [키움] 홍길*고객님, 키움증권 계좌 개설이 완료되었습니다. 계좌번호 : 1234-5678 ','1544-9000');
INSERT INTO Message(id, confirm, time, userId, category, msg, pNum) VALUES ('6', 0, '2024-04-12T12:05:33.992734','1','계좌 개설','[Web발신] [카카오뱅크] 홍*동님 입출금통장(9634)이 정상적으로 개설되었습니다. ','1599-3333');

INSERT INTO Message(id, confirm, time, userId, category, msg, pNum) VALUES ('7', 0, '2024-04-12T15:01:42.257153','1','자동 이체','[Web발신] 홍*동 고객님의 우리은행 계좌에 2024년04월12일 SKpay(기관코드 : 00013457)의 자동이체(일회성출금이체포함)가 등록 되었습니다. 자동이체를 신청한 적이 없으시면 SK pay 고객센터( 18003388 )로 문의 하시기 바랍니다. ','1588-5000');
INSERT INTO Message(id, confirm, time, userId, category, msg, pNum) VALUES ('8', 0, '2024-04-12T17:29:15.221637','1','자동 이체','[Web발신] [우리은행] 홍*동 고객님께서 우아한형제들에서 신청하신 오픈뱅킹 출금이체(자동이체)건이 아래와 같이 등록되었습니다. * 출금은행 : 우리은행 * 계좌번호 : 100235******* * 예금주명 :  홍*동 * 신청기관 : 우아한형제들 ','1588-5000');

INSERT INTO Confirm(id, msgId, cost, location, method, time) VALUES ('1','1','4,900','맥도날드판교테크노','우리카드','2024-04-12T12:23:30.327959');
INSERT INTO Confirm(id, msgId, cost, location, method, time) VALUES ('2','2','1,500','태평할인마트','신한체크카드','2024-04-12T21:19:28.124235');

INSERT INTO Cancel(id, msgId, cost, location, method, time) VALUES ('1','3','30,000','이마트천호점','KB국민카드','2024-04-12T21:24:14.478235');

INSERT INTO Invoice(id, msgId, cost, payee, time) VALUES ('1', '4', '60,400', '교통대금', '04/13');

INSERT INTO Open(id, msgId, bank, type) VALUES ('1', '5','키움증권', '증권');
INSERT INTO Open(id, msgId, bank, type) VALUES ('2', '6','카카오뱅크', '입출금통장');

INSERT INTO AutoTransfer(id, msgId, bank, company) VALUES ('1', '7', '우리은행', 'SKpay');
INSERT INTO AutoTransfer(id, msgId, bank, company) VALUES ('2', '8', '우리은행', '우아한형제들');


