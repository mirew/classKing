package gallery.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gallery.model.service.GalleryService;
import gallery.model.vo.Gallery;

/**
 * Servlet implementation class classGalleryDetailUpdateServlet
 */
@WebServlet("/cGDUpdate")
public class classGalleryDetailUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public classGalleryDetailUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");		
		int currentPage = 1;
		if(request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}	
		int classNo =Integer.parseInt(request.getParameter("classNo"));
		int galNo = Integer.parseInt(request.getParameter("galNo"));
		int classking =Integer.parseInt(request.getParameter("classking"));
		
		Gallery DgalNo = new GalleryService().gDetail(galNo,classNo);
		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;
		if(DgalNo != null) {			
			view = request.getRequestDispatcher("views/gallery/classGalleryDetailUpdate.jsp");
			request.setAttribute("DgalNo", DgalNo);
			request.setAttribute("classNo", classNo);
			request.setAttribute("currentPage", currentPage);
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
