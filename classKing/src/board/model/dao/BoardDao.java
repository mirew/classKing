package board.model.dao;

import static common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import board.model.vo.Board;
import classes.model.vo.Classes;

public class BoardDao {

	public ArrayList<Board> new3Board(Connection conn, int classNo) {
		ArrayList<Board> list = new ArrayList<Board>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM (SELECT b.board_no,cm.member_id,b.board_title,b.board_note,b.board_date "
				+ "FROM board b,class_member cm WHERE cm.class_no = ? and b.class_member_no = cm.class_member_no AND b.board_delete = 'N' ORDER BY b.board_no desc) "
				+ "WHERE rownum <= 3";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, classNo);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				Board board = new Board();
				board.setBoardNo(rset.getInt(1));
				board.setMemberId(rset.getString(2));
				board.setBoardTitle(rset.getString(3));
				board.setBoardNote(rset.getString(4));
				board.setBoardDate(rset.getDate(5));

				list.add(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int myGetListCount(Connection con, String memberId) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = "select count(*) from board b,class_member cm WHERE cm.member_id = ? and b.class_member_no = cm.class_member_no ";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, memberId);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rset);
		}

		return listCount;
	}

	public ArrayList<Board> myBoardList(Connection conn, String memberId, int currentPage, int limit) {
		ArrayList<Board> list = new ArrayList<Board>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from(" + "SELECT rownum rnum ,board_no, class_title, board_title, board_date,class_no FROM("
				+ "  SELECT  b.board_no, c.class_title, b.board_title, b.board_date,c.class_no "
				+ "  FROM board b,class c, class_member cm "
				+ "  WHERE cm.member_id = ? AND cm.class_no = c.class_no AND cm.class_member_no = b.class_member_no "
				+ "  ORDER BY b.board_no desc)) " + "WHERE rnum >= ? AND rnum <= ?";

		int startRow = (currentPage - 1) * limit + 1;
		int endRow = startRow + limit - 1;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				Board board = new Board();
				board.setBoardNo(rset.getInt(2));
				board.setClassTitle(rset.getString(3));
				board.setBoardTitle(rset.getString(4));
				board.setBoardDate(rset.getDate(5));
				board.setClassNo(rset.getInt(6));
				list.add(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public ArrayList<Board> recentNotice(Connection conn,int classNo) {
		ArrayList<Board> list = new ArrayList<Board>();
		PreparedStatement pstmt=null;
		ResultSet rset = null;
		String query ="select *from(select rownum rnum,board_date,board_title,board_no from "
	               + "(select * from board a,board_tag b,class c,class_member d where b.board_tag_no='01' "
	               + "and a.board_tag_no=b.board_tag_no and a.class_member_no=d.class_member_no "
	               + "and c.class_no=d.class_no and a.board_delete like 'N' and c.class_no=? order by a.board_date desc)) where rnum>=1 and rnum<=5";
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, classNo);
			rset=pstmt.executeQuery();
			while (rset.next()) {
				Board b = new Board();
				b.setBoardDate(rset.getDate("board_date"));
				b.setBoardTitle(rset.getString("board_title"));
				b.setBoardNo(rset.getInt("board_no"));
				list.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int getListCount(Connection con, int classNo) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
	//	String query = "select count(board_no) from (select * from board b,class_member c where b.class_member_no = c.class_member_no and c.class_no=?)";
	String query = "select count(*) from (select rownum rnum, board_note, board_no, board_title, member_id, board_date, "
			+"board_view, board_original_file, board_rename_file , board_delete, class_no , board_tag_name from (select * from class_member c,"
			 +"board b, board_tag bt where c.class_member_no= b.class_member_no and b.board_delete='N' and c.class_no=? and "
			 +"b.board_tag_no = bt.board_tag_no order by board_no desc, board_date desc ))";	
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

	public int insertBoard(Connection con, Board board) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "insert into board values ((select max(board_no)+1 from board),"
				+"(select class_member_no from class_member where member_id=? and class_no=?),?,?,sysdate,?,default,?,?,default)" ;
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, board.getMemberId());
			pstmt.setInt(2, board.getClassNo());
			pstmt.setString(3, board.getBoardTitle());
			pstmt.setString(4, board.getBoardNote());
			pstmt.setString(5, board.getBoardTagNo());
			pstmt.setString(6, board.getBoardOriginalFile());
			pstmt.setString(7, board.getBoardRenameFile());
			
			result = pstmt.executeUpdate();
			
			
		}catch(Exception e ) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public ArrayList<Board> selectList(Connection con, int currentPage, int limit, int classNo) {
		ArrayList<Board> list = new ArrayList<Board>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * from (select rownum rnum, board_note, board_no, board_title, member_id, board_date, "
				+"board_view, board_original_file, board_rename_file , board_delete, class_no , board_tag_name from (select * from class_member c,"
				 +"board b, board_tag bt where c.class_member_no= b.class_member_no and b.board_delete='N' and c.class_no=? and "
				 +"b.board_tag_no = bt.board_tag_no order by board_no desc, board_date desc )) where rnum >=? and rnum <= ?";

	
		int startRow = (currentPage-1)*limit +1;
		int endRow = startRow + limit -1;
		Board board= null;
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, classNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			

			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				board = new Board();
				board.setBoardNo(rset.getInt("board_no"));
				board.setClassNo(rset.getInt("class_no"));
				board.setBoardTagName(rset.getString("board_tag_name"));
				board.setBoardTitle(rset.getString("board_title"));
				board.setBoardNote(rset.getString("board_note"));
				board.setMemberId(rset.getString("member_id"));
				board.setBoardDate(rset.getDate("board_date"));
				board.setBoardView(rset.getInt("board_view"));
				board.setBoardOriginalFile(rset.getString("BOARD_ORIGINAL_FILE"));
				board.setBoardRenameFile(rset.getString("BOARD_RENAME_FILE"));
				board.setBoardDelete(rset.getString("board_delete"));
				
				list.add(board);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public Board selectBoard(Connection con, int boardNo) {
		Board board = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * from board, class_member,board_tag where board_no = ? and board.class_member_no=class_member.class_member_no and board_tag.board_tag_no=board.board_tag_no";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				board = new Board();
				board.setMemberId(rset.getString("member_id"));
				board.setBoardNo(rset.getInt("board_no"));
				board.setClassMemberNo(rset.getInt("class_member_no"));
				board.setBoardTitle(rset.getString("board_title"));
				board.setBoardNote(rset.getString("board_note"));
				board.setBoardDate(rset.getDate("board_date"));
				board.setBoardTagNo(rset.getString("board_tag_no"));
				board.setBoardTagName(rset.getString("board_tag_name"));
				board.setBoardView(rset.getInt("board_view"));
				board.setBoardOriginalFile(rset.getString("board_original_file"));
				board.setBoardRenameFile(rset.getString("board_rename_file"));

			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return board;
	}

	public int updateBoard(Connection con, Board board) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query ="update board set board_title=?,board_tag_no=?, board_note=?, board_original_file=?, board_rename_file=? where board_no=?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(2, board.getBoardTagNo());
			pstmt.setString(3, board.getBoardNote());
			pstmt.setString(4, board.getBoardOriginalFile());
			pstmt.setString(5, board.getBoardRenameFile());
			pstmt.setInt(6, board.getBoardNo());
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int deleteBoard(Connection con, int boardNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query= "update board set board_delete='Y' where board_no=?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int addReadCount(Connection con, int boardNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "update board set board_view = board_view +1 where board_no=?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int getSrchCount(Connection con, String b_keyword, String b_category, int classNo) {
		int listCount = 0;
		
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query = null;
		if (b_category.equals("제목")==true) {
	          // query = "select count(*) from (select * from board b, class_member c where c.class_member_no=b.class_member_no and c.class_no=?) where board_title like '%'||?||'%'";
	         query = "select count(*) from (select rownum rnum, board_note, board_no, board_title, member_id, board_date, "+
	               "board_view, board_original_file, board_rename_file , board_delete , class_no,board_tag_name from (select * from class_member c,"+
	               " board b,board_tag a where a.board_tag_no=b.board_tag_no and c.class_no = ? and c.class_member_no = b.class_member_no and b.board_delete='N' and b.board_title like '%'||?||'%' order by board_no desc))";
	    } else {
	          // query = "select count(*) from (select member_id from (select * from class_member c,board b where c.class_no =? and c.class_member_no = b.class_member_no) where member_id like '%'||?||'%')";
	          query = "select count(*) from (select rownum rnum, board_note, board_no, board_title, member_id, board_date, "+
	                  "board_view, board_original_file, board_rename_file , board_delete , class_no,board_tag_name from (select * from class_member c,"+
	                  " board b,board_tag a where a.board_tag_no=b.board_tag_no and c.class_no=? and c.class_member_no= b.class_member_no and b.board_delete='N' and member_id like '%'||?||'%' order by board_no desc))";
	    }
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, classNo);
			pstmt.setString(2, b_keyword);
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

	public ArrayList<Board> searchList(Connection con, int currentPage, int limit, String b_keyword,
			String b_category, int classNo) {
		ArrayList<Board> list=new ArrayList<Board>();
		PreparedStatement pstmt=null;
		ResultSet rset=null;

		String query = null;
		if (b_category.equals("제목")) {
	           query = "select * from (select rownum rnum, board_note, board_no, board_title, member_id, board_date, "+
	                  "board_view, board_original_file, board_rename_file , board_delete , class_no,board_tag_name from (select * from class_member c,"+
	                  " board b,board_tag a where a.board_tag_no=b.board_tag_no and c.class_no = ? and c.class_member_no = b.class_member_no and b.board_delete='N' and b.board_title like '%'||?||'%' order by board_no desc)) where rnum >=? and rnum <= ?";
	    } else {
	           query = "select * from (select rownum rnum, board_note, board_no, board_title, member_id, board_date, "+
	                   "board_view, board_original_file, board_rename_file , board_delete , class_no,board_tag_name from (select * from class_member c,"+
	                   " board b,board_tag a where a.board_tag_no=b.board_tag_no and c.class_no=? and c.class_member_no= b.class_member_no and b.board_delete='N' and member_id like '%'||?||'%' order by board_no desc)) where rnum >=? and rnum <= ?";
	    }   
		int startRow = (currentPage-1)*limit +1;
		int endRow = startRow +limit -1;
		int count=1;
		Board board = null;
		
		try {			
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, classNo);
			pstmt.setString(2, b_keyword);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			rset=pstmt.executeQuery();			
			
			while(rset.next()) {
				board = new Board();
				board.setBoardNo(rset.getInt("board_no"));
				board.setClassNo(rset.getInt("class_no"));
				board.setBoardTitle(rset.getString("board_title"));
				board.setBoardNote(rset.getString("board_note"));
				board.setMemberId(rset.getString("member_id"));
				board.setBoardDate(rset.getDate("board_date"));
				board.setBoardView(rset.getInt("board_view"));
				board.setBoardOriginalFile(rset.getString("BOARD_ORIGINAL_FILE"));
				board.setBoardRenameFile(rset.getString("BOARD_RENAME_FILE"));
				board.setBoardDelete(rset.getString("board_delete"));
				board.setBoardTagName(rset.getString("board_tag_name"));
				list.add(board);				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}		
		return list;
	}

	public ArrayList<Board> recentNotice(Connection con) {
		      ArrayList<Board> list = new ArrayList<Board>();
		      Statement stmt = null;
		      ResultSet rset = null;
		      String query = "select *from(select rownum rnum,board_date,board_title from "
		            + "(select * from board a,board_tag b where b.board_tag_no='01' "
		            + "and a.board_tag_no=b.board_tag_no order by a.board_date desc)) where rnum>=1 and rnum<=5";
		      try {
		         stmt = con.createStatement();

		         rset = stmt.executeQuery(query);

		         while (rset.next()) {
		            Board b = new Board();
		            b.setBoardDate(rset.getDate("board_date"));
		            b.setBoardTitle(rset.getString("board_title"));
		            list.add(b);
		         }

		      } catch (Exception e) {
		         e.printStackTrace();
		      } finally {
		         close(rset);
		         close(stmt);
		      }
		      return list;
	}
}
