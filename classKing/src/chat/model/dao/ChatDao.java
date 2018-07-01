package chat.model.dao;

import static common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import calendar.model.vo.Calendar;
import chat.model.vo.Chat;

public class ChatDao {

	public int chatInsert(Connection conn, Chat chat) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO class_chat values((SELECT max(chat_no) FROM class_chat)+1,"
				+ "?,?,?,default)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, chat.getClassNo());
			pstmt.setString(2, chat.getMemberId());
			pstmt.setString(3, chat.getChatNote());
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<Chat> chatList(Connection conn, int classNo) {
		ArrayList<Chat> list = new ArrayList<Chat>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT member_id,chat_date,chat_note FROM class_chat " + 
				"WHERE class_no = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, classNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Chat chat = new Chat();
				chat.setMemberId(rset.getString(1));
				chat.setChatDate(rset.getDate(2));
				chat.setChatNote(rset.getString(3));
				
				list.add(chat);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public ArrayList<Chat> chat1on1List(Connection conn, String[] id) {
		ArrayList<Chat> list = new ArrayList<Chat>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT c1n.member_id,c1n.chat_note FROM chat_1on1 c1,chat_1on1_note c1n "
				+ "WHERE c1.chat_no = c1n.chat_no AND c1.member_id1 = ? AND c1.member_id2 = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id[0]);
			pstmt.setString(2, id[1]);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Chat chat = new Chat();
				chat.setMemberId(rset.getString(1));
				chat.setChatNote(rset.getString(2));
				
				list.add(chat);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int chat1on1Insert(Connection conn, String[] id) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO chat_1on1 values((SELECT max(chat_no) FROM chat_1on1)+1,"
				+ "?,?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id[0]);
			pstmt.setString(2, id[1]);
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int chat1on1NoteInsert(Connection conn, Chat chat) {
		int result = 0;
		PreparedStatement pstmt = null;
		String id[] = {chat.getMemberId(),chat.getMemberId2()};
		Arrays.sort(id);
		String query = "INSERT INTO chat_1on1_note values((SELECT max(chat_no) FROM chat_1on1 "
				+ "WHERE member_id1 = ? AND member_id2 = ?),?,?,default)";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id[0]);
			pstmt.setString(2, id[1]);
			pstmt.setString(3, chat.getMemberId());
			pstmt.setString(4, chat.getChatNote());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}