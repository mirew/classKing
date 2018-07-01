package comment.model.service;

import static common.JDBCTemplate.*;


import java.sql.Connection;
import java.util.ArrayList;

import board.model.dao.BoardDao;
import board.model.vo.Board;
import comment.model.dao.CommentDao;
import comment.model.vo.Comment;

public class CommentService {

	public int myGetListCount(String memberId) {
		Connection con=getConnection();
		int listCount = new CommentDao().myGetListCount(con,memberId);
		close(con);
		return listCount;
	}

	public ArrayList<Comment> myCommentList(String memberId, int currentPage, int limit) {
		Connection conn = getConnection();
		ArrayList<Comment> list = new CommentDao().myCommentList(conn,memberId,currentPage,limit);
		close(conn);
		
		return list;
	}
	public int galCommentInsert(int classNo,Comment galComment, int galNo) {
		Connection con = getConnection();
		int result = new CommentDao().galCommentInsert(con,classNo,galComment,galNo);
		if(result > 0) {
			commit(con);
		}else {
			rollback(con);
		}	
		close(con);
		return result;
	}

	public ArrayList<Comment> galCommentDetailList(int galNo) {
		Connection con = getConnection();
		ArrayList<Comment> list = new CommentDao().galCommentDetailList(con,galNo);
		close(con);
		
		return list;
	}

	public int galCommentDelete(int galNo, int commentNo) {
		Connection con = getConnection();
		
		int result = new CommentDao().galCommentDelete(con, galNo,commentNo);
		
		if(result > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		return result;
	}


	public int galCommentUpdate(int galNo, Comment comment) {
		Connection con = getConnection();
		int result = new CommentDao().galCommentUpdate(con,galNo,comment);
		if(result > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		return result;
	}


	public int insertComment(Comment c) {
		Connection con = getConnection();
		int result = new CommentDao().insertComment(con, c);
		if(result > 0)
			commit(con);
		else
			rollback(con);
		close(con);
		return result;
	}

	public int boardCommentUpdate(int boardNo, Comment comment) {
		Connection con = getConnection();
		int result = new CommentDao().boardCommentUpdate(con,boardNo,comment);
		if(result > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		return result;
	}


	public int boardCommentDelete(int commentNo) {
		Connection con = getConnection();

		int result = new CommentDao().boardCommentDelete(con, commentNo);
		if (result > 0) {
			commit(con);

		} else {
			rollback(con);
		}
		return result;
	}

	public int classGalleryComAllDel(int galNo) {
		Connection con = getConnection();
		
		int ComAllDel = new CommentDao().galComAllDel(con,galNo) ;
		if(ComAllDel > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		return ComAllDel;
	}

	public ArrayList<Comment> boardCommnetDetailList(int boardNo) {
		Connection con = getConnection();
		ArrayList<Comment> list = new CommentDao().boardCommentDetailList(con,boardNo);
		close(con);
		
		return list;
		
	}
}
