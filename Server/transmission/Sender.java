package Server.transmission;

import Server.ServerMain;
import Server.model.dao.UnivDAO;
import Server.model.dao.UnivDetailDAO;
import Server.model.dto.DepartmentDTO;
import Server.model.dto.DepartmentRatingDTO;
import Server.model.dto.UnivRatingDTO;

import java.io.*;

public class Sender {
	/*
	 * 클라이언트의 요청에 따라 적절한 데이터를 반환함
	 */
	private static ObjectOutputStream oos;


	public Sender(ObjectOutputStream oos) {
		this.oos = oos;
	}

	public static void send(int type, int code) {
		try {
			oos.writeObject(new Protocol(type, code));

			//서버로 전송
			oos.flush();

		} catch (IOException e) {
			System.out.println("Sender.send(type, code) 입출력오류");
			e.printStackTrace();

		}
	}

	public static void send(int type, int code, Object body) {
		try {
			oos.writeObject(new Protocol(type, code, body));

			//서버로 전송
			oos.flush();

			System.out.println("패킷 송신 완료");

		} catch (IOException e) {
			System.out.println("Sender.send(type, code, body) 입출력오류");
			e.printStackTrace();

		}
	}

	public static void send(Protocol pt) {
		try {
			oos.writeObject(pt);

			//서버로 전송
			oos.flush();

			System.out.println("패킷 송신 완료");

		} catch (IOException e) {
			System.out.println("Sender.send(Protocol) 입출력오류");
			e.printStackTrace();

		}
	}

}
