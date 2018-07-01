<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="member.model.vo.Member,classes.model.vo.Classes,java.util.ArrayList"%>
	<%
		Member loginUser = (Member) session.getAttribute("loginUser");
		int classNo = 0;
		if(request.getParameter("classNo") != null){
		 classNo = Integer.parseInt(request.getParameter("classNo"));
		}else if(request.getAttribute("classNo") != null){
		classNo =((Integer)request.getAttribute("classNo")).intValue();
		} 
		Classes classes = (Classes)request.getAttribute("classes");
		
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">

<!-- font awesome -->
<script defer
	src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>

<script src="js/jquery-3.3.1.min.js"></script>

<title>Class Join</title>
<style>
.cjoin {
	width: 97%;
	margin: 5px;
	padding-left: 13px;
	boarder: 0;
}

.cjoin tr th {
	border: 0;
}

.cjoin tr td {
	border: 0;
}
</style>

</head>
<body>

	<section>
		<div>
		<form action="/classKing/cJoinApply" method="post">
		<input type="hidden" method="post" name="classNo" value="<%= classNo%>">
			<table class="table cjoin">
				<tr>
					<th colspan="2" style="background: #3b64af; color: white;">&nbsp;<i
						class="far fa-edit" style="color: white;"></i>&nbsp; Class 가입신청
					</th>
				</tr>
				<tr>
					<th style="background: #c5cdd7;">클래스명</th>
					<td><input type="text" name="classTitle" value="<%= classes.getClassTitle() %>" readonly></td>
				</tr>
				<tr>
					<th style="background: #c5cdd7;">카테고리</th>
					<td><input type="text" name="class_category" value="<%= classes.getCategoryName() %>" readonly></td>
				</tr>
				<tr>
					<th style="background: #c5cdd7;">아이디</th>
					<td><input type="text" name="memberId" value="<%= loginUser.getMemberId()%>" readonly></td>
				</tr>
				<tr>
					<th style="background: #c5cdd7;">메시지</th>
					<td><textarea rows="4" cols="30"
							placeholder=" class 장에게 하고싶은 말을 적어주세요." name="applyNote"></textarea></td>
				</tr>
				<tr>
					<th colspan="2"><button class="btn btn-primary center-block"
							type="submit">신청하기</button></th>
				</tr>
			</table>
			</form>
		</div>
	</section>
</body>
</html>