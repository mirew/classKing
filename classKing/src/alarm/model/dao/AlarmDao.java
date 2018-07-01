package alarm.model.dao;

import static common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import classes.model.vo.Classes;
import qna.model.vo.Qna;

public class AlarmDao {

	public Classes checkClass(Connection con,String id, int classNo) {
		Classes c=null;
		
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query = "select a.class_title from class a,class_member c "
				+ "where c.member_id=? and a.class_no=c.class_no and a.class_no=?";
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1,id);
			pstmt.setInt(2,classNo);
			
			rset=pstmt.executeQuery();
			if(rset.next()) {
				c=new Classes();
				c.setClassTitle(rset.getString(1));
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
			
		}
		return c;
		
		
		
		
	}

	public Qna checkQna(Connection con, String id, int qnaNum) {
		Qna q=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query="select * from qna where qna_writer=? and qna_no=?";
	
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setInt(2, qnaNum);
			
			rset=pstmt.executeQuery();
			if(rset.next()) {
				q=new Qna();
				q.setQnaTitle(rset.getString("qna_title"));
			
			}
			
			
		} catch (Exception e) {
		e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return q;
	}



}
