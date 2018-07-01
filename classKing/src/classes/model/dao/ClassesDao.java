package classes.model.dao;

import static common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import board.model.vo.Board;
import classes.model.vo.Classes;
import member.model.vo.Member;

public class ClassesDao {

	public int classMake(Connection conn, Classes classes) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO class values((SELECT MAX(class_no) FROM class) + 1"
				+ ",?,?,?,?,?,?,default,'N')";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,classes.getClassTitle());
			pstmt.setString(2, classes.getClassSubTitle());
			pstmt.setString(3, classes.getCategoryNo());
			pstmt.setString(4, classes.getOpen());
			pstmt.setString(5, classes.getClassOriginalImg());
			pstmt.setString(6, classes.getClassRenameImg());

			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public ArrayList<Classes> classesList(Connection conn, String memberId) {
		ArrayList<Classes> list = new ArrayList<Classes>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT c.class_no,c.class_title,c.class_subtitle,c.class_category_no,c.class_open,c.class_original_img,c.class_rename_img FROM class c,(" + 
				"SELECT c.class_no,c.class_member_no,count(l.class_member_no) lc FROM class_member c left join  latest_access_date l on c.class_member_no = l.class_member_no " + 
				"WHERE c.class_member_no IN(SELECT class_member_no FROM class_member WHERE member_id = ?) " + 
				"GROUP BY c.class_no,c.class_member_no " + 
				"ORDER BY count(l.class_member_no) desc) cm " + 
				"WHERE c.class_no = cm.class_no AND c.class_close = 'N' " + 
				"ORDER BY cm.lc desc";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);

			rset = pstmt.executeQuery();

			while(rset.next()) {
				Classes classes = new Classes();
				classes.setClassNo(rset.getInt(1));
				classes.setClassTitle(rset.getString(2));
				classes.setClassSubTitle(rset.getString(3));
				classes.setCategoryNo(rset.getString(4));
				classes.setOpen(rset.getString(5));
				classes.setClassOriginalImg(rset.getString(6));
				classes.setClassRenameImg(rset.getString(7));

				list.add(classes);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public Classes selectClasses(Connection conn, int classNo) {
		Classes classes = new Classes();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT class_title FROM class WHERE class_no = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, classNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				classes = new Classes();
				classes.setClassTitle(rset.getString(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return classes;
	}

	public ArrayList<Classes> myClassesList(Connection conn, String memberId,int currentPage,int limit) {
		ArrayList<Classes> list = new ArrayList<Classes>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM (SELECT c.class_no,c.class_title,c.create_date,count(cm.class_member_no) FROM class_member cm,class c " + 
				"WHERE cm.class_no IN(SELECT class_no FROM(SELECT c.class_no ,rownum as rnum " + 
				"    FROM class c,class_member cm " + 
				"    WHERE c.class_no = cm.class_no and cm.member_id = ?) " + 
				"    WHERE rnum >=? and rnum <=? AND c.class_close = 'N') " + 
				"AND cm.class_no = c.class_no " + 
				"GROUP BY c.class_no,c.class_title,c.create_date) "+
				"ORDER BY class_no desc";
		
		int startRow = (currentPage-1)*limit +1;
		int endRow = startRow +limit -1;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Classes classes = new Classes();
				classes.setClassNo(rset.getInt(1));
				classes.setClassTitle(rset.getString(2));
				classes.setCreateDate(rset.getDate(3));
				classes.setMemberCount(rset.getInt(4));
				
				list.add(classes);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int myGetListCount(Connection con,String memberId) {
		int listCount = 0;
		PreparedStatement pstmt=null;
		ResultSet rset=null;

		String query = "select count(*) from class c,class_member cm WHERE member_id = ? "
				+ "AND c.class_no = cm.class_no";

		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, memberId);
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
	public ArrayList<Classes> allClassList(Connection conn, int currentPage, int limit, String c_category) {
		ArrayList<Classes> list = new ArrayList<Classes>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from(" + 
				"SELECT rownum rnum,ta.class_title,cm.member_id,ta.class_open,ta.class_no,ta.cmcount,ta.create_date FROM" + 
				"(SELECT c.class_category_no,c.class_title,c.class_open,c.create_date,count(class_member_no) cmcount,c.class_no FROM class_member cm,class c,member m " + 
				"WHERE c.class_no = cm.class_no and cm.member_id = m.member_id " + 
				"and c.class_category_no like ? and class_close like 'N'" + 
				"GROUP BY c.class_title,c.class_open,c.create_date,c.class_no,c.class_category_no) ta," + 
				"class_member cm WHERE ta.class_no = cm.class_no AND class_king = 'Y') where rnum>=? and rnum<=?";
		
		int startRow = (currentPage-1)*limit +1;
		int endRow = startRow +limit -1;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, c_category);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Classes classes = new Classes();
				classes.setRowNum(rset.getInt("rnum"));
				classes.setClassNo(rset.getInt("class_no"));
				classes.setClassTitle(rset.getString("class_title"));
				classes.setMemberId(rset.getString("member_id"));
				classes.setMemberCount(rset.getInt("cmcount"));
				classes.setOpen(rset.getString("class_open"));
				classes.setCreateDate(rset.getDate("create_date"));				
				
				list.add(classes);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int getClassListCount(Connection con, String c_category) {
		int listCount = 0;
		PreparedStatement pstmt=null;
		ResultSet rset=null;

		String query = "select count(*) from class where class_category_no like ? and class_close like 'N'";

		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, c_category);
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

	public int deleteClass(Connection conn, String classNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query="update class set class_close='Y' where class_no=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, classNo);
			
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}		
		return result;			
	}

	public ArrayList<Classes> rankClass(Connection conn) {
		ArrayList<Classes> list = new ArrayList<Classes>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT class_no,class_title,class_subtitle,class_rename_img FROM(" + 
				"SELECT class_no,class_title,class_subtitle,class_rename_img,cc,rownum rnum FROM(" + 
				"SELECT c.class_no,c.class_title,c.class_subtitle,c.class_rename_img,count(cm.class_member_no) cc,c.class_close FROM class_member cm,class c " + 
				"WHERE cm.class_no = c.class_no AND c.class_close = 'N' AND c.class_open = 'Y'" + 
				"GROUP BY c.class_no,c.class_title,c.class_subtitle,c.class_rename_img,c.class_close " + 
				"ORDER BY count(class_member_no) desc,c.class_title)) " + 
				"WHERE rnum <= 3";
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				Classes c = new Classes();
				c.setClassNo(rset.getInt(1));
				c.setClassTitle(rset.getString(2));
				c.setClassSubTitle(rset.getString(3));
				c.setClassRenameImg(rset.getString(4));

				list.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	public int getSrchListCount(Connection con, String keyword) {
		int listCount = 0;
		PreparedStatement pstmt=null;
		ResultSet rset=null;

		String query = "select count(*) from class where class_title like '%'||?||'%' or class_subtitle like '%'||?||'%'";

		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
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

	public ArrayList<Classes> srchClassList(Connection conn, int currentPage, int limit, String keyword) {
		ArrayList<Classes> list = new ArrayList<Classes>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select * from (select rownum rnum,c.class_no,c.class_title,c.class_subtitle,cc.class_category_name,c.class_open,c.class_original_img,c.class_rename_img,c.create_date " + 
				"from (select * from class where class_title like '%'||?||'%' or class_subtitle like '%'||?||'%') c, class_category cc " + 
				"where c.class_category_no = cc.class_category_no order by c.create_date) where rnum >=? and rnum <=?";
		
		int startRow = (currentPage-1)*limit +1;
		int endRow = startRow +limit -1;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);

			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Classes classes = new Classes();
				classes.setRowNum(rset.getInt("rnum"));
				classes.setClassNo(rset.getInt("class_no"));
				classes.setClassTitle(rset.getString("class_title"));
				classes.setClassSubTitle(rset.getString("class_subtitle"));
				classes.setCategoryName(rset.getString("class_category_name"));
				classes.setOpen(rset.getString("class_open"));
				classes.setClassOriginalImg(rset.getString("class_original_img"));
				classes.setClassRenameImg(rset.getString("class_rename_img"));
				classes.setCreateDate(rset.getDate("create_date"));				

				list.add(classes);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public Classes classJoinSelect(Connection conn, int classNo) {
		Classes classes = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select c.class_title, cc.class_category_name "
				+ "from class c, class_category cc " + 
				"where c.class_category_no = cc.CLASS_CATEGORY_NO " + 			
				"and c.class_no = ? ";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, classNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				classes = new Classes();
				classes.setClassNo(classNo);
				classes.setClassTitle(rset.getString("class_title"));
				classes.setCategoryName(rset.getString("class_category_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return classes;
	}
	public int getAllListCount(Connection con) {
		int listCount = 0;
		Statement stmt=null;
		ResultSet rset=null;

		String query = "select count(*) from class where class_close = 'N'";

		try {
			stmt=con.createStatement();
			rset=stmt.executeQuery(query);
			
			if(rset.next()) {
				listCount=rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(rset);			
		}		
		return listCount;
	}

	public ArrayList<Classes> srchAllList(Connection conn, int currentPage, int limit) {
		ArrayList<Classes> list=new ArrayList<Classes>();
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		String query="select * from (select rownum rnum,c.class_no,c.class_title,c.class_subtitle,cc.class_category_name,c.class_open,c.class_original_img,c.class_rename_img,c.create_date\r\n" + 
				"from class c, class_category cc where c.class_category_no = cc.class_category_no order by c.create_date) where rnum >=? and rnum <=?";
	
		int startRow = (currentPage-1)*limit +1;
		int endRow = startRow +limit -1;
		
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				Classes classes = new Classes();
				classes.setRowNum(rset.getInt("rnum"));
				classes.setClassNo(rset.getInt("class_no"));
				classes.setClassTitle(rset.getString("class_title"));
				classes.setClassSubTitle(rset.getString("class_subtitle"));
				classes.setCategoryName(rset.getString("class_category_name"));
				classes.setOpen(rset.getString("class_open"));
				classes.setClassOriginalImg(rset.getString("class_original_img"));
				classes.setClassRenameImg(rset.getString("class_rename_img"));
				classes.setCreateDate(rset.getDate("create_date"));				

				list.add(classes);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int checkClassName(Connection conn, String className) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result=0;
		String query="select class_title from class where class_title=?";
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, className);
			rset=pstmt.executeQuery();
			if(rset.next()==true) {
				result=1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}
	public Classes selectClass(Connection conn, int classNo) {
	      PreparedStatement pstmt = null;
	      ResultSet rset = null;
	      Classes c=null;
	      String query="select * from class where class_no=?";
	      try {
	         pstmt=conn.prepareStatement(query);
	         pstmt.setInt(1, classNo);
	         rset=pstmt.executeQuery();
	         if(rset.next()) {
	            c=new Classes();
	            c.setClassNo(classNo);
	            c.setClassTitle(rset.getString("class_title"));
	            c.setClassSubTitle(rset.getString("class_subtitle"));
	            c.setCategoryNo(rset.getString("class_category_no"));
	            c.setOpen(rset.getString("class_open"));
	            c.setClassOriginalImg(rset.getString("class_original_img"));
	            c.setClassRenameImg(rset.getString("class_rename_img"));
	            c.setCreateDate(rset.getDate("create_date"));
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }finally {
	         close(rset);
	         close(pstmt);
	      }
	      return c;
	   }
	   public int classUpdate(Connection conn, Classes classes) {
	      PreparedStatement pstmt=null;
	      int result = 0;
	      
	      String query="update class set class_title=?,class_subtitle=?,class_open=?,class_original_img=?,class_rename_img=? where class_no =?";
	      
	      try {
	         pstmt=conn.prepareStatement(query);
	         pstmt.setString(1, classes.getClassTitle());
	         pstmt.setString(2, classes.getClassSubTitle());
	         pstmt.setString(3, classes.getOpen());
	         pstmt.setString(4, classes.getClassOriginalImg());
	         pstmt.setString(5, classes.getClassRenameImg());
	         pstmt.setInt(6, classes.getClassNo());
	         
	         result = pstmt.executeUpdate();
	         
	      } catch (Exception e) {
	         e.printStackTrace(); 
	      } finally {
	         close(pstmt);
	      }
	      return result;  
	   }
}