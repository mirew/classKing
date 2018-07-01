package member.model.vo;

import java.sql.Date;

public class Member implements java.io.Serializable{
	private final static long serialVersionUID = 8L;
	private String memberId;
	private String memberPwd;
	private String memberName;
	private String memberEmail;
	private String memberBirthday;
	private String question;
	private String answer;
	private Date joinDate;
	private String memberOriginalImg;
	private String memberRenameImg;
	private String memberNickname;
	private String memberOut;
	private int rowNum;
	private int reportNum;
	public Member() {}

	public Member(String memberId, String memberPwd, String memberName, String memberEmail, String memberBirthday, String question,
			String answer, Date joinDate, String memberOriginalImg, String memberRenameImg, String memberNickname,
			String memberOut) {
		super();
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.memberName = memberName;
		this.memberEmail = memberEmail;
		this.memberBirthday = memberBirthday;
		this.question = question;
		this.answer = answer;
		this.joinDate = joinDate;
		this.memberOriginalImg = memberOriginalImg;
		this.memberRenameImg = memberRenameImg;
		this.memberNickname = memberNickname;
		this.memberOut = memberOut;
	}
	
	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getReportNum() {
		return reportNum;
	}

	public void setReportNum(int reportNum) {
		this.reportNum = reportNum;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPwd() {
		return memberPwd;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String email) {
		this.memberEmail = email;
	}

	public String getMemberBirthday() {
		return memberBirthday;
	}

	public void setMemberBirthday(String birthday) {
		this.memberBirthday = birthday;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getMemberOriginalImg() {
		return memberOriginalImg;
	}

	public void setMemberOriginalImg(String memberOriginalImg) {
		this.memberOriginalImg = memberOriginalImg;
	}

	public String getMemberRenameImg() {
		return memberRenameImg;
	}

	public void setMemberRenameImg(String memberRenameImg) {
		this.memberRenameImg = memberRenameImg;
	}

	public String getMemberNickname() {
		return memberNickname;
	}

	public void setMemberNickname(String memberNickname) {
		this.memberNickname = memberNickname;
	}

	public String getMemberOut() {
		return memberOut;
	}

	public void setMemberOut(String memberOut) {
		this.memberOut = memberOut;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
