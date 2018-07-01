<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	 <%@ page import="member.model.vo.Member" %>
	<%
		Member loginUser1 = (Member)session.getAttribute("loginUser");
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/classKing/css/adminLogin.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="admin_box" style="margin-bottom:10px;">
		<table class="user_table">
			<tr>
				<td colspan="2" id="user_img"><img src="/classKing/upload/
				<% if(loginUser1.getMemberRenameImg() != null){ %>
					member_upload/<%= loginUser1.getMemberRenameImg() %>
				<% }else{ %>
					images/user.png
				<% }%>">
					<span class="username"><%= loginUser1.getMemberId() %>님, 반갑습니다.</span></td>
			</tr>
			<tr style="border-top:none;">
				<td><a href="/classKing/mpage?userid=<%=loginUser1.getMemberId()%>"><img
						src="/classKing/images/setting.png"></a>
				<p>설정</p></td>
				<td><a href="/classKing/mlogout"><img src="/classKing/images/logout.png"></a>
				<p>로그아웃</p></td>
			</tr>
		</table>
	</div>
</body>
</html>