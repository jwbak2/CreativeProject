package src.server.Transmission;

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
	public static final int PT_REQ_UNIV_INF = 0x01; // 학교 상세정보 조회 요청

	//TYPE 0x02 (RESPONSE) CODE
	public static final int PT_RES_UNIV_INF = 0x01; // 학교 상세정보 조회 응답

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
	public Protocol(byte[] buffer){
		this.protocolType = buffer[0];
		this.protocolCode = buffer[1];

		packet = buffer;
	}

	public Protocol(int protocolType, int protocolCode) {
		this.protocolType = protocolType;
		this.protocolCode = protocolCode;

		packet = new byte[LEN_HEADER];  // header packet 초기화
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
		return (packet[LEN_TYPE + LEN_CODE] << 8) + (packet[LEN_TYPE + LEN_CODE + 1]);
	}

	public void setBodyLength(int length) {
		packet[LEN_TYPE + LEN_CODE] = (byte) ((length & 0x0000FF00) >> 8);	// Body Length
		packet[LEN_TYPE + LEN_CODE + 1] = (byte) (length & 0x000000FF);		// Body Length
	}

    /*
        1. 타입 코드 별 패킷 초기화   -> getPacket
        2. 패킷 바이트 배열 중 데이터 영역에 데이터 -> setPacket
    */

	public byte[] getPacket() { // 1. 그냥 패킷 바이트배열 리턴 2. 타입, 코드 별 패킷 바이트 배열 초기화하고 리턴
		return packet;
	}

	public void setPacket(byte[] data) { // 패킷 바이트 배열 중 데이터 영역에 데이터 set
		byte[] buffer = new byte[LEN_HEADER + data.length]; // 새로운 바이트 배열 buffer 생성해서 초기화
		// 기존 packet 바이트 배열 + data 바이트 배열

		System.arraycopy(packet, 0, buffer, 0, LEN_HEADER);	// header 복사
		System.arraycopy(data, 0, buffer, LEN_HEADER, data.length);	// data 복사

		packet = buffer;

		setBodyLength(data.length);
	}

	public byte[] getBody() {
		byte[] body = new byte[getBodyLength()];

		System.arraycopy(packet, LEN_HEADER, body, 0, getBodyLength());
		return body;
	}
}
