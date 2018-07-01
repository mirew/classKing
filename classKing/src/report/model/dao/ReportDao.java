package report.model.dao;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import report.model.vo.Report;

public class ReportDao {

	public int mybListCount(Connection con, String memberId) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select count(*) from board_report a,board b where a.member_id=? and a.board_no=b.board_no";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, memberId);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				listCount = rset.getInt("count(*)");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rset);
		}

		return listCount;
	}

	public ArrayList<Report> mybreportList(Connection con, String memberId, int currentPage, int limit) {

		ArrayList<Report> list = new ArrayList<Report>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from(select rownum rnum,b.board_no,c.class_title,e.report_category_note,a.report_date from " + 
				" board_report a,board b,class c,class_member d,report_category e where a.member_id=? " + 
				" and a.board_no=b.board_no and d.class_no=c.class_no and b.class_member_no=d.class_member_no and e.report_category_no=a.report_category_no order by a.report_date desc)where rnum>=? and rnum<=?";

		int startRow = (currentPage - 1) * limit + 1;
		int endRow = startRow + limit - 1;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, memberId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				Report report = new Report();
				report.setReportNo(rset.getInt(2));
				report.setClassName(rset.getString(3));
				report.setCategoryNo(rset.getString(4));
				report.setReportDate(rset.getDate(5));
				list.add(report);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int mycListCount(Connection con, String memberId) {

		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select count(*) from comments_report a, comments b where a.member_id=?"
				+ " and a.comments_no=b.comments_no";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, memberId);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rset);
		}

		return listCount;

	}

	public ArrayList<Report> mycreportList(Connection con, String memberId, int currentPage, int limit) {

		ArrayList<Report> list = new ArrayList<Report>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from(select rownum rnum,a.comments_no,b.class_title,f.report_category_note,a.comments_date "
				+ "from comments a,class b,comments_report c,board d,class_member e,report_category f "
				+ "where c.member_id=? and a.comments_no=c.comments_no "
				+ "and a.board_no=d.board_no and d.class_member_no=e.class_member_no "
				+ "and e.class_no=b.class_no and f.report_category_no=c.report_category_no order by c.report_date desc) where rnum>=? and rnum<=?";

		int startRow = (currentPage - 1) * limit + 1;
		int endRow = startRow + limit - 1;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, memberId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				Report report = new Report();
				report.setReportNo(rset.getInt(2));
				report.setClassName(rset.getString(3));
				report.setCategoryNo(rset.getString(4));
				report.setReportDate(rset.getDate(5));
				list.add(report);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;

	}

	public int getReportListCount(Connection con, String r_category) {

		int listCount = 0;
		Statement stmt = null;
		ResultSet rset = null;
		
		String query = null;
		
		if(r_category.equals("board")) {
			query="select count(*) from board_report";
		}else {
			query="select count(*) from comments_report";
		}
		try {
			stmt=con.createStatement();
			rset=stmt.executeQuery(query);

			if (rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(rset);
		}

		return listCount;
	}

	public ArrayList<Report> allReportList(Connection con, String r_category, int currentPage, int limit) {

		ArrayList<Report> list = new ArrayList<Report>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = null;
		
		if(r_category.equals("board")) {
			query="select * from (select rownum rnum,br.report_no,br.member_id,br.board_no,b.board_title,rc.report_category_note,br.report_date,cm.class_no,br.report_fake\r\n" + 
					"from board_report br,report_category rc,board b,class_member cm where br.report_category_no = rc.report_category_no and b.board_no = br.board_no and b.class_member_no=cm.class_member_no) where rnum>=? and rnum <= ? order by report_no desc";
		}else {
			query="select * from (select rownum rnum,cr.comments_no,c.board_no,cr.report_no,cr.member_id,c.comments_note,rc.report_category_note,cr.report_date,cm.class_no,cr.report_fake\r\n" + 
					"from comments_report cr,report_category rc,comments c,board b,class_member cm where cr.report_category_no = rc.report_category_no and c.comments_no = cr.comments_no and \r\n" + 
					"b.board_no=c.board_no and b.class_member_no = cm.class_member_no) where rnum>=? and rnum <= ? order by report_no desc";
		}

		int startRow = (currentPage - 1) * limit + 1;
		int endRow = startRow + limit - 1;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);

			rset = pstmt.executeQuery();
			if(r_category.equals("board")) {
				while (rset.next()) {
					Report report = new Report();
					report.setRowNum(rset.getInt("rnum"));
					report.setBoardNo(rset.getInt("board_no"));
					report.setClassNo(rset.getString("class_no"));
					report.setMemberId(rset.getString("member_id"));
					report.setBoardTitle(rset.getString("board_title"));
					report.setCategoryNote(rset.getString("report_category_note"));
					report.setReportDate(rset.getDate("report_date"));
					report.setReportNo(rset.getInt("report_no"));	
					report.setReportFake(rset.getString("report_fake"));
					
					list.add(report);
				}
				
			}else {
				
				while (rset.next()) {
					Report report = new Report();
					report.setRowNum(rset.getInt("rnum"));
					report.setBoardNo(rset.getInt("board_no"));
					report.setMemberId(rset.getString("member_id"));
					report.setCommentsNo(rset.getString("comments_no"));
					report.setCommentsNote(rset.getString("comments_note"));
					report.setCategoryNote(rset.getString("report_category_note"));
					report.setReportDate(rset.getDate("report_date"));	
					report.setClassNo(rset.getString("class_no"));
					report.setReportNo(rset.getInt("report_no"));
					report.setReportFake(rset.getString("report_fake"));
					
					list.add(report);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int insertReport(Connection con, Report report) {
		int result = 0 ;
		PreparedStatement pstmt = null;
		
		String query ="insert into board_report values((select max(report_no)+1 from board_report),?, ?, ?, ? , sysdate,?,?,default)";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, report.getMemberId());
			pstmt.setInt(2, report.getBoardNo());
			pstmt.setString(3, report.getCategoryNo());
			pstmt.setString(4, report.getReportNote());
			pstmt.setString(5, report.getReportOriginFile());
			pstmt.setString(6, report.getReportRenameFile());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int updateReport(Connection con, String r_category, String status, int reportNo) {
		int result = 0 ;
		PreparedStatement pstmt = null;
		String query=null;
		
		if(r_category.equals("board")) {
			query ="update board_report set report_fake = ? where report_no=?";
		}else {
			query ="update comments_report set report_fake = ? where report_no=?";
		}
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, status);
			pstmt.setInt(2, reportNo);			
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public Report detailBReport(Connection con, Integer reportNo) {
		Report report=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query="select a.member_id,b.board_title,a.report_date,a.report_note,d.class_title,a.report_original_file,a.report_rename_file from board_report a,board b,class_member c,class d where report_no=? and "
				+ "a.board_no=b.board_no and b.class_member_no=c.class_member_no and c.class_no=d.class_no";
	
			try {
				pstmt=con.prepareStatement(query);
				pstmt.setInt(1,reportNo);
				rset=pstmt.executeQuery();
				if(rset.next()) {
					
					report=new Report();
					report.setMemberId(rset.getString(1));
					report.setBoardTitle(rset.getString(2));
					report.setReportDate(rset.getDate(3));
					report.setReportNote(rset.getString(4));
					report.setClassName(rset.getString(5));
					report.setReportOriginFile(rset.getString(6));
					report.setReportRenameFile(rset.getString(7));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				close(rset);
				close(pstmt);
			}
		return report;
	}

	public Report detailCReport(Connection con, Integer reportNo) {
		Report report=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		String query="select b.member_id,b.report_date,b.report_note,d.class_title,b.report_original_filename,b.report_rename_filename,a.comments_note from comments a,comments_report b,board c,class d,class_member e where b.report_no=? and a.comments_no=b.comments_no"
				+ " and a.board_no=c.board_no and c.class_member_no=e.class_member_no and e.class_no=d.class_no";
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1,reportNo);
			rset=pstmt.executeQuery();
			if(rset.next()) {
				report=new Report();
				report.setMemberId(rset.getString(1));
				report.setReportDate(rset.getDate(2));
				report.setReportNote(rset.getString(3));
				report.setClassName(rset.getString(4));
				report.setReportOriginFile(rset.getString(5));
				report.setReportRenameFile(rset.getString(6));
				report.setCommentsNote(rset.getString(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return report;
	}
}