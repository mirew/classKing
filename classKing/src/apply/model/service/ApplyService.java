package apply.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import apply.model.dao.ApplyDao;
import apply.model.vo.Apply;


public class ApplyService {

	public int classmemberInsert(Apply apply) {
		Connection con = getConnection();
		int result = new ApplyDao().classmemberInsert(con,apply);
		if(result > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}

	public int memberapplyCheck(int classNo, String memberId) {
		Connection con = getConnection();
		int result = new ApplyDao().memberapplyCheck(con,classNo,memberId);
		
		if(result > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}
	public ArrayList<Apply> selectList(int currentPage, int limit, int classNo){
		Connection con = getConnection();
		ArrayList<Apply> list = new ApplyDao().selectApply(con,currentPage, limit, classNo);
		close(con);
		return list;
	}

	public int getListCount(int classNo) {
		Connection con = getConnection();
		int listCount = new ApplyDao().getListCount(con,classNo);
		close(con);
		return listCount;
	}

	public int applyUpdate(Apply apply, int appStatus) {
		Connection con = getConnection();
		int result = new ApplyDao().applyUpdate(con,apply,appStatus);
		if(result>0)
			commit(con);
		else
			rollback(con);
		return result;
	}

	public Apply alarmapply(int classNo) {
		Connection con = getConnection();
		Apply apply=new ApplyDao().alarmapply(con,classNo);
		
		close(con);
		return apply;
	}
}