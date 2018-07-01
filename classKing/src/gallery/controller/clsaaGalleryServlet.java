package gallery.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import gallery.model.service.GalleryService;
import gallery.model.vo.Gallery;

/**
 * Servlet implementation class clsaaGalleryServlet2
 */
@WebServlet("/cGallery23")
public class clsaaGalleryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public clsaaGalleryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Gallery List 컨트롤러
		int classNo =Integer.parseInt(request.getParameter("classNo"));
		int currentPage = 1;
		if(request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		int limit = 9;
		int listCount = new GalleryService().getListCount(classNo);
	
		ArrayList<Gallery> list = new GalleryService().classGalleryList(classNo, currentPage, limit);
		
		int maxPage = (int)((double)listCount / limit + 0.89);
		int startPage = (((int)((double)currentPage / 5 + 0.89)) - 1) * 5 + 1;
		int endPage = startPage + 5 - 1;
		
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		//전송은 json 객체 한개만 전송할 수 있음
		//최종 전송용 json 객체 생성함
		JSONObject json = new JSONObject();
				
		//list를 옮겨  담을 json 배열 객체가 필요함
		JSONArray jarr = new JSONArray();
		
		for(Gallery gallery : list) {
			JSONObject job = new JSONObject();
			job.put("galNo", gallery.getGalNo());
			job.put("calssMemberNo", gallery.getCalssMemberNo());
			job.put("galTitle", gallery.getGalTitle());
			job.put("galNote", gallery.getGalNote());
			job.put("galDate", gallery.getGalDate().toString());
			job.put("galView",gallery.getGalView());
			job.put("getGalOriginalImg", gallery.getGalOriginalImg());
			job.put("galRenameImg", gallery.getGalRenameImg());
			job.put("memberId", gallery.getMemberId());
			job.put("startPage", startPage);
			job.put("endPage", endPage);
			job.put("currentPage", currentPage);
			job.put("maxPage", maxPage);
			
			jarr.add(job);
		}
		json.put("list", jarr);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json.toJSONString());
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
