package common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JDBCTemplate {

	//DB Connection Pooling 기법
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			Context initContext = new InitialContext(); //context.xml에서 불러오는??
			DataSource ds = (DataSource)initContext.lookup("java:comp/env/jdbc/oraDB");//연결
			conn = ds.getConnection();
			conn.setAutoCommit(false);
		} catch (Exception e) {
			System.out.println("JDBCTemplate.getConnection() : "
					+ e.getMessage());
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) 
				conn.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) 
				conn.rollback();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) 
				conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed()) 
				stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) 
				rset.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
