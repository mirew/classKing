package qna.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import qna.model.service.QnaService;
import qna.model.vo.Qna;

/**
 * Servlet implementation class QnaDetailServlet
 */
@WebServlet("/qdetail")
public class QnaDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QnaDetailServlet() {
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

		int qnaNum = Integer.parseInt(request.getParameter("qnanum"));
		int currentPage = 1;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));

		}
		QnaService qnaservice = new QnaService();
		qnaservice.readCount(qnaNum);
		Qna qna = qnaservice.selectQna(qnaNum);

		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;
		if (qna != null) {

			view = request.getRequestDispatcher("views/qna/qnaDetail.jsp");
			request.setAttribute("qna", qna);
			request.setAttribute("currentPage", currentPage);
			view.forward(request, response);
		} else {

			view = request.getRequestDispatcher("views/qna/qnaError.jsp");
			request.setAttribute("message", "답변 상세 내용 조회 실패");
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
