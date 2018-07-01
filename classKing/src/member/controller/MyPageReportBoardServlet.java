package member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import board.model.service.BoardService;
import board.model.vo.Board;
import report.model.service.ReportService;
import report.model.vo.Report;

/**
 * Servlet implementation class MyPageReportBoardServlet
 */
@WebServlet("/rboard")
public class MyPageReportBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyPageReportBoardServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String memberId = request.getParameter("memberid");
		int currentPage = 1;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		int limit = 12;
		ReportService rService = new ReportService();
		int listCount = rService.mybListCount(memberId);
		ArrayList<Report> list = rService.mybreportList(memberId, currentPage, limit);
		int maxPage = (int) ((double) listCount / limit + 0.917);
		int startPage = ((int) ((double) currentPage / 5 + 0.8) - 1) * 5 + 1;
		int endPage = startPage + 5 - 1;

		if (maxPage < endPage) {
			endPage = maxPage;
		}
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		for (Report report : list) {
			JSONObject job = new JSONObject();
			job.put("no", report.getReportNo());
			job.put("classname", report.getClassName());
			job.put("category", report.getCategoryNo());
			job.put("date", report.getReportDate().toString());
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
