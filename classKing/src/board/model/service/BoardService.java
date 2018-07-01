package board.model.service;

import static common.JDBCTemplate.*;
import java.sql.Connection;
import java.util.ArrayList;

import board.model.dao.BoardDao;
import board.model.vo.Board;
import classes.model.dao.ClassesDao;
import classes.model.vo.Classes;

public class BoardService {

	public ArrayList<Board> new3Board(int classNo) {
		Connection conn = getConnection();
		ArrayList<Board> list = new BoardDao().new3Board(conn,classNo);
		close(conn);
		
		return list;
	}

	public int myGetListCount(String memberId) {
		Connection con=getConnection();
		int listCount = new BoardDao().myGetListCount(con,memberId);
		close(con);
		return listCount;
	}

	public ArrayList<Board> myBoardList(String memberId, int currentPage, int limit) {
		Connection conn = getConnection();
		ArrayList<Board> list = new BoardDao().myBoardList(conn,memberId,currentPage,limit);
		close(conn);
		
		return list;
	}
	public ArrayList<Board> recentNotice(int classNo) {
		Connection con = getConnection();
		ArrayList<Board> list = new BoardDao().recentNotice(con,classNo);
		close(con);

		return list;
	}
	public int insertBoard(Board board) {
		Connection con = getConnection();
		int result = new BoardDao().insertBoard(con, board);
		if(result > 0)
			commit(con);
		else
			rollback(con);
		close(con);
		return result;
	}

	public int getListCount(int classNo) {
		Connection con = getConnection();
		int listCount = new BoardDao().getListCount(con,classNo);
		close(con);
		return listCount;
	}

	public ArrayList<Board> selectList(int currentPage, int limit, int classNo) {
		Connection con = getConnection();
		ArrayList<Board> list = new BoardDao().selectList(con,currentPage,limit,classNo);
		close(con);
		return list;
	}

	public Board selectBoard(int boardNo) {
		Connection con = getConnection();
		Board board = new BoardDao().selectBoard(con, boardNo);
		close(con);
		return board;
	}

	public void addViewCount(int boardNo) {
		Connection con = getConnection();
		int result = new BoardDao().addReadCount(con, boardNo);
		if(result > 0)
			commit(con);
		else
			rollback(con);
		close(con);
		return;
		
	}

	public int updateBoard(Board board) {
		Connection con = getConnection();
		int result = new BoardDao().updateBoard(con,board);
		if(result > 0)
			commit(con);
		else
			rollback(con);
		close(con);
		return result;
	}

	public int deleteBoard(int boardNo) {
		Connection con = getConnection();
		int result = new BoardDao().deleteBoard(con, boardNo);
		if(result > 0)
			commit(con);
		else
			rollback(con);
		close(con);
		return result;
	}
	public int getSrchCount(String b_keyword, String b_category,int classNo) {
		Connection con=getConnection();
		int listCount = new BoardDao().getSrchCount(con,b_keyword,b_category,classNo);
		close(con);
		return listCount;
	}

	public ArrayList<Board> serchList(int currentPage, int limit, String b_keyword, String b_category,int classNo) {
		Connection con=getConnection();
		ArrayList<Board> list=new BoardDao().searchList(con,currentPage,limit,b_keyword,b_category,classNo);
		close(con);
		return list;
	}
}
