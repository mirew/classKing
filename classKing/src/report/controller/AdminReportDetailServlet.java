package report.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import report.model.service.ReportService;
import report.model.vo.Report;

/**
 * Servlet implementation class AdminReportDetail
 */
@WebServlet("/ardetail")
public class AdminReportDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminReportDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer caNo=Integer.parseInt(request.getParameter("cano"));
		Integer reportNo=Integer.parseInt(request.getParameter("reportNo"));
		
		Report reportA = null;
		
		if(caNo==1) {
		reportA=new ReportService().detailBReport(reportNo);
		}
		
		if(caNo==2) {
			reportA =new ReportService().detailCReport(reportNo);
		}

		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;
		if(reportA != null) {
			
			view = request.getRequestDispatcher("views/admin/adminReportPopup.jsp");
			request.setAttribute("caNo",caNo);
			request.setAttribute("reportA", reportA);
			
			view.forward(request, response);
		}else {
			
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
