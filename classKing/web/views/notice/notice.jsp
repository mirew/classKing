<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member,java.util.*,notice.model.vo.Notice"%>
<%	
	ArrayList <Notice> list = (ArrayList<Notice>)request.getAttribute("list");
	int listCount =  ((Integer)(request.getAttribute("listCount"))).intValue();
	int maxPage =  ((Integer)(request.getAttribute("maxPage"))).intValue();
	int startPage =  ((Integer)(request.getAttribute("startPage"))).intValue();
	int endPage =  ((Integer)(request.getAttribute("endPage"))).intValue();
	int currentPage =  ((Integer)(request.getAttribute("currentPage"))).intValue();
	Member loginUser = (Member) session.getAttribute("loginUser");
	String nkeyword = (String)request.getAttribute("nkeyword");
	String ncategory = (String)request.getAttribute("ncategory");	
	String searchmsg = (String)request.getAttribute("searchmsg");
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/board.css" rel="stylesheet" type="text/css">

<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script defer src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>
<script>
   $(function($){
	   
      <%if(request.getAttribute("message")!=null){%>
         alert("<%=request.getAttribute("message")%>");
      <%}%>

      <%if(request.getParameter("searchmsg")!=null){%>
         alert("<%=request.getAttribute("searchmsg")%>");
      <%}%>
   });
   
</script>
<title>Class King</title>
</head>
<body>
<%@ include file="../../views/etc/alramAlert.jsp" %>
   <%@ include file="../etc/header.jsp" %>
   
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
         
         <!-- 알람박스 시작 -->
        <% if (loginUser != null) { %>
			<%@ include file="../../views/etc/alarm.jsp"%>
		<% } %>           
          <!-- 알람박스 끝 -->
      </div>   
       <div id="left_box">
          <div class="board_title">
             <div class="title"><i class="fas fa-bullhorn"></i> 공지사항</div>  
       	    <% if(loginUser != null && loginUser.getMemberId().equals("admin")) {%> 
             	<button type="button" class="btn btn-secondary" style="float:right" onclick="location.href='/classKing/views/notice/noticeMake.jsp'"><i class="far fa-file-alt"></i> 새글쓰기</button>
             <% } %> 
          </div>
                 
          <div id="notice_table" class="board_table">
          <table class="table" id="nTable">
          	<tr>
          		<th>번호</th>
          		<th>제목</th>
          		<th>작성일</th>
          		<th>조회수</th>          	
          	</tr>
          	
          	<% for(Notice n:list){ %>
			<tr>
				<td><%=n.getNoticeNo() %></td>
				<td><a href="/classKing/ndetail?no=<%=n.getNoticeNo()%>&page=<%=currentPage%>"><%=n.getNoticeTitle() %></a></td>					
				<td><%=n.getNoticeDate() %></td>
				<td><%=n.getNoticeView() %></td>					
			</tr>
			<% } %>
             
          </table>
          
          </div>
          <div id="board_searchbox">
          	<form method="post" action="nlist" >
	             <select class="form-control notice_select" name="ncategory">
	              <option>제목</option>
	              <option>내용</option>
	            </select>
	             <input type="text" name="nkeyword" class="notice_search form-control">    
	             <input type="submit" name="search_submit" class="btn btn-dark" value="검색">   
	        </form>
          </div>
          <div id="board_page">
             <ul class="pagination">
					
			<% if(startPage>5){ %>
				<li class="page-item"><a class="page-link" href="/classKing/nlist?page=<%= startPage-1 %>&nkeyword=<%=nkeyword%>&ncategory=<%=ncategory%>">PREV</a></li>
			<% }else{ %>
				<li class="page-item"><a class="page-link">PREV</a></li>
			<% } %>
			
			<!-- 현재 페이지가 포함된 그룹의 페이지 숫자 출력 -->
			<% for(int p = startPage;p<=endPage;p++){ 
				if(p == currentPage){%>
					<li class="page-item"><a class="page-link"><%=p %></a></li>
				<% }else{ %>
					<li class="page-item"><a class="page-link" href="/classKing/nlist?page=<%=p%>&nkeyword=<%=nkeyword%>&ncategory=<%=ncategory%>"><%= p %></a></li>
				<% } %>		
			<% } %>
			
			<% if(endPage < maxPage){ %>
				<li class="page-item"><a class="page-link" href="/classKing/nlist?page=<%= endPage+1 %>&nkeyword=<%=nkeyword%>&ncategory=<%=ncategory%>">NEXT</a></li>
			<% }else{ %>
				<li class="page-item"><a class="page-link">NEXT</a></li>
			<% } %>		

             
            </ul>          
          </div>    
       </div>    
   </div>
   </section>   
   <%@ include file="../etc/footer.jsp" %>
   
</body>
</html>