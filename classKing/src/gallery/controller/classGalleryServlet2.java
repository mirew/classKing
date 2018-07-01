package gallery.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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
@WebServlet("/cGallery2")
public class classGalleryServlet2 extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public classGalleryServlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
      //Gallery List 컨트롤러
      request.setCharacterEncoding("utf-8");

      int classNo =Integer.parseInt(request.getParameter("classNo"));
      
      int currentPage = 1;
      if(request.getParameter("page") != null) {
         currentPage = Integer.parseInt(request.getParameter("page"));
      }
      
      String massage = "검색결과가 없습니다.";
      
      /*검색*/
      String galcategory = "n";
      String gal_search = "n";
      String searchmsg = "검색결과가 없습니다.";
      
      
      if(request.getParameter("gal_search") != null) {
         gal_search =request.getParameter("gal_search");
      }
      if(request.getParameter("galcategory") != null) {
         galcategory = request.getParameter("galcategory");
      }
      
      if(request.getParameter("searchmsg")!=null) {
         searchmsg="검색결과가 없습니다.";
      }
      
      //한페이지당 출력할 목록 갯수 지정
      int limit = 9;
      int result2 = 5;
      if(request.getParameter("result2")!=null) {
         result2 = 1;
      }
      
      int listCount = new GalleryService().getListCount(classNo);   
      ArrayList<Gallery> list = new GalleryService().classGalleryList(classNo, currentPage, limit);
            
      if(!galcategory.equals("n") && !gal_search.equals("n")) {
         listCount = new GalleryService().getSrchCount(galcategory,gal_search,classNo);   
         list = new GalleryService().searchList(currentPage,limit,galcategory,gal_search,classNo);      
      }
      

      int maxPage = (int)((double)listCount / limit + 0.89);
      int startPage = (((int)((double)currentPage / 5 + 0.89)) - 1) * 5 + 1;
      int endPage = startPage + 5 - 1;
      
      if(maxPage < endPage) {
         endPage = maxPage;
      }
      
      
      response.setContentType("text/html; charset=utf-8");
      RequestDispatcher view = null;
      
      
      if(list.size() > 0) {   
         
         view = request.getRequestDispatcher("views/gallery/classGallery.jsp");
         request.setAttribute("result2",result2);
         request.setAttribute("list", list);
         request.setAttribute("page", currentPage);
         request.setAttribute("maxPage",maxPage);
         request.setAttribute("startPage",startPage);
         request.setAttribute("endPage", endPage);
         request.setAttribute("listCount", listCount);
         request.setAttribute("classNo", classNo);
         //request.setAttribute("searchmsg", searchmsg);
         request.setAttribute("gal_search", gal_search);
         request.setAttribute("galcategory", galcategory);   
         request.setAttribute("searchmsg", searchmsg);
         view.forward(request, response);
         
      }else {      
         response.sendRedirect("/classKing/cGallery2?classNo="+classNo+"&result2="+result2);
         
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