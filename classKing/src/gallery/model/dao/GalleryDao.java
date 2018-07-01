package gallery.model.dao;

import static common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import classes.model.vo.Classes;
import gallery.model.vo.Gallery;

public class GalleryDao {

	public ArrayList<Gallery> new3Gallery(Connection conn, int classNo) {
		ArrayList<Gallery> list = new ArrayList<Gallery>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM (SELECT g.gal_no,cm.member_id,g.gal_title,g.gal_note,g.gal_date,g.gal_rename_img "
				+ "FROM gallery g,class_member cm WHERE cm.class_no = ? and g.class_member_no = cm.class_member_no ORDER BY g.gal_no desc) "
				+ "WHERE rownum <= 3";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, classNo);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Gallery gal = new Gallery();
				gal.setGalNo(rset.getInt(1));
				gal.setMemberId(rset.getString(2));
				gal.setGalTitle(rset.getString(3));
				gal.setGalNote(rset.getString(4));
				gal.setGalDate(rset.getDate(5));
				gal.setGalRenameImg(rset.getString(6));
				
				list.add(gal);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
public Gallery gDetail(Connection con, int galNo,int classNo) {
		
		Gallery gDetail = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query="select g.gal_no, g.gal_title,g.gal_note,g.gal_date,g.gal_original_img, "
				+ "g.gal_rename_img,g.gal_original_img2,g.gal_rename_img2,g.gal_original_img3,g.gal_rename_img3,cm.member_id "
				+ "from gallery g, class_member cm "
				+ "where g.class_member_no = cm.class_member_no "
				+ "and g.gal_no=? and cm.class_no = ?";
				
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, galNo);
			pstmt.setInt(2, classNo);
			rset = pstmt.executeQuery();
			
			if(rset.next() ) {
				gDetail= new Gallery();
				gDetail.setGalNo(rset.getInt("gal_no"));
				gDetail.setGalTitle(rset.getString("gal_title"));
				gDetail.setGalNote(rset.getString("gal_note"));
				gDetail.setGalDate(rset.getDate("gal_date"));
				//gDetail.setGalView(rset.getInt("GAL_VIEW"));
				gDetail.setGalOriginalImg(rset.getString("gal_original_img"));
				gDetail.setGalRenameImg(rset.getString("gal_rename_img"));
				gDetail.setGalOriginalImg2(rset.getString("gal_original_img2"));
				gDetail.setGalRenameImg2(rset.getString("gal_rename_img2"));
				gDetail.setGalOriginalImg3(rset.getString("gal_original_img3"));
				gDetail.setGalRenameImg3(rset.getString("gal_rename_img3"));
				gDetail.setMemberId(rset.getString("member_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return gDetail;
	}
	

	public int getListCount(Connection con, int classNo) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query ="select count(*) from gallery g, class_member cm where g.class_member_no = cm.class_member_no " + 
				"and cm.class_no = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, classNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}
	
	/*public ArrayList<Gallery> gselectList(Connection con, int currentPage, int limit) {
		
		ArrayList<Gallery> list = new ArrayList<Gallery>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query ="select * from (select rownum as rnum, gal_no, gal_title, gal_original_img, class_no from ("
				+ "select * from gallery order by gal_no desc)) "
				+ "where rnum > = ? and rnum < = ?";
		
		int startRow =(currentPage - 1) * limit + 1;
		int endRow = startRow + limit - 1;
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Gallery g = new Gallery();
				g.setGalNo(rset.getInt("gal_no"));
				g.setGalTitle(rset.getString("gal_title"));
				g.setGalOriginalImg(rset.getString("gal_original_img"));
				list.add(g);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}*/

	
	public ArrayList<Gallery> classGalleryList(Connection con, int classNo, int currentPage, int limit) {
		ArrayList<Gallery> list = new ArrayList<Gallery>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query ="select * from ("
				+ "select rownum as rnum, g.gal_no, g.class_member_no, g.gal_title, g.gal_note,"
				+ "g.gal_date, g.gal_view, g.gal_original_img, g.gal_rename_img,cm.member_id from ("
				+ "select * from gallery order by gal_no desc) g, class_member cm "
				+ "where g.class_member_no = cm.class_member_no and cm.class_no = ? ) "
				+ "where rnum >= ? and rnum <= ?";
		
		int startRow =(currentPage - 1) * limit + 1;
		int endRow = startRow + limit - 1;
			
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, classNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Gallery g = new Gallery();
				g.setGalNo(rset.getInt("gal_no"));
				g.setCalssMemberNo(rset.getInt("class_member_no"));
				g.setGalTitle(rset.getString("gal_title"));
				g.setGalNote(rset.getString("gal_note"));
				g.setGalDate(rset.getDate("gal_date"));
				g.setGalView(rset.getInt("gal_view"));
				g.setGalOriginalImg(rset.getString("gal_original_img"));
				g.setGalRenameImg(rset.getString("gal_rename_img"));
				
				list.add(g);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(rset);
			close(pstmt);
		}
		return list;
	}

	//겔러리 삭제
	public int classGalleryDelete(Connection con, int galNo) {		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query ="delete from gallery where gal_no = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, galNo);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}


	public int classGalleryDetailUpdate(Connection con, Gallery gallery) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query ="update gallery set gal_title = ?, gal_note = ?, gal_original_img = ?, gal_rename_img =?, "
				+ "gal_original_img2 = ?, gal_rename_img2 =?, gal_original_img3 = ?, gal_rename_img3 =?"
				+ "where gal_no=?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, gallery.getGalTitle());
			pstmt.setString(2, gallery.getGalNote());
			pstmt.setString(3, gallery.getGalOriginalImg());	
			pstmt.setString(4, gallery.getGalRenameImg());
			pstmt.setString(5, gallery.getGalOriginalImg2());	
			pstmt.setString(6, gallery.getGalRenameImg2());
			pstmt.setString(7, gallery.getGalOriginalImg3());	
			pstmt.setString(8, gallery.getGalRenameImg3());
			pstmt.setInt(9, gallery.getGalNo());
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
			return result;
	}


	public int classGalleryMake(Connection con, Gallery gallery, int classNo) {	
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "insert into gallery values " + 
				"((select max(gal_no) from gallery)+1," + 
				"(select DISTINCT class_member_no from class_member " + 
				"where class_no = ? and member_id = ?),"
				+ "?,?,sysdate,default,?,?,?,?,?,?)";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, classNo);
			pstmt.setString(2, gallery.getMemberId());
			pstmt.setString(3, gallery.getGalTitle());
			pstmt.setString(4, gallery.getGalNote());		
			pstmt.setString(5, gallery.getGalOriginalImg());
			pstmt.setString(6, gallery.getGalRenameImg());
			pstmt.setString(7, gallery.getGalOriginalImg2());
			pstmt.setString(8, gallery.getGalRenameImg2());
			pstmt.setString(9, gallery.getGalOriginalImg3());
			pstmt.setString(10, gallery.getGalRenameImg3());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
				
		return result;
	}


	public int getSrchCount(Connection con, String galcategory, String gal_search, int classNo) {
		
		int listCount = 0;
		
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query = null;
		
		if (galcategory.equals("제목") == true) {
			  query = "select count(*) from gallery g, class_member cm "
			  		+ "where g.class_member_no = cm.class_member_no "
			  		+ "and class_no = ? and gal_title like '%'||?||'%'";
		} else {
		     query = "select count(*) from gallery g, class_member cm "
		     		+ "where g.class_member_no = cm.class_member_no "
		     		+ "and class_no = ? and gal_note like '%'||?||'%'";
		}		

		try {
			pstmt=con.prepareStatement(query);	
			pstmt.setInt(1, classNo);
			pstmt.setString(2, gal_search);
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				listCount=rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rset);			
		}
		
		return listCount;
	}


