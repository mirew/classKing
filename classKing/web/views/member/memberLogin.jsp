<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>classKing</title>
<link href="/classKing/css/join.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/login.css" rel="stylesheet" type="text/css">
<script>
	<%if(request.getAttribute("message")!=null){%>
		alert("<%=request.getAttribute("message")%>");
	<%}%>
</script>
</head>
<body style="margin:0px;">
	<%@ include file="../etc/header2.jsp" %>
	<section>
		<div id="wrap" style="display:table-cell;vertical-align:middle;text-align:center;">
		<form action="/classKing/mlogin" method="post">
			<div class="login_div">	
				<div class="login_img"><img src="/classKing/images/login.png"></div>
				<p class="wel">WELCOME</p>
				<div><input type="text" name="userid" id="userid" class="form-control" placeholder="아이디"></div>
				<div><input type="password" name="userpwd" id="userpwd" class="form-control" placeholder="비밀번호"></div>
				<input type="submit" name="login_btn" id="login_btn" class="btn btn-primary" value="로그인">
			</div>
		</form>
	
		</div>
	
	</section>
	<%@ include file="../etc/footer.jsp" %>
</body>
</html>