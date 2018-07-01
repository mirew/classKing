<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page
   import="member.model.vo.Member,qna.model.vo.Qna,java.util.ArrayList,java.sql.Date"%>
<%
   Member loginUser = (Member) session.getAttribute("loginUser");
%>

<%
   String condition = "a";
   String searchItem = "b";
int nosearch=0;
   ArrayList<Qna> list = (ArrayList<Qna>) request.getAttribute("qnalist");
   int listCount = ((Integer) request.getAttribute("listCount")).intValue();
   int startPage = ((Integer) request.getAttribute("startPage")).intValue();
   int maxPage = ((Integer) request.getAttribute("maxPage")).intValue();
   int endPage = ((Integer) request.getAttribute("endPage")).intValue();
   int currentPage = ((Integer) request.getAttribute("currentPage")).intValue();
   
   nosearch=((Integer)request.getAttribute("nosearch")).intValue();
   
   if (request.getParameter("condition") != "a") {
      condition = (String) request.getAttribute("condition");
   }
   if (request.getParameter("searchitem") != "b") {
      searchItem = (String) request.getAttribute("searchitem");
   }
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"
   href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
   integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
   crossorigin="anonymous">
<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/board.css" rel="stylesheet" type="text/css">
<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script src="/classKing/js/jquery.lbslider.js"></script>
<script defer
   src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>
<script>
$(function(){
   <%if (request.getAttribute("message") != null) {%>
      alert("<%=request.getAttribute("message")%>");
<%}%>




   if(<%=nosearch%>==1){
      
      alert("검색 결과가 없습니다.");
      
   }
   



   });

   


   
   
   
   

   
      
   
   
   
   
   


</script>
<title>Class King</title>
</head>
<body>
<%@ include file="../../views/etc/alramAlert.jsp" %>
   <%@ include file="../etc/header.jsp"%>

   <section>
      <div id="wrap">



         <div id="right_box">
            <!-- 로그인박스 시작 -->
            <%
               if (loginUser != null && loginUser.getMemberId().equals("admin")) {
            %>
            <%@ include file="../etc/loginAdmin.jsp"%>
            <%
               } else if (loginUser != null) {
            %>
            <%@ include file="../etc/loginMember.jsp"%>
            <%
               } else {
            %>
            <%@ include file="../etc/login.jsp"%>
            <%
               }
            %>
            <!-- 로그인박스 끝 -->

            <!-- 알람박스 시작 -->
            <% if (loginUser != null) { %>
               <%@ include file="../../views/etc/alarm.jsp"%>
            <% } %>
            <!-- 알람박스 끝 -->
         </div>
         <div id="left_box" >
            <div class="board_title">
               <div class="title">
                  <i class="fas fa-question-circle"></i> Q & A
               </div>
                <%if(loginUser!=null && !(loginUser.getMemberId().equals("admin"))) {%>
               <button type="button" class="btn btn-secondary"
                  style="float: right"
                  onclick="location.href='/classKing/views/qna/qnaMake.jsp'">
                  <i class="far fa-file-alt"></i> 문의하기
               </button>
               <%} %>
            </div>
            <div id="qna_table">
               <table class="table" id="t_subject">
                  <tr>
                     <th>번호</th>
                     <th>제목</th>
                     <th>작성일</th>
                     <th>작성자</th>
                     <th>답변여부</th>
                     <th>조회</th>
                  </tr>

                  <%
                     for (Qna q : list) {
                  %>
                  <tr>
                     <td><%=q.getQnaNo()%></td>
                     <%
                        if (loginUser != null) {
                     %>
                     <td><a
                        href="/classKing/qdetail?qnanum=<%=q.getQnaNo()%>&page=<%=currentPage%>"><%=q.getQnaTitle()%></a></td>

                     <%
                        } else {
                     %>
                     <td><%=q.getQnaTitle()%></td>

                     <%
                        }
                     %>
                     <td><%=q.getQnaDate()%></td>
                     <td><%=q.getQnaWriter()%></td>

                     <%
                        if (q.getQnaAnswer() == null) {
                     %>
                     <td><span class="qna_false">답변대기</span></td>
                     <%
                        } else {
                     %>
                     <td><span class="qna_true">답변완료</span></td>
                     <%
                        }
                     %>
                     <td><%=q.getQnaView()%></td>
                     <%
                        }
                     %>
                  
               </table>



            </div>
            <div id="board_searchbox">
               <form action="/classKing/qlist" method="post">
                  <input type="hidden"> <select
                     class="form-control notice_select" name="condition">
                     <option>제목</option>
                     <option>작성자</option>
                  </select> <input type="text" name="searchitem"
                     class="notice_search form-control"> <input type="submit"
                     class="btn btn-dark" value="검색" onload="nosearch();">
               </form>
            </div>

            <div id="notice_page">
               <ul class="pagination">









                  <%
                     if (startPage > 5) {
                  %>
                  <li class="page-item"><a class="page-link"
                     href="/classKing/qlist?page=<%=startPage - 1%>&condition=<%=condition%>&searchitem=<%=searchItem%>">
                        Previous</a></li>
                  <%
                     } else {
                  %>
                  <li class="page-item"><a class="page-link">
                        Previous</a></li>
                  <%
                     }
                  %>

                  <%
                     for (int p = startPage; p <= endPage; p++) {
                  %>
                  <%
                     if (p == currentPage) {
                  %>
                  <li class="page-item"><a class="page-link"><%=p%></a></li>
                  <%
                     } else {
                  %>
                  <li class="page-item"><a class="page-link"
                     href="/classKing/qlist?page=<%=p%>&condition=<%=condition%>&searchitem=<%=searchItem%>">
                           <%=p%></a></li>

                  <% } %>
                  <% } %>

                  <%
                     if (endPage < maxPage) {
                  %>

                  <li class="page-item"><a class="page-link"
                     href="/classKing/qlist?page=<%=endPage + 1%>&condition=<%=condition%>&searchitem=<%=searchItem%>">
                        Next</a></li>
                  <%
                     } else {
                  %>
                  <li class="page-item"><a class="page-link">next</a></li>
                  <%
                     }
                  %>
               </ul>
            </div>
     <%--       <%} %> --%>
         </div>
      </div>
   </section>
   <%@ include file="../etc/footer.jsp"%>
</body>
</html>