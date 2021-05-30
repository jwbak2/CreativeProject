package Server.controller;

import Server.model.dao.DepartmentDAO;
import Server.model.dao.UnivDAO;
import Server.model.dao.UnivDetailDAO;
import Server.model.dto.UnivDTO;
import Server.model.dto.UnivDetailDTO;
import Server.transmission.Protocol;

import java.util.ArrayList;

public class CompareUniv implements RequestHandler {
	private Object clientMsg;	// Client Message

	private int type;
	private int code;
	private ArrayList<Object> bodyList;

	public CompareUniv (Object message) {
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

	@Override
	public void handleRequest() {
		try {

			// 두 개의 대학 이름이 담김 ArrayList 역 직렬화
			ArrayList<String> univNameList = (ArrayList<String>) clientMsg;

			// 대학 리스트
			String[][] univList = Server.model.Cache.getUnivList();

			// 대학 리스트 자료구조 및 탐색 - 개선 필요해보임
			String firstUnivCode = "";
			String secondUnivCode = "";
			for (int i = 0; i < univList.length; i++) {
				if (univList[i][1].equals(univNameList.get(0))) {
					firstUnivCode = univList[i][0];
				}
				if (univList[i][1].equals(univNameList.get(1))) {
					secondUnivCode = univList[i][0];
				}
			}

			if (!firstUnivCode.equals("")) {
				// 전송할 패킷의 Type, Code 설정
				type = Protocol.PT_RES;
				code = Protocol.PT_RES_UNIV_CP;

				bodyList.add(inquiryUniv(firstUnivCode));
				bodyList.add(inquiryUnivDetail(firstUnivCode));
//				bodyList.add(inquiryDepartmentList(firstUnivCode));

				// 전송할 패킷의 Body 설정 (아래는 바디 3개 저장 = 프로토콜 3개 보냄)
//				UnivDTO univDTO = univDAO.select(firstUnivCode);
//				bodyList.add(univDTO);
//				bodyList.add(univDetailDAO.select(firstUnivCode));
//				bodyList.add(deptDAO.getDepartmentList(univCode));

			} else {
				System.out.println("---조회된 대학 없음---");
				type = Protocol.PT_FAIL;
				code = Protocol.PT_FAIL_UNIV_INF;

			}

			if (!secondUnivCode.equals("")) {
				// DAO 선언
				UnivDAO univDAO = new UnivDAO();
				UnivDetailDAO univDetailDAO = new UnivDetailDAO();
//				DepartmentDAO deptDAO = new UnivDAO();

				// 전송할 패킷의 Type, Code 설정
				type = Protocol.PT_RES;
				code = Protocol.PT_RES_UNIV_CP;

				// 전송할 패킷의 Body 설정 (아래는 바디 3개 저장 = 프로토콜 3개 보냄)
				UnivDTO univDTO = univDAO.select(firstUnivCode);
				univDTO.setUnivLogoImageFile(new byte[1]);
				bodyList.add(univDTO);
				bodyList.add(univDetailDAO.select(firstUnivCode));
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

	public UnivDTO inquiryUniv(String univCode) throws Exception {
		UnivDAO dao = new UnivDAO();

		return dao.select(univCode);
	}

	public UnivDetailDTO inquiryUnivDetail(String univCode) throws Exception {
		UnivDetailDAO dao = new UnivDetailDAO();

		return dao.select(univCode);
	}

//	public UnivDTO inquiryDepartmentList(String univCode) throws Exception {
//		DepartmentDAO dao = new DepartmentDAO();
//
//		return dao.getDepartmentList(univCode);
//	}

}
