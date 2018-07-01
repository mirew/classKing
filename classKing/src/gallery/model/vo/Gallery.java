package gallery.model.vo;

import java.sql.Date;

public class Gallery implements java.io.Serializable{
   private final static long serialVersionUID = 9L;
   
   private int galNo;
   private int calssMemberNo;
   private String galTitle;
   private String galNote;
   private Date galDate;
   private int galView;
   private String galOriginalImg;
   private String galRenameImg;
   private String memberId;
   private String galOriginalImg2;
	private String galRenameImg2;
	private String galOriginalImg3;
	private String galRenameImg3;
   public Gallery() {}

   
   public Gallery(int galNo, int calssMemberNo, String galTitle, String galNote, Date galDate, int galView,
         String galOriginalImg, String galRenameImg, String memberId) {
      super();
      this.galNo = galNo;
      this.calssMemberNo = calssMemberNo;
      this.galTitle = galTitle;
      this.galNote = galNote;
      this.galDate = galDate;
      this.galView = galView;
      this.galOriginalImg = galOriginalImg;
      this.galRenameImg = galRenameImg;
      this.memberId = memberId;
   }


   public int getGalNo() {
      return galNo;
   }

   public String getGalOriginalImg2() {
	return galOriginalImg2;
}


public void setGalOriginalImg2(String galOriginalImg2) {
	this.galOriginalImg2 = galOriginalImg2;
}


public String getGalRenameImg2() {
	return galRenameImg2;
}


public void setGalRenameImg2(String galRenameImg2) {
	this.galRenameImg2 = galRenameImg2;
}


public String getGalOriginalImg3() {
	return galOriginalImg3;
}


public void setGalOriginalImg3(String galOriginalImg3) {
	this.galOriginalImg3 = galOriginalImg3;
}


public String getGalRenameImg3() {
	return galRenameImg3;
}


public void setGalRenameImg3(String galRenameImg3) {
	this.galRenameImg3 = galRenameImg3;
}


public void setGalNo(int galNo) {
      this.galNo = galNo;
   }

   public int getCalssMemberNo() {
      return calssMemberNo;
   }

   public void setCalssMemberNo(int calssMemberNo) {
      this.calssMemberNo = calssMemberNo;
   }

   public String getGalTitle() {
      return galTitle;
   }

   public void setGalTitle(String galTitle) {
      this.galTitle = galTitle;
   }

   public String getGalNote() {
      return galNote;
   }

   public void setGalNote(String galNote) {
      this.galNote = galNote;
   }

   public Date getGalDate() {
      return galDate;
   }

   public void setGalDate(Date galDate) {
      this.galDate = galDate;
   }

   public int getGalView() {
      return galView;
   }

   public void setGalView(int galView) {
      this.galView = galView;
   }

   public String getGalOriginalImg() {
      return galOriginalImg;
   }

   public void setGalOriginalImg(String galOriginalImg) {
      this.galOriginalImg = galOriginalImg;
   }

   public String getGalRenameImg() {
      return galRenameImg;
   }

   public void setGalRenameImg(String galRenameImg) {
      this.galRenameImg = galRenameImg;
   }

   public static long getSerialversionuid() {
      return serialVersionUID;
   }


   public String getMemberId() {
      return memberId;
   }


   public void setMemberId(String memberId) {
      this.memberId = memberId;
   }
   
   
   
}