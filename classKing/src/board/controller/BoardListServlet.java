package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;

/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/blist")
public class BoardListServlet extends HttpServlet {
   private static final long serialVersionUID = 12L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("utf-8");
      // 페이지별로 출력되는 클래스 게시글 전체 조회 처리용 컨트롤러 
      // 페이지 기본값 지정 
      int currentPage = 1;
      
      int classNo = Integer.parseInt(request.getParameter("classNo"));
      // 전달된 페이지값 추출 
      if(request.getParameter("page") != null) {
         currentPage = Integer.parseInt(request.getParameter("page"));
      }
      String b_keyword ="b";
      String b_category ="b";
      String searchmsg = null;
      
      if(request.getParameter("bkeyword") != null) {
         b_keyword = request.getParameter("bkeyword");
      }
      if(request.getParameter("bcategory") != null) {
         b_category = request.getParameter("bcategory");
      }   
      if(request.getParameter(("searchmsg"))!= null){
         searchmsg="검색결과가 없습니다.";
      }
      
      //한 페이지당 출력할 목록 갯수 지정 
      int limit = 12;
      int result2 = 5;
      if(request.getParameter("result2")!=null) {
         result2 = 1;
      }
      BoardService bservice = new BoardService();
      //전체 목록 갯수 조회 
      int listCount = bservice.getListCount(classNo);
      
      ArrayList<Board> list = bservice.selectList(currentPage,limit,classNo);
      
      if(!b_keyword.equals("b") && !b_category.equals("b")) {
         listCount = bservice.getSrchCount(b_keyword, b_category,classNo);
         list = bservice.serchList(currentPage,limit,b_keyword,b_category,classNo);      
         
      }
      
      int maxPage = (int)((double)listCount/limit+0.917);
      
      int startPage = ((int)((double)currentPage/5 + 0.8)-1)*5+1;
      int endPage = startPage + 5 -1;
      
      if(maxPage < endPage) {
         endPage = maxPage;
      }
      
      response.setContentType("text/html; charset=utf-8");
      RequestDispatcher view = null;
      
      
      
      
      
      if(list.size()>0) {
         view = request.getRequestDispatcher("views/board/classBoard.jsp");
         request.setAttribute("result2", result2);
         request.setAttribute("list", list);
         request.setAttribute("page", currentPage);
         request.setAttribute("maxPage",maxPage);
         request.setAttribute("startPage",startPage);
         request.setAttribute("endPage", endPage);
         request.setAttribute("listCount", listCount);
         request.setAttribute("classNo", classNo);
         //request.setAttribute("searchmsg", searchmsg);
         request.setAttribute("bkeyowrd", b_keyword);
         request.setAttribute("bcategory", b_category);
         view.forward(request, response);
      }
      else {
         /*view = request.getRequestDispatcher("views/board/boardError.jsp");
         request.setAttribute("message", currentPage + "에 대한 목록 조회 실패!!");
         view.forward(request, response);*/
         response.sendRedirect("/classKing/blist?classNo="+classNo+"&result2="+result2);
      }
   
      
   }
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // TODO Auto-generated method stub
      doGet(request, response);
   }

}