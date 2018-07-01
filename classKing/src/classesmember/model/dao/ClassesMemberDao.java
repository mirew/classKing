package classesmember.model.dao;

import static common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import board.model.vo.Board;
import classes.model.vo.Classes;
import classesmember.model.vo.ClassesMember;

public class ClassesMemberDao {

	public int classMemberJoin(Connection conn, String memberId, Classes classes) {
		int result = 0;
		int classNo = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "INSERT INTO class_member values((SELECT MAX(class_member_no)+1 FROM class_member)"
				+ ",?,?,'Y',default,default)";
		
		String query2 = "SELECT class_no FROM class WHERE class_title = ?";
		try {
			pstmt = conn.prepareStatement(query2);
			pstmt.setString(1,classes.getClassTitle());
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				classNo = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,classNo);
			pstmt.setString(2, memberId);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public ClassesMember joinCheck(Connection conn, String memberId, int classNo) {
		ClassesMember cm = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT class_king FROM class_member WHERE class_no = ? and member_id = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, classNo);
			pstmt.setString(2, memberId);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				cm = new ClassesMember();
				cm.setClassKing(rset.getString(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return cm;
	}

	public ArrayList<ClassesMember> classMemberList(Connection conn, int classNo) {
		ArrayList<ClassesMember> list = new ArrayList<ClassesMember>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT cm.class_member_no,m.member_rename_img,cm.member_id,cm.class_king,cm.class_join_date,max(lad.latest_date) \r\n" + 
				"FROM class_member cm  LEFT JOIN member m on cm.member_id = m.member_id \r\n" + 
				"LEFT JOIN latest_access_date lad on cm.class_member_no = lad.class_member_no \r\n" + 
				"WHERE cm.class_no = ?\r\n" + 
				"GROUP BY cm.class_member_no,m.member_rename_img,cm.member_id,cm.class_king,cm.class_join_date\r\n" + 
				"ORDER BY cm.class_member_no";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, classNo);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				ClassesMember cm = new ClassesMember();
				
				cm.setClassMemberNo(rset.getInt(1));
				cm.setMemberRenameImg(rset.getString(2));
				cm.setMemberId(rset.getString(3));
				cm.setClassKing(rset.getString(4));
				cm.setClassJoinDate(rset.getDate(5));
				cm.setLatestDate(rset.getDate(6));
				
				list.add(cm);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int latestUpdate(Connection conn, String memberId, int classNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO latest_access_date values("
				+ "(SELECT max(class_member_no) FROM class_member WHERE member_id = ? and class_no = ?),"
				+ "TO_DATE(sysdate, 'YY-MM-DD'))";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			pstmt.setInt(2, classNo);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int ClassMemberInsert(Connection con, ClassesMember cm) {
		int result2 = 0;
		PreparedStatement pstmt = null;
      	String query ="insert into class_member values((select max(class_member_no)+1 from class_member), ?,?,'N',sysdate,'N')";
		
      	try {
      		pstmt = con.prepareStatement(query);
      		pstmt.setInt(1, cm.getClassNo());
      		pstmt.setString(2, cm.getMemberId());
      		
      		result2 = pstmt.executeUpdate();
      	}catch(Exception e) {
      		e.printStackTrace();
      	}finally {
      		close(pstmt);
      	}
      	return result2;
	}

	public ArrayList<ClassesMember> classMemberList2(Connection conn, int classNo) {
		
		ArrayList<ClassesMember> cmlist2 = new ArrayList<ClassesMember>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from class_member where class_no =? and class_member_leave='N'";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, classNo);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				ClassesMember cm = new ClassesMember();				
				cm.setClassMemberNo(rset.getInt("class_member_no"));
				cm.setClassNo(rset.getInt("class_no"));
				cm.setMemberId(rset.getString("member_id"));
				cm.setClassKing(rset.getString("class_king"));
				cm.setClassJoinDate(rset.getDate("class_join_date"));
				cm.setClassMemberLeave(rset.getString("class_member_leave"));
				
				cmlist2.add(cm);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return cmlist2;
	}

	public ClassesMember secretCmCheck(Connection conn, String memberId, int classNo) {
		ClassesMember cm = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT class_member_no FROM class_member WHERE class_no = ? and member_id = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, classNo);
			pstmt.setString(2, memberId);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				cm = new ClassesMember();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return cm;
	}

	public ClassesMember classKingCheck(Connection conn, String memberId, int classNo) {
		ClassesMember cm = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT class_king FROM class_member WHERE class_no = ? and member_id = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, classNo);
			pstmt.setString(2, memberId);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				cm = new ClassesMember();
				cm.setClassKing(rset.getString(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return cm;
	}

	public ClassesMember checkJoinClass(Connection con, String id, int classNo) {
		ClassesMember checkClass=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		String query="select b.class_title from class_member a,class b where a.member_id=? and a.class_no=? and a.class_no=b.class_no";
		
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setInt(2, classNo);
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				checkClass=new ClassesMember();
				checkClass.setClassTitle(rset.getString(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
			return checkClass;
		}
}