package member.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class AdminMemberListServlet
 */
@WebServlet("/allmlist")
public class AdminMemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminMemberListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//어드민페이지 - 멤버 전체조회리스트

		int currentPage=1;
		if(request.getParameter("page")!=null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}		
		
		int limit = 12;
	
		MemberService mService = new MemberService();
		
		int listCount = mService.getListCount();		
		
		ArrayList<Member> list = mService.allMemberList(currentPage,limit);
		
		int maxPage = (int)((double)listCount/limit+0.917);
		int startPage = ((int)((double)currentPage/5+0.8)-1)*5+1;
	    int endPage = startPage + 5 -1;
		
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		response.setContentType("text/html;charset=utf-8");
		RequestDispatcher view= null;
		if(list.size()>0) {
			view=request.getRequestDispatcher("views/admin/adminMember.jsp");
			request.setAttribute("list", list);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("listCount", listCount);
			view.forward(request, response);
		}else {
			view=request.getRequestDispatcher("views/notice/noticeError.jsp");
			request.setAttribute("message", currentPage + "에 대한 목록 조회 실패");
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
