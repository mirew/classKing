package board.controller;

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

import board.model.service.BoardService;
import board.model.vo.Board;

/**
 * Servlet implementation class New3BoardServlet
 */
@WebServlet("/new3board")
public class New3BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public New3BoardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		
		ArrayList<Board> list = new BoardService().new3Board(classNo);
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		for(Board board : list) {
			JSONObject job = new JSONObject();
			job.put("no", board.getBoardNo());
			job.put("title", board.getBoardTitle());
			job.put("note", board.getBoardNote());
			job.put("date", board.getBoardDate().toString());
			job.put("id", board.getMemberId());
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
