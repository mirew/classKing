package chat.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import calendar.model.dao.CalendarDao;
import calendar.model.vo.Calendar;
import chat.model.dao.ChatDao;
import chat.model.vo.Chat;

public class ChatService {

	public int chatInsert(Chat chat) {
		Connection conn = getConnection();
		int result = new ChatDao().chatInsert(conn,chat);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public ArrayList<Chat> chatList(int classNo) {
		Connection conn = getConnection();
		ArrayList<Chat> list = new ChatDao().chatList(conn,classNo);
		close(conn);
		
		return list;
	}

	public ArrayList<Chat> chat1on1List(String[] id) {
		Connection conn = getConnection();
		ArrayList<Chat> list = new ChatDao().chat1on1List(conn,id);
		close(conn);
		
		return list;
	}

	public void chat1on1Insert(String[] id) {
		Connection conn = getConnection();
		int result = new ChatDao().chat1on1Insert(conn,id);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
	}

	public int chat1on1NoteInsert(Chat chat) {
		Connection conn = getConnection();
		int result = new ChatDao().chat1on1NoteInsert(conn,chat);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
}