package report.model.vo;

import java.sql.Date;

public class Report implements java.io.Serializable {
	private final static long serialVersionUID = 5L;

	private int reportNo;
	private String memberId;
	private String itemNo;
	private String categoryNo;
	private String reportNote;
	private Date reportDate;
	private String reportOriginFile;
	private String reportRenameFile;
	private String reportFake;
	private String className;
	private String categoryNote;
	private int rowNum;
	private String commentsNote;
	private String boardTitle;
	private int boardNo;
	private String classNo;
	private String commentsNo;
	
	public Report() {
	};

	public Report(int reportNo, String memberId, String itemNo, String categoryNo, String reportNote, Date reportDate,
			String reportOriginFile, String reportRenameFile, String reportFake, String className) {
		super();
		this.reportNo = reportNo;
		this.memberId = memberId;
		this.itemNo = itemNo;
		this.categoryNo = categoryNo;
		this.reportNote = reportNote;
		this.reportDate = reportDate;
		this.reportOriginFile = reportOriginFile;
		this.reportRenameFile = reportRenameFile;
		this.reportFake = reportFake;
		this.className = className;
	}

	
	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getCommentsNote() {
		return commentsNote;
	}

	public void setCommentsNote(String commentsNote) {
		this.commentsNote = commentsNote;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getCommentsNo() {
		return commentsNo;
	}

	public void setCommentsNo(String commentsNo) {
		this.commentsNo = commentsNo;
	}

	public String getCategoryNote() {
		return categoryNote;
	}

	public void setCategoryNote(String categoryNote) {
		this.categoryNote = categoryNote;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getReportNo() {
		return reportNo;
	}

	public void setReportNo(int reportNo) {
		this.reportNo = reportNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getReportNote() {
		return reportNote;
	}

	public void setReportNote(String reportNote) {
		this.reportNote = reportNote;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getReportOriginFile() {
		return reportOriginFile;
	}

	public void setReportOriginFile(String reportOriginFile) {
		this.reportOriginFile = reportOriginFile;
	}

	public String getReportRenameFile() {
		return reportRenameFile;
	}

	public void setReportRenameFile(String reportRenameFile) {
		this.reportRenameFile = reportRenameFile;
	}

	public String getReportFake() {
		return reportFake;
	}

	public void setReportFake(String reportFake) {
		this.reportFake = reportFake;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {

		return this.reportNo + "," + this.memberId + "," + this.itemNo + "," + this.categoryNo + "," + this.reportNote
				+ "," + this.reportDate + "," + this.reportOriginFile + "," + this.reportRenameFile + ","
				+ this.reportFake + this.className;

	}

}
