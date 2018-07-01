<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member,board.model.vo.Board, java.util.ArrayList,java.sql.Date, classesmember.model.vo.ClassesMember"%>
<%
   ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
   int listCount = ((Integer)request.getAttribute("listCount")).intValue();
   int startPage = ((Integer)request.getAttribute("startPage")).intValue();
   int endPage = ((Integer)request.getAttribute("endPage")).intValue();
   int maxPage = ((Integer)request.getAttribute("maxPage")).intValue();
   int currentPage = ((Integer)request.getAttribute("page")).intValue();
   String bkeyword = (String)request.getAttribute("bkeyowrd");
   String bcategory = (String)request.getAttribute("bcategory");
   
   int classNo = ((Integer)request.getAttribute("classNo")).intValue();
   Member loginUser = (Member) session.getAttribute("loginUser");
   int result2 = ((Integer)request.getAttribute("result2")).intValue();

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

<link href="/classKing/css/menuBar.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/classBoard.css" rel="stylesheet"
   type="text/css">

<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script defer
   src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>

<title>Class King</title>

<script type="text/javascript">
   function showWriteForm(){
      location.href="/classKing/views/board/classBoardMake.jsp?currentPage=1&classNo="+<%=classNo%>;
      //alert("boarNo="+boardNo);
   }

   $(function(){
      <%if(result2 == 1){%>
         alert("검색 결과가 없습니다.");
         
      <%}%>
   });


   $(function(){
      memberList();
     <%--  <%if(request.getAttribute("message")!=null){%>
         alert("<%=request.getAttribute("message")%>");
      <%}%>

      <%if(request.getParameter("searchmsg")!=null){%>
         alert("<%=request.getAttribute("searchmsg")%>");
      <%}%> --%>
   });
   
   function memberList(){
      $.ajax({
         url : "/classKing/cmlist2",
         data : {classNo : "<%=classNo%>"},
         type : "get",
         datatype: "json",
         success : function(data){
            var jsonStr = JSON.stringify(data);
            var json = JSON.parse(jsonStr);
            var values = "<tr style='font-size:12px; text-align:center;'><th style='width:100px;'>번호</th><th style='width:60px;'>말머리</th><th style='width:280px;'>제목</th><th style='width:150Spx;'>작성자</th><th style='width:120px;'>작성일</th><th style='width:80px;'>조회수</th></tr>";
            var values2 ="";
            var num1 = 0;
            var num2 = 0;
            var classking = 5;
            
            //alert("num:"+num);
            for(var i =0; i< Object.keys(json.cmlist2).length; i++){            
               //alert("for문 실행!!");
               
                  if(((json.cmlist2[i].classNo==<%=classNo%> && json.cmlist2[i].memberId=='<%=loginUser.getMemberId()%>')) || <%=loginUser.getMemberId().equals("admin")%>==true){                        
                     num1=1;
                     
                     if(json.cmlist2[i].classKing =="Y"){
                        classking = 0; 
                     }else{
                        classking = 1;
                     }
                     
                     //alert("classking:"+classking);
                     
                  }else{
                     num2=0;
                  }      
            }
            
            if(num1==1){
               //alert("num1:"+num1);
               if(!<%=loginUser.getMemberId().equals("admin")%>){
               values2 +="<button type='button' class='btn btn-secondary'"
                     +"style='float: right' onclick='showWriteForm();'>"
                     +"<i class='far fa-file-alt'></i> 새글쓰기</button>";
                  
                  <%for(int i=0; i < list.size(); i++){%>      
                  
                        values += "<tr style='font-size:12px;'><td><%=list.get(i).getBoardNo()%></td><td style='width:80px; text-align:center;'><%=list.get(i).getBoardTagName() %></td>"
                        +"<td><a href='/classKing/bdetail?boardNo=<%=list.get(i).getBoardNo()%>&classNo=<%=classNo%>&page=<%=currentPage%>&classking="+classking+"'><%= list.get(i).getBoardTitle() %></a></td>"
                        +"<td><%=list.get(i).getMemberId() %></td>"
                        +"<td><%=list.get(i).getBoardDate() %></td>"
                        +"<td style='width:80px; text-align:center;'><%= list.get(i).getBoardView() %></td>"
                        +"</tr>";
                  <%}%>
                  //alert("classking:"+classking);
               }else{
                  values2 ="";
                  <%for(int i=0; i < list.size(); i++){%>      
                  
                        values += "<tr style='font-size:12px;'><td><%=list.get(i).getBoardNo()%></td><td style='width:80px; text-align:center;'><%=list.get(i).getBoardTagName() %></td>"
                        +"<td><a href='/classKing/bdetail?boardNo=<%=list.get(i).getBoardNo()%>&classNo=<%=classNo%>&page=<%=currentPage%>&classking="+classking+"'><%= list.get(i).getBoardTitle() %></a></td>"
                        +"<td><%=list.get(i).getMemberId() %></td>"
                        +"<td><%=list.get(i).getBoardDate() %></td>"
                        +"<td style='width:80px; text-align:center;'><%= list.get(i).getBoardView() %></td>"
                        +"</tr>";
                  <%}%>
               }
            }else{
               //alert("num1:"+num1);
                  <%for(int i=0; i < list.size(); i++){%>
                  values += "<tr style='font-size:12px;'><td><%=list.get(i).getBoardNo()%></td><td style='width:80px;'><%=list.get(i).getBoardTagName() %></td><td><%= list.get(i).getBoardTitle() %></td>"
                  +"<td><%=list.get(i).getMemberId() %></td>"
                  +"<td><%=list.get(i).getBoardDate() %></td>"
                  +"<td style='width:80px; text-align:center;'><%= list.get(i).getBoardView() %></td>"
                  +"</tr>";
                  <%}%>
            }
            
            $("#bTable").append(values);
            $("#editBtn").append(values2);
         }/* ,error:function(a,b,c){
            alert("리스트 에러발생!");
            console.log("error: " + a + ", " + b + ", " + c);
         }    */
      });
    }
 
   
