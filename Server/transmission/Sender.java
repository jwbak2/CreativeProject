package Server.transmission;

import Server.ServerMain;
import Server.model.dao.UnivDAO;
import Server.model.dao.UnivDetailDAO;

import java.io.*;

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
				// 대학 로고 ON / OFF
				//univ.setUnivLogoImageFile(new byte[1]);

				// 대학 전송
				byte[] data = serializeObject(univDAO.select(univCode));
				Protocol protocol = new Protocol(Protocol.PT_RES, Protocol.PT_RES_UNIV_INF);

				// 패킷 바디에 직렬화 객체 삽입
				protocol.setPacket(data);

				// 전송
				os.write(protocol.getPacket());
				os.flush();
				System.out.println("---패킷 송신 완료(대학)---");

				// 학교 상세정보
				data = serializeObject(univDetailDAO.select(univCode));

				// 패킷 바디에 직렬화 객체 삽입
				protocol.setPacket(data);

				// 전송
				os.write(protocol.getPacket());
				os.flush();

				System.out.println("---패킷 송신 완료(대학 상세정보)---");
			} else {
				System.out.println("---조회된 대학 없음---");
				os.write(new Protocol(Protocol.PT_FAIL, Protocol.PT_FAIL_UNIV_INF).getPacket());
				os.flush();
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
