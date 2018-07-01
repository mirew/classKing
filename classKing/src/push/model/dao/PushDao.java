package push.model.dao;

import java.sql.*;
import java.util.ArrayList;

import push.model.vo.Push;

import static common.JDBCTemplate.*;

public class PushDao {

	public PushDao() {}

	public int insertPush(Connection con, int boardNo, String memberId) {
		int result=0;
		PreparedStatement pstmt=null;
		String query="insert into push values((select max(push_no)+1 from push),?,?,default,default)";
		
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			pstmt.setString(2, memberId);
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public ArrayList<Push> selectPushList(Connection conn, String memberId) {
		ArrayList<Push> list = new ArrayList<Push>();
		
		PreparedStatement pstmt=null;
		ResultSet rset = null;
		String query="select * from(select rownum rnum,p.push_no,p.board_no,p.comments_writer,p.push_read,p.push_date,cm.member_id,c.class_title,c.class_no\r\n" + 
				"from push p,board b, class_member cm,class c\r\n" + 
				"where p.board_no = b.board_no and b.class_member_no = cm.class_member_no and c.class_no = cm.class_no\r\n" + 
				"and p.comments_writer != cm.member_id and push_read like 'N'\r\n" + 
				"and cm.member_id like ? order by push_no desc) where rnum>=1 and rnum<=5";
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				Push p =new Push();
				p.setPushNo(rset.getInt("push_no"));
				p.setBoardNo(rset.getInt("board_no"));
				p.setCommentsWriter(rset.getString("comments_writer"));
				p.setPushRead(rset.getString("push_read"));
				p.setClassTitle(rset.getString("class_title"));
				p.setClassNo(rset.getInt("class_no"));
				p.setPushDate(rset.getDate("push_date"));
				
				list.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		 return list;
	}

	public int checkPush(Connection con, int pushNo) {
		int result=0;
		PreparedStatement pstmt=null;
		String query="update push set push_read = 'Y' where push_no=?";
		
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, pushNo);
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
}