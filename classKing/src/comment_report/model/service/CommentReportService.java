package comment_report.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;

import comment_report.model.dao.CommentReportDao;
import comment_report.model.vo.CommentReport;


public class CommentReportService {

	public int insertReport(CommentReport creport) {
		Connection con = getConnection();
		int result = new CommentReportDao().insertReport(con,creport);
		if(result >0)
			commit(con);
		else
			rollback(con);
		close(con);
		return result;
	}
}
