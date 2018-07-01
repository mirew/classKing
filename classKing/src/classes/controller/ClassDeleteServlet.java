package classes.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import classes.model.service.ClassesService;
import classes.model.vo.Classes;

/**
 * Servlet implementation class ClassDeleteServlet
 */
@WebServlet("/cdel")
public class ClassDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String classNo = request.getParameter("classNo");
		
		
		int result = new ClassesService().deleteClass(classNo);		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view=null;
		
		if(result>0) {
			
			
			JSONObject job=new JSONObject();
			job.put("result",result);	
			
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(job.toJSONString());
			out.flush();
			out.close(); 

		}else {
			view = request.getRequestDispatcher("views/member/memberError.jsp");
			request.setAttribute("message", "클래스 삭제 실패!");
			view.forward(request, response);
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
