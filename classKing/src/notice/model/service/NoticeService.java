package notice.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import notice.model.dao.NoticeDao;
import notice.model.vo.Notice;

public class NoticeService {

	public ArrayList<Notice> newNotice() {
		Connection conn = getConnection();
		ArrayList<Notice> list = new NoticeDao().newNotice(conn);
		close(conn);
		
		return list;
	}
	public ArrayList<Notice> selectList(int currentPage, int limit) {
		Connection con=getConnection();
		ArrayList<Notice> list=new NoticeDao().selectList(con,currentPage,limit);
		close(con);
		return list;
	}

	public Notice selectNotice(int noticeNo) {
		Connection con=getConnection();
		Notice notice = new NoticeDao().selectNotice(con, noticeNo);
		close(con);
		return notice;
	}

	public int getListCount() {
		Connection con=getConnection();
		int listCount = new NoticeDao().getListCount(con);
		close(con);
		return listCount;
	}

	public int insertNotice(Notice notice) {
		Connection con=getConnection();
		int result = new NoticeDao().insertNotice(con,notice);
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}

	public int deleteNotice(int noticeNo) {
		Connection con=getConnection();
		int result = new NoticeDao().deleteNotice(con,noticeNo);
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}


	public void addViewCount(int noticeNo) {
		Connection con = getConnection();
		int result= new NoticeDao().addViewCount(con,noticeNo);
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);		
	}


	public int updateNotice(Notice notice) {
		Connection con=getConnection();
		int result = new NoticeDao().updateNotice(con,notice);
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}


	public ArrayList<Notice> searchList(int currentPage, int limit, String n_keyword, String n_category) {
		Connection con=getConnection();
		ArrayList<Notice> list=new NoticeDao().searchList(con,currentPage,limit,n_keyword,n_category);
		close(con);
		return list;
	}


	public int getSrchCount(String n_keyword, String n_category) {
		Connection con=getConnection();
		int listCount = new NoticeDao().getSrchCount(con,n_keyword,n_category);
		close(con);
		return listCount;
	}
}
