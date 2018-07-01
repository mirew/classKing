package classes.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import classes.model.service.ClassesService;
import classes.model.vo.Classes;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class AdminClassListServlet
 */
@WebServlet("/adminclist")
public class AdminClassListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminClassListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//어드민 - 클래스 관리 목록 처리 페이지
		request.setCharacterEncoding("utf-8");
		int currentPage=1;
		if(request.getParameter("page")!=null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}		
		int limit = 10;
		
		ClassesService cService = new ClassesService();

		String c_category = "01";
		
		if(request.getParameter("classcategory") !=null) {	
			int cat = Integer.parseInt(request.getParameter("classcategory"));
			switch(cat) {
			case 1:c_category = "01";break;
			case 2:c_category = "02";break;
			case 3:c_category = "03";break;
			case 4:c_category = "04";break;
			case 5:c_category = "05";break;
			case 6:c_category = "06";break;
			}
			
		}
		int listCount = cService.getClassListCount(c_category);	
		
		ArrayList<Classes> list = cService.allClassList(currentPage,limit,c_category);
		int maxPage = (int)((double)listCount/limit+0.917);
		int startPage = ((int)((double)currentPage/5+0.8)-1)*5+1;
	    int endPage = startPage + 5 -1;
			
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		for(Classes c:list) {
			JSONObject job = new JSONObject();
			job.put("rnum", c.getRowNum());
			job.put("cno", c.getClassNo());
			job.put("title", c.getClassTitle());
			job.put("date", c.getCreateDate().toString());
			job.put("king", c.getMemberId());
			job.put("mc", c.getMemberCount());
			job.put("maxPage", maxPage);
			job.put("startPage", startPage);
			job.put("endPage", endPage);
			job.put("currentPage", currentPage);
			job.put("classcategory", c_category);
			jarr.add(job);
		}
		json.put("list", jarr);
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
