package comment.model.dao;

import static common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import comment.model.vo.Comment;

public class CommentDao {

	public int myGetListCount(Connection con, String memberId) {
		int listCount = 0;
		PreparedStatement pstmt=null;
		ResultSet rset=null;

		String query = "select count(*) from comments WHERE member_id = ?";

		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, memberId);
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				listCount=rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rset);			
		}
		return listCount;
	}

	public ArrayList<Comment> myCommentList(Connection conn, String memberId, int currentPage, int limit) {
		ArrayList<Comment> list = new ArrayList<Comment>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT *  FROM(" + 
				"  SELECT  rownum rnum, co.board_no, c.class_title, co.comments_note, co.comments_date,c.class_no " + 
				"  FROM comments co,class c,board b,class_member cm " + 
				"  WHERE co.member_id = ? AND b.board_no = co.board_no AND b.class_member_no = cm.class_member_no AND c.class_no = cm.class_no AND co.comments_delete = 'N' " + 
				"  ORDER BY co.comments_no desc) " + 
				"WHERE rnum >= ? AND rnum <= ? ";
		
		int startRow = (currentPage-1)*limit +1;
		int endRow = startRow +limit -1;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Comment comment = new Comment();
				comment.setBoardNo(rset.getInt(2));
				comment.setClassTitle(rset.getString(3));
				comment.setCommentNote(rset.getString(4));
				comment.setCommentDate(rset.getDate(5));
				comment.setClassNo(rset.getInt(6));
				
				list.add(comment);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	public int galCommentInsert(Connection con, int classNo, Comment galComment, int galNo) {
		int result = 0;		
		PreparedStatement pstmt = null;
		
		String query = "insert into gallery_comments values ("
				+ "(select max(comments_no) from gallery_comments)+1,?,?,sysdate,?)";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, galNo);			
			pstmt.setString(2, galComment.getMemberId());
			pstmt.setString(3, galComment.getCommentNote());
			
			result =pstmt.executeUpdate();
						
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public ArrayList<Comment> galCommentDetailList(Connection con, int galNo) {
		ArrayList<Comment> list = new ArrayList<Comment>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query ="select * from gallery_comments where gal_no = ? "
				+ "order by comments_no desc";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, galNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Comment c = new Comment();
				c.setCommentNo(rset.getInt("comments_no"));
				c.setMemberId(rset.getString("member_id"));
				c.setCommentDate(rset.getDate("comments_date"));
				c.setCommentNote(rset.getString("comments_note"));
				
				list.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int galCommentDelete(Connection con, int galNo, int commentNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "delete from gallery_comments where comments_No= ? "
				+ "and gal_No = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, commentNo);
			pstmt.setInt(2, galNo);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}


	public int galCommentUpdate(Connection con, int galNo, Comment comment) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query ="update gallery_comments set comments_note =? "
				+ "where gal_no = ? and comments_no = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, comment.getCommentNote());
			pstmt.setInt(2, galNo);
			pstmt.setInt(3, comment.getCommentNo());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public ArrayList<Comment> boardCommentDetailList(Connection con, int boardNo) {
		ArrayList<Comment> list = new ArrayList<Comment>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Comment c =null;
		String query ="select * from comments where board_no = ? and comments_delete ='N' order by comments_date desc";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				c = new Comment();
				c.setCommentNo(rset.getInt("comments_no"));
				c.setMemberId(rset.getString("member_id"));
				c.setBoardNo(rset.getInt("board_no"));
				c.setCommentDate(rset.getDate("comments_date"));
				c.setCommentNote(rset.getString("comments_note"));
			
				list.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int insertComment(Connection con, Comment c) {
		int result = 0;
		PreparedStatement pstmt = null;
		
	/*	COMMENTS_NO	NUMBER
BOARD_NO	NUMBER
MEMBER_ID	VARCHAR2(20 BYTE)
COMMENTS_DATE	DATE
COMMENTS_NOTE	VARCHAR2(300 BYTE)*/
		
		String query = "insert into comments values((select max(comments_no)+1 from comments),?,?,sysdate,?,default)";
		
		try {
	
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, c.getBoardNo());
			pstmt.setString(2, c.getMemberId());
			pstmt.setString(3, c.getCommentNote());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int boardCommentUpdate(Connection con, int boardNo, Comment comment) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		String query ="update comments set comments_note =? "
				+ "where board_no = ? and comments_no = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, comment.getCommentNote());
			pstmt.setInt(2, boardNo);
			pstmt.setInt(3, comment.getCommentNo());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int boardCommentDelete(Connection con, int commentNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "update comments set comments_delete ='Y' where comments_no = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, commentNo);
			
			result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int galComAllDel(Connection con, int galNo) {
		
		int ComAllDel = 0;
		PreparedStatement pstmt = null;
		
		String query = " delete from gallery_comments where gal_no = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, galNo);
			
			ComAllDel = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return ComAllDel;
	}
}
