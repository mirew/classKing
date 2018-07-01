package comment.model.vo;

import java.sql.Date;

public class Comment implements java.io.Serializable{
	private final static long serialVersionUID = 4L;
	
	private int commentNo;
	private int boardNo;
	private String memberId;
	private Date commentDate;
	private String commentNote;
	private String classTitle;
	private int classNo;
	
	public Comment () {}

	public int getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}

	public int getClassNo() {
		return classNo;
	}

	public void setClassNo(int classNo) {
		this.classNo = classNo;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public String getCommentNote() {
		return commentNote;
	}

	public void setCommentNote(String commentNote) {
		this.commentNote = commentNote;
	}

	public String getClassTitle() {
		return classTitle;
	}

	public void setClassTitle(String classTitle) {
		this.classTitle = classTitle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
