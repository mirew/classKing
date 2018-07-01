package chat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.model.service.ChatService;
import chat.model.vo.Chat;

/**
 * Servlet implementation class Chat1on1Servlet
 */
@WebServlet("/chat11")
public class Chat1on1Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Chat1on1Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String memberId = request.getParameter("memberId");
		String uId = request.getParameter("uId");
		String id[] = {memberId,uId};
		Arrays.sort(id);
		
		ArrayList<Chat> list = new ChatService().chat1on1List(id);
		if(list.size() == 0) {
			new ChatService().chat1on1Insert(id);
		}
		
		RequestDispatcher view = request.getRequestDispatcher("views/chat/chat1on1.jsp");
		request.setAttribute("list", list);
		request.setAttribute("uId", uId);
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
