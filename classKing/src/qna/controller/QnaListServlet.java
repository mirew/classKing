package qna.controller;

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

import qna.model.service.QnaService;
import qna.model.vo.Qna;

/**
 * Servlet implementation class QnaListServlet
 */
@WebServlet("/qlist")
public class QnaListServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   /**
    * @see HttpServlet#HttpServlet()
    */
   public QnaListServlet() {
      super();
      // TODO Auto-generated constructor stub
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
    *      response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      int nosearch = 0;
      request.setCharacterEncoding("utf-8");

      String condition = "a";
      String searchItem = "b";

      if (request.getParameter("condition") == null) {
         condition = "a";
      } else {
         condition = request.getParameter("condition");
      }
      if (request.getParameter("searchitem") == null) {
         searchItem = "b";
      } else {
         searchItem = request.getParameter("searchitem");
      }

      int currentPage = 1;

      if (request.getParameter("page") != null) {
         currentPage = Integer.parseInt(request.getParameter("page"));

      }

      int limit = 12;
      QnaService qnaservice = new QnaService();
      int listCount = 0;
      ArrayList<Qna> qnalist = null;

      if (condition.equals("a") || searchItem.equals("b")) {

         listCount = qnaservice.getListCount();
         qnalist = qnaservice.selectList(currentPage, limit);
      } else {

         listCount = qnaservice.getListSearhCount(searchItem, condition);
         qnalist = qnaservice.searchList(currentPage, limit, searchItem, condition);
         
         if(qnalist.size()==0) {
            
            nosearch=1;
            listCount = qnaservice.getListCount();
            qnalist = qnaservice.selectList(currentPage, limit);
         }
         
      }

      int maxPage = (int) ((double) listCount / limit + 0.917);

      int startPage = ((int) ((double) currentPage / 5 + 0.8) - 1) * 5 + 1;

      int endPage = startPage + 5 - 1;

      if (maxPage < endPage) {
         endPage = maxPage;
      }
      response.setContentType("text/html; charset=utf-8");
      RequestDispatcher view = null;

      if (qnalist.size() >= 0) {
         view = request.getRequestDispatcher("views/qna/qna.jsp");
         
         request.setAttribute("qnalist", qnalist);
         request.setAttribute("currentPage", currentPage);
         request.setAttribute("maxPage", maxPage);
         request.setAttribute("startPage", startPage);
         request.setAttribute("endPage", endPage);
         request.setAttribute("listCount", listCount);
         request.setAttribute("condition", condition);
         request.setAttribute("searchitem", searchItem);
         
         request.setAttribute("nosearch", nosearch);
         
         view.forward(request, response);
      } else {
         view = request.getRequestDispatcher("views/qna/qnaError.jsp");
         request.setAttribute("message", "목록 조회 실패");
         view.forward(request, response);

      }

   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
    *      response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // TODO Auto-generated method stub
      doGet(request, response);
   }

}