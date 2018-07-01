package member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import classes.model.service.ClassesService;
import classes.model.vo.Classes;

/**
 * Servlet implementation class MyPageClassServlet
 */
@WebServlet("/mpclass")
public class MyPageClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPageClassServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String memberId = request.getParameter("memberid");
		int currentPage = 1;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		int limit = 12;
		
		ClassesService cService = new ClassesService();
		int listCount = cService.myGetListCount(memberId);
		
		ArrayList<Classes> list = cService.myClassesList(memberId,currentPage,limit);
		
		int maxPage = (int) ((double) listCount / limit + 0.917);
		// int startPage=((int)((double)currentPage/limit+0.917)-1)*limit+1;
		// int endPage =startPage + limit -1;

		int startPage = ((int) ((double) currentPage / 5 + 0.8) - 1) * 5 + 1;
		int endPage = startPage + 5 - 1;

		if (maxPage < endPage) {
			endPage = maxPage;
		}
		
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		for(Classes classes : list) {
			JSONObject job = new JSONObject();
			job.put("no", classes.getClassNo());
			job.put("title", classes.getClassTitle());
			job.put("date", classes.getCreateDate().toString());
			job.put("count", classes.getMemberCount());
			job.put("maxPage", maxPage);
			job.put("startPage", startPage);
			job.put("endPage", endPage);
			job.put("currentPage", currentPage);
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
