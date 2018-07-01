<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member"%>
<%
int classNo = Integer.parseInt(request.getParameter("classNo"));
Member loginUser = (Member) session.getAttribute("loginUser");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/menuBar.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/classBoardMake.css" rel="stylesheet"
	type="text/css">
<script defer
	src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>

<script src="/classKing/js/jquery-3.3.1.min.js"></script>

<title>classKing</title>

</head>
<body>

	<%@ include file="../etc/header.jsp"%>


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
				<!-- 가입신청버튼 -->
				<jsp:include page="../etc/joinCheck.jsp"> 
				  <jsp:param name="classNo" value="<%=classNo %>"/> 
				</jsp:include>
				<!-- 가입신청버튼 끝 -->
				<!-- 알람박스 시작 -->
				<% if (loginUser != null) { %>
					<%@ include file="../../views/etc/alarm.jsp"%>
				<% } %>
				<!-- 알람박스 끝 -->
			</div>
			<div id="left_box">

				<!-- menu -->
				<%@ include file="../etc/menuBar.jsp"%>
				<!-- menu -->

				<!-- 게시판 시작 -->
				<form action="/classKing/binsert" method="post" encType="multipart/form-data">
				<input type="hidden" name="classNo" value="<%=classNo%>">
				<input type="hidden" name="memberId" value="<%=loginUser.getMemberId()%>">
			
				<div id="main_box">
					<div id="container">
						<table class="table table-bordered">
							<thead>
								<!-- <caption>
								<h2 style="text-align: center">글쓰기</h2>
							</caption> -->
								<div class="board_title">
									<div class="title">
										<i class="far fa-edit"></i> 글쓰기
									</div>
								</div>

									<tr>
										<th style="border: 0; color: #777"><div style="font-size:10pt;margin-bottom:10px;">카테고리</div></th>
										<td style="border: 0;">
										
										<select name="boardTag"
											class="form-control" class="step01" style="width: 156px;"
											onchange="BoardCategoryMgr.onChangeBoardCategory(); return false;"
											id="bTag">
												<option value="02" selected>잡담</option>
												<option value="01">공지</option>
												<option value="03">질문</option>
												<option value="04">과제</option>
												<option value="05">기타</option>

										</select>
					
										</td>
									</tr>
									
									<tr>
										<th style="border: 0; color: #777; ">
										<div style="font-size:10pt; margin-bottom:10px;">제목</div></th>
										<td style="border: 0;"><input type="text"
											placeholder="제목을 입력하세요. " name="boardTitle"
											class="form-control" id="edit_remove" maxlength="33"/></td>
									</tr>
									<tr>
										<th style="border: 0; color: #777; padding-bottom:270px;"><div style="font-size:10pt;margin-bottom:10px; ">내용</div></th>
										<td style="border: 0;"><textarea id="contents" cols="50"
												placeholder="내용을 입력하세요. " name="boardNote"
												class="form-control" id="edit_remove2" maxlength="1000"></textarea></td> 
									</tr>
									<tr>
										<th style="border: 0; color: #777; "><div style="font-size:10pt;margin-bottom:10px;">첨부파일</div></th>
										<td style="border: 0;">
										<input type="file"
											placeholder=" 파일을 선택하세요. " name="upfile" class="form-control"  id="edit_remove3"/></td>
									</tr>

									<tr>
										<td colspan="2" style="border: 0;">
										<input type="button"
											value="목록" class="btn pull-left"
											onclick="location.href='/classKing/blist?classNo=<%= classNo%>'"
											style="background: #3b64af; color: white;" /> 
										<input
											type="submit" value="등록" class="btn pull-right"
											style="margin-right: 5px; background: red; color: white;" />
											

											<!-- <a class="btn btn-default" onclick="sendData()"> 등록 </a>
                    <a class="btn btn-default" type="reset"> reset </a>
                    <a class="btn btn-default" onclick="javascript:location.href='list.jsp'">글 목록으로...</a> -->
										</td>
									</tr>
						</table>
					</div>
					<!-- 게시판 끝 -->

				</div>
				</form>
			</div>
	</section>


	<%@ include file="../etc/footer.jsp"%>

</body>
</html>