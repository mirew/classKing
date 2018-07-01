package classes.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import board.model.dao.BoardDao;
import board.model.vo.Board;
import classes.model.dao.ClassesDao;
import classes.model.vo.Classes;
import member.model.dao.MemberDao;
import member.model.vo.Member;

public class ClassesService {

	public int classMake(Classes classes,String memberId) {
		Connection conn = getConnection();
		int result = new ClassesDao().classMake(conn, classes);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public ArrayList<Classes> classesList(String memberId) {
		Connection conn = getConnection();
		ArrayList<Classes> list = new ClassesDao().classesList(conn,memberId);
		close(conn);
		
		return list;
	}

	public Classes selectClasses(int classNo) {
		Connection conn = getConnection();
		Classes classes = new ClassesDao().selectClasses(conn,classNo);
		close(conn);
		
		return classes;
	}

	public ArrayList<Classes> myClassesList(String memberId,int currentPage,int limit) {
		Connection conn = getConnection();
		ArrayList<Classes> list = new ClassesDao().myClassesList(conn,memberId,currentPage,limit);
		close(conn);
		
		return list;
	}

	public int myGetListCount(String memberId) {
		Connection con=getConnection();
		int listCount = new ClassesDao().myGetListCount(con,memberId);
		close(con);
		return listCount;
	}
	public int getClassListCount(String c_category) {
		Connection con=getConnection();
		int listCount = new ClassesDao().getClassListCount(con,c_category);
		close(con);
		
		return listCount;
	}

	public ArrayList<Classes> allClassList(int currentPage, int limit, String c_category) {
		Connection conn = getConnection();
		ArrayList<Classes> list = new ClassesDao().allClassList(conn,currentPage,limit,c_category);
		close(conn);
		
		return list;
	}

	public int deleteClass(String classNo) {
		Connection conn = getConnection();
		int result = new ClassesDao().deleteClass(conn, classNo);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public ArrayList<Classes> rankClass() {
		Connection conn = getConnection();
		ArrayList<Classes> list = new ClassesDao().rankClass(conn);
		close(conn);
		
		return list;
	}
	public int getSrchListCount(String keyword) {
		Connection con=getConnection();
		int listCount = new ClassesDao().getSrchListCount(con,keyword);
		close(con);
		
		return listCount;
	}

	public ArrayList<Classes> srchClassList(int currentPage, int limit, String keyword) {
		Connection conn = getConnection();
		ArrayList<Classes> list = new ClassesDao().srchClassList(conn,currentPage,limit,keyword);
		close(conn);
		
		return list;
	}

	public Classes classJoinSelect(int classNo) {
		Connection conn = getConnection();
		Classes classes = new ClassesDao().classJoinSelect(conn,classNo);
		if(classes != null) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return classes;
	}
	public int getAllListCount() {
		Connection con=getConnection();
		int listCount = new ClassesDao().getAllListCount(con);
		close(con);
		
		return listCount;
	}

	public ArrayList<Classes> srchAllList(int currentPage, int limit) {
		Connection conn = getConnection();
		ArrayList<Classes> list = new ClassesDao().srchAllList(conn,currentPage,limit);
		close(conn);
		
		return list;
	}

	public int classNameCheck(String className) {
		Connection conn=getConnection();
		int result=new ClassesDao().checkClassName(conn, className);
		close(conn);
		return result;
	}
	public Classes selectClass(int classNo) {
		Connection conn=getConnection();
		Classes c=new ClassesDao().selectClass(conn, classNo);
		close(conn);
		return c;
	}

	public int classUpdate(Classes classes) {
		Connection conn = getConnection();
		int result = new ClassesDao().classUpdate(conn, classes);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}
}
