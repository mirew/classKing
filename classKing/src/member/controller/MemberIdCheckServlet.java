package member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberIdCheckServlet
 */
@WebServlet("/idcheck")
public class MemberIdCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberIdCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ID중복검사
		String userid = request.getParameter("userid");
		
		Member user = new MemberService().idCheck(userid);
		int result = 0;
		if(user != null) {
			result = 1;
		}else {
			result = 2;
		}
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();//클라이언트와 출력스트림 생성
		out.append(result + "");
	}
}
