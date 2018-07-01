package gallery.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.model.service.CommentService;
import comment.model.vo.Comment;
import gallery.model.service.GalleryService;
import gallery.model.vo.Gallery;

/**
 * Servlet implementation class classGalleryDetailServlet
 */
@WebServlet("/cGDetail")
public class classGalleryDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public classGalleryDetailServlet() {
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
		
		/*int commentNo = Integer.parseInt(request.getParameter("commentNo"));*/
		Gallery DgalNo = new GalleryService().gDetail(galNo,classNo);
		ArrayList<Comment> list = new CommentService().galCommentDetailList(galNo);
		/*int result = new CommentService().galCommentDelete(galNo,commentNo);*/
				
		response.setContentType("text/html; charset=UTF-8");
		RequestDispatcher view = null;
		if(DgalNo != null ) {			
			view = request.getRequestDispatcher("views/gallery/classGalleryDetail.jsp");
			request.setAttribute("DgalNo", DgalNo);
			request.setAttribute("classNo", classNo);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("galComment", list);
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
