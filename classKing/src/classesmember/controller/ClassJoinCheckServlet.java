package classesmember.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classesmember.model.service.ClassMemberService;
import classesmember.model.vo.ClassesMember;

/**
 * Servlet implementation class ClassJoinCheckServlet
 */
@WebServlet("/joincheck")
public class ClassJoinCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassJoinCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String memberId = request.getParameter("memberId");
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		
		ClassesMember cm = new ClassMemberService().joinCheck(memberId,classNo);
		int result = 0;
		if(cm != null && cm.getClassKing().equals("Y"))
			result = 1;
		else if(cm != null)
			result = 2;
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.append(result+"");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
