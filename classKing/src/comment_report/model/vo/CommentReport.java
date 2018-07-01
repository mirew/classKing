package comment_report.model.vo;

import java.sql.Date;

public class CommentReport implements java.io.Serializable{
		private final static long serialVersionUID = 5L;
		
//		REPORT_NO	NUMBER
//		MEMBER_ID	VARCHAR2(20 BYTE)
//		COMMENTS_NO	NUMBER
//		REPORT_CATEGORY_NO	CHAR(2 BYTE)
//		REPORT_NOTE	VARCHAR2(300 BYTE)
//		REPORT_DATE	DATE
//		REPORT_ORIGINAL_FILENAME	VARCHAR2(30 BYTE)
//		REPORT_RENAME_FILENAME	VARCHAR2(30 BYTE)
//		REPORT_FAKE	CHAR(1 BYTE)
		
		private int reportNo;
		private String memberId;
		private int commentNo;
		private String reportCategoryNo;
		private String repoartCategoryNote;
		private String reportNote;
		private Date reportDate;
		private String reportOriginalFile;
		private String reportRenameFile;
		private String reportFake;
		
		public CommentReport() {}
		

		public CommentReport(int reportNo, String memberId, int commentNo, String reportCategoryNo, String repoartCategoryNote,String reportNote,
				Date reportDate, String reportOriginalFile, String reportRenameFile, String reportFake) {
			super();
			this.reportNo = reportNo;
			this.memberId = memberId;
			this.commentNo = commentNo;
			this.reportCategoryNo = reportCategoryNo;
			this.repoartCategoryNote = repoartCategoryNote;
			this.reportNote = reportNote;
			this.reportDate = reportDate;
			this.reportOriginalFile = reportOriginalFile;
			this.reportRenameFile = reportRenameFile;
			this.reportFake = reportFake;
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

		
		public int getCommentNo() {
			return commentNo;
		}


		public void setCommentNo(int commentNo) {
			this.commentNo = commentNo;
		}


		public String getReportCategoryNo() {
			return reportCategoryNo;
		}

		public void setReportCategoryNo(String reportCategoryNo) {
			this.reportCategoryNo = reportCategoryNo;
		}

		public String getReportNote() {
			return reportNote;
		}
		

		public String getRepoartCategoryNote() {
			return repoartCategoryNote;
		}


		public void setRepoartCategoryNote(String repoartCategoryNote) {
			this.repoartCategoryNote = repoartCategoryNote;
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

		public String getReportOriginalFile() {
			return reportOriginalFile;
		}

		public void setReportOriginalFile(String reportOriginalFile) {
			this.reportOriginalFile = reportOriginalFile;
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
		
	

}
