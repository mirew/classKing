package gallery.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import gallery.model.dao.GalleryDao;
import gallery.model.vo.Gallery;

public class GalleryService {

	public ArrayList<Gallery> new3Gallery(int classNo) {
		Connection conn = getConnection();
		ArrayList<Gallery> list = new GalleryDao().new3Gallery(conn,classNo);
		close(conn);
		
		return list;
	}
	public int getListCount(int classNo) {
		Connection con = getConnection();
		int listCount = new GalleryDao().getListCount(con,classNo);
		close(con);
		return listCount;
	}

	/*public ArrayList<Gallery> gselectList(int currentPage, int limit) {
		Connection con = getConnection();
		ArrayList<Gallery> list = 
				new GalleryDao().gselectList(con, currentPage, limit);
			close(con);
			return list;
	}*/

	public Gallery gDetail(int galNo,int classNo) {
		Connection con = getConnection();
		Gallery gDetail = new GalleryDao().gDetail(con,galNo,classNo);
		close(con);
		return gDetail;
	}

	public ArrayList<Gallery> classGalleryList(int classNo, int currentPage, int limit) {
		Connection con = getConnection();
		ArrayList<Gallery> list = new GalleryDao().classGalleryList(con, classNo, currentPage, limit);
		close(con);
		return list;
	}

	public int classGalleryDelete(int galNo) {
		Connection con = getConnection();
		int result = new GalleryDao().classGalleryDelete(con, galNo);
		if(result > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		
		return result;
	}

	public int classGalleryDetailUpdate(Gallery gallery) {
		Connection con = getConnection();
		int result = new GalleryDao().classGalleryDetailUpdate(con, gallery);
		if(result > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		
		return result;
	}

	public int classGalleryMake(Gallery gallery, int classNo) {
		Connection con = getConnection();
		int result = new GalleryDao().classGalleryMake(con,gallery,classNo);
		if(result > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		
		return result;
	}

	public int getSrchCount(String galcategory, String gal_search, int classNo) {
		Connection con=getConnection();
		int listCount = new GalleryDao().getSrchCount(con,galcategory,gal_search,classNo);
		close(con);
		return listCount;
	}

	public ArrayList<Gallery> searchList(int currentPage, int limit, String galcategory, String gal_search, int classNo) {
		Connection con=getConnection();
		ArrayList<Gallery> list=new GalleryDao().searchList(con,currentPage,limit,gal_search,galcategory,classNo);
		close(con);
		return list;
	}
}
