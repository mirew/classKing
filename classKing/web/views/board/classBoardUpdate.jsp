<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="board.model.vo.Board,member.model.vo.Member"%>
<% 
 	Board board = (Board)request.getAttribute("board");
	int boardNo = ((Integer)request.getAttribute("boardNo")).intValue();
 	int currentPage = ((Integer)request.getAttribute("page")).intValue();
 	int classNo = ((Integer)request.getAttribute("classNo")).intValue();
 	Member loginUser = (Member)session.getAttribute("loginUser");
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
<link href="/classKing/css/menuBar.css" rel="stylesheet" type="text/css">

<link href="/classKing/css/classBoardDetail.css" rel="stylesheet"
	type="text/css">
<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script src="/classKing/js/jquery.lbslider.js"></script>
<script defer
	src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>
<title>Class King</title>

<script>
function showBoardUpdate(){
	location.href="/classKing/boriginup?page="+<%= currentPage%>+"&classNo="+<%=classNo%>+"&boardNo="+<%=boardNo%>;

}
</script>
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
				
		
					
				<form action="/classKing/boriginup" method="post" enctype="multipart/form-data">
					<input type="hidden" name="boardNo" value="<%= boardNo%>">
					<input type="hidden" name="classNo" value="<%= classNo%>">
					<input type="hidden" name="page" value="<%= currentPage%>">
					<input type="hidden" name="ofile" value="<%= board.getBoardOriginalFile() %>">						<input type="hidden" name="rfile" value="<%= board.getBoardRenameFile() %>">
					
					<div id="main_box">
					<div class="board_title">
					<div class="title detail_title">
						<i class="fas fa-bullhorn"></i> 글수정
						<div class="button_box">
							
							<button type="submit" class="btn btn-success" 
							onclick="location.href='/classKing/boriginup?page=<%= currentPage%>&classNo=<%=classNo%>&boardNo=<%=boardNo%>">수정</button>
							<button type="button" name="delete" class="btn btn-danger" 
							onclick="showBoardDelete();">삭제</button>
					</div>
					</div>
					</div>
					
					<div id="container">	
						<table class="table table-bordered">
							<thead>
							<tr>
								<th style="border: 0; color: #777">주제</th>
								<td style="border: 0;">
									<div style="padding:5px;">
									<select name="boardTag"
										class="form-control" class="step01" style="width: 156px;"
										onchange="BoardCategoryMgr.onChangeBoardCategory(); return false;"
										id="boardCategory">
										<option value="02" selected>잡담</option>
										<option value="01">공지</option>
										<option value="03">질문</option>
										<option value="04">과제</option>
										<option value="05">기타</option>
										</select>
									</div>
										</td>
							</tr>

							<tr>
								<th style="border: 0; color: #777;">제목</th>
								<td style="border: 0;">
								<div style="padding:5px;">
								<input type="text"
											name="boardTitle" value="<%=board.getBoardTitle() %>"
											class="form-control" />
								</div>
								</td>
							</tr>
							
							<tr>
								<th style="border: 0; color: #777">내용</th>
								<td style="border: 0; ">
								<div style="padding:5px;">
								<textarea id="contents" cols="50"
												name="boardNote" class="form-control"><%=board.getBoardNote() %></textarea>
								</div>
								</td>
							</tr>
							<tr>
								<th style="border: 0; color: #777">첨부파일</th>
								<td style="border: 0;">
								<div style="padding:6px;">
									<% if(board.getBoardOriginalFile() != null) {%>
										 <%= board.getBoardOriginalFile()%>
									<% } %>
									<input type="file" name="upfile" value="">	
								</div>						 
								</td>
							</tr>
							<tr>
							<th colspan="2" style="border:0; text-align:center;">
							<button type="button" name="report" class="btn" style="background: gray; color:white;"
							onclick="location.href='/classKing/blist?page=<%= currentPage%>&classNo=<%=classNo%>&boardNo=<%=boardNo%>'">목록</button>
							</th>
							</tr>				
						</table>						
					</div>
				</div>
			</form>
			</div>
	</section>


	<%@ include file="../etc/footer.jsp"%>

</body>
</html>