package calendar.model.vo;

import java.sql.Date;

public class Calendar implements java.io.Serializable{
	private final static long serialVersionUID = 1L;
	private int calNo;
	private int classMemberNo;
	private String calTitle;
	private String calNote;
	private Date calSdate;
	private Date calEdate;
	private int boardNo;
	private String memberId;
	private int classNo;
	
	public Calendar() {}

	public Calendar(int calNo, int classMemberNo, String calTitle, String calNote, Date calSdate, Date calEdate,
			int boardNo) {
		super();
		this.calNo = calNo;
		this.classMemberNo = classMemberNo;
		this.calTitle = calTitle;
		this.calNote = calNote;
		this.calSdate = calSdate;
		this.calEdate = calEdate;
		this.boardNo = boardNo;
	}

	
	public int getClassNo() {
		return classNo;
	}

	public void setClassNo(int classNo) {
		this.classNo = classNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getCalNo() {
		return calNo;
	}

	public void setCalNo(int calNo) {
		this.calNo = calNo;
	}

	public int getClassMemberNo() {
		return classMemberNo;
	}

	public void setClassMemberNo(int classMemberNo) {
		this.classMemberNo = classMemberNo;
	}

	public String getCalTitle() {
		return calTitle;
	}

	public void setCalTitle(String calTitle) {
		this.calTitle = calTitle;
	}

	public String getCalNote() {
		return calNote;
	}

	public void setCalNote(String calNote) {
		this.calNote = calNote;
	}

	public Date getCalSdate() {
		return calSdate;
	}

	public void setCalSdate(Date calSdate) {
		this.calSdate = calSdate;
	}

	public Date getCalEdate() {
		return calEdate;
	}

	public void setCalEdate(Date calEdate) {
		this.calEdate = calEdate;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return calNo + ", " + classMemberNo + ", " + calTitle
				+ ", " + calNote + ", " + calSdate + ", " + calEdate + ", " + boardNo;
	}
}