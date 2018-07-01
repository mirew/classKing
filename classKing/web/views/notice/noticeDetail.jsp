<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="member.model.vo.Member,notice.model.vo.Notice,java.sql.*,java.util.*"%>
<%
	Member loginUser = (Member) session.getAttribute("loginUser");
	int currentPage = ((Integer)request.getAttribute("currentPage")).intValue(); 
	Notice notice=(Notice)request.getAttribute("notice");
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
<script type="text/javascript">
	function deleteCheck(){
		
		
		if(confirm("정말로 삭제하시겠습니까?") == true){
			location.href="/classKing/ndel?no=<%=notice.getNoticeNo()%>&page=<%= currentPage %>";			
			
		}else{
			return false;
		}
	}



</script>
<script defer
	src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>

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
					<div class="title detail_title">
						<i class="fas fa-bullhorn"></i> 공지사항
					</div>

				</div>

				<table id="notice_detail" class="table">
					<tr>
						<th><%= notice.getNoticeTitle() %></th>
						<th class="n_date"><%= notice.getNoticeDate() %></th>
					</tr>
					<tr>
						<td colspan="2" class="n_content">
							<%=notice.getNoticeNote().replace("\r\n", "<br>")%>
						</td>
					</tr>
					<% if(notice.getNoticeOriginalFile()!=null){ %>
					<tr>				
						<td colspan="2">				
							
							<i class="fas fa-paperclip"></i>&nbsp;
							<a href="/classKing/nfdown?ofile=<%= notice.getNoticeOriginalFile()%>&rfile=<%=notice.getNoticeRenameFile()%>"><%= notice.getNoticeOriginalFile() %></a>
						</td>						
					</tr>
					<% } %>	
				</table>
				<div>
					<input type="button" name="notice_list" class="btn btn-secondary" onclick="location.href='/classKing/nlist?page=<%= currentPage %>'" value="목록">
					<% if(loginUser !=null && loginUser.getMemberId().equals("admin")) {%> 
						<div style="float:right">
							<input type="button" name="modified" class="btn btn-success" onclick = "location.href='/classKing/nupview?no=<%=notice.getNoticeNo()%>&page=<%=currentPage%>'"value="수정">
							<input type="button" name="delete" class="btn btn-danger" onclick="deleteCheck()" value="삭제">
						</div>
					<% } %>
				</div>
						
				
			</div>
		</div>
	</section>
	<%@ include file="../etc/footer.jsp" %>

</body>
</html>