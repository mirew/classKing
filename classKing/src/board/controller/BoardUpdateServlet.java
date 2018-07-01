package board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;

/**
 * Servlet implementation class BoardUpdateServlet
 */
@WebServlet("/bupdate")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateServlet() {
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
		Board board = new BoardService().selectBoard(boardNo);
		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;
		if(board != null) {
			view = request.getRequestDispatcher("views/board/classBoardUpdate.jsp");
			request.setAttribute("board", board);
			request.setAttribute("page", currentPage);
			request.setAttribute("classNo", classNo);
			request.setAttribute("boardNo", boardNo);
			view.forward(request, response);
		}else {
			view = request.getRequestDispatcher("views/board/boardError.jsp");
			request.setAttribute("message", "수정페이지 출력 처리 실패!");
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
