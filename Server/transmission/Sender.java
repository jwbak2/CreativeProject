package Server.transmission;

import Server.ServerMain;
import Server.model.dao.UnivDAO;
import Server.model.dao.UnivDetailDAO;
import Server.model.dto.DepartmentDTO;
import Server.model.dto.DepartmentRatingDTO;
import Server.model.dto.UnivRatingDTO;

import java.io.*;
import java.util.ArrayList;

public class Sender {
	/*
	 * 클라이언트의 요청에 따라 적절한 데이터를 반환함
	 */
	OutputStream os;

	// 직렬화 및 역직렬화 스트림
	ByteArrayInputStream bais;
	ObjectInputStream ois;
	ByteArrayOutputStream baos;
	ObjectOutputStream oos;

	public Sender(OutputStream os) {
		this.os = os;
	}

	public void inquiryUnivInfo(byte[] univName) {
		// 대학 정보를 요청
		// 일단 클라이언트가 보낸 대학 이름의 ID가 있는지 확인하고 (2차원 스트링 배열 받아오고)
		// 1. 아이디가 없다 -> fail 전송
		// 2. 아이디가 있다 -> DAO를 통해서 조회 후에 DTO를 패킷에 담아서 전송
		try {
			// 대학 이름(바이트 배열) 역 직렬화
			String strUnivName = (String) deserializeByteArray(univName);
			System.out.println("조회할 학교 이름: " + strUnivName);

			// 대학 DAO, 대학 상세정보 DAO, 대학 리스트
			UnivDAO univDAO = new UnivDAO();
			UnivDetailDAO univDetailDAO = new UnivDetailDAO();
			String[][] univList = ServerMain.serverCache.getUnivList();

			// 대학 리스트 자료구조 및 탐색 개선 필요해보임
			String univCode = "";
			for (int i = 0; i < univList.length; i++)
			{
				if (univList[i][1].equals(strUnivName))
				{
					univCode = univList[i][0];
					break;
				}
			}

			if (!univCode.equals(""))
			{
				// 대학 정보 직렬화
				byte[] data = serializeObject(new UnivDAO().select(univCode));

				// 대학 정보 전송
				send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, data);
				System.out.println("---패킷 송신 완료(대학)---");


				// 대학 상세정보 직렬화 (18, 19, 20년도 전부)
				data = serializeObject(new UnivDetailDAO().select(univCode));

				// 대학 상세정보 전송
				send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, data);
				System.out.println("---패킷 송신 완료(대학 상세정보)---");


				// 대학 학과 리스트 직렬화
//				data = serializeObject(new DepartmentDAO().getDepartmentList(univCode));

				// 대학 학과 리스트 전송
				send(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF, data);
				System.out.println("---패킷 송신 완료(학과 리스트)---");
			} else {
				System.out.println("---조회된 대학 없음---");
				send(Protocol.PT_FAIL, Protocol.PT_FAIL_UNIV_INF);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public inquiryUnivDetail(String univCode) {
//
//	}

	public void inquiryUnivList() {
		// 대학 리스트를 조회
		try {
			// 대학 리스트 직렬화
			ArrayList<String> univArr = new ArrayList<String>();
			String[][] univList = ServerMain.serverCache.getUnivList();

			for (int i = 0; i < univList.length; i++)
			{
				univArr.add(univList[i][1]);
			}
			byte[] data = serializeObject(univArr);

			// 대학 리스트 전송
			Protocol protocol = new Protocol(Protocol.PT_RES, Protocol.PT_RES_UNIV_LIST);
			protocol.setPacket(data);
			os.write(protocol.getPacket());
			os.flush();
//			send(Protocol.PT_RES, Protocol.PT_RES_UNIV_LIST, data);
			System.out.println("---패킷 송신 완료(대학 리스트)---");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resUnivComp(byte[] univNameList) {
		try {
			ArrayList<String> univList = ( ArrayList<String> ) deserializeByteArray(univNameList);

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/*
	public void registerUnivRating(byte[] univRating) {
		// 대학 평점 등록 요청
		try {
			// 대학 평가를 DB에 저장
//			UnivRatingDAO univRatingDAO = new UnivRatingDAO();
			if (UnivRatingDAO.insert((UnivRatingDTO) deserializeByteArray(univRating))) {
				// 대학 평가 저장 성공
				System.out.println("---대학 평가 저장 성공---");
				os.write(new Protocol(Protocol.PT_SUCC, Protocol.PT_SUCC_UNIV_RATING).getPacket());
				os.flush();
			} else {
				// 대학 평가 저장 실패
				System.out.println("---대학 평가 저장 실패---");
				os.write(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_UNIV_RATING).getPacket());
				os.flush();
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void registerDepartmentRating(byte[] departmentRating) {
		// 학과 평점 등록 요청
		try {
			// 학과 평가를 DB에 저장
//			DepartmentRatingDAO departmentRatingDAO = new DepartmentRatingDAO();
			if (DepartmentRatingDAO.insert((DepartmentRatingDTO) deserializeByteArray(departmentRating))) {
				// 학과 평가 저장 성공
				System.out.println("---학과 평가 저장 성공---");
				os.write(new Protocol(Protocol.PT_SUCC, Protocol.PT_SUCC_DEPT_RATING).getPacket());
				os.flush();
			} else {
				// 학과 평가 저장 실패
				System.out.println("---학과 평가 저장 실패---");
				os.write(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_DEPT_RATING).getPacket());
				os.flush();
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	public void send(int type, int code) throws IOException {
		os.write(new Protocol(type, code).getPacket());
		os.flush();
	}
	public void send(int type, int code, byte[] body) throws IOException {
		Protocol protocol = new Protocol(type, code);
		protocol.setPacket(body);
		os.write(protocol.getPacket());
		os.flush();
	}

	public byte[] serializeObject(Object obj) throws IOException {
		baos = new ByteArrayOutputStream();
		oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);	// obj 객체 직렬화

		return baos.toByteArray();
	}

	public Object deserializeByteArray(byte[] obj) throws IOException, ClassNotFoundException {
		bais = new ByteArrayInputStream(obj);
		ois = new ObjectInputStream(bais);

		return ois.readObject();
	}
}
