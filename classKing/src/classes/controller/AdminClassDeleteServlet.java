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

/**
 * Servlet implementation class AdminClassDeleteServlet
 */
@WebServlet("/admincdel")
public class AdminClassDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminClassDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 멤버 탈퇴 처리 컨트롤러
		request.setCharacterEncoding("utf-8");
		String classNo = request.getParameter("classNo");
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
		
		int result = new ClassesService().deleteClass(classNo);		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view=null;
		
		if(result>0) {
			
			int currentPage=1;				
			int limit = 10;			
			ClassesService cService = new ClassesService();
			int listCount = cService.getClassListCount(c_category);				
			ArrayList<Classes> list = cService.allClassList(currentPage,limit,c_category);
			int maxPage = (int)((double)listCount/limit+0.917);
			int startPage = ((int)((double)currentPage/5+0.8)-1)*5+1;
		    int endPage = startPage + 5 -1;
				
			if(maxPage < endPage) {
				endPage = maxPage;
			}
			
			JSONObject job=new JSONObject();
			job.put("page","1");
			job.put("category", c_category);			
			
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(job.toJSONString());
			out.flush();
			out.close(); 

		}else {
			view = request.getRequestDispatcher("views/member/memberError.jsp");
			request.setAttribute("message", "회원 탈퇴 처리 실패!");
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
