package qna.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import qna.model.service.QnaService;

/**
 * Servlet implementation class QnaAdDeleteServlet
 */
@WebServlet("/qaddelete")
public class QnaAdDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QnaAdDeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getParameter("utf-8");
		int qnaNum = Integer.parseInt(request.getParameter("qnanum"));
		int currentPage=Integer.parseInt(request.getParameter("page"));
		int result = new QnaService().deleteAnswer(qnaNum);

		if (result > 0) {
			response.sendRedirect("/classKing/qdetail?qnanum="+qnaNum+"&page="+currentPage+"");
		} else {
			RequestDispatcher view = null;
			view = request.getRequestDispatcher("views/qna/qnaError.jsp");
			request.setAttribute("message", "질문 삭제 실패");
			view.forward(request, response);
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
