package comment.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.model.service.CommentService;
import comment.model.vo.Comment;

/**
 * Servlet implementation class BoardCommentUpdateServlet
 */
@WebServlet("/bcommentup")
public class BoardCommentUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardCommentUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		int currentPage = 1;
		if(request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		/*int commentNo = Integer.parseInt(request.getParameter("commentNo")); */
		/*String commentsNote = request.getParameter("commentsNote");*/
		
		Comment comment = new Comment();
		comment.setCommentNo(Integer.parseInt(request.getParameter("commentNo")));
		comment.setCommentNote(request.getParameter("commentsNote"));
		
		int result = new CommentService().boardCommentUpdate(boardNo,comment);
		
		response.setContentType("text/html; charset=UTF-8");
		RequestDispatcher view = null;
		
		if(result > 0) {			
			
			view = request.getRequestDispatcher("bdetail?boardNo="+boardNo);
			request.setAttribute("classNo", classNo);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("bComment", comment);
			view.forward(request, response);
		}else {
			response.sendRedirect("views/board/classBoardError.jsp");
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
