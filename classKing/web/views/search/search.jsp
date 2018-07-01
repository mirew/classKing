<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="member.model.vo.Member,classes.model.vo.Classes,java.util.*"%>
<%
	Member loginUser = (Member) session.getAttribute("loginUser");
 	ArrayList <Classes> list = (ArrayList<Classes>)request.getAttribute("list");
	int listCount =  ((Integer)(request.getAttribute("listCount"))).intValue();
	int maxPage =  ((Integer)(request.getAttribute("maxPage"))).intValue();
	int startPage =  ((Integer)(request.getAttribute("startPage"))).intValue();
	int endPage =  ((Integer)(request.getAttribute("endPage"))).intValue();
	int currentPage =  ((Integer)(request.getAttribute("currentPage"))).intValue();
	String keyword = (String)request.getAttribute("keyword");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">	
<script defer src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>

<script src="js/jquery-3.3.1.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>

<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/allSearch.css" rel="stylesheet" type="text/css">	

<script>
$(function(){
	
    <%if(request.getAttribute("searchmsg")!=null){%>
       alert("<%=request.getAttribute("searchmsg")%>");
    <%}%>     
   
 });
 <%if(loginUser != null){%>
	function secretClass(classNo){
		$.ajax({
			url : "/classKing/secretcm",
			data : {classNo : classNo,
				memberId : "<%= loginUser.getMemberId() %>"},
			type : "get",
			success : function(data){
				if(data == 0){
					window.open("/classKing/views/class/secretClassJoin.jsp?classNo="+classNo,"비공개클래스", "width=400, height=380, left=100, top=50");
				}else{
					location.href='/classKing/views/class/classHome.jsp?classNo='+ classNo;
				}
			},error : function(a,b,c){
				console.log("error: " + a + ", " + b + ", " + c);
			}
		});
	}
<%}%>
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
					<div class="title">
						<i class="fas fa-angle-right"></i> 통합검색 결과						
					</div>				
				</div>
				<div class="listCount">검색결과 : <%=listCount %>개</div>
				<div id="class_list" style="clear: left;">
					
					<table class="table" id="srchResult">
						<tr>
							<th>카테고리</th>
							<th>클래스명</th>
							<th>소개</th>
							<th>공개</th>
							<th>개설일</th>
						</tr>

						<% for(Classes c:list) {%>
							<tr>
								<td><%=c.getCategoryName() %></td>
								
								<%if(c.getOpen().equals("N")){ %>
									<td><a href="<%if(loginUser!=null){ %>javascript:secretClass(<%=c.getClassNo() %>)<%}else{ %>javascript:alert('로그인 후 이용해주세요.')<%} %>"><%=c.getClassTitle() %></a></td>
								<%}else{ %>								
									<td><a href="/classKing/views/class/classHome.jsp?classNo=<%=c.getClassNo() %>"><%=c.getClassTitle() %></a></td>
								<% } %>
								
								<td><%=c.getClassSubTitle() %></td>
								
								<%if(c.getOpen().equals("N")){ %>
									<td>비공개</td>
								<%}else{ %>								
									<td>공개</td>
								<% } %>
								
								<td><%=c.getCreateDate() %></td>						
							</tr>					
						<% } %>
					</table>


					<div id="search_page">
			             <ul class="pagination">
								
						<% if(startPage>1){ %>
							<li class="page-item"><a class="page-link" href="/classKing/allsrch?page=<%= startPage-1 %>&keyword=<%=keyword%>">PREV</a></li>
						<% }else{ %>
							<li class="page-item"><a class="page-link">PREV</a></li>
						<% } %>
						
						
						<% for(int p = startPage;p<=endPage;p++){ 
							if(p == currentPage){%>
								<li class="page-item"><a class="page-link"><%=p %></a></li>
							<% }else{ %>
								<li class="page-item"><a class="page-link" href="/classKing/allsrch?page=<%=p%>&keyword=<%=keyword%>"><%= p %></a></li>
							<% } %>		
						<% } %>
						
						<% if(endPage < maxPage){ %>
							<li class="page-item"><a class="page-link" href="/classKing/allsrch?page=<%= endPage+1 %>&keyword=<%=keyword%>">NEXT</a></li>
						<% }else{ %>
							<li class="page-item"><a class="page-link">NEXT</a></li>
						<% } %>		
			
			             
			            </ul>          
					</div>
			</div>
		</div>
	</section>
	<%@ include file="../etc/footer.jsp"%>

</body>
</html>