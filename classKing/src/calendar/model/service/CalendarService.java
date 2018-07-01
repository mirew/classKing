package calendar.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import calendar.model.dao.CalendarDao;
import calendar.model.vo.Calendar;

public class CalendarService {

	public ArrayList<Calendar> calendarList(int classNo) {
		Connection conn = getConnection();
		ArrayList<Calendar> list = new CalendarDao().calendarList(conn,classNo);
		close(conn);
		
		return list;
	}

	public int calInsert(Calendar cal) {
		Connection conn = getConnection();
		int result = new CalendarDao().calInsert(conn,cal);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	public Calendar calDetail(int calNo) {
		Connection conn = getConnection();
		Calendar cal = new CalendarDao().calDetail(conn,calNo);
		close(conn);
		
		return cal;
	}

	public int calUpdate(Calendar cal) {
		Connection conn = getConnection();
		int result = new CalendarDao().calUpdate(conn,cal);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	public int calDelete(int calNo) {
		Connection conn = getConnection();
		int result = new CalendarDao().calDelete(conn,calNo);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	
}
