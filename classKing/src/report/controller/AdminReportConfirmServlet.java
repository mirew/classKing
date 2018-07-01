package report.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import report.model.service.ReportService;

/**
 * Servlet implementation class ReportConfirmServlet
 */
@WebServlet("/reportc")
public class AdminReportConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminReportConfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 신고처리 컨트롤러
		request.setCharacterEncoding("utf-8");
		
		int reportNo = Integer.parseInt(request.getParameter("reportNo"));
		String status = request.getParameter("status");
		
		int limit=10;
		String r_category ="board";
		if(Integer.parseInt(request.getParameter("cano"))==2) {
			r_category="comments";
		}
		
		ReportService rService = new ReportService();
		int result = rService.updateReport(r_category,status,reportNo);
		
		JSONObject json = new JSONObject();
		json.put("result", result);
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
