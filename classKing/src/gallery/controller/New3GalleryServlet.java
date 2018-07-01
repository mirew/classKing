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

import classes.model.service.ClassesService;
import classes.model.vo.Classes;
import gallery.model.service.GalleryService;
import gallery.model.vo.Gallery;

/**
 * Servlet implementation class New3GalleryServlet
 */
@WebServlet("/new3gallery")
public class New3GalleryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public New3GalleryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		
		ArrayList<Gallery> list = new GalleryService().new3Gallery(classNo);
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		for(Gallery gal : list) {
			JSONObject job = new JSONObject();
			job.put("no", gal.getGalNo());
			job.put("title", gal.getGalTitle());
			job.put("img", gal.getGalRenameImg());
			job.put("note", gal.getGalNote());
			job.put("date", gal.getGalDate().toString());
			job.put("id", gal.getMemberId());
			jarr.add(job);
		}
		json.put("list", jarr);
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json.toString());
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
