package notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeUpdateViewServlet
 */
@WebServlet("/nupview")
public class NoticeUpdateViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//수정페이지로 이동처리하는 컨트롤러

		int noticeNo = Integer.parseInt(request.getParameter("no"));
		int currentPage=Integer.parseInt(request.getParameter("page"));
		
		Notice notice = new NoticeService().selectNotice(noticeNo);
		
		response.setContentType("test/html;charset=utf-8");
		RequestDispatcher view = null;
		
		if(notice != null) {
			view=request.getRequestDispatcher("views/notice/noticeUpdateView.jsp");
			request.setAttribute("notice", notice);
			request.setAttribute("page", currentPage);
			view.forward(request, response);
		}else {
			view=request.getRequestDispatcher("views/notice/noticeError.jsp");
			request.setAttribute("message", noticeNo+"번글 수정페이지 출력 실패");
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
