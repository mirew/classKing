package notice.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeListServlet
 */
@WebServlet("/nlist")
public class NoticeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 공지 전체 조회용 컨트롤러
		request.setCharacterEncoding("utf-8");
		int currentPage=1;
		if(request.getParameter("page")!=null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}	
		String n_keyword = "n";
		String n_category ="n";
		if(request.getParameter("nkeyword")!=null) {
			n_keyword = request.getParameter("nkeyword");
		}
		
		if(request.getParameter("ncategory")!=null) {
			n_category=request.getParameter("ncategory");
		}	
		
		String searchmsg= "검색결과가 없습니다.";

		int limit = 12;
		
		NoticeService nService = new NoticeService();
		
		int listCount = nService.getListCount();	
		ArrayList<Notice> list = nService.selectList(currentPage,limit);
		
		if(!n_keyword.equals("n") && !n_category.equals("n")) {
			listCount = nService.getSrchCount(n_keyword,n_category);	
			list = nService.searchList(currentPage,limit,n_keyword,n_category);		
		}
		
		int maxPage = (int)((double)listCount/limit+0.917);
		int startPage = ((int)((double)currentPage/5+0.8)-1)*5+1;
	    int endPage = startPage + 5 -1;
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		response.setContentType("text/html;charset=utf-8");
		RequestDispatcher view= null;
		if(list.size()>0) {
			view=request.getRequestDispatcher("views/notice/notice.jsp");
			request.setAttribute("list", list);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("listCount", listCount);
			request.setAttribute("searchmsg", searchmsg);
			request.setAttribute("nkeyword", n_keyword);
			request.setAttribute("ncategory", n_category);
			view.forward(request, response);
		}else {			
			response.sendRedirect("/classKing/nlist?page=1&searchmsg="+searchmsg);
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
