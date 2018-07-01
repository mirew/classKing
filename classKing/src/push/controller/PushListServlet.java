package push.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import push.model.service.PushService;
import push.model.vo.Push;

import java.util.*;

/**
 * Servlet implementation class PushListServlet
 */
@WebServlet("/plist")
public class PushListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PushListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// pushlist
		
		String memberId = request.getParameter("memberid");
		ArrayList<Push> list = new PushService().selectPushList(memberId);
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		for(Push push:list) {
			JSONObject job=new JSONObject();
			job.put("boardNo", push.getBoardNo());
			job.put("pushNo", push.getPushNo());
			job.put("commentsWriter", push.getCommentsWriter());
			job.put("pushRead", push.getPushRead());
			job.put("classTitle", push.getClassTitle());	
			job.put("classNo", push.getClassNo());
			job.put("pushDate", push.getPushDate().toString());
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
