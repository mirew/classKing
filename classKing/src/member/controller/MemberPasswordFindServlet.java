package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;
import member.controller.*;

/**
 * Servlet implementation class MemberPasswordFind
 */
@WebServlet("/pfind")
public class MemberPasswordFindServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberPasswordFindServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("userId");
		String passwordQ = request.getParameter("optionQ");
		String passwordA = request.getParameter("userA");
		response.setCharacterEncoding("utf-8");
		MemberService ms = new MemberService();
		int result = ms.pwdFind(memberId, passwordQ, passwordA);

		try {
			if (result == 1) {
				RequestDispatcher view = null;
				view = request.getRequestDispatcher("views/member/pwdReset.jsp");
				request.setAttribute("memberId", memberId);
				view.forward(request, response);
			} else {
				response.sendRedirect("views/member/memberPwdFind.jsp?result=" + result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
