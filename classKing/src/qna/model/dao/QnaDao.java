package qna.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import qna.model.vo.Qna;


import static common.JDBCTemplate.*;

public class QnaDao {

	public int insertQuestion(Connection con, Qna qna) {
		int result = 0;
		PreparedStatement pstmt = null;
		
				
		
		String query = "insert into qna values((select max(qna_no) from qna)+1,?,?,?,sysdate,default,?,?,null,null)";

		try {
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, qna.getQnaWriter());
			pstmt.setString(2, qna.getQnaTitle());
			pstmt.setString(3, qna.getQnaNote());
			pstmt.setString(4, qna.getQnaOriginalFile());
			pstmt.setString(5, qna.getQnaRenameFile());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int getListCount(Connection con) {
		int listCount = 0;
		Statement stmt = null;
		ResultSet rset = null;

		String query = "select count(*) from qna";

		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			if (rset.next()) {
				listCount = rset.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}

	public ArrayList<Qna> selectList(Connection con, int currentPage, int limit) {
		ArrayList<Qna> qnalist = new ArrayList<Qna>();

		PreparedStatement pstmt = null;
		ResultSet rset = null;

		int startRow = (currentPage - 1) * limit + 1;
		int endRow = startRow + limit - 1;

		String query = "select * from (select rownum rnum,qna_no,qna_writer,qna_title,qna_note,qna_date,qna_view,qna_original_file,qna_rename_file,qna_answer,answer_date"
				+ " from(select * from qna order by qna_no desc)) where rnum>=? and rnum<=?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);

			rset = pstmt.executeQuery();

			while (rset.next()) {

				Qna q = new Qna();
				q.setQnaNo(rset.getInt("qna_no"));
				q.setQnaTitle(rset.getString("qna_title"));
				q.setQnaDate(rset.getDate("qna_date"));
				q.setQnaWriter(rset.getString("qna_writer"));
				q.setQnaView(rset.getInt("qna_view"));
				q.setQnaAnswer(rset.getString("qna_answer"));
				q.setAnswerDate((rset.getDate("answer_date")));
				q.setQnaNote((rset.getString("qna_note")));
				q.setQnaAnswer((rset.getString("qna_answer")));
				qnalist.add(q);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rset);
		}
		return qnalist;
	}

	public int readCount(Connection con, int qnaNum) {
		int result = 0;
		PreparedStatement pstmt = null;

		String query = "update Qna set qna_view =qna_view+1 where qna_no=?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, qnaNum);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public Qna selectQna(Connection con, int qnaNum) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Qna qna = null;
		String query = "select * from qna where qna_no=?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, qnaNum);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				qna = new Qna();
				qna.setQnaNo(rset.getInt("qna_no"));
				qna.setQnaWriter(rset.getString("qna_writer"));
				qna.setQnaTitle(rset.getString("qna_title"));
				qna.setQnaNote(rset.getString("qna_note"));
				qna.setQnaDate(rset.getDate("qna_date"));
				qna.setQnaView(rset.getInt("qna_view"));
				qna.setQnaOriginalFile(rset.getString("qna_original_file"));
				qna.setQnaRenameFile(rset.getString("qna_rename_file"));
				qna.setQnaAnswer(rset.getString("qna_answer"));
				qna.setAnswerDate(rset.getDate("answer_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return qna;
	}

	public int insertQna(int no, String qnaAnswer, Connection con) {
		int result = 0;
		PreparedStatement pstmt = null;

		String query = "update qna set qna_answer=?,answer_date=sysdate where qna_no=? ";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, qnaAnswer);
			pstmt.setInt(2, no);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int insertAnswer(Connection con, int qnaNum, String qnaAnswer) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = "update qna set qna_Answer=?,answer_date=sysdate where qna_no=?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, qnaAnswer);
			pstmt.setInt(2, qnaNum);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public Qna selectqna(Connection con, int qnaNum) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Qna qna = null;
		String query = "select * from qna where qna_no=?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, qnaNum);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				qna = new Qna();
				qna.setQnaNo(rset.getInt("qna_no"));
				qna.setQnaWriter(rset.getString("qna_writer"));
				qna.setQnaTitle(rset.getString("qna_title"));
				qna.setQnaNote(rset.getString("qna_note"));
				qna.setQnaDate(rset.getDate("qna_date"));
				qna.setQnaView(rset.getInt("qna_view"));
				qna.setQnaOriginalFile(rset.getString("qna_original_file"));
				qna.setQnaRenameFile(rset.getString("qna_rename_file"));
				qna.setQnaAnswer(rset.getString("qna_answer"));
				qna.setAnswerDate(rset.getDate("answer_date"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return qna;
	}

	public int updateAsk(Connection con, Qna q) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = null;
		if (q.getQnaOriginalFile() != null) {
			query = "update qna set qna_title=?,qna_note=?,qna_original_file=?,qna_rename_file=? where qna_no=?";
		} else {
			query = "update qna set qna_title=?,qna_note=? where qna_no=?";
		}
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, q.getQnaTitle());
			pstmt.setString(2, q.getQnaNote());
			if (q.getQnaOriginalFile() != null) {
				pstmt.setString(3, q.getQnaOriginalFile());
				pstmt.setString(4, q.getQnaRenameFile());
				pstmt.setInt(5, q.getQnaNo());
			} else {
				pstmt.setInt(3, q.getQnaNo());
			}
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updateAnswer(Connection con, int qnaNum, String textArea) {
		int result = 0;
		PreparedStatement pstmt = null;

		String query = "update qna set qna_answer=?,answer_date=sysdate where qna_no=?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, textArea);
			pstmt.setInt(2, qnaNum);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int deleteQna(Connection con, int qnaNum) {
		int result = 0;
		PreparedStatement pstmt = null;

		String query = "delete from qna where qna_no=?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, qnaNum);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int deleteAnswer(Connection con, int qnaNum) {
		int result = 0;
		PreparedStatement pstmt = null;

		String query = "update qna set qna_answer=null where qna_no=?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, qnaNum);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int getListSearchCount(Connection con, String searchItem, String condition) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = null;
		if (condition.equals("제목")) {

			query = "select count(*) from qna where qna_title like '%'||?||'%'";
		} else {

			query = "select count(*) from qna where qna_writer like '%'||?||'%'";

		}
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, searchItem);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	public ArrayList<Qna> searchList(Connection con, int currentPage, int limit, String searchItem, String condition) {

		ArrayList<Qna> qnalist = new ArrayList<Qna>();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = null;
		int startRow = (currentPage - 1) * limit + 1;
		int endRow = startRow + limit - 1;
		if (condition.equals("제목")) {
			query = "select * from (select rownum rnum,qna_no,qna_writer,qna_title,qna_note,qna_date,qna_view,qna_original_file,qna_rename_file,qna_answer,answer_date"
					+ " from(select * from qna where qna_title like '%'||?||'%' order by qna_no desc)) where rnum>=? and rnum<=?";
		} else {
			query = "select * from (select rownum rnum,qna_no,qna_writer,qna_title,qna_note,qna_date,qna_view,qna_original_file,qna_rename_file,qna_answer,answer_date"
					+ " from(select * from qna where qna_writer like '%'||?||'%' order by qna_no desc)) where rnum>=? and rnum<=?";
		}
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, searchItem);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				Qna q = new Qna();
				q.setQnaNo(rset.getInt("qna_no"));
				q.setQnaTitle(rset.getString("qna_title"));
				q.setQnaDate(rset.getDate("qna_date"));
				q.setQnaWriter(rset.getString("qna_writer"));
				q.setQnaView(rset.getInt("qna_view"));
				q.setQnaAnswer(rset.getString("qna_answer"));
				q.setAnswerDate((rset.getDate("answer_date")));
				q.setQnaNote((rset.getString("qna_note")));
				q.setQnaAnswer((rset.getString("qna_answer")));
				qnalist.add(q);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rset);
		}
		return qnalist;
	}

	public int myqlistCount(Connection con, String memberId) {
		int listCount = 0;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query="select count(*) from qna a,member b where b.member_id=? and a.qna_writer=b.member_id";
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

	public ArrayList<Qna> myqList(Connection con, String memberId, int currentPage, int limit) {
		
		ArrayList<Qna> list = new ArrayList<Qna>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query ="select * from(select rownum rnum,a.qna_no,a.qna_title,a.qna_answer,a.qna_date from qna a,member b"
				+ " where a.qna_writer=? and a.qna_writer=b.member_id order by a.qna_no desc)where rnum>=? and rnum<=?";
		int startRow = (currentPage-1)*limit +1;
		int endRow = startRow +limit -1;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, memberId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Qna qna=new Qna();
				qna.setQnaNo(rset.getInt(2));
				qna.setQnaTitle(rset.getString(3));
				qna.setQnaAnswer(rset.getString(4));
				qna.setQnaDate(rset.getDate(5));
				list.add(qna);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
}