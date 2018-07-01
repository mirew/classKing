package report.controller;

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

import report.model.service.ReportService;
import report.model.vo.Report;

/**
 * Servlet implementation class AdminReportListServlet
 */
@WebServlet("/adminrlist")
public class AdminReportListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminReportListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//어드민 - report관리 리스트 처리 컨트롤러
		request.setCharacterEncoding("utf-8");
		int currentPage=1;
		if(request.getParameter("page")!=null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}				
		int limit = 10;
		String r_category="board";		
		
		if(Integer.parseInt(request.getParameter("cano"))==2) {
			r_category="comments";
		}
		
		ReportService rService = new ReportService();
		int listCount = rService.getReportListCount(r_category);		
		ArrayList<Report> list = rService.allReportList(currentPage,limit,r_category);
		int maxPage = (int)((double)listCount/limit+0.917);
		int startPage = ((int)((double)currentPage/5+0.8)-1)*5+1;
	    int endPage = startPage + 5 -1;
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		for(Report r:list) {
			JSONObject job = new JSONObject();
			job.put("rnum", r.getRowNum());
			job.put("boardNo",r.getBoardNo());
			job.put("boardTitle", r.getBoardTitle());
			job.put("memberId", r.getMemberId());
			job.put("categoryNote", r.getCategoryNote());
			job.put("commentsNote", r.getCommentsNote());
			job.put("reportDate", r.getReportDate().toString());
			job.put("commentsNo", r.getCommentsNo());
			job.put("classNo", r.getClassNo());
			job.put("reportNo", r.getReportNo());
			job.put("reportFake", r.getReportFake());			
			job.put("maxPage", maxPage);
			job.put("startPage", startPage);
			job.put("endPage", endPage);
			job.put("currentPage", currentPage);
			job.put("reportcategory", r_category);
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
