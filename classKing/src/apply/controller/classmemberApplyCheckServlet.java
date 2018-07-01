package apply.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import apply.model.service.ApplyService;

/**
 * Servlet implementation class classmemberApplyCheckServlet
 */
@WebServlet("/applyCheck")
public class classmemberApplyCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public classmemberApplyCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int classNo = Integer.parseInt(request.getParameter("ClassNo"));
		
		String memberId = request.getParameter("MemberId");
		
		int result = new ApplyService().memberapplyCheck(classNo,memberId);
		JSONObject job = new JSONObject();
		/*job.put("ClassNo", classNo);
		job.put("MemberId", URLEncoder.encode(memberId, "utf-8"));*/
		job.put("result", result);
				
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(job.toJSONString());
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
