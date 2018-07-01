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
import push.model.service.PushService;

/**
 * Servlet implementation class BoardCommentInsertServlet
 */
@WebServlet("/bcommentinsert")
public class BoardCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardCommentInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int classking = Integer.parseInt(request.getParameter("classking"));
		Comment c = new Comment();
		c.setBoardNo(Integer.parseInt(request.getParameter("boardNo")));
		c.setMemberId(request.getParameter("loginUser"));
		c.setCommentNote(request.getParameter("commentsNote"));
		
		int result = new CommentService().insertComment(c);
		response.setContentType("text/html; charset=utf-8");
		if(result > 0) {
			int result2 = new PushService().insertPush(boardNo,request.getParameter("loginUser"));
			response.sendRedirect("/classKing/bdetail?boardNo="+boardNo+"&classNo="+classNo+"&page="+currentPage+"&classking="+classking);
		}else {
			response.sendRedirect("views/comment/classcommentError.html");
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