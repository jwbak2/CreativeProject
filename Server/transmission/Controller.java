package Server.transmission;

import Server.controller.UnivDetail;

public class Controller {

	// 대학 리스트 조회
	public void inquiryUnivList() {
		UnivDetail univDetail = new UnivDetail();
		Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_LIST, univDetail.getUnivList());
	}

	// 대학 상세정보 조회
	public void inquiryUnivInfo(String univName) {
		try {
			UnivDetail univDetail = new UnivDetail();

			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, univDetail.getUniv(univName));
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, univDetail.getUnivDetail(univName));
//			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, 학과리스트조회(univName));

		} catch (Exception e) {
			System.out.println("Controller - 대학 상세정보 조회 오류");
			e.printStackTrace();

		}

	}

}
