<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="member.model.vo.Member" %>
    <%
    	Member loginUser = (Member)session.getAttribute("loginUser");
		int classNo = Integer.parseInt(request.getParameter("classNo"));
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">	
<title>비공개 Class</title>
<style>
	.t{
	border:0;
	margin-top: 50px;
	}
	.t th{
		border:none;
		align: center;
	}
	.btnDiv{
		width:100%;
		text-align:center;
	}
	
</style>
<script>
function showClassJoin() {
	 window.open("/classKing/cJoin?classNo=<%= classNo%>&loginUser=<%= loginUser.getMemberId()%>","가입신청", "width=350, height=330, left=100, top=50");
	window.close();
}
</script>
</head>

<body style="background:#c5cdd7;">
<div class="col-md-4 center-block">
<br>
<br>
<br>
<h2 align="center">비공개 CLASS</h2>
<br>
<h5 align="center">클래스 회원만 이용할 수 있습니다.</h5>
<h5 align="center">가입하시겠습니까?</h5>
<br>
<div class="btnDiv">
<button class="btn btn-secondary center-block" onclick="showClassJoin();">가입하기</button>
</div>

</div>
</body>
</html>