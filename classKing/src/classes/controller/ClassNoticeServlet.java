package classes.controller;

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
import report.model.vo.Report;

/**
 * Servlet implementation class ClassNoticeServlet
 */
@WebServlet("/cnotice")
public class ClassNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int classNo=Integer.parseInt(request.getParameter("classNo"));
		ArrayList<Board> list=new BoardService().recentNotice(classNo);
		
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		for (Board b : list) {
			JSONObject job = new JSONObject();
			job.put("no",b.getBoardNo());
			job.put("date",b.getBoardDate().toString());
			job.put("note",URLEncoder.encode(b.getBoardTitle(),"utf-8"));
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
