package Server.transmission;

import java.io.Serializable;

public class Protocol implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int PT_UNDEFINED = -1;    // 프로토콜이 지정되어 있지 않은 경우
	public static final int PT_EXIT = 0;        // 프로그램 종료
	//test
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
	public static final int PT_REQ_LOGIN = 0x0C;                    // 학과 평가 페이지에서 평가 내용 전송(등록 요청)
	public static final int PT_REQ_DEPT_DETAIL = 0x0D;				// 학과 상세정보 요청

	//TYPE 0x02 (RESPONSE) CODE
	public static final int PT_RES_UNIV_INF = 0x01;                 // 학교 상세정보 조회 응답
	public static final int PT_RES_UNIV_LIST = 0x02;                // 학교 리스트 응답
	public static final int PT_RES_UNIV_CP = 0x03;                  // 학교 상세정보 비교 응답(CP = compare)
	public static final int PT_RES_USER_DETAIL = 0x04;              // 사용자 마이페이지 데이터 응답
	public static final int PT_RES_USER_STATS = 0x05;               // 사용자 통계 데이터 응답
	public static final int PT_RES_DEPT_LIST_FROM_KEYWORD = 0x06;   // 맞춤형 평점 키워드 응답(해당 키워드를 포함한 학과 리스트 전송)
	public static final int PT_RES_DEPT_LANK_LIST = 0x07;           // 맞춤형 평점 지표 선택 후 학과 리스트 응답 : 0x07
	public static final int PT_RES_UNIV_RATING_LIST = 0x08;         // 학교 평가 페이지에서 기존에 있던 학교 평가 내역 응답
	public static final int PT_RES_DEPT_RATING_LIST = 0x09;         // 학과 평가 페이지에서 기존에 있던 학교 평가 내역 응답
	public static final int PT_RES_DEPT_DETAIL = 0x0A;				// 학과 상세정보 응답

	//TYPE 0x03 (SUCCESS) CODE
	public static final int PT_SUCC_UNIV_RATING = 0x01;             // 학교 평가 페이지에서 평가 내용 등록 성공
	public static final int PT_SUCC_DEPT_RATING = 0x02;             // 학과 평가 페이지에서 평가 내용 등록 성공
	public static final int PT_SUCC_LOGIN = 0x03;                   // 로그인 성공

	//TYPE 0x04 (ERROR) CODE
	public static final int PT_FAIL_UNIV_INF = 0x01;				// 학교 상세정보 조회 실패
	public static final int PT_FAIL_DEPT_INF = 0x02;				// 학과 상세정보 조회 실패
	public static final int PT_FAIL_UNIV_LIST = 0x03;				// 학교 리스트 요청 실패
	public static final int PT_FAIL_UNIV_CP = 0x04;					// 학교 상세정보 비교 요청(CP = compare) 실패
	public static final int PT_FAIL_UNIV_RATING = 0x05;             // 학교 평가 페이지에서 평가 내용 등록 실패
	public static final int PT_FAIL_DEPT_RATING = 0x06;             // 학과 평가 페이지에서 평가 내용 등록 실패
	public static final int PT_FAIL_LOGIN = 0x07;                   // 로그인 실패


	private int protocolType;
	private int protocolCode;

	private Object body;	// DTO, String, ArrayList, etc...


	public Protocol() { // 인수가 정해지지 않은 경우의 생성자
		protocolType = PT_UNDEFINED;
		protocolCode = PT_UNDEFINED;

		body = null;
	}

	public Protocol(int protocolType, int protocolCode) {
		this.protocolType = protocolType;
		this.protocolCode = protocolCode;

		body = null;
	}

	public Protocol(int protocolType, int protocolCode, Object body) {
		this.protocolType = protocolType;
		this.protocolCode = protocolCode;

		this.body = body;
	}


	public int getProtocolType() {
		return protocolType;
	}

	public int getProtocolCode() {
		return protocolCode;
	}

	public Object getBody() { return body; }

	public void setBody(Object body) {
		this.body = body;
	}

}

