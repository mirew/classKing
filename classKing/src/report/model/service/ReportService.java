package report.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import report.model.dao.ReportDao;
import report.model.vo.Report;

public class ReportService {

	public int mybListCount(String memberId) {
		Connection con = getConnection();
		int listCount = new ReportDao().mybListCount(con, memberId);
		close(con);
		return listCount;

	}

	public ArrayList<Report> mybreportList(String memberId, int currentPage, int limit) {

		Connection con = getConnection();
		ArrayList<Report> list = new ReportDao().mybreportList(con, memberId, currentPage, limit);
		close(con);

		return list;

	}

	public int mycListCount(String memberId) {
		Connection con = getConnection();
		int listCount = new ReportDao().mycListCount(con, memberId);
		close(con);

		return listCount;

	}

	public ArrayList<Report> mycreportList(String memberId, int currentPage, int limit) {

		Connection con = getConnection();
		ArrayList<Report> list = new ReportDao().mycreportList(con, memberId, currentPage, limit);
		close(con);

		return list;

	}

	public int getReportListCount(String r_category) {
		Connection con = getConnection();
		int listCount = new ReportDao().getReportListCount(con, r_category);
		close(con);

		return listCount;

	}

	public ArrayList<Report> allReportList(int currentPage, int limit, String r_category) {

		Connection con = getConnection();
		ArrayList<Report> list = new ReportDao().allReportList(con, r_category, currentPage, limit);
		close(con);

		return list;

	}
	public int insertReport(Report report) {
		Connection con = getConnection();
		int result = new ReportDao().insertReport(con,report);
		if(result >0)
			commit(con);
		else
			rollback(con);
		close(con);
		return result;
	}
	public int updateReport(String r_category, String status, int reportNo) {
		Connection con = getConnection();
		int result = new ReportDao().updateReport(con,r_category,status,reportNo);
		if(result >0)
			commit(con);
		else
			rollback(con);
		close(con);
		return result;
	}

	public Report detailBReport(Integer reportNo) {
		Connection con = getConnection();
		Report report=new ReportDao().detailBReport(con,reportNo);
		close(con);
		return report;
	}

	public Report detailCReport(Integer reportNo) {
		Connection con = getConnection();
		Report report=new ReportDao().detailCReport(con,reportNo);
		close(con);
		return report;
	}
}
