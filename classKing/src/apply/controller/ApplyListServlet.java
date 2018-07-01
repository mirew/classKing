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
import board.model.service.BoardService;
import board.model.vo.Board;

/**
 * Servlet implementation class ApplyListServlet
 */
@WebServlet("/alist")
public class ApplyListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplyListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 페이지별로 출력되는 클래스 게시글 전체 조회 처리용 컨트롤러 
		// 페이지 기본값 지정 
		int currentPage = 1;
		int classNo = Integer.parseInt(request.getParameter("classNo"));
//		int appStatus = 0; 
//		if(request.getParameter("appStatus") != null) {
//			appStatus = Integer.parseInt(request.getParameter("appStatus"));
//		}else {
//			appStatus = 0;
//		}
		
		// 전달된 페이지값 추출 
		if(request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		//한 페이지당 출력할 목록 갯수 지정 
		int limit = 12;
		
		ApplyService aservice = new ApplyService();
		//전체 목록 갯수 조회 
		int listCount = aservice.getListCount(classNo);	
		
		ArrayList<Apply> list = aservice.selectList(currentPage,limit,classNo);
		
		
		int maxPage = (int)((double)listCount/limit+0.917);
		
		int startPage = ((int)((double)currentPage/5 + 0.8)-1)*5+1;
		int endPage = startPage + 5 -1;
		
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;
		
		if(list.size()>0) {
			view = request.getRequestDispatcher("views/apply/classApply.jsp?classNo="+classNo);
			request.setAttribute("list", list);
			request.setAttribute("page", currentPage);
			request.setAttribute("maxPage",maxPage);
			request.setAttribute("startPage",startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("listCount", listCount);
			request.setAttribute("classNo", classNo);
		//	request.setAttribute("appStatus", appStatus);
			
			view.forward(request, response);
		}else {
			
			view = request.getRequestDispatcher("views/apply/Error.jsp");
			request.setAttribute("message", currentPage + "에 대한 목록 조회 실패!!");
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
