package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.CryptoUtils;
import member.model.service.MemberService;

import java.security.MessageDigest;

/**
 * Servlet implementation class PasswordReset
 */
@WebServlet("/pwdreset")
public class PasswordResetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PasswordResetServlet() {
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
		// 암호화 하기
		String userId = (String) request.getParameter("userId");
		String userPwd = CryptoUtils.encryptSHA256(new String (request.getParameter("userpwd")));

		MemberService ms = new MemberService();
		int result = ms.pwdUpdate(userId, userPwd);
		response.setContentType("text/html; charset=utf-8");

		if (result != 0) {
			response.sendRedirect("/classKing/main.jsp");
		} else {
			response.sendRedirect("views/member/memberPwdFind.jsp");
			String message = "비밀번호 수정을 실패하였습니다.";
			request.setAttribute("message", message);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
