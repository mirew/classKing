package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.CryptoUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberLoginServlet
 */
@WebServlet("/mlogin")
public class MemberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인
		request.setCharacterEncoding("UTF-8");
		
		// 2. 전송 온 값 꺼내서 변수에 저장하기
		
		String userId = request.getParameter("userid");
		String userPwd =  CryptoUtils.encryptSHA256(new String (request.getParameter("userpwd")));
		/* 3. 비즈니스 로직(디비영역)을 처리할 서비스 모델 클래스의
		로그인 처리용 메소드로 값을 전달하고, 결과를 받을 준비*/
		Member loginUser = new MemberService().loginCheck(userId,userPwd);
		
		// 4. 받은 결과를 가지고 성공/실패에 따라 뷰를 선택해서 내보냄
		response.setContentType("text/html; charset=UTF-8"); // 내보낼 값에 한글이있다면 response의 타입을 바꿔준다
		if(loginUser != null) {
			
			//로그인 성공처리 : session 객체 이용
			HttpSession session = request.getSession();
			//session.setMaxInactiveInterval(60*30);
			session.setAttribute("loginUser", loginUser);

			response.sendRedirect("/classKing/main.jsp");
		}else {
			RequestDispatcher view = request.getRequestDispatcher("views/member/memberLogin.jsp");
			request.setAttribute("message","아이디나 비밀번호를 확인해주세요.");
			view.forward(request, response);
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}