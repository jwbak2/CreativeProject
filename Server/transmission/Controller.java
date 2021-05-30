package Server.transmission;

import Server.controller.DepartmentDetail;
import Server.controller.UnivDetail;
import Server.model.dao.DepartmentDAO;
import Server.model.dto.DepartmentRatingDTO;
import Server.model.dto.UnivRatingDTO;

import java.util.ArrayList;

public class Controller {

	// 대학 리스트 조회 요청 처리
	public void inquiryUnivList() {
		UnivDetail univDetail = new UnivDetail();
		Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_LIST, univDetail.getUnivList());
	}

	// 대학 상세정보 조회 요청 처리
	public void inquiryUnivInfo(String univName) {
		try {
			UnivDetail univDetail = new UnivDetail();
			DepartmentDetail deptDetail = new DepartmentDetail();

			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, univDetail.getUniv(univName));
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, univDetail.getUnivDetail(univName));
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, deptDetail.getDepartmentList(univDetail.getUnivCode(univName)));

		} catch (Exception e) {
			System.out.println("Controller - 대학 상세정보 조회 오류");
			e.printStackTrace();

		}

	}

	// 학과 상세정보 조회 요청 처리
	public void inquiryDepartmentInfo(String deptName) {
			DepartmentDetail deptDetail = new DepartmentDetail();

			Sender.send(Protocol.PT_RES, Protocol.PT_RES_DEPT_INF, deptDetail.getDepartmentDetail(deptName));
	}

	// 대학 비교 요청 처리
	public void compareTwoUniv(ArrayList<String> list) {
		try {
			UnivDetail univDetail = new UnivDetail();
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, univDetail.getUnivDetail(list.get(0)));
			Sender.send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, univDetail.getUnivDetail(list.get(1)));

		} catch (Exception e) {
			System.out.println("Controller - 대학 비교 오류");
			e.printStackTrace();

		}
	}

	// 대학 평가 등록 요청 처리
	public void registerUnivRating(UnivRatingDTO rating) {
		// TODO: 로직 필요
	}

	// 학과 평가 등록 요청 처리
	public void registerDepartmentRating(DepartmentRatingDTO rating) {
		// TODO: 로직 필요
	}
}
