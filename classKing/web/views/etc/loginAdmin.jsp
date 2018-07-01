<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="member.model.vo.Member" %>
	<%
		Member loginUser2 = (Member)session.getAttribute("loginUser");
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login2</title>
<link href="/classKing/css/adminLogin.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="admin_box" style="margin-bottom:10px;">
		<table class="user_table">
			<tr>
				<td colspan="2" id="user_img"><img src="/classKing/images/user.png">
					<span class="username"><%= loginUser2.getMemberId() %>님, 반갑습니다.</span></td>
			</tr>
			<tr>
				<td colaspan="2"><a href="/classKing/mlogout"><img src="/classKing/images/logout.png"></a>
				<p>로그아웃</p></td>
			</tr>
		</table>
		<hr class="hr_gray">
		<div class="admin_menu">
			<ul>
				<li><a href="/classKing/views/admin/adminClass.jsp"><i
						class="fas fa-angle-right"></i> 클래스 관리</a></li>
				<li><a href="/classKing/allmlist?page=1"><i
						class="fas fa-angle-right"></i> 회원 관리</a></li>
				<li><a href="/classKing/views/admin/adminReport.jsp"><i
						class="fas fa-angle-right"></i> 신고 관리</a></li>
			</ul>
		</div>
	</div>
</body>
</html>