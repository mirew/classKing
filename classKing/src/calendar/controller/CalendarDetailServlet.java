package calendar.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import calendar.model.service.CalendarService;
import calendar.model.vo.Calendar;

/**
 * Servlet implementation class CalendarDetailServlet
 */
@WebServlet("/caldetail")
public class CalendarDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalendarDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int calNo = Integer.parseInt(request.getParameter("calno"));
		
		Calendar cal = new CalendarService().calDetail(calNo);
		
		response.setContentType("text/html; charset=utf-8");
		if(cal != null) {
			RequestDispatcher view = request.getRequestDispatcher("views/calendar/calendarUpdate.jsp");
			request.setAttribute("cal", cal);
			view.forward(request, response);
		}else {
			System.out.println("에러");
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
