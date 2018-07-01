<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="comment.model.vo.Comment"%>
    <%
   // Comment comment = (Comment) session.getAttribute("comment");
    
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="css/reset.css" rel="stylesheet" type="text/css">
<link href="css/main2.css" rel="stylesheet" type="text/css">
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
<script src="js/jquery.lbslider.js"></script>

<title>classKing</title>

<style>
  table {
    width: 100%;
    border: 0;
  }
  table tr th{
    border: 0;
  }
  table tr td{
    border: 0;
  }
</style>
</head>
<body>

<!-- 댓글 수정 시작 -->
	<div id="cal_board" style="margin: 5px;">
		<form action="/bcommentup" method="post">
			<table class="table table-bordered" style="border: 0;">
			<thread>
				<caption
					style="padding-left: 10px; background: #3b64af; color: white;"><i class="far fa-edit"></i>&nbsp;댓글 수정</caption>
				
								<tr>
					<th style="background: #c5cdd7;">제목</th>
					<td></td>
				</tr>	
				<tr>
					<th style="background: #c5cdd7;">ID</th>
					<td></td>
				</tr>
				<tr>
					<th style="background: #c5cdd7;">댓글</th>
					<td><textarea cols="36" rows="7"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" align="center" style="border:none;"><button type="submit" style="align: center;">수정</button></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 댓글수정 끝! -->


</body>
</html>