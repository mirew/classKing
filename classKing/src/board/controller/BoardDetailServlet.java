package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;
import comment.model.service.CommentService;
import comment.model.vo.Comment;

/**
 * Servlet implementation class BoardDetailServlet
 */
@WebServlet("/bdetail")
public class BoardDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		
		int classking = Integer.parseInt(request.getParameter("classking"));
		
		
		Board board = new BoardService().selectBoard(boardNo);
		ArrayList<Comment> list = new CommentService().boardCommnetDetailList(boardNo);
		//게시글 조회수 1 증가 처리 
		new BoardService().addViewCount(boardNo);
		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;
		if(board != null) {
			view = request.getRequestDispatcher("views/board/classBoardDetail.jsp?");
			request.setAttribute("classking", classking);
			request.setAttribute("board", board);
			request.setAttribute("currentPage", currentPage);
			//request.setAttribute("currentPage", currentPage);
			request.setAttribute("bComment", list);
			view.forward(request, response);
		}else {
			view = request.getRequestDispatcher("views/board/boardError.jsp");
			request.setAttribute("message", boardNo + "번 게시글 상세보기 실패!!");
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
