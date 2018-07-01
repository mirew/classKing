<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member,java.util.*"%>

<%
	Member loginUser = (Member) session.getAttribute("loginUser");
	ArrayList <Member> list = (ArrayList<Member>)request.getAttribute("list");
	int listCount =  ((Integer)(request.getAttribute("listCount"))).intValue();
	int maxPage =  ((Integer)(request.getAttribute("maxPage"))).intValue();
	int startPage =  ((Integer)(request.getAttribute("startPage"))).intValue();
	int endPage =  ((Integer)(request.getAttribute("endPage"))).intValue();
	int currentPage =  ((Integer)(request.getAttribute("currentPage"))).intValue();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/admin.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/board.css" rel="stylesheet" type="text/css">

<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script defer src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script type="text/javascript">

	function delMember(memberId){
		if(confirm("정말로 삭제하시겠습니까?") == true){
			location.href="/classKing/mdel?mid="+memberId;

		}else{
			return false;
		}
	}
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
		 		<div class="title"><i class="fas fa-bars"></i> 회원 관리</div>	
		 	</div>
		 	<div id="member_list" style="clear:left;">
				<table class="table" id="ml_table">
					<tr>
						<th>번호</th>
						<th>아이디</th>	
						<th>이름</th>							
						<th>가입 날짜</th>	
						<th>신고횟수</th>
						<th>회원삭제</th>
					</tr>
					
					<% for(Member m:list){ %>
					
					<tr>
						<td><%= m.getRowNum() %></td>
						<td><%= m.getMemberId() %></td>
						<td><%= m.getMemberName() %></td>
						<td><%= m.getJoinDate() %></td>
						<% if(m.getReportNum()>=5){ %>
                     <td style="color:#dc3545;font-weight:bold;"><%= m.getReportNum()%></td>
                  <%}else{ %>
                     <td><%= m.getReportNum()%></td>
                  <% } %>
						<td><button name="class_delete" class="btn btn-secondary btn_cdelete" onclick="delMember('<%=m.getMemberId()%>')"><i class="far fa-trash-alt"></i> 삭제</button>
					</tr>
					<% } %>					
				</table>
			</div>
			
	 		<div id="board_searchbox">
	 			<form action="msearch" method="post">
		 			<select class="form-control notice_select"  name="member_select" style="width:100px;">
					  <option>아이디</option>
					  <option>이름</option>
					</select>
		 			<input type="text" name="member_search" class="notice_search form-control">	 
		 			<input type="submit" name="search_submit" class="btn btn-dark" value="검색">
	 			</form>	
	 		</div>
	 		<div id="notice_page">
		 		<ul class="pagination">
					<% if(startPage >1){ %>
						<li class="page-item"><a class="page-link" href="/classKing/allmlist?page=<%= startPage-1 %>">PREV</a></li>
					<% }else{ %>
						<li class="page-item"><a class="page-link">PREV</a></li>
					<% } %>
					
					<!-- 현재 페이지가 포함된 그룹의 페이지 숫자 출력 -->
					<% for(int p = startPage;p<=endPage;p++){ 
						if(p == currentPage){%>
							<li class="page-item"><a class="page-link"><%=p %></a></li>
						<% }else{ %>
							<li class="page-item"><a class="page-link" href="/classKing/allmlist?page=<%=p%>"><%= p %></a></li>
						<% } %>		
					<% } %>
					
					<% if(endPage < maxPage){ %>
						<li class="page-item"><a class="page-link" href="/classKing/allmlist?page=<%= endPage+1 %>">NEXT</a></li>
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