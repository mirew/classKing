package classes.controller;

import java.io.IOException;

import java.io.PrintWriter;

import classes.model.service.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ClassNameVarification
 */
@WebServlet("/cnamecheck")
public class ClassNameVerification extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClassNameVerification() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		ClassesService cs = new ClassesService();
		String className = (String)request.getParameter("classname");
		int result = cs.classNameCheck(className);
		
		response.setContentType("text/html; charset=UTF-8");
		
		if (result > 0) {
			PrintWriter write = response.getWriter();
			String alert1 = "이미 사용 중인 클래스 명입니다.";
			write.append(alert1);
			write.flush();
			write.close();
		} else{
			PrintWriter write = response.getWriter();
			String alert2 = "사용 가능한 클래스 명입니다.";
			write.append(alert2);
			write.flush();
			write.close();
		}
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
