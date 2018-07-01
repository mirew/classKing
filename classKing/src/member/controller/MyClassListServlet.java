package member.controller;

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

/**
 * Servlet implementation class MyClassListServlet
 */
@WebServlet("/mclist")
public class MyClassListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyClassListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String memberId = request.getParameter("memberid");
		
		ArrayList<Classes> list = new ClassesService().classesList(memberId);
		
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		for(Classes classes : list) {
			JSONObject job = new JSONObject();
			job.put("no", classes.getClassNo());
			job.put("title", classes.getClassTitle());
			job.put("img", classes.getClassRenameImg());
			jarr.add(job);
		}
		json.put("list", jarr);
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
	}
}