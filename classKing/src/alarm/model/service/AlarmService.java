package alarm.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

import java.sql.Connection;

import alarm.model.dao.AlarmDao;

import board.model.vo.Board;
import classes.model.vo.Classes;
import qna.model.vo.Qna;

public class AlarmService {

	public Board boardAlarm(Board board) {
		// TODO Auto-generated method stub
		return null;
	}

	public Classes checkClass(String id, int classNo) {
		Connection con = getConnection();

		Classes checkClass = new AlarmDao().checkClass(con, id, classNo);
		close(con);

		return checkClass;

	}

	public Qna checkQna(String id, int qnaNum) {
		Connection con = getConnection();
		Qna checkQna=new AlarmDao().checkQna(con,id,qnaNum);
		close(con);
		return checkQna;
	}



}
