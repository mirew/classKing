<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="member.model.vo.Member,report.model.vo.Report" %>
	<% 
	
 int caNo=((Integer)request.getAttribute("caNo")).intValue(); 
	Report reportA=(Report)request.getAttribute("reportA");
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

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>


<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/jquery.lbslider.js"></script>

<title>신고하기</title>

<style>
table {
	width: 100%;
	border: 0;
}

table tr th {
	border: 0;
}

table tr td {
	border: 0;
}
</style>
</head>
<body>

	<!-- 일정추가 시작 -->
	<div id="cal_board" style="margin: 5px;">
	
	
			<table class="table table-bordered" style="border: 0;">
				
				<caption
					style="padding-left: 10px; background: #3b64af; color: white;">신고 상세</caption>
				<tr>
					<th style="background: #c5cdd7; width:90px;">클래스명</th>
					<td style="word-break:break-all" ><div  style="width:260px"><%=reportA.getClassName().replace("\r\n","<br>") %></div></td>
				</tr>
				<tr>
				<%if(caNo==1) { %>
					<th style="background: #c5cdd7;  width:90px;">게시판명</th>
					<td style="width:260px"><%=reportA.getBoardTitle() %></td>
					<%}else{ %>
						<th style="background: #c5cdd7;  width:90px;">댓글</th>
					<td style="width:260px"><%=reportA.getCommentsNote() %></td>
					<%} %>
					</tr>
					<tr>
					<th style="background: #c5cdd7;  width:90px;">신고 내용</th>
					<td style="word-break:break-all" ><div  style="width:260px"><%=reportA.getReportNote().replace("\r\n","<br>") %></div></td>
					</tr>
					<tr>
					<th style="background: #c5cdd7;  width:90px;">신고날짜</th>
					<td style="width:260px"><%=reportA.getReportDate() %></td>
				</tr>
					
				
				<tr>
										<th style="background: #c5cdd7;  width:90px;">첨부파일</th>
										<%if(reportA.getReportOriginFile()!=null) { %>
										<td style=" width:260px;">
											<%if(caNo==1)  {%>
										<i class="fas fa-paperclip"></i><a href="/classKing/rbdown?ofile=<%=reportA.getReportOriginFile()%>&rfile=<%=reportA.getReportRenameFile() %>"><%=reportA.getReportOriginFile()%></a>
												<% }else{ %>
												<i class="fas fa-paperclip"></i><a href="/classKing/rcdown?ofile=<%=reportA.getReportOriginFile()%>&rfile=<%=reportA.getReportRenameFile() %>"><%=reportA.getReportOriginFile()%></a>
										<%} %>	
										</td>	
										<%}else{ %>
										<td style="width:260px;">첨부파일 없음</td>
										<%} %>
				</tr>
				<tr>
					<td colspan="2" align="center" style="border: none;"><input
						type="button" class="btn btn_report" value="확인"
						style="background: pink;" onclick="window.close();"></td>
				</tr>
			
			</table>
	
	</div>
	
</body>
</html>