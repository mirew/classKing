package chat.model.vo;

import java.sql.Date;

public class Chat implements java.io.Serializable{
	private final static long serialVersionUID = 20L;
	private int chatNo;
	private int classNo;
	private String memberId;
	private String memberId2;
	private String chatNote;
	private Date chatDate;
	public Chat() {}
	public Chat(int chatNo, int classNo, String memberId, String chatNote, Date chatDate) {
		super();
		this.chatNo = chatNo;
		this.classNo = classNo;
		this.memberId = memberId;
		this.chatNote = chatNote;
		this.chatDate = chatDate;
	}
	
	public String getMemberId2() {
		return memberId2;
	}
	public void setMemberId2(String memberId2) {
		this.memberId2 = memberId2;
	}
	public int getChatNo() {
		return chatNo;
	}
	public void setChatNo(int chatNo) {
		this.chatNo = chatNo;
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
	public String getChatNote() {
		return chatNote;
	}
	public void setChatNote(String chatNote) {
		this.chatNote = chatNote;
	}
	public Date getChatDate() {
		return chatDate;
	}
	public void setChatDate(Date chatDate) {
		this.chatDate = chatDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
