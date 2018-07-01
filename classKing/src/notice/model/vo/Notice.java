package notice.model.vo;

import java.sql.Date;

public class Notice implements java.io.Serializable{
	private final static long serialVersionUID = 7L;
	
	private int noticeNo;
	private String noticeTitle;
	private String noticeNote;
	private Date noticeDate;
	private int noticeView;
	private String noticeOriginalFile;
	private String noticeRenameFile;
	
	public Notice() {}

	public Notice(int noticeNo, String noticeTitle, String noticeNote, Date noticeDate, int noticeView,
			String noticeOriginalFile, String noticeRenameFile) {
		super();
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
		this.noticeNote = noticeNote;
		this.noticeDate = noticeDate;
		this.noticeView = noticeView;
		this.noticeOriginalFile = noticeOriginalFile;
		this.noticeRenameFile = noticeRenameFile;
	}

	public int getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeNote() {
		return noticeNote;
	}

	public void setNoticeNote(String noticeNote) {
		this.noticeNote = noticeNote;
	}

	public Date getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	public int getNoticeView() {
		return noticeView;
	}

	public void setNoticeView(int noticeView) {
		this.noticeView = noticeView;
	}

	public String getNoticeOriginalFile() {
		return noticeOriginalFile;
	}

	public void setNoticeOriginalFile(String noticeOriginalFile) {
		this.noticeOriginalFile = noticeOriginalFile;
	}

	public String getNoticeRenameFile() {
		return noticeRenameFile;
	}

	public void setNoticeRenameFile(String noticeRenameFile) {
		this.noticeRenameFile = noticeRenameFile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}