package Server.controller;

import Client.trasmission.Protocol;
import Server.model.Cache;

import java.io.IOException;
import java.util.ArrayList;

public class UnivList implements RequestHandler {

	private Object clientMsg;	// Client Message

	private int type;
	private int code;
	private ArrayList<String> body;

	public UnivList (Object message) {
		clientMsg = message;
		body = null;
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
		return body;
	}

	@Override
	public boolean hasMessage() {
		return false;
	}

	@Override
	public void handleRequest() {
		// 대학 리스트를 조회
		try {
			// 대학 리스트 직렬화
			String[][] univList = Cache.getUnivList();
			body = new ArrayList<String>();


			for (int i = 0; i < univList.length; i++)
			{
				body.add(univList[i][1]);
			}

			type = Protocol.PT_RES_UNIV_LIST;
			code = Protocol.PT_RES_UNIV_LIST;

			System.out.println("finish - univList handleRequest()");

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
