package Client.trasmission;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Protocol {

    public static final int PT_UNDEFINED = -1;    // 프로토콜이 지정되어 있지 않은 경우
    public static final int PT_EXIT = 0;        // 프로그램 종료

    //Type 요청, 응답, 성공, 실패
    public static final int PT_REQ = 0x01;
    public static final int PT_RES = 0x02;
    public static final int PT_SUCC = 0x03;
    public static final int PT_FAIL = 0x04;

    //Type 별 Code

    //TYPE 0x01 (REQUEST) CODE
    public static final int PT_REQ_UNIV_INF = 0x01;                 // 학교 상세정보 조회 요청
    public static final int PT_REQ_UNIV_LIST = 0x02;                // 학교 리스트 요청
    public static final int PT_REQ_UNIV_CP = 0x03;                  // 학교 상세정보 비교 요청(CP = compare)
    public static final int PT_REQ_USER_DETAIL = 0x04;              // 사용자 마이페이지 데이터 요청
    public static final int PT_REQ_USER_STATS = 0x05;               // 사용자 통계 데이터 요청
    public static final int PT_REQ_DEPT_LIST_FROM_KEYWORD = 0x06;   // 맞춤형 평점 키워드 전송(해당 키워드에 포함되는 학과 리스트 요청)
    public static final int PT_REQ_DEPT_LANK_LIST = 0x07;           // 맞춤형 평점 키워드 전송(해당 키워드에 포함되는 학과 리스트 요청)
    public static final int PT_REQ_UNIV_RATING_LIST = 0x08;         // 학교 평가 페이지에서 기존에 있던 학교 평가 내역 요청
    public static final int PT_REQ_UNIV_RATING = 0x09;              // 학교 평가 페이지에서 평가 내용 전송(등록 요청)
    public static final int PT_REQ_DEPT_RATING_LIST = 0x0A;         // 학과 평가 페이지에서 기존에 있던 학교 평가 내역 요청
    public static final int PT_REQ_DEPT_RATING = 0x0B;              // 학과 평가 페이지에서 평가 내용 전송(등록 요청)
    public static final int PT_REQ_LOGIN = 0x0c;                    // 학과 평가 페이지에서 평가 내용 전송(등록 요청)

    //TYPE 0x02 (RESPONSE) CODE
    public static final int PT_RES_UNIV_INF = 0x01;                 // 학교 상세정보 조회 응답
    public static final int PT_RES_UNIV_LIST = 0x02;                // 학교 상세정보 조회
    public static final int PT_RES_UNIV_CP = 0x03;                  // 학교 리스트 응답
    public static final int PT_RES_USER_DETAIL = 0x04;              // 사용자 마이페이지 데이터 응답
    public static final int PT_RES_USER_STATS = 0x05;               // 사용자 통계 데이터 응답
    public static final int PT_RES_DEPT_LIST_FROM_KEYWORD = 0x06;   // 맞춤형 평점 키워드 응답(해당 키워드를 포함한 학과 리스트 전송)
    public static final int PT_RES_DEPT_LANK_LIST = 0x07;           // 맞춤형 평점 지표 선택 후 학과 리스트 응답 : 0x07
    public static final int PT_RES_UNIV_RATING_LIST = 0x08;         // 학교 평가 페이지에서 기존에 있던 학교 평가 내역 응답
    public static final int PT_RES_DEPT_RATING_LIST = 0x09;         // 학과 평가 페이지에서 기존에 있던 학교 평가 내역 응답

    //TYPE 0x03 (SUCCESS) CODE
    public static final int PT_SUCC_UNIV_RATING = 0x01;             // 학과 상세정보 비교
    public static final int PT_SUCC_DEPT_RATING = 0x02;             // 학과 평가 페이지에서 평가 내용 등록 성공
    public static final int PT_SUCC_LOGIN = 0x03;                   // 로그인 성공

    //TYPE 0x04 (ERROR) CODE
    public static final int PT_FAIL_UNIV_INF = 0x01;
    public static final int PT_FAIL_UNIV_RATING = 0x02;             // 학교 평가 페이지에서 평가 내용 등록 실패
    public static final int PT_FAIL_DEPT_RATING = 0x03;             // 학과 평가 페이지에서 평가 내용 등록 실패
    public static final int PT_FAIL_LOGIN = 0x04;                   // 로그인 실패

    //Header 길이
    public static final int LEN_TYPE = 1;
    public static final int LEN_CODE = 1;
    public static final int LEN_BODY_LENGTH = 2;    // 65536
    public static final int LEN_HEADER = 4;

    public static final int LEN_MAX_DATA = 2000;

    private int protocolType;
    private int protocolCode;

    private byte[] packet;

    public Protocol() { // 인수가 정해지지 않은 경우의 생성자
        protocolType = PT_UNDEFINED;
        protocolCode = PT_UNDEFINED;
        packet = new byte[LEN_MAX_DATA];
    }

    // 패킷 바이트 배열 받는 생성자 필요
    public Protocol(byte[] buffer) {
        this.protocolType = buffer[0];
        this.protocolCode = buffer[1];

        packet = buffer;
    }

    public Protocol(int protocolType, int protocolCode) {
        this.protocolType = protocolType;
        this.protocolCode = protocolCode;

        // header packet 초기화
        packet = new byte[LEN_HEADER];
        packet[0] = (byte) protocolType;
        packet[LEN_TYPE] = (byte) protocolCode;
    }


    public int getProtocolType() {
        return packet[0];
    }

    public int getProtocolCode() {
        return packet[LEN_TYPE];
    }

    public int getBodyLength() {
        final int size = Integer.SIZE / 8;
        ByteBuffer buff = ByteBuffer.allocate(size);
        final byte[] newBytes = new byte[size];

        newBytes[0] = 0x00;
        newBytes[1] = 0x00;
        newBytes[2] = packet[LEN_TYPE + LEN_CODE];
        newBytes[3] = packet[LEN_TYPE + LEN_CODE + 1];

        buff = ByteBuffer.wrap(newBytes);
        buff.order(ByteOrder.BIG_ENDIAN);
        return buff.getInt();
    }

    public void setBodyLength(int length) {
        ByteBuffer buff = ByteBuffer.allocate(Integer.SIZE / 8);
        buff.putInt(length);
        buff.order(ByteOrder.BIG_ENDIAN);

        packet[LEN_TYPE + LEN_CODE] = buff.array()[2];
        packet[LEN_TYPE + LEN_CODE + 1] = buff.array()[3];
    }

    public byte[] getPacket() { // 1. 그냥 패킷 바이트배열 리턴 2. 타입, 코드 별 패킷 바이트 배열 초기화하고 리턴
        return packet;
    }

    public void setPacket(byte[] data) { // 패킷 바이트 배열 중 데이터 영역에 데이터 set
        byte[] buffer = new byte[LEN_HEADER + data.length]; // 새로운 바이트 배열 buffer 생성해서 초기화
        // 기존 packet 바이트 배열 + data 바이트 배열
        setBodyLength(data.length); // 데이터 길이를 dataLength 헤더에 저장

        System.arraycopy(packet, 0, buffer, 0, LEN_HEADER);
        System.arraycopy(data, 0, buffer, LEN_HEADER, data.length);

        packet = buffer;
    }

    public byte[] getBody() {   // get body of packet
        byte[] body = new byte[getBodyLength()];

        System.arraycopy(packet, LEN_HEADER, body, 0, getBodyLength());
        return body;
    }
}
