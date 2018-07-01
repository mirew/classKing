package apply.model.vo;

import java.sql.Date;

public class Apply implements java.io.Serializable{
	private final static long serialVersionUID = 11L;
	
	private int applyNo;
	private String memberId;	
	private int classNo;
	private String applyNote;
	private	Date applyDate;
	private String applyStatus;
	private String classKing;
	private String classTitle;
	public Apply(){}	
	
	
	public Apply(int applyNo, String memberId, int classNo, String applyNote, Date applyDate, String applyStatus) {
		super();
		this.applyNo = applyNo;
		this.memberId = memberId;
		this.classNo = classNo;
		this.applyNote = applyNote;
		this.applyDate = applyDate;
		this.applyStatus = applyStatus;
	}


	public int getApplyNo() {
		return applyNo;
	}


	public String getClassKing() {
		return classKing;
	}


	public void setClassKing(String classKing) {
		this.classKing = classKing;
	}


	public String getClassTitle() {
		return classTitle;
	}


	public void setClassTitle(String classTitle) {
		this.classTitle = classTitle;
	}


	public void setApplyNo(int applyNo) {
		this.applyNo = applyNo;
	}


	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public int getClassNo() {
		return classNo;
	}


	public void setClassNo(int classNo) {
		this.classNo = classNo;
	}


	public String getApplyNote() {
		return applyNote;
	}


	public void setApplyNote(String applyNote) {
		this.applyNote = applyNote;
	}


	public Date getApplyDate() {
		return applyDate;
	}


	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}


	public String getApplyStatus() {
		return applyStatus;
	}


	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}