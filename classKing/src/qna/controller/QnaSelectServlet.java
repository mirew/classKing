package qna.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import qna.model.service.QnaService;
import qna.model.vo.Qna;

/**
 * Servlet implementation class QnaModifyServlet
 */
@WebServlet("/mqna")
public class QnaSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QnaSelectServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int qnaNum = Integer.parseInt(request.getParameter("qnanum"));
		int currentPage = Integer.parseInt(request.getParameter("page"));

		Qna q = new QnaService().selectqna(qnaNum);

		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;

		if (q != null) {
			view = request.getRequestDispatcher("views/qna/qnaModify.jsp");
			request.setAttribute("qna", q);
			request.setAttribute("page", currentPage);
			view.forward(request, response);
		} else {
			view = request.getRequestDispatcher("views/qna/qnaError.jsp");
			request.setAttribute("message", "질문 수정 페이지 오류");
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
