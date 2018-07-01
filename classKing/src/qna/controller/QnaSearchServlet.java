package qna.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import qna.model.service.QnaService;
import qna.model.vo.Qna;

/**
 * Servlet implementation class QnaSearchServlet
 */
@WebServlet("/qsearch")
public class QnaSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QnaSearchServlet() {
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

		String condition = request.getParameter("condition");
		String searchItem = request.getParameter("searchitem");

		int currentPage = 1;

		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}

		int limit = 12;
		QnaService qnaservice = new QnaService();
		int listCount = qnaservice.getListSearhCount(searchItem, condition);
		ArrayList<Qna> qnalist = qnaservice.searchList(currentPage, limit, searchItem, condition);
		int maxPage = (int) ((double) listCount / limit + 0.917);
		int startPage = ((int) ((double) currentPage / limit + 0.917) - 1) * limit + 1;
		int endPage = startPage + limit - 1;
		if (maxPage < endPage) {
			endPage = maxPage;
		}
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;

		if (qnalist.size() > 0) {
			view = request.getRequestDispatcher("views/qna/qna.jsp");

			request.setAttribute("qnalist", qnalist);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("maxPage", maxPage);

			request.setAttribute("startPage", startPage);

			request.setAttribute("endPage", endPage);
			request.setAttribute("listCount", listCount);

			view.forward(request, response);
		} else {
			view = request.getRequestDispatcher("views/qna/qnaError.jsp");
			request.setAttribute("message", "질문 목록 조회 실패");
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
