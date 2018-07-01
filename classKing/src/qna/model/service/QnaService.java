package qna.model.service;

import qna.model.dao.QnaDao;
import qna.model.vo.Qna;
import report.model.dao.ReportDao;
import report.model.vo.Report;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;


public class QnaService {

	public int insertQuestion(Qna qna) {
		Connection con=getConnection();
		
		int result=new QnaDao().insertQuestion(con,qna);
		
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		
		return result;
	}

	public int getListCount() {
		
		Connection con = getConnection();
		int listCount = new QnaDao().getListCount(con);

		close(con);

		return listCount;
		
		
		
		
		
	}

	public ArrayList<Qna> selectList(int currentPage, int limit) {
		Connection con=getConnection();
		ArrayList<Qna> qnalist=new QnaDao().selectList(con,currentPage,limit);
		
		close(con);
		
		return qnalist;
	}

	public void readCount(int qnaNum) {
		Connection con=getConnection();
		int result=new QnaDao().readCount(con,qnaNum);
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		
		return;
	}

	public Qna selectQna(int qnaNum) {
		Connection con=getConnection();
		Qna qna=new QnaDao().selectQna(con,qnaNum);
		close(con);
		
		
		return qna;
	}

	public int insertQna(int no, String qnaAnswer) {
		Connection con=getConnection();
		int result=new QnaDao().insertQna(no, qnaAnswer,con);
		
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}

	public int insertAnswer(int qnaNum,String qnaAnswer) {
		Connection con=getConnection();
		int result=new QnaDao().insertAnswer(con,qnaNum,qnaAnswer);
		
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}

	public Qna selectqna(int qnaNum) {
		Connection con=getConnection();
		
		Qna q=new QnaDao().selectqna(con,qnaNum);
		close(con);

		return q;
	}

	public int updateAsk(Qna q) {
		Connection con=getConnection();
		int result=new QnaDao().updateAsk(con,q);
		
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}

	public int updateAnswer(int qnaNum, String textArea) {
		Connection con=getConnection();
		int result=new QnaDao().updateAnswer(con,qnaNum,textArea);
			
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);

		return result;
	}

	public int deleteQna(int qnaNum) {
		Connection con=getConnection();
		int result=new QnaDao().deleteQna(con,qnaNum);
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		
		return result;
	}
	
	public int deleteAnswer(int qnaNum) {
		Connection con=getConnection();
		int result=new QnaDao().deleteAnswer(con,qnaNum);
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}
	

	public int getListSearhCount(String searchItem, String condition) {
		
		Connection con = getConnection();
		int listCount = new QnaDao().getListSearchCount(con,searchItem,condition);

		close(con);

		return listCount;
	}

	public ArrayList<Qna> searchList(int currentPage, int limit, String searchItem, String condition) {
		
		Connection con=getConnection();
		ArrayList<Qna> qnalist=new QnaDao().searchList(con,currentPage,limit,searchItem,condition);
		
		close(con);
		
		return qnalist;
	}

	public int myqListCount(String memberId) {
		
		Connection con=getConnection();
		int listCount = new QnaDao().myqlistCount(con,memberId);
		close(con);
		return listCount;
	}

	public ArrayList<Qna> myqList(String memberId, int currentPage, int limit) {
		
		Connection con = getConnection();
		ArrayList<Qna> list = new QnaDao().myqList(con,memberId,currentPage,limit);
		close(con);
		
		return list;
	}
}
