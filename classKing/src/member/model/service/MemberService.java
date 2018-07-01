package member.model.service;

import member.model.dao.MemberDao;
import member.model.vo.Member;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

public class MemberService {

	public int joinMember(Member member) {
		Connection conn = getConnection();
		int result = new MemberDao().joinMember(conn, member);
		if (result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public Member loginCheck(String userId, String userPwd) {
		Connection conn = getConnection();
		Member member = new MemberDao().loginMember(conn, userId, userPwd);
		close(conn);

		return member;
	}

	public Member idCheck(String userid) {
		Connection conn = getConnection();
		Member user = new MemberDao().idCheck(conn, userid);
		close(conn);

		return user;
	}

	public int updateMember(Member member) {
		Connection conn = getConnection();
		int result = new MemberDao().updateMember(conn, member);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		return result;
	}

	public int getListCount() {
		Connection con = getConnection();
		int listCount = new MemberDao().getListCount(con);
		close(con);
		return listCount;
	}

	public ArrayList<Member> allMemberList(int currentPage, int limit) {
		Connection con = getConnection();
		ArrayList<Member> list = new MemberDao().allMemberList(con, currentPage, limit);
		close(con);
		return list;
	}

	public int deleteMember(String memberId) {
		Connection conn = getConnection();
		int result = new MemberDao().deleteMember(conn, memberId);
		if (result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public int getSrchCount(String m_keyword, String m_category) {
		Connection con = getConnection();
		int listCount = new MemberDao().getSrchCount(con, m_keyword, m_category);
		close(con);
		return listCount;
	}

	public ArrayList<Member> searchList(int currentPage, int limit, String m_keyword, String m_category) {
		Connection con = getConnection();
		ArrayList<Member> list = new MemberDao().searchList(con, currentPage, limit, m_keyword, m_category);
		close(con);
		return list;
	}

	public int pwdFind(String memberId, String passwordQ, String passwordA) {
		Connection con = getConnection();
		int result = new MemberDao().passwordCheck(con, memberId, passwordQ, passwordA);
		close(con);
		return result;
	}

	public int pwdUpdate(String memberId, String password) {
		Connection con = getConnection();
		int result = new MemberDao().pwdUpdate(con, memberId, password);
		if(result >0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}

	public Member checkUser(String userId) {
		Connection con = getConnection();
		Member checkUser = new MemberDao().checkUser(con, userId);
		close(con);
		
		return checkUser;
	}

	public Member idFind(String name, String email, String birth) {
		Connection con = getConnection();
		Member member = new MemberDao().idFind(con, name,email,birth);
		close(con);
		
		return member;
	}
	
	   public static int signout(String userId) {
		      Connection con = getConnection();
		      int result= new MemberDao().signoutDao(con,userId);
		      
		      if (result > 0){
		         commit(con);
		      }else{
		         rollback(con);
		         }

		      return result;
		   }

}
