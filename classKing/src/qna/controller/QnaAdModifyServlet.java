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
 * Servlet implementation class QnaAdModifyServlet
 */
@WebServlet("/admodify")
public class QnaAdModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QnaAdModifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		int qnaNum=Integer.parseInt(request.getParameter("qnanum"));
		int currentPage=Integer.parseInt(request.getParameter("page"));
		String textArea=request.getParameter("area");
		
		int result=new QnaService().updateAnswer(qnaNum,textArea);
		
		
		if(result>0) {
			response.sendRedirect("/classKing/qdetail?qnanum="+qnaNum+"&page="+currentPage+"");
			
		}else {
			RequestDispatcher view=null;
			view=request.getRequestDispatcher("views/qna/qnaError.jsp");
			request.setAttribute("message", "답변 수정 실패");
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
