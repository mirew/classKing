package gallery.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.model.service.CommentService;
import comment.model.vo.Comment;
import gallery.model.service.GalleryService;

/**
 * Servlet implementation class classGalleryDelete
 */
@WebServlet("/cGDelete")
public class classGalleryDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public classGalleryDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//겔러리 삭제 컨트롤러
		int galNo = Integer.parseInt(request.getParameter("galNo"));
		int currentPage = 1;
		if(request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		int classNo =Integer.parseInt(request.getParameter("classNo"));
		
		int ComAllDel = new CommentService().classGalleryComAllDel(galNo);
		int result = new GalleryService().classGalleryDelete(galNo);
		
		
		
		
		response.setContentType("text/html; charset=utf-8");
		/*RequestDispatcher view = null;*/
		if(result > 0) {
			//response.sendRedirect("/classKing/cGallery2?page="+currentPage+"classNo="+classNo);
			response.sendRedirect("/classKing/cGallery2?page="+currentPage+"&classNo="+classNo);
			
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
