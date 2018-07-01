package comment.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.model.service.CommentService;

/**
 * Servlet implementation class BoardCommentDelete
 */
@WebServlet("/bcommentdelete")
public class BoardCommentDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardCommentDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int classking = Integer.parseInt(request.getParameter("classking"));
		int commentNo = Integer.parseInt(request.getParameter("commentNo")); 
		
		int result = new CommentService().boardCommentDelete(commentNo);
		if(result > 0) {
			response.sendRedirect("/classKing/bdetail?classNo="+classNo+"&page="+currentPage+"&boardNo="+boardNo+"&classking="+classking);
		}else {
			response.sendRedirect("views/board/classBoardEorror.html");

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
