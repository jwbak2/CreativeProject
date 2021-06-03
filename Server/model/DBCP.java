package Server.model;

import java.sql.*;
import java.util.Stack;



public class DBCP {   // 데이터베이스 커넥션 풀

    static private int curPoolSize = 0;             //활성화된 연결 수를 나타내는 변수

    final static private int INIT_POOL_SIZE = 5;    // 초기 연결 수
    final static private int MAX_POOL_SIZE = 50;    // 최대 연결 수


    // 접속 정보 설정
    final static private String SERVER_IP = "18.215.187.203";
    final static private String USER = "crtvp";
    final static private String PWD = "ganggoo";
    final static private String URL = "jdbc:oracle:thin:@" + SERVER_IP + ":1521:xe";



    static private final Stack<Connection> POOL = new Stack<>();    // connection은 stack 자료형에 에 저장

    static public void init() {     // Pool 초기화

        for (int i = 0; i < INIT_POOL_SIZE; i++) {  //초기 연결 수 만큼 연결을 생성하여 stack에 저장
            POOL.push(createConnection());

        }

    }

    public static Connection createConnection() {   //DB와의 연결 객체를 생성하는 함수

        Connection conn = null;

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");   // DB 드라이버 로드
            conn = DriverManager.getConnection(URL, USER, PWD);  // 연결 생성
            curPoolSize++;

        } catch (ClassNotFoundException cnfe) {
            System.out.println("DB 드라이버 로딩 실패 :" + cnfe.toString());

        } catch (SQLException sqle) {
            System.out.println("DB 접속실패 : " + sqle.toString());

        } catch (Exception e) {
            System.out.println("Unkonwn error");
            e.printStackTrace();

        }
        return conn;
    }



    static public Connection getConnection() {     // 연결 객체들이 존재하는 pool stack에서 연결을 하나 pop하여 반환하는 함수
        // 연결 객체가 부족하면 연결 객체를 만드는 함수를 호출하여 반환

        Connection result = null;

        try {

            if (!POOL.empty()) {
                result = POOL.pop();

            } else {

                if (curPoolSize < MAX_POOL_SIZE) {
                    result = createConnection();

                } else {
                    throw new Exception("Exception : DB 최대 연결 초과");

                }

            }
        } catch(Exception E){
            System.out.println(E);

        }

        return result;
    }

    static public Connection returnConnection(Connection conn){     // DAO에서 연결 객체를 사용 후 호출하는 함수
        return POOL.push(conn);                                     // pool stack에 사용이 끝난 객체를 push
    }



    static public void terminate() {    // DBCP를 close()하면 모든 connection들을 close()

        try {
            for (Connection x : POOL) {
                if (x != null) {
                    x.close();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}

