package comment.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.model.service.CommentService;
import comment.model.vo.Comment;

/**
 * Servlet implementation class galleryCommentDeleteServlet
 */
@WebServlet("/gComDelete")
public class galleryCommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public galleryCommentDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int galNo = Integer.parseInt(request.getParameter("galNo"));
		int classNo = Integer.parseInt(request.getParameter("classNo"));
	
		/*int currentPage = 1;
		
		if(request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}*/
		
		int classking = Integer.parseInt(request.getParameter("classking"));
		int commentNo = Integer.parseInt(request.getParameter("commentNo")); 
		
		int result = new CommentService().galCommentDelete(galNo,commentNo);
		
		if(result > 0) {
			
			response.sendRedirect("/classKing/cGDetail?&DgalNo="+galNo+"&classking="+classking+"&classNo="+classNo);
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
