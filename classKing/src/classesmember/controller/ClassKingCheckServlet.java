package classesmember.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classesmember.model.service.ClassMemberService;
import classesmember.model.vo.ClassesMember;

/**
 * Servlet implementation class ClassKingCheckServlet
 */
@WebServlet("/kingcheck")
public class ClassKingCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassKingCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		String memberId = request.getParameter("memberId");
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		
		//int classking = Integer.parseInt(request.getParameter("classking"));
		
		ClassesMember cm = new ClassMemberService().classKingCheck(memberId,classNo);
		int classKing = 1;
		if(cm!=null) {
			if(cm.getClassKing().equals("Y"))
				classKing = 0;
		}
		if(memberId.equals("admin"))
			classKing = 0;
		response.sendRedirect("/classKing/bdetail?classNo="+classNo+"&boardNo="+boardNo+"&page="+currentPage+"&classking="+classKing);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
