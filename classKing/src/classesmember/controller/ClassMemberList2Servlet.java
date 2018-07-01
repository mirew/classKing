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

import classesmember.model.service.ClassMemberService;
import classesmember.model.vo.ClassesMember;

/**
 * Servlet implementation class ClassMemberList2Servlet
 */
@WebServlet("/cmlist2")
public class ClassMemberList2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassMemberList2Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		
		ArrayList<ClassesMember> cmlist2 = new ClassMemberService().classMemberList2(classNo);
		
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		for(ClassesMember cm : cmlist2) {
			JSONObject job = new JSONObject();
			job.put("class_member_no", cm.getClassMemberNo());
			job.put("classNo", cm.getClassNo());
			job.put("joindate", cm.getClassJoinDate().toString());
			job.put("cmleave", cm.getClassMemberLeave());
			job.put("memberId", cm.getMemberId());
			job.put("classKing", cm.getClassKing());
			jarr.add(job);
		}
		json.put("cmlist2", jarr);
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
