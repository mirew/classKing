package classesmember.model.vo;

import java.sql.Date;

public class ClassesMember implements java.io.Serializable{
	private static final long SerialVersionUID = 13L;
	
	private int classMemberNo;
	private int classNo;
	private String memberId;
	private String classKing;
	private Date classJoinDate;
	private String classMemberLeave;
	private Date latestDate;
	private String memberRenameImg;
	private String classTitle;
	
	public ClassesMember() {}
	public ClassesMember(int classMemberNo, int classNo, String memberId, String classKing, Date classJoinDate,
			String classMemberLeave) {
		super();
		this.classMemberNo = classMemberNo;
		this.classNo = classNo;
		this.memberId = memberId;
		this.classKing = classKing;
		this.classJoinDate = classJoinDate;
		this.classMemberLeave = classMemberLeave;
	}
	
	public String getClassTitle() {
		return classTitle;
	}
	public void setClassTitle(String classTitle) {
		this.classTitle = classTitle;
	}
	public String getMemberRenameImg() {
		return memberRenameImg;
	}
	public void setMemberRenameImg(String memberRenameImg) {
		this.memberRenameImg = memberRenameImg;
	}
	public Date getLatestDate() {
		return latestDate;
	}
	public void setLatestDate(Date latestDate) {
		this.latestDate = latestDate;
	}
	public int getClassMemberNo() {
		return classMemberNo;
	}
	public void setClassMemberNo(int classMemberNo) {
		this.classMemberNo = classMemberNo;
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
	public String getClassKing() {
		return classKing;
	}
	public void setClassKing(String classKing) {
		this.classKing = classKing;
	}
	public Date getClassJoinDate() {
		return classJoinDate;
	}
	public void setClassJoinDate(Date classJoinDate) {
		this.classJoinDate = classJoinDate;
	}
	public String getClassMemberLeave() {
		return classMemberLeave;
	}
	public void setClassMemberLeave(String classMemberLeave) {
		this.classMemberLeave = classMemberLeave;
	}
	public static long getSerialversionuid() {
		return SerialVersionUID;
	}
	
	
}
