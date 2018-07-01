package classes.model.vo;

import java.sql.Date;

public class Classes implements java.io.Serializable{
	private final static long serialVersionUID = 2L;
	private int classNo;
	private String classTitle;
	private String classSubTitle;
	private String categoryNo;
	private String open;
	private String classOriginalImg;
	private String classRenameImg;
	private Date createDate;
	private String classClose;
	private int memberCount;
	private int rowNum;
	private String memberId;
	private String categoryName;
	
	public Classes() {}

	public Classes(int classNo, String classTitle, String classSubTitle, String categoryNo, String open,
			String classOriginalImg, String classRenameImg, Date createDate, String classClose) {
		super();
		this.classNo = classNo;
		this.classTitle = classTitle;
		this.classSubTitle = classSubTitle;
		this.categoryNo = categoryNo;
		this.open = open;
		this.classOriginalImg = classOriginalImg;
		this.classRenameImg = classRenameImg;
		this.createDate = createDate;
		this.classClose = classClose;
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public int getClassNo() {
		return classNo;
	}

	public void setClassNo(int classNo) {
		this.classNo = classNo;
	}

	public String getClassTitle() {
		return classTitle;
	}

	public void setClassTitle(String classTitle) {
		this.classTitle = classTitle;
	}

	public String getClassSubTitle() {
		return classSubTitle;
	}

	public void setClassSubTitle(String classSubTitle) {
		this.classSubTitle = classSubTitle;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getClassOriginalImg() {
		return classOriginalImg;
	}

	public void setClassOriginalImg(String classOriginalImg) {
		this.classOriginalImg = classOriginalImg;
	}

	public String getClassRenameImg() {
		return classRenameImg;
	}

	public void setClassRenameImg(String classRenameImg) {
		this.classRenameImg = classRenameImg;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getClassClose() {
		return classClose;
	}

	public void setClassClose(String classClose) {
		this.classClose = classClose;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return classNo + ", " + classTitle + ", " + classSubTitle
				+ ", " + categoryNo + ", " + open + ", " + classOriginalImg
				+ ", " + classRenameImg + ", " + createDate + ", " + classClose;
	}
}
