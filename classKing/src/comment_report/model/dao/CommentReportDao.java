package comment_report.model.dao;

import static common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;

import comment_report.model.vo.CommentReport;
import report.model.vo.Report;

public class CommentReportDao {
	public int insertReport(Connection con, CommentReport creport) {
		int result = 0 ;
		PreparedStatement pstmt = null;

		
		String query ="insert into comments_report values((select max(report_no)+1 from comments_report),?, ?, ?, ? , sysdate,?,?,default)";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, creport.getMemberId());
			pstmt.setInt(2, creport.getCommentNo());
			pstmt.setString(3, creport.getRepoartCategoryNote());
			pstmt.setString(4, creport.getReportNote());
			pstmt.setString(5, creport.getReportOriginalFile());
			pstmt.setString(6, creport.getReportRenameFile());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
}
