<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="member.model.vo.Member,qna.model.vo.Qna"%>
<%
	Member loginUser = (Member) session.getAttribute("loginUser");
	Qna q=(Qna)request.getAttribute("qna");
	int currentPage=((Integer)request.getAttribute("page")).intValue();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
   rel="stylesheet" />
<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/menuBar.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/classBoardMake.css" rel="stylesheet" type="text/css">

<script src="/classKing/js/jquery-3.3.1.min.js"></script>

<title>classKing</title>
<script type="text/javascript">
	function golist() {
location.href="/classKing/qlist";
	}
</script>
</head>
<body>
<%@ include file="../../views/etc/alramAlert.jsp" %>
   <%@ include file="../etc/header.jsp" %>
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

            <!-- 알람박스 시작 -->
            <% if (loginUser != null) { %>
					<%@ include file="../../views/etc/alarm.jsp"%>
				<% } %>
            <!-- 알람박스 끝 -->
         </div>
         <div id="left_box">

            <!-- 게시판 시작 -->
      <div id="main_box">
               <div id="container">
                  <table class="table table-bordered">
                     <thead>
                     <!-- <caption>
                        <h2 style="text-align: center">글쓰기</h2>
                     </caption> -->
                     <div class="board_title">
                  <div class="title">
                     <i class="fas fa-bullhorn"></i> Q&A 수정
                  </div>
               </div>
                     <tbody>
                        	<form action="/classKing/qmodify" method="post"
									encType="multipart/form-data">
							
									 <input type="hidden" name="qwriter"
										value="<%=loginUser.getMemberId()%>" >
									<input type="hidden" name="qnanum" value="<%=q.getQnaNo() %>">
									<input type="hidden" name="page" value="<%=currentPage %>">
									<input type="hidden" name="ofile" value="<%=q.getQnaOriginalFile()%>">
									<input type="hidden" name="rfile" value="<%=q.getQnaRenameFile() %>">
									

									<tr>
										<th style="border: 0; color: #777;">제목</th>
										<td style="border: 0;"><input type="text"
											name="subject" class="form-control" value="<%=q.getQnaTitle() %>" required/></td>
									</tr>
									<tr>
										<th style="border: 0; color: #777">내용</th>
										<td style="border: 0;"><textarea id="contents" cols="50"
												 name="content"
												class="form-control" required><%=q.getQnaNote() %></textarea></td>
									</tr>
									<tr>
										<th style="border: 0; color: #777">첨부파일</th>
										<td>
                  <% if(q.getQnaOriginalFile() != null){ %>
                     <%=q.getQnaOriginalFile()%>
                     <br>
                     <input type="file" name="upfile">   
                  <% } else{ %>
                  <input type="file" name="upfile">
                  <% } %>               
               </td>
										
										
									</tr>

									<tr>
										<td colspan="2" style="border: 0;"><input type="button"
											value="글목록" class="btn pull-left" onclick="golist();"
											style="background: #3b64af; color: white;" /> 
							
											
											<input
											type="submit" value="등록" class="btn pull-right"
											style="margin-right: 5px; background: red; color: white;" />
											
											

											<!-- <a class="btn btn-default" onclick="sendData()"> 등록 </a>
                    <a class="btn btn-default" type="reset"> reset </a>
                    <a class="btn btn-default" onclick="javascript:location.href='list.jsp'">글 목록으로...</a> -->
										</td>
									</tr>
								</form>
							</tbody>
						</table>
					</div>
					<!-- 게시판 끝 -->
				</div>
			</div>
	</section>
	<%@ include file="../etc/footer.jsp"%>
</body>
</html>