package push.model.vo;

import java.sql.Date;

public class Push implements java.io.Serializable{

	private static final long serialVersionUID = 94L;
	
	private int pushNo;
	private int boardNo;
	private String commentsWriter;
	private String pushRead;
	private String classTitle;
	private int classNo;
	private Date pushDate;
	
	
	public Push() {}

	
	
	public Push(int pushNo, int boardNo, String commentsWriter, String pushRead, String classTitle, int classNo,
			Date pushDate) {
		super();
		this.pushNo = pushNo;
		this.boardNo = boardNo;
		this.commentsWriter = commentsWriter;
		this.pushRead = pushRead;
		this.classTitle = classTitle;
		this.classNo = classNo;
		this.pushDate = pushDate;
	}

	

	public int getPushNo() {
		return pushNo;
	}



	public void setPushNo(int pushNo) {
		this.pushNo = pushNo;
	}



	public int getBoardNo() {
		return boardNo;
	}



	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}



	public String getCommentsWriter() {
		return commentsWriter;
	}



	public void setCommentsWriter(String commentsWriter) {
		this.commentsWriter = commentsWriter;
	}



	public String getPushRead() {
		return pushRead;
	}



	public void setPushRead(String pushRead) {
		this.pushRead = pushRead;
	}



	public String getClassTitle() {
		return classTitle;
	}



	public void setClassTitle(String classTitle) {
		this.classTitle = classTitle;
	}



	public int getClassNo() {
		return classNo;
	}



	public void setClassNo(int classNo) {
		this.classNo = classNo;
	}



	public Date getPushDate() {
		return pushDate;
	}



	public void setPushDate(Date pushDate) {
		this.pushDate = pushDate;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public String toString() {
		return this.pushNo+","+this.boardNo+","+this.commentsWriter+","+this.pushRead+","+this.classTitle+","+this.pushDate;
	}

}
