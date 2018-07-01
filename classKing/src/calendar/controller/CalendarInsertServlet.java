package calendar.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import calendar.model.service.CalendarService;
import calendar.model.vo.Calendar;

/**
 * Servlet implementation class CalendarInsertServlet
 */
@WebServlet("/calinsert")
public class CalendarInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalendarInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		String title = request.getParameter("title");
		String note  = request.getParameter("note");
		String sdate = request.getParameter("sdate");
		String edate = null;
		if(request.getParameter("edate") != null)
			edate = request.getParameter("edate");
		String memberId = request.getParameter("memberid");
		int classNo = Integer.parseInt(request.getParameter("classno"));
		Calendar cal = new Calendar();
		cal.setCalTitle(title);
		cal.setClassNo(classNo);
		cal.setCalNote(note);
		cal.setCalSdate(Date.valueOf(sdate));
		if(request.getParameter("edate") != null)
			cal.setCalEdate(Date.valueOf(edate));
		cal.setMemberId(memberId);
		
		int result = new CalendarService().calInsert(cal);
		
		if(result > 0) {
			response.sendRedirect("/classKing/callist?classNo="+classNo);
		}else {
			response.sendRedirect("views/calendarError.jsp");
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
