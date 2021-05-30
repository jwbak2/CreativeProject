package Server.transmission;

import Server.controller.UnivDetail;
import Client.vo.LoginReqVO;
import Server.model.dao.UserDAO;
import Server.model.dto.UserDTO;

public class Controller {

	private UserDTO curUser;

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

	public void reqLogin(LoginReqVO loginInfo){

		UserDAO userDAO = new UserDAO();
		UserDTO result = userDAO.login(loginInfo.getID(), loginInfo.getPW());

		if(result != null){

			curUser = result;
			Sender.send(Protocol.PT_SUCC, Protocol.PT_SUCC_LOGIN, null);

		} else {

			Sender.send(Protocol.PT_FAIL, Protocol.PT_FAIL_LOGIN, null);

		}


	}

}