</script>
</head>
<body>

   <%@include file="../etc/header.jsp" %>

   <section>
      <div id="wrap">
         <div id="right_box">
            <!-- 로그인박스 시작 -->
            <% if (loginUser != null && loginUser.getMemberId().equals("admin")) { %>
               <%@ include file="../etc/loginAdmin.jsp"%>
            <% } else if (loginUser != null) { %>
               <%@ include file="../etc/loginMember.jsp" %>
            <% } else { %>
               <%@ include file="../etc/login.jsp"%>
            <% } %>
            <!-- 로그인박스 끝 -->
            <!-- 가입신청버튼 -->
            <jsp:include page="../etc/joinCheck.jsp"> 
              <jsp:param name="classNo" value="<%=classNo %>"/> 
            </jsp:include>
            <!-- 가입신청버튼 끝 -->
            <!-- 알람박스 시작 -->
            <% if (loginUser != null) { %>
               <%@ include file="../../views/etc/alarm.jsp"%>
            <% } %>
            <!-- 알람박스 끝 -->
         </div>
         <div id="left_box">
            <%@ include file="../etc/menuBar.jsp"%>
            <div id="main_box" >
               <div class="board_title">
                  <div class="title">
                     <i class="fas fa-bullhorn"></i> 게시글
                  </div>
                  
                  <div id="editBtn">
                  <!-- 새글쓰기 -->
                  </div>
               </div>

            
               <%if(list.size()==0 && result2 ==0){ %>
               
               <div style="text-align: center;"> 
               <br><br><br><br><br><br><br><br><br><br><br>
               
               <h2 style="color:#DDDDDD; text-align:center;">등록된 게시물이 없습니다</h2>
               <br><br><br><br><br><br><br><br><br><br><br><br>
               </div>
               <%} else{%>
               <div id="notice_table2" class="board_table2">
         <!--Board 출력 태이블  -->
                  <table class="table bTable" id="bTable">
                  
                  </table>
               </div>
               
               <div id="board_searchbox" >
               <form action="/classKing/blist" method="post">
               <input type="hidden" name="classNo" value="<%=classNo%>">
               <input type="hidden" name="page" value="<%=currentPage%>">
               
               <div style="float:left;">

                  <select class="form-control bcategory" name="bcategory">
                     <option value="제목" selected="">제목</option>
                     <option value="작성자">작성자</option>
                  </select> 
                  </div>
                  <input type="text" name="bkeyword"
                     class="notice_search form-control"> 
                     <input type="submit"
                     name="search_submit" class="btn btn-dark" value="검색">
               </form>
               </div>
               
               <div id="board_page">
                  <ul class="pagination">
                  <% if (startPage > 5) { %>   
                     <li class="page-item"><a class="page-link" href="/classKing/blist?page=<%=startPage-1%>&classNo=<%=classNo%>">prev</a></li>
                  <%} else {%>
                     <li class="page-item"><a class="page-link">prev</a></li>
                  <%}%>

                  <% for (int p = startPage; p <= endPage; p++) {
                     if (p == currentPage) {%>
                        <li class="page-item"><a class="page-link"><%= p%></a></li>
                     <% } else {%>
                        <li class="page-item"><a class="page-link" href="/classKing/blist?page=<%=p%>&classNo=<%=classNo%>"><%=p %></a></li>
                     <% }%>
                  <% }%>
                  
                  <% if (endPage < maxPage) { %>
                     <li class="page-item"><a class="page-link" href="/classKing/blist?page=<%= endPage+1 %>&classNo=<%=classNo%>">next</a></li>
                  <%} else {%>
                     <li class="page-item"><a class="page-link">next</a></li>
                  <%} %>

                  </ul>
               </div>
               <%} %>
            </div>
         </div>
      </div>
   </section>
   <%@ include file="../etc/footer.jsp"%>
</body>
</html>