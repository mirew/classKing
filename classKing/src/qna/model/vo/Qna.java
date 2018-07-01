package qna.model.vo;

import java.sql.Date;

public class Qna implements java.io.Serializable {
	private final static long serialVersionUID = 6L;

	private int qnaNo;
	private String qnaWriter;
	private String qnaTitle;
	private String qnaNote;
	private Date qnaDate;
	private int qnaView;
	private String qnaOriginalFile;
	private String qnaRenameFile;
	private String qnaAnswer;
	private Date answerDate;

	public Qna() {
	};

	public Qna(int qnaNo, String qnaWriter, String qnaTitle, String qnaNote, Date qnaDate, int qnaView,
			String qnaOriginalFile, String qnaRenameFile, String qnaAnswer, Date answerDate) {
		super();
		this.qnaNo = qnaNo;
		this.qnaWriter = qnaWriter;
		this.qnaTitle = qnaTitle;
		this.qnaNote = qnaNote;
		this.qnaDate = qnaDate;
		this.qnaView = qnaView;
		this.qnaOriginalFile = qnaOriginalFile;
		this.qnaRenameFile = qnaRenameFile;
		this.qnaAnswer = qnaAnswer;
		this.answerDate = answerDate;
	}

	public int getQnaNo() {
		return qnaNo;
	}

	public void setQnaNo(int qnaNo) {
		this.qnaNo = qnaNo;
	}

	public String getQnaWriter() {
		return qnaWriter;
	}

	public void setQnaWriter(String qnaWriter) {
		this.qnaWriter = qnaWriter;
	}

	public String getQnaTitle() {
		return qnaTitle;
	}

	public void setQnaTitle(String qnaTitle) {
		this.qnaTitle = qnaTitle;
	}

	public String getQnaNote() {
		return qnaNote;
	}

	public void setQnaNote(String qnaNote) {
		this.qnaNote = qnaNote;
	}

	public Date getQnaDate() {
		return qnaDate;
	}

	public void setQnaDate(Date qnaDate) {
		this.qnaDate = qnaDate;
	}

	public int getQnaView() {
		return qnaView;
	}

	public void setQnaView(int qnaView) {
		this.qnaView = qnaView;
	}

	public String getQnaOriginalFile() {
		return qnaOriginalFile;
	}

	public void setQnaOriginalFile(String qnaOriginalFile) {
		this.qnaOriginalFile = qnaOriginalFile;
	}

	public String getQnaRenameFile() {
		return qnaRenameFile;
	}

	public void setQnaRenameFile(String qnaRenameFile) {
		this.qnaRenameFile = qnaRenameFile;
	}

	public String getQnaAnswer() {
		return qnaAnswer;
	}

	public void setQnaAnswer(String qnaAnswer) {
		this.qnaAnswer = qnaAnswer;
	}

	public Date getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.qnaNo + "," + this.qnaWriter + "," + this.qnaTitle + "," + this.qnaNote + "," + this.qnaDate + ","
				+ this.qnaView + "," + this.qnaOriginalFile + "," + this.qnaRenameFile + "," + this.qnaAnswer + ","
				+ this.answerDate;
	}

}
