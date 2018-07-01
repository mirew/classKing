package classesmember.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import classes.model.dao.ClassesDao;
import classes.model.vo.Classes;
import classesmember.model.dao.ClassesMemberDao;
import classesmember.model.vo.ClassesMember;

public class ClassMemberService {

	public int classMemberJoin(String memberId, Classes classes) {
		Connection conn = getConnection();
		int result = new ClassesMemberDao().classMemberJoin(conn,memberId, classes);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public ClassesMember joinCheck(String memberId, int classNo) {
		Connection conn = getConnection();
		ClassesMember cm = new ClassesMemberDao().joinCheck(conn,memberId,classNo);
		close(conn);
		
		return cm;
	}

	public ArrayList<ClassesMember> classMemberList(int classNo) {
		Connection conn = getConnection();
		ArrayList<ClassesMember> list = new ClassesMemberDao().classMemberList(conn,classNo);
		
		close(conn);
		
		return list;
	}

	public void latestUpdate(String memberId, int classNo) {
		Connection conn = getConnection();
		int result = new ClassesMemberDao().latestUpdate(conn,memberId,classNo);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
	}

	public int ClassMemberInsert(ClassesMember cm) {
		Connection con = getConnection();
		int result = new ClassesMemberDao().ClassMemberInsert(con, cm);
		if(result >0)
			commit(con);
		else
			rollback(con);
		close(con);
		return result;
	}

	public ArrayList<ClassesMember> classMemberList2(int classNo) {
		Connection conn = getConnection();
		ArrayList<ClassesMember> list = new ClassesMemberDao().classMemberList2(conn,classNo);
		
		close(conn);
		
		return list;
	}

	public ClassesMember secretCmCheck(String memberId, int classNo) {
		Connection conn = getConnection();
		ClassesMember cm = new ClassesMemberDao().secretCmCheck(conn,memberId,classNo);
		close(conn);
		
		return cm;
	}

	public ClassesMember classKingCheck(String memberId, int classNo) {
		Connection conn = getConnection();
		ClassesMember cm = new ClassesMemberDao().classKingCheck(conn,memberId,classNo);
		close(conn);
		
		return cm;
	}
	public ClassesMember checkJoinClass(String id, int classNo) {
		Connection con=getConnection();
		ClassesMember checkClass=new ClassesMemberDao().checkJoinClass(con,id,classNo);
		close(con);
		
		
		return checkClass;
	}
}