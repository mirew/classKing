package apply.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static common.JDBCTemplate.*;
import apply.model.vo.Apply;
import javafx.css.PseudoClass;

public class ApplyDao {

	public int classmemberInsert(Connection con, Apply apply) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query ="insert into apply values (" + 
				"(select max(apply_no) from apply)+1, ?, ?, ?, sysdate, '0')";
		try {
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, apply.getMemberId());
			pstmt.setInt(2, apply.getClassNo());
			pstmt.setString(3, apply.getApplyNote());
			
			result = pstmt.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int memberapplyCheck(Connection con, int classNo, String memberId) {
		 int result = 0;
		 PreparedStatement pstmt = null;
		 ResultSet rset = null;
		 String query = "select count(*) from apply where class_no = ? and member_id = ?";
		 try {
			 pstmt = con.prepareStatement(query);
			 pstmt.setInt(1, classNo);
			 pstmt.setString(2, memberId);
			 rset = pstmt.executeQuery();
			 if(rset.next()) {
				 result=rset.getInt(1);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}
	public ArrayList<Apply> selectApply(Connection con, int currentPage, int limit, int classNo) {
		ArrayList<Apply> list = new ArrayList<Apply>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		

		String query = "select * from (select rownum rnum,apply_no, member_id, class_no, apply_note, apply_date, apply_status "+
		"from apply where class_no=? order by apply_no desc) where rnum >=? and rnum <= ?";
//		 
		int startRow = (currentPage-1)*limit +1;
		int endRow = startRow + limit-1;
		Apply apply = null;
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, classNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				apply = new Apply();
				apply.setApplyNo(rset.getInt("apply_no"));
				apply.setMemberId(rset.getString("member_id"));
				apply.setClassNo(rset.getInt("class_no"));;
				apply.setApplyNote(rset.getString("apply_note"));
				apply.setApplyDate(rset.getDate("apply_date"));
				apply.setApplyStatus(rset.getString("apply_status"));
				
				list.add(apply);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int getListCount(Connection con, int classNo) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select count(*) from apply where class_no=?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, classNo);
			
			rset = pstmt.executeQuery();
		
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	public int applyUpdate(Connection con, Apply apply, int appStatus) {
		
		int result = 0 ;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = null;
		
		if(appStatus == 1) {
		query ="update apply set apply_status = 1 where apply_no=?";
		}else {
		query ="update apply set apply_status = 2 where apply_no=?";		
		}
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, apply.getApplyNo());
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public Apply alarmapply(Connection con, int classNo) {
		Apply apply=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query=null;
		
		query="select a.member_id,b.class_title from class_member a,class b where a.class_no=? and a.class_no=b.class_no and a.class_king='Y'";
		
		
		
		try {
			pstmt=con.prepareStatement(query);
			
			pstmt.setInt(1, classNo);
				
			rset=pstmt.executeQuery();
			if(rset.next()) {
				apply=new Apply();
				
				
				apply.setMemberId(rset.getString(1));
				apply.setClassTitle(rset.getString(2));
				
			}
			
			
		} catch (Exception e) {
		e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
			
			
			
		}
		
		
		
		
		return apply;
	}
}