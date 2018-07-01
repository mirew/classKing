package member.model.dao;

import static common.JDBCTemplate.*;

import java.sql.*;
import java.util.ArrayList;

import member.model.vo.Member;

public class MemberDao {

	public int joinMember(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO member values(?,?,?,?,?,?,?,default,?,?,default)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberEmail());
			pstmt.setString(5, member.getMemberBirthday());
			pstmt.setString(6, member.getQuestion());
			pstmt.setString(7, member.getAnswer());
			pstmt.setString(8, member.getMemberOriginalImg());
			pstmt.setString(9, member.getMemberRenameImg());
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public Member loginMember(Connection conn, String userId, String userPwd) {
		Member loginUser = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM member WHERE member_id = ? and member_pwd = ? and member_out = 'N'";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);

			rset = pstmt.executeQuery();
			if(rset.next()) {
				loginUser = new Member();
				loginUser.setMemberId(rset.getString("member_id"));
				loginUser.setMemberPwd(rset.getString("member_pwd"));
				loginUser.setMemberName(rset.getString("member_name"));
				loginUser.setMemberEmail(rset.getString("member_email"));
				loginUser.setMemberBirthday(rset.getString("member_birthday"));
				loginUser.setQuestion(rset.getString("question"));
				loginUser.setAnswer(rset.getString("answer"));
				loginUser.setJoinDate(rset.getDate("join_date"));
				loginUser.setMemberOriginalImg(rset.getString("member_original_img"));
				loginUser.setMemberRenameImg(rset.getString("member_rename_img"));
				loginUser.setMemberOut(rset.getString("member_out"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return loginUser;
	}

	public Member idCheck(Connection conn, String userid) {
		Member user = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM member WHERE member_id = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userid);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				user = new Member();

			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return user;
	}

	public int updateMember(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "UPDATE member set member_email = ?, member_pwd = ?, "
				+ "question = ?, answer = ?, member_original_img = ?, member_rename_img = ? "
				+ "where member_id = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberEmail());
			pstmt.setString(2, member.getMemberPwd());
			pstmt.setString(3, member.getQuestion());
			pstmt.setString(4, member.getAnswer());
			pstmt.setString(5, member.getMemberOriginalImg());
			pstmt.setString(6, member.getMemberRenameImg());
			pstmt.setString(7, member.getMemberId());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	public int getListCount(Connection con) {
		int listCount = 0;
		
		Statement stmt=null;
		ResultSet rset=null;
		
		String query = "select count(*) from member";

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

	public ArrayList<Member> allMemberList(Connection con, int currentPage, int limit) {
		ArrayList<Member> list=new ArrayList<Member>();
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		String query="select * from(select rownum rnum, m.member_id,m.member_name,m.member_email,m.question,m.answer,m.join_date,nvl(reportnum2,0) breport,nvl(reportnum,0) creport\r\n" + 
				"from member m left join (select member_id,count(member_id) reportnum2 from board_report where report_fake ='1' group by member_id) br on m.member_id = br.member_id left join (select member_id,count(member_id) reportnum \r\n" + 
				"from comments_report where report_fake ='1' group by member_id) cr on m.member_id= cr.member_id where member_out = 'N') where rnum>=? and rnum<=?";
		
		int startRow = (currentPage-1)*limit +1;
		int endRow = startRow +limit -1;
		
		try {			
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset=pstmt.executeQuery();			
			
			while(rset.next()) {
				if(!rset.getString("member_id").equals("admin")) {
					Member m = new Member();
					m.setRowNum(rset.getInt("rnum"));
					m.setMemberId(rset.getString("member_id"));
					m.setMemberName(rset.getString("member_name"));
					m.setMemberEmail(rset.getString("member_email"));
					m.setQuestion(rset.getString("question"));
					m.setAnswer(rset.getString("answer"));
					m.setJoinDate(rset.getDate("join_date"));
					
					m.setReportNum(rset.getInt("creport")+rset.getInt("breport"));
					list.add(m);	
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}		
		return list;
	}

	public int deleteMember(Connection conn, String memberId) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query="update member set member_out='Y' where member_id=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}		
		return result;			
	}

	public int getSrchCount(Connection con, String m_keyword, String m_category) {
		int listCount = 0;
		
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query = null;
		if (m_category.equals("아이디")) {
			  query = "select count(*) from member where member_id like '%'||?||'%'";
		} else {
		     query = "select count(*) from member where member_name like '%'||?||'%'";
		}		

		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, m_keyword);
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

	public ArrayList<Member> searchList(Connection con, int currentPage, int limit, String m_keyword,
			String m_category) {
		ArrayList<Member> list=new ArrayList<Member>();
		PreparedStatement pstmt=null;
		ResultSet rset=null;

		String query = null;
		if (m_category.equals("아이디")) {
			  query = "select * from (select rownum rnum,member_id,member_name,member_email,question,answer,join_date\r\n" + 
			  		"from (select * from member where member_id like '%'||?||'%')) where rnum >=? and rnum<=?";
		} else {
		     query = "select * from (select rownum rnum,member_id,member_name,member_email,question,answer,join_date\r\n" + 
		     		"from (select * from member where member_name like '%'||?||'%')) where rnum >=? and rnum<=?";
		}	
		
		int startRow = (currentPage-1)*limit +1;
		int endRow = startRow +limit -1;
		int count=1;
		try {			
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, m_keyword);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rset=pstmt.executeQuery();			

			while(rset.next()) {
				if(!rset.getString("member_id").equals("admin")) {
					Member m = new Member();
					m.setRowNum(rset.getInt("rnum"));
					m.setMemberId(rset.getString("member_id"));
					m.setMemberName(rset.getString("member_name"));
					m.setMemberEmail(rset.getString("member_email"));
					m.setQuestion(rset.getString("question"));
					m.setAnswer(rset.getString("answer"));
					m.setJoinDate(rset.getDate("join_date"));
					
					list.add(m);	
				}
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}		
		return list;
	}
	public int passwordCheck(Connection con, String memberId, String passwordQ, String passwordA) {
	      int result=0;
	      PreparedStatement pstmt = null;
	      ResultSet rset = null;
	      String query = "SELECT MEMBER_PWD FROM member WHERE member_id = ? "
	            + "and question=? and answer=?";
	      try {
	         pstmt = con.prepareStatement(query);
	      
	         pstmt.setString(1, memberId);
	         pstmt.setString(2, passwordQ);
	         pstmt.setString(3, passwordA);
	         
	         rset=pstmt.executeQuery();
	         
	         if(rset.next()) {
	            result=1;
	         }else {
	            result=0;
	         }
	         
	         
	      }catch(Exception e) {
	         e.printStackTrace();
	      }finally{
	         close(rset);
	         close(pstmt);
	   }
	      return result;
	   }

	   public int pwdUpdate(Connection con, String memberId, String password) {
	      int result=0;
	      PreparedStatement pstmt=null;
	      ResultSet rset=null;
	      String query="update member set MEMBER_PWD=? where MEMBER_ID=?";
	      try {
	      pstmt=con.prepareStatement(query);
	      pstmt.setString(1, password);
	      pstmt.setString(2, memberId);
	      
	      result=pstmt.executeUpdate();
	      
	      
	      }catch(Exception e){
	         e.printStackTrace();
	      }finally {
	         close(rset);
	         close(pstmt);
	      }
	      return result;
	   }

	public Member checkUser(Connection con, String userId) {
		Member checkUser = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM member WHERE member_id = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();

			if(rset.next()) {
				checkUser = new Member();
				checkUser.setMemberId(rset.getString("member_id"));
				checkUser.setMemberPwd(rset.getString("member_pwd"));
				checkUser.setMemberName(rset.getString("member_name"));
				checkUser.setMemberEmail(rset.getString("member_email"));
				checkUser.setMemberBirthday(rset.getString("member_birthday"));
				checkUser.setQuestion(rset.getString("question"));
				checkUser.setAnswer(rset.getString("answer"));
				checkUser.setJoinDate(rset.getDate("join_date"));
				checkUser.setMemberOriginalImg(rset.getString("member_original_img"));
				checkUser.setMemberRenameImg(rset.getString("member_rename_img"));
				checkUser.setMemberOut(rset.getString("member_out"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return checkUser;
	}

	public Member idFind(Connection con, String name, String email, String birth) {
		Member member = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM member WHERE member_name = ? AND member_email = ? AND member_birthday = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3, birth);
			
			rset = pstmt.executeQuery();

			if(rset.next()) {
				member = new Member();
				member.setMemberId(rset.getString("member_id"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return member;
	}
	
	   public int signoutDao(Connection con,String userid) {
		      int result=0;
		      PreparedStatement pstmt = null;
		      ResultSet rset = null;
		      String query="UPDATE member SET member_out='Y' wHERE member_id=?";
		      try {
		         pstmt = con.prepareStatement(query);
		         pstmt.setString(1, userid);
		         
		         result = pstmt.executeUpdate();
		         
		      }catch(Exception e){
		         e.printStackTrace();
		         
		      }finally {
		         close(rset);
		         close(pstmt);
		      }
		      return result;
		   }
	
	
	
	
}