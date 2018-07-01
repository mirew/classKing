<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="member.model.vo.Member,apply.model.vo.Apply, java.util.ArrayList, java.sql.Date"%>
<%
	Member loginUser = (Member) session.getAttribute("loginUser");
	ArrayList<Apply> applylist = (ArrayList<Apply>) request.getAttribute("list");
	int startPage = ((Integer) request.getAttribute("startPage")).intValue();
	int endPage = ((Integer) request.getAttribute("endPage")).intValue();
	int maxPage = ((Integer) request.getAttribute("maxPage")).intValue();
	int currentPage = ((Integer) request.getAttribute("page")).intValue();
	Apply apply = (Apply) session.getAttribute("apply");
	int classNo = Integer.parseInt(request.getParameter("classNo"));
	 
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
<link href="/classKing/css/classApply.css" rel="stylesheet"
	type="text/css">
<link href="/classKing/css/menuBar.css" rel="stylesheet" type="text/css">

<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script src="/classKing/js/jquery.lbslider.js"></script>
<script defer
	src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>

<title>Class Apply</title>

<script>
	function applyStatusUpdate(i, appNo,appId){
		var appId = appId;		
		location.href = "/classKing/aupdate?applyNo="+appNo+"&memberId="+appId+"&page="+<%=currentPage%>
		+"&classNo="+<%=classNo%>+"&appStatus=1"; 		
	}
	function applyStatusUpdate2(i, appNo, appId){		
		location.href = "/classKing/aupdate?applyNo="+appNo+"&memberId="+appId+"&page="+<%=currentPage%>
		+"&classNo="+<%=classNo%>+"&appStatus=2"; 	
	}
</script>
</head>
<body>

	<%@ include file="../etc/header.jsp"%>

	<section>
		<div id="wrap">
			<div id="right_box">
				<!-- 로그인박스 시작 -->
				<%
					if (loginUser != null && loginUser.getMemberId().equals("admin")) {
				%>
				<%@ include file="../etc/loginAdmin.jsp"%>
				<%
					} else if (loginUser != null) {
				%>
				<%@ include file="../etc/loginMember.jsp"%>
				<%
					} else {
				%>
				<%@ include file="../etc/login.jsp"%>
				<%
					}
				%>
				<!-- 로그인박스 끝 -->
				<jsp:include page="../etc/joinCheck.jsp"> 
				  <jsp:param name="classNo" value="<%=classNo %>"/> 
				</jsp:include>
				<!-- 알람박스 시작 -->
				<% if (loginUser != null) { %>
					<%@ include file="../../views/etc/alarm.jsp"%>
				<% } %>
				<!-- 알람박스 끝 -->
			</div>
			<div id="left_box">
				<%@ include file="../etc/menuBar.jsp"%>
				<div id="main_box">
					<div class="board_title">
						<div class="title">
							<i class="fas fa-bullhorn"></i> 가입신청
						</div>

					</div>

					<div id="apply_table" class="board_table">
						<table class="table">
							<tr>
								<th>번호</th>
								<th style="text-align: center;">신청글</th>
								<th>신청자</th>
								<th>신청날짜</th>
								<th>수락</th>
								<th>거절</th>
							</tr>
							<%			
								for (int i = 0; i < applylist.size(); i++) {
									if(classNo == applylist.get(i).getClassNo()){
							%>
							<tr>
								<td><%=applylist.get(i).getApplyNo()%></td>
								<td><%=applylist.get(i).getApplyNote()%></td>
								<td><%=applylist.get(i).getMemberId()%></td>
								<td><%=applylist.get(i).getApplyDate()%></td>
								
								
								<%if(applylist.get(i).getApplyStatus().equals("1")== true){ %>
								<td>
								<input id="button1" type="button" value="수락"										
										style="padding:3px 6px;color:#fff;font-size:14px; float: right;background:#dc3545;"/>								
								</td>
								<td>
									<input id="button2" type="button" value="거절"										
										style=" padding:3px 6px;color:#fff; font-size:14px;background:#17a2b8; float: right;"/>
								</td>
								<%}else if(applylist.get(i).getApplyStatus().equals("2")== true){ %>
								<td>
								<input id="button1" type="button" value="수락"										
										style="padding:3px 6px;color:#fff;font-size:14px; float: right;background:#17a2b8;"/>								
								</td>
								<td>
									<input id="button2" type="button" value="거절"										
										style="padding:3px 6px;color:#fff; font-size:14px;background:#dc3545; float: right;"/>
								</td>
								<%}else{ %>
								<td>
								<input id="button1" type="button" value="수락"										
										style="padding:3px 6px;color:#fff;font-size:14px; float: right;background:#17a2b8;"
										onclick="applyStatusUpdate(<%=i%>, <%=applylist.get(i).getApplyNo()%>,'<%=applylist.get(i).getMemberId()%>');" />								
								</td>
								<td>
									<input id="button2" type="button" value="거절"										
										style="padding:3px 6px;color:#fff; font-size:14px;background:#17a2b8; float: right;"
										onclick="applyStatusUpdate2(<%=i%>, <%=applylist.get(i).getApplyNo()%>,'<%=applylist.get(i).getMemberId()%>');" />
								</td>
								<%} %>
						</tr>
							<%
								}}
							%>

						</table>

					</div>

					

					<div id="board_page">
						<ul class="pagination">
							<%
								if (startPage > 5) {
							%>
							<li class="page-item"><a class="page-link"
								href="/classKing/alist?page=<%=startPage - 1%>">[prev]</a></li>
							<%
								} else {
							%>
							<li class="page-item"><a class="page-link">prev</a></li>
							<%
								}
							%>

							<%
								for (int p = startPage; p <= endPage; p++) {
									if (p == currentPage) {
							%>
							<li class="page-item"><a class="page-link"><%=p%></a></li>
							<%
								} else {
							%>
							<li class="page-item"><a class="page-link"
								href="/classKing/alist?page=<%=p%>&classNo=<%=classNo%>"><%=p%></a></li>
							<%
								}
							%>
							<%
								}
							%>

							<%
								if (endPage < maxPage) {
							%>
							<li class="page-item"><a class="page-link"
								href="/classKing/alist?page=<%=endPage + 1%>&classNo=<%=classNo%>">[next]</a></li>
							<%
								} else {
							%>
							<li class="page-item"><a class="page-link">next</a></li>
							<%
								}
							%>

						</ul>
					</div>
				</div>
			</div>
		</div>
	</section>
	<%@ include file="../etc/footer.jsp"%>

</body>
</html>