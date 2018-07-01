package classesmember.controller;

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
import classes.model.service.ClassesService;
import classes.model.vo.Classes;
import classesmember.model.service.ClassMemberService;
import classesmember.model.vo.ClassesMember;

/**
 * Servlet implementation class ClassMemberListServlet
 */
@WebServlet("/cmlist")
public class ClassMemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassMemberListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		
		ArrayList<ClassesMember> list = new ClassMemberService().classMemberList(classNo);
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		for(ClassesMember cm : list) {
			JSONObject job = new JSONObject();
			job.put("no", cm.getClassMemberNo());
			job.put("joindate", cm.getClassJoinDate().toString());
			if(cm.getLatestDate()!=null) {
				job.put("latestdate", cm.getLatestDate().toString());
			}else {
				job.put("latestdate", "접속 기록 없음");
			}
			job.put("img", cm.getMemberRenameImg());
			job.put("id", cm.getMemberId());
			job.put("classking", cm.getClassKing());
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
