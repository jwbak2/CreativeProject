package Server.controller;

import Server.model.dao.UnivDAO;
import Server.model.dao.UnivDetailDAO;
import Server.model.dto.UnivDTO;
import Server.model.dto.UnivDetailDTO;
import Server.transmission.Protocol;

import java.util.ArrayList;

public class UnivDetail implements RequestHandler{

	private Object clientMsg;	// Client Message

	private int type;
	private int code;
	private ArrayList<Object> bodyList;

	public UnivDetail() {

	}
	public UnivDetail (Object message) {
		clientMsg = message;
		bodyList = new ArrayList<Object>();
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public Object getBody() {
		return bodyList.remove(0);
	}

	@Override
	public boolean hasMessage() {
		return bodyList.isEmpty() ? false : true;
	}

	// 대학 정보 요청 처리
	@Override
	public void handleRequest() {
		try {

			// 대학 이름(바이트 배열) 역 직렬화
			String strUnivName = (String) clientMsg;

			String univCode = getUnivCode(strUnivName);

			if (!univCode.equals("null"))
			{
				// DAO 선언
				UnivDAO univDAO = new UnivDAO();
				UnivDetailDAO univDetailDAO = new UnivDetailDAO();
//				DepartmentDAO deptDAO = new UnivDAO();

				// 전송할 패킷의 Type, Code 설정
				type = Protocol.PT_RES;
				code = Protocol.PT_RES_UNIV_INF;

				// 전송할 패킷의 Body 설정 (아래는 바디 3개 저장 = 프로토콜 3개 보냄)
				UnivDTO univDTO = univDAO.select(univCode);
//				univDTO.setUnivLogoImageFile(new byte[1]);
				bodyList.add(univDTO);
				bodyList.add(univDetailDAO.select(univCode));
//				bodyList.add(deptDAO.getDepartmentList(univCode));

			} else {
				System.out.println("---조회된 대학 없음---");
				type = Protocol.PT_FAIL;
				code = Protocol.PT_FAIL_UNIV_INF;

			}

//	} catch (ClassNotFoundException | IOException e) {
//			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public ArrayList<String> getUnivList() {
		// 대학 리스트
		String[][] univList = Server.model.Cache.getUnivList();
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < univList.length; i++)
		{
			list.add(univList[i][0]);
		}

		return list;
	}

	public UnivDTO getUniv(String univName) throws Exception {
		UnivDAO dao = new UnivDAO();

		return dao.select(getUnivCode(univName));
	}

	public UnivDetailDTO getUnivDetail(String univName) throws Exception {
		UnivDetailDAO dao = new UnivDetailDAO();

		return dao.select(getUnivCode(univName));
	}

	public String getUnivCode(String univName) {
		System.out.println("조회할 학교 이름: " + univName);

		// 대학 리스트
		String[][] univList = Server.model.Cache.getUnivList();

		// 대학 리스트 자료구조 및 탐색 - 개선 필요해보임
		String univCode = "null";
		for (int i = 0; i < univList.length; i++)
		{
			if (univList[i][1].equals(univName))
			{
				univCode = univList[i][0];
				break;
			}
		}

		return univCode;
	}

}
