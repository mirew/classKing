package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MemberLogoutServlet
 */
@WebServlet("/mlogout")
public class MemberLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberLogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그아웃
		HttpSession session = request.getSession(false);//있으면 가져오고 없으면 null리턴
		//회원탈퇴 시에만 값이 들어가고 그 이외에는 null 이 들어갈 것임. jsp 에서 이 값이 null 이 아닐 경우에만 alert
	    
		int result=0;
	    if(request.getParameter("signedOut") != null) {
	    	 result = 1;
	    }
		if(session != null) {
			session.invalidate();
		}
		response.sendRedirect("/classKing/main.jsp?signedOut="+result);
		
	}
	/*
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}