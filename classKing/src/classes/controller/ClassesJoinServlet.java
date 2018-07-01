package classes.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.model.service.ClassesService;
import classes.model.vo.Classes;
import member.model.vo.Member;


/**
 * Servlet implementation class classesJoinServlet
 */
@WebServlet("/cJoin")
public class ClassesJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassesJoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*가입신청 버튼 누를시 서블릿*/
		request.setCharacterEncoding("UTF-8");
		
		
		String loginUser = request.getParameter("loginUser");
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		
		Classes classes = new ClassesService().classJoinSelect(classNo);
		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;
		if(classes != null) {
			view = request.getRequestDispatcher("views/class/classJoin.jsp");
			request.setAttribute("classes", classes);
			request.setAttribute("loginUser", loginUser);
			request.setAttribute("classNo", classNo);
			view.forward(request, response);
		}else {
			response.sendRedirect("/classKing/classGalleryError.html");
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
