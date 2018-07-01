<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="member.model.vo.Member,notice.model.vo.Notice,java.sql.*,java.util.*"%>
<%
	Member loginUser = (Member) session.getAttribute("loginUser");
	int currentPage = ((Integer)request.getAttribute("page")).intValue(); 
	Notice notice=(Notice)request.getAttribute("notice");
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
<link href="/classKing/css/board.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/classBoardMake.css" rel="stylesheet" type="text/css">
<style type="text/css">
	.table th,.table td{
		border:none;
	}
	
</style>

<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	function deleteCheck(){
		
		if(confirm("정말로 삭제하시겠습니까?") == true){
			location.href="/classKing/ndel?no=<%=notice.getNoticeNo()%>&page=<%= currentPage %>";			
			
		}else{
			return false;
		}
	}



</script>
<script defer
	src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>

<title>Class King</title>
</head>
<body>
<%@ include file="../../views/etc/alramAlert.jsp" %>
	<%@ include file="../etc/header.jsp" %>
	<section>
      <div id="wrap">

         <div id="right_box">
            <!-- 로그인박스 시작 -->
            <%@ include file="../etc/login.jsp"%>
            <!-- 로그인박스 끝 -->

            <!-- 알람박스 시작 -->
            <% if (loginUser != null) { %>
					<%@ include file="../../views/etc/alarm.jsp"%>
				<% } %>
            <!-- 알람박스 끝 -->
         </div>
         <div id="left_box">

            <!-- 게시판 시작 -->
            <div id="main_box" style="margin-top:0px;">
               <div id="container">
					<div class="board_title">
						<div class="title">
						   <i class="fas fa-bullhorn"></i> 공지사항 수정
						</div>
					</div>
					<form action="/classKing/nupdate" method="post" enctype="multipart/form-data">
					<input type="hidden" value="<%=notice.getNoticeNo() %>" name="no">
					<input type="hidden" value="<%=currentPage %>" name="page">
					<input type="hidden" value="<%=notice.getNoticeOriginalFile()%>" name="ofile">
					<input type="hidden" value="<%=notice.getNoticeRenameFile() %>" name="rfile">
					
                    <table class="table" style="border:solid 1px #DCDFE4">
                        <tr>
               			  <th style="color: #777;vertical-align:middle;">제목</th>
                          <td>
                          	<input type="text" placeholder="제목을 입력하세요." name="n_title" class="form-control" value="<%=notice.getNoticeTitle()%>"/></td>
                         </tr>
                         <tr>
                            <th style="color:#777">내용</th>
                            <td>
                            	<textarea id="contents" cols="50" name="n_content" class="form-control"><%=notice.getNoticeNote() %></textarea>
                            </td>
                         </tr>
                         <tr>
                            <th style="color:#777;vertical-align:middle;">첨부파일</th>
                            <td>
                            	<% if(notice.getNoticeOriginalFile() != null){ %>
                            		<%=notice.getNoticeOriginalFile() %><br>
                            		<input type="file" name="upfile">
                            	<% }else{ %>
                            		<input type="file" placeholder="파일을 선택하세요." name="upfile" class="form-control" />                          		
                            	<% } %>      
                             </td>
                         </tr>

                         <tr>
                            <td colspan="2" style="border: 0;">
                            	<input type="button" value="글목록" class="btn btn-dark" style="text-aglin:left" onclick="location.href='/classKing/nlist?page=<%=currentPage%>'"/>
                            	<div style="float:right">
	                                <button type="submit" class="btn btn-primary">수정</button>
                                </div>
                            </td>
                         </tr>
                                            
                  </table>
                  </form> 
               </div>
               <!-- 게시판 끝 -->
            </div>
         </div>
         </div>
	</section>
	<%@ include file="../etc/footer.jsp" %>

</body>
</html>