package Server.transmission;

import Server.model.dao.UnivDAO;
import Server.model.dao.UnivDetailDAO;
import Server.model.dto.UnivDTO;
import Server.model.dto.UnivDetailDTO;

import java.io.*;

public class Controller {
	InputStream is;
	OutputStream os;

	public Controller(InputStream is, OutputStream os) {
		this.is = is;
		this.os = os;
	}

	public void getUnivData(String univName, Protocol protocol) {
		// 대학 정보를 요청
		// 난 뭘 해야 할까
		// 일단 클라이언트가 보낸 대학 이름의 ID가 있는지 확인하고 (2차원 스트링 배열 받아오고)
		// 1. 아이디가 없다 -> fail 전송
		// 2. 아이디가 있다 -> DAO를 통해서 조회 후에 DTO를 패킷에 담아서 전송
		try {
			Protocol justTmp = new Protocol();
			justTmp.setBodyLength(990);
			System.out.println(justTmp.getBodyLength());

			justTmp.setBodyLength(1025);
			System.out.println(justTmp.getBodyLength());

			System.out.println("getUnivData() start");
//			is.read();
			UnivDAO univDAO = new UnivDAO();
			UnivDetailDAO univDetailDAO = new UnivDetailDAO();
			String[][] univList = univDAO.getUnivList();
			System.out.println("univList Length: " + univList.length);
			System.out.println("univList[0] Length: " + univList[0].length);
			String univId = "";
			for (int i = 0; i < univList.length; i++)
			{
				if (univList[i][1].equals(univName))
				{
					univId = univList[i][0];
					break;
				}
			}

			System.out.println("univId equal if statement");
			if (!univId.equals(""))
			{
				System.out.println("대학 정보 DAO로 조회");
				UnivDTO univ = univDAO.select(univId);
				univ.setUnivLogoImageFile(new byte[1]);
				UnivDetailDTO univDetail = univDetailDAO.select(univId);

				System.out.println("대학 정보 전송 시작");
				byte[] serializedDTO;	// 직렬화 결과가 담기는 바이트
				try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
					try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
						oos.writeObject(univ);	// tmp 객체 직렬화

						serializedDTO = baos.toByteArray();
					}
				}
				System.out.println("직렬화 완료");

				// 직렬화 완료된 데이터 사용자에게 전달
				Protocol univProtocol = new Protocol(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF);

				// 패킷 바디에 직렬화 객체 삽입
				univProtocol.setPacket(serializedDTO);
				System.out.println(serializedDTO.length);
				System.out.println(univProtocol.getBodyLength());
				// 전송
				os.write(univProtocol.getPacket());
				os.flush();
				System.out.println("대학 정보 전송 완료");

				System.out.println("대학 상세정보 전송 시작");
				byte[] serializedDTO2;	// 직렬화 결과가 담기는 바이트
				try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
					try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
						oos.writeObject(univDetail);	// tmp 객체 직렬화

						serializedDTO2 = baos.toByteArray();
					}
				}
				System.out.println("직렬화 완료");
				System.out.println("역직렬화 - try1");
				try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedDTO2)) {
					try (ObjectInputStream ois = new ObjectInputStream(bais)) {
						Object objectMember = ois.readObject();
						UnivDetailDTO empty1 = (UnivDetailDTO) objectMember;

						System.out.println(empty1.getAdmissionFee());
						System.out.println(empty1.getArtMusPhysTuition());
						System.out.println(empty1.getEnteringRate());
					}
					catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}

				// 직렬화 완료된 데이터 사용자에게 전달
				Protocol univDetailProtocol = new Protocol(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF);

				// 패킷 바디에 직렬화 객체 삽입
				univDetailProtocol.setPacket(serializedDTO2);
				System.out.println(serializedDTO2.length);
				System.out.println(univDetailProtocol.getBodyLength());
				System.out.println(univDetailProtocol.getBody().length);
				// 전송
				System.out.println("역직렬화 - try2");
				try (ByteArrayInputStream bais = new ByteArrayInputStream(univDetailProtocol.getBody())) {
					try (ObjectInputStream ois = new ObjectInputStream(bais)) {
						Object objectMember = ois.readObject();
						UnivDetailDTO empty1 = (UnivDetailDTO) objectMember;

						System.out.println(empty1.getAdmissionFee());
						System.out.println(empty1.getArtMusPhysTuition());
						System.out.println(empty1.getEnteringRate());
					}
					catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				os.write(univDetailProtocol.getPacket());
				os.flush();
				System.out.println("대학 상세정보 전송 완료");

			} else {
				System.out.println("대학 없음");
				os.write(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_UNIV_INF).getPacket());
				os.flush();
			}



		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
