package classes.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.model.service.ClassesService;
import classes.model.vo.Classes;

/**
 * Servlet implementation class SelectClassesServlet
 */
@WebServlet("/sclasses")
public class SelectClassesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectClassesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		
		Classes classes = new ClassesService().selectClasses(classNo);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();//클라이언트와 출력스트림 생성
		out.append(classes.getClassTitle());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
