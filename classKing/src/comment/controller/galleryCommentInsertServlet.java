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
 * Servlet implementation class galleryCommentServlet
 */
@WebServlet("/galComment")
public class galleryCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public galleryCommentInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int galNo = Integer.parseInt(request.getParameter("DgalNo"));
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		int currentPage = 1;		
		if(request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		int classking = Integer.parseInt(request.getParameter("classking"));
		
		Comment galComment = new Comment();
		/*galComment.setBoardNo(Integer.parseInt(request.getParameter("boardNo")));*/
		galComment.setMemberId(request.getParameter("loginUser"));
		galComment.setCommentNote(request.getParameter("commentsNote"));
		
		int result = new CommentService().galCommentInsert(classNo,galComment,galNo);
		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;
		if(galComment !=null) {
			view = request.getRequestDispatcher("cGDetail");
			request.setAttribute("classNo", classNo);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("galComment", galComment);
			request.setAttribute("classking", classking);
			view.forward(request, response);
		}else {
			response.sendRedirect("views/gallery/classGalleryError.html");

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
