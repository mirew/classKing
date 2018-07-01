package notice.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import board.model.vo.Board;
import notice.model.vo.Notice;

import static common.JDBCTemplate.*;

public class NoticeDao {

	public ArrayList<Notice> newNotice(Connection conn) {
		ArrayList<Notice> list = new ArrayList<Notice>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM (SELECT rownum as rnum,notice_no,notice_title,notice_date "
				+ "FROM notice ORDER BY notice_no desc) "
				+ "WHERE rnum <= 5";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Notice notice = new Notice();
				notice.setNoticeNo(rset.getInt(2));
				notice.setNoticeTitle(rset.getString(3));
				notice.setNoticeDate(rset.getDate(4));
				
				list.add(notice);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	public int getListCount(Connection con) {
		int listCount = 0;
		
		Statement stmt=null;
		ResultSet rset=null;
		
		String query = "select count(*) from notice";

		try {
			stmt=con.createStatement();
			rset=stmt.executeQuery(query);
			
			if(rset.next()) {
				listCount=rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(rset);			
		}
		
		return listCount;
	}
	public ArrayList<Notice> selectList(Connection con,int currentPage, int limit) {
		ArrayList<Notice> list=new ArrayList<Notice>();
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		String query="select * from (select rownum rnum,notice_no,notice_title,notice_note,notice_date,notice_view,notice_original_file," + 
				"notice_rename_file from (select * from notice order by notice_no desc)) where rnum >=? and rnum<=?";
		
		int startRow = (currentPage-1)*limit +1;
		int endRow = startRow +limit -1;
		
		try {			
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset=pstmt.executeQuery();			
			
			while(rset.next()) {
				Notice n=new Notice();
				n.setNoticeNo(rset.getInt("notice_no"));
				n.setNoticeTitle(rset.getString("notice_title"));
				n.setNoticeNote(rset.getString("notice_note"));
				n.setNoticeDate(rset.getDate("notice_date"));
				n.setNoticeView(rset.getInt("notice_view"));
				n.setNoticeOriginalFile(rset.getString("notice_original_file"));
				n.setNoticeRenameFile(rset.getString("notice_rename_file"));
				
				list.add(n);				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}		
		return list;
	}

	public Notice selectNotice(Connection con, int noticeNo) {
		Notice notice=null; 
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		String query="select * from notice where notice_no=?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				notice=new Notice();
				notice.setNoticeNo(rset.getInt("notice_no"));
				notice.setNoticeTitle(rset.getString("notice_title"));
				notice.setNoticeNote(rset.getString("notice_note"));
				notice.setNoticeDate(rset.getDate("notice_date"));
				notice.setNoticeView(rset.getInt("notice_view"));
				notice.setNoticeOriginalFile(rset.getString("notice_original_file"));
				notice.setNoticeRenameFile(rset.getString("notice_rename_file"));		
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return notice;
	}

	public int insertNotice(Connection con, Notice notice) {
		int result=0;
		
		PreparedStatement pstmt=null;		
		
		String query="insert into notice values ((select max(notice_no) from notice)+1,?,?,sysdate,default,?,? )";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, notice.getNoticeTitle());
			pstmt.setString(2, notice.getNoticeNote());
			pstmt.setString(3, notice.getNoticeOriginalFile());
			pstmt.setString(4, notice.getNoticeRenameFile());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}		
		return result;
	}

	public int deleteNotice(Connection con, int noticeNo) {
		int result=0;
		PreparedStatement pstmt = null;
		
		String query="delete from notice where notice_no=?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}		
		return result;
	}



	public int addViewCount(Connection con, int noticeNo) {
		int result=0;
		PreparedStatement pstmt=null;
		
		String query = "update notice set notice_view = notice_view + 1 where notice_no =?";
		
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}		
		return result;
	}



	public int updateNotice(Connection con, Notice notice) {
		int result=0;
		
		PreparedStatement pstmt=null;		
		String query="update notice set notice_title=?,notice_note=?,notice_original_file=?,notice_rename_file=? where notice_no=?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, notice.getNoticeTitle());
			pstmt.setString(2, notice.getNoticeNote());
			pstmt.setString(3, notice.getNoticeOriginalFile());
			pstmt.setString(4, notice.getNoticeRenameFile());
			pstmt.setInt(5, notice.getNoticeNo());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}		
		return result;
	}

	public int getSrchCount(Connection con, String n_keyword, String n_category) {
		int listCount = 0;
		
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query = null;
		if (n_category.equals("제목")) {
			  query = "select count(*) from notice where notice_title like '%'||?||'%'";
		} else {
		     query = "select count(*) from notice where notice_note like '%'||?||'%'";
		}		

		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, n_keyword);
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				listCount=rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rset);			
		}
		
		return listCount;
	}

	public ArrayList<Notice> searchList(Connection con, int currentPage, int limit, String n_keyword, String n_category) {
		ArrayList<Notice> list=new ArrayList<Notice>();
		PreparedStatement pstmt=null;
		ResultSet rset=null;

		String query = null;
		if (n_category.equals("제목")) {
			  query = "select * from (select rownum rnum,notice_no,notice_title,notice_note,notice_date,notice_view,notice_original_file,"+
		"notice_rename_file from (select * from notice where notice_title like '%'||?||'%' order by notice_no desc)) where rnum >=? and rnum<=?";
		} else {
		     query = "select * from (select rownum rnum,notice_no,notice_title,notice_note,notice_date,notice_view,notice_original_file,"+
		"notice_rename_file from (select * from notice where notice_note like '%'||?||'%' order by notice_no desc)) where rnum >=? and rnum<=?";
		}	
		
		int startRow = (currentPage-1)*limit +1;
		int endRow = startRow +limit -1;
		int count=1;
		try {			
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, n_keyword);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rset=pstmt.executeQuery();			
			
			while(rset.next()) {
				Notice n=new Notice();
				n.setNoticeNo(rset.getInt("notice_no"));
				n.setNoticeTitle(rset.getString("notice_title"));
				n.setNoticeNote(rset.getString("notice_note"));
				n.setNoticeDate(rset.getDate("notice_date"));
				n.setNoticeView(rset.getInt("notice_view"));
				n.setNoticeOriginalFile(rset.getString("notice_original_file"));
				n.setNoticeRenameFile(rset.getString("notice_rename_file"));
				
				list.add(n);				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}		
		return list;
	}
}
