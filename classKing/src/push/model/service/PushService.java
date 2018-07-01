package push.model.service;

import java.sql.*;
import java.util.ArrayList;

import classes.model.dao.ClassesDao;
import classes.model.vo.Classes;
import push.model.dao.PushDao;
import push.model.vo.Push;

import static common.JDBCTemplate.*;

public class PushService {

	public PushService() {}

	public int insertPush(int boardNo, String memberId) {
		Connection con=getConnection();
		int result2 = new PushDao().insertPush(con,boardNo,memberId);
		if(result2 > 0)
			commit(con);
		else
			rollback(con);
		close(con);
		return result2;
	}

	public ArrayList<Push> selectPushList(String memberId) {
		Connection conn = getConnection();
		ArrayList<Push> list = new PushDao().selectPushList(conn,memberId);
		close(conn);
		
		return list;
	}

	public int checkPush(int pushNo) {
		Connection con=getConnection();
		int result = new PushDao().checkPush(con,pushNo);
		if(result > 0)
			commit(con);
		else
			rollback(con);
		close(con);
		return result;
	}
}