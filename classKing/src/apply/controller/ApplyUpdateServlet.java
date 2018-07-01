package apply.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import apply.model.service.ApplyService;
import apply.model.vo.Apply;
import classesmember.model.service.ClassMemberService;
import classesmember.model.vo.ClassesMember;

/**
 * Servlet implementation class ApplyUpdateServlet
 */
@WebServlet("/aupdate")
public class ApplyUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static int result6;
    public static int classNo;
    public static String memberId;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplyUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클래스 가입 수락시 구동 서블릿!!
		request.setCharacterEncoding("UTF-8");
		
		classNo = Integer.parseInt(request.getParameter("classNo"));
		int applyNo = Integer.parseInt(request.getParameter("applyNo"));
		int appStatus = Integer.parseInt(request.getParameter("appStatus"));
		int currentPage = 1;
				
		if(request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		Apply apply = new Apply();
		apply.setApplyNo(applyNo);
		apply.setApplyStatus(request.getParameter("appStatus"));
		apply.setMemberId(request.getParameter("memberId"));
		memberId=apply.getMemberId();
		
		int result = new ApplyService().applyUpdate(apply,appStatus);
		
		if(appStatus == 1) {
			
			
				ClassesMember cm = new ClassesMember();
				cm.setClassNo(classNo);
				cm.setMemberId(apply.getMemberId());
				result6 = new ClassMemberService().ClassMemberInsert(cm);
			
		}
		
		response.setContentType("text/html; charset=UTF-8");
		
		RequestDispatcher view = null;
		
		if(result>0) {
			view= request.getRequestDispatcher("/alist?classNo="+classNo);
			//view= request.getRequestDispatcher("/alist?classNo="+classNo+"&appStatus="+appStatus);
			request.setAttribute("classNo", classNo);
			request.setAttribute("page", currentPage);
			//request.setAttribute("appStatus", appStatus);
			//request.setAttribute("apply", apply);
		
			view.forward(request, response);
		}else {
			view = request.getRequestDispatcher("views/apply/applydError.jsp");
			request.setAttribute("message", "apply 출력 처리 실패!");
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
