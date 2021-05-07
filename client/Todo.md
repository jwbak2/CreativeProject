## TODO

#### 0507
- [ ] 학교 상세정보 조회에서 학교이름 송신 로직 + 데이터 수신 로직
- [ ] Server로 받은 데이터 GUI 뿌려주기  
- [x] Protocol - getReceive 가변 패킷 받기
- [x] Protocol - getBody

---

### FIX
- protocol body(data) 길이 정보 저장 필요
    - 지금은 단순히 무조건 최대 바이트로 읽음
- 단순 String 하나 보낼떄도 객체 직렬화 필요?
    - send, receive 객체직렬화용 그냥 string용 분리?
- setPacket 작명



#### 0506
- [x] git 해보기
    - fetch 로 기존 branch 읽어온 다음 merge 시킴
    - 원격 레포에 있던 commit하고 로컬 commit 이랑 안맞는 멍청한 짓함
- [x] SampleDTO 역직렬화
    - Serialization Interface 구현