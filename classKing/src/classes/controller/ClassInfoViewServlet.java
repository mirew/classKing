package classes.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.model.service.ClassesService;
import classes.model.vo.Classes;

/**
 * Servlet implementation class ClassInfoViewServlet
 */
@WebServlet("/cinfo")
public class ClassInfoViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassInfoViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 클래스 정보 관리 수정 처리 컨트롤러
		String memberId = request.getParameter("memberId");
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		
		Classes c = new ClassesService().selectClass(classNo);
		
		response.setContentType("test/html;charset=utf-8");
		RequestDispatcher view = null;
		
		if(c != null) {
			view=request.getRequestDispatcher("views/class/ClassInfoView.jsp");
			request.setAttribute("class", c);
			view.forward(request, response);
		}else {
			view=request.getRequestDispatcher("views/class/classError.jsp");
			request.setAttribute("message", "클래스 수정페이지 출력 실패");
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
