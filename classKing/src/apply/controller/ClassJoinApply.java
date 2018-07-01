package apply.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import apply.model.service.ApplyService;
import apply.model.vo.Apply;

/**
 * Servlet implementation class ClassJoinApply
 */
@WebServlet("/cJoinApply")
public class ClassJoinApply extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static int result7;
    public static int classNo;
    public static String applyId;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassJoinApply() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		Apply apply = new Apply();
		apply.setClassNo(Integer.parseInt(request.getParameter("classNo")));
		apply.setMemberId(request.getParameter("memberId"));
		apply.setApplyNote(request.getParameter("applyNote"));
		classNo=apply.getClassNo();
		applyId=apply.getMemberId();
		result7 = new ApplyService().classmemberInsert(apply);
		RequestDispatcher view = null;
		if(result7 > 0) {
			view =request.getRequestDispatcher("/views/class/applySuccess.jsp");
			view.forward(request, response);
		}else {
			response.sendRedirect("/classKing/views/class/classApplyError.html");
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
