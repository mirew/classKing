package board.model.vo;

import java.sql.Date;

public class Board implements java.io.Serializable{
	private final static long serialVersionUID = 3L;
	
	private int boardNo;
	private int classMemberNo;
	private String boardTitle;
	private String boardNote;
	private Date boardDate;
	private int boardLike;
	private String boardTagNo;
	private int boardView;
	private String boardOriginalFile;
	private String boardRenameFile;
	private String boardAlarm;
	private String memberId;
	private String classTitle;
	private int classNo;
	private String boardTagName;
	private String boardDelete;
	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Board() {}
	
	public Board(int boardNo, int classMemberNo, String boardTitle, String boardNote, Date boardDate, int boardLike,
			String boardTagNo, int boardView, String boardOriginalFile, String boardRenameFile, String boardAlarm) {
		super();
		this.boardNo = boardNo;
		this.classMemberNo = classMemberNo;
		this.boardTitle = boardTitle;
		this.boardNote = boardNote;
		this.boardDate = boardDate;
		this.boardLike = boardLike;
		this.boardTagNo = boardTagNo;
		this.boardView = boardView;
		this.boardOriginalFile = boardOriginalFile;
		this.boardRenameFile = boardRenameFile;
		this.boardAlarm = boardAlarm;
	}
	
	public String getBoardTagName() {
		return boardTagName;
	}

	public String getBoardDelete() {
		return boardDelete;
	}

	public void setBoardDelete(String boardDelete) {
		this.boardDelete = boardDelete;
	}

	public void setBoardTagName(String boardTagName) {
		this.boardTagName = boardTagName;
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

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getClassMemberNo() {
		return classMemberNo;
	}

	public void setClassMemberNo(int classMemberNo) {
		this.classMemberNo = classMemberNo;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardNote() {
		return boardNote;
	}

	public void setBoardNote(String boardNote) {
		this.boardNote = boardNote;
	}

	public Date getBoardDate() {
		return boardDate;
	}

	public void setBoardDate(Date boardDate) {
		this.boardDate = boardDate;
	}

	public int getBoardLike() {
		return boardLike;
	}

	public void setBoardLike(int boardLike) {
		this.boardLike = boardLike;
	}

	public String getBoardTagNo() {
		return boardTagNo;
	}

	public void setBoardTagNo(String boardTagNo) {
		this.boardTagNo = boardTagNo;
	}

	public int getBoardView() {
		return boardView;
	}

	public void setBoardView(int boardView) {
		this.boardView = boardView;
	}

	public String getBoardOriginalFile() {
		return boardOriginalFile;
	}

	public void setBoardOriginalFile(String boardOriginalFile) {
		this.boardOriginalFile = boardOriginalFile;
	}

	public String getBoardRenameFile() {
		return boardRenameFile;
	}

	public void setBoardRenameFile(String boardRenameFile) {
		this.boardRenameFile = boardRenameFile;
	}

	public String getBoardAlarm() {
		return boardAlarm;
	}

	public void setBoardAlarm(String boardAlarm) {
		this.boardAlarm = boardAlarm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return this.boardNo + ", " + this.classMemberNo + ", " + this.boardTitle + ", "
				+this.boardNote + ", " + this.boardDate + ", "
				+this.boardLike + ", " + this.boardTagNo + ", " + this.boardView + ", "
				+this.boardOriginalFile + ", " + this.boardRenameFile +", " + this.boardAlarm;
	}
	
	
}