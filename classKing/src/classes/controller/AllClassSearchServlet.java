package classes.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.model.service.ClassesService;
import classes.model.vo.Classes;

/**
 * Servlet implementation class AllClassSearchServlet
 */
@WebServlet("/allsrch")
public class AllClassSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllClassSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//header에서 class 검색 처리 컨트롤러
		request.setCharacterEncoding("utf-8");
		String keyword = null;
		if(request.getParameter("keyword")!=null) {
			keyword = request.getParameter("keyword");
		}
		
		
		int currentPage = 1;
		if(request.getParameter("page")!=null) {
			currentPage = Integer.parseInt(request.getParameter("page"));			
		}
		int limit=10;
		
		ClassesService cService = new ClassesService();
		
		int listCount = cService.getSrchListCount(keyword);
		ArrayList<Classes> list = cService.srchClassList(currentPage,limit,keyword);

		ArrayList<Classes> list2 = null;
		String searchmsg= null;
		if(list.size()==0) {
			listCount=cService.getAllListCount();

			list2=cService.srchAllList(currentPage,limit);
			searchmsg="검색결과가 없습니다.";

		
		}
		
		int maxPage = (int)((double)listCount/limit+0.917);
		int startPage = ((int)((double)currentPage/5+0.8)-1)*5+1;
	    int endPage = startPage + 5 -1;
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		response.setContentType("text/html;charset=utf-8");
		RequestDispatcher view=null;
		if(list.size()>0) {
			view=request.getRequestDispatcher("views/search/search.jsp");
			request.setAttribute("list", list);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("listCount", listCount);
			request.setAttribute("keyword", keyword);
						
			view.forward(request, response);
		}else {

			view=request.getRequestDispatcher("views/search/search.jsp");
			request.setAttribute("list", list2);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("listCount", listCount);
			request.setAttribute("keyword", keyword);
			request.setAttribute("searchmsg", searchmsg);
					
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
