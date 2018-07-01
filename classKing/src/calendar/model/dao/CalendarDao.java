package calendar.model.dao;

import static common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import board.model.vo.Board;
import calendar.model.vo.Calendar;

public class CalendarDao {

	public ArrayList<Calendar> calendarList(Connection conn, int classNo) {
		ArrayList<Calendar> list = new ArrayList<Calendar>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT c.cal_no,c.class_member_no,c.cal_title,c.cal_note,c.cal_sdate,c.cal_edate "
				+ "FROM calendar c,class_member cm WHERE cm.class_no = ? AND c.class_member_no = cm.class_member_no";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, classNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Calendar calendar = new Calendar();
				calendar.setCalNo(rset.getInt(1));
				calendar.setClassMemberNo(rset.getInt(2));
				calendar.setCalTitle(rset.getString(3));
				calendar.setCalNote(rset.getString(4));
				calendar.setCalSdate(rset.getDate(5));
				calendar.setCalEdate(rset.getDate(6));
				
				list.add(calendar);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int calInsert(Connection conn, Calendar cal) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO calendar values((SELECT max(cal_no) FROM calendar)+1,"
				+ "(SELECT max(class_member_no) FROM class_member WHERE class_no = ? AND member_id = ?),"
				+ "?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, cal.getClassNo());
			pstmt.setString(2, cal.getMemberId());
			pstmt.setString(3, cal.getCalTitle());
			pstmt.setString(4, cal.getCalNote());
			pstmt.setDate(5, cal.getCalSdate());
			pstmt.setDate(6, cal.getCalEdate());
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Calendar calDetail(Connection conn, int calNo) {
		Calendar cal = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT c.cal_no,cm.member_id,c.cal_title,c.cal_note,c.cal_sdate,c.cal_edate "
				+ "FROM calendar c,class_member cm "
				+ "WHERE cm.class_member_no = c.class_member_no "
				+ "AND c.cal_no = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, calNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				cal = new Calendar();
				cal.setCalNo(rset.getInt(1));
				cal.setMemberId(rset.getString(2));
				cal.setCalTitle(rset.getString(3));
				cal.setCalNote(rset.getString(4));
				cal.setCalSdate(rset.getDate(5));
				cal.setCalEdate(rset.getDate(6));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return cal;
	}

	public int calUpdate(Connection conn, Calendar cal) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "UPDATE calendar SET cal_title = ?, cal_note = ?, cal_sdate = ?, cal_edate = ? "
				+ "WHERE cal_no = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cal.getCalTitle());
			pstmt.setString(2, cal.getCalNote());
			pstmt.setDate(3, cal.getCalSdate());
			pstmt.setDate(4, cal.getCalEdate());
			pstmt.setInt(5, cal.getCalNo());
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int calDelete(Connection conn, int calNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "DELETE FROM calendar WHERE cal_no = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, calNo);
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
