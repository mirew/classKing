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
 * Servlet implementation class AnswerInserServlet
 */
@WebServlet("/qinsert")
public class AnswerInserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static int result5;
    public static int qnaNum;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnswerInserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("utf-8");
		
		qnaNum=Integer.parseInt(request.getParameter("qnanum"));
		String Currentpage=request.getParameter("page");
		String qnaAnswer=request.getParameter("textarea");
		
		result5=new QnaService().insertAnswer(qnaNum,qnaAnswer);
		
		response.setContentType("text/html; charset=utf-8");
		
		if(result5>0) {
			String qnanum1=Integer.toString(qnaNum);
			response.sendRedirect("/classKing/qdetail?qnanum="+qnanum1+"&page="+Currentpage+"");
		}else {
			RequestDispatcher view = null;
			view=request.getRequestDispatcher("views/qna/qnaError.jsp");
			request.setAttribute("message", "질문 등록 실패");
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