	public ArrayList<Gallery> searchList(Connection con, int currentPage, int limit, String gal_search,
			String galcategory, int classNo) {
		
		ArrayList<Gallery> list=new ArrayList<Gallery>();
		PreparedStatement pstmt=null;
		ResultSet rset=null;

		String query = null;
		if (galcategory.equals("제목")) {
			  query = "select * from (select rownum rnum,gal_no,gal_title,gal_note,gal_date,gal_view,gal_original_img,gal_rename_img " + 
			  		"from (select * from gallery g, class_member cm where g.CLASS_MEMBER_NO = cm.class_member_no " + 
			  		"and class_no = ? and gal_title like '%'||?||'%' order by gal_no desc)) where rnum >=? and rnum<=?";
		} else {
		     query = "select * from (select rownum rnum,gal_no,gal_title,gal_note,gal_date,gal_view,gal_original_img,gal_rename_img " + 
		     		"from (select * from gallery g, class_member cm where g.CLASS_MEMBER_NO = cm.class_member_no " + 
		     		"and class_no = ? and gal_note like '%'||?||'%' order by gal_no desc)) where rnum >=? and rnum<=?";
		}	
		
		int startRow = (currentPage-1)*limit +1;
		int endRow = startRow +limit -1;
		
		try {			
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, classNo);
			pstmt.setString(2, gal_search);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			rset=pstmt.executeQuery();			
			
			while(rset.next()) {
				Gallery g=new Gallery();
				g.setGalNo(rset.getInt("gal_no"));
				g.setGalTitle(rset.getString("gal_title"));
				g.setGalNote(rset.getString("gal_note"));
				g.setGalDate(rset.getDate("gal_date"));
				g.setGalView(rset.getInt("gal_view"));
				g.setGalOriginalImg(rset.getString("gal_original_img"));
				g.setGalRenameImg(rset.getString("gal_rename_img"));
				
				list.add(g);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
}