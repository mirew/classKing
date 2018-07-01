<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member,notice.model.vo.Notice"%>
<%
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
<link href="/classKing/css/classBoardMake.css" rel="stylesheet" type="text/css">

<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">


</script>

<title>classKing</title>
<style type="text/css">
	.table th,.table td{
		border:none;
	}
</style>


</head>
<body>
<%@ include file="../../views/etc/alramAlert.jsp" %>
   <%@ include file="../etc/header.jsp"%>
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
						   <i class="fas fa-bullhorn"></i> 공지사항 글쓰기
						</div>
					</div>
					<form action="/classKing/ninsert" method="post" enctype="multipart/form-data">
                    <table class="table" style="border:solid 1px #DCDFE4">
                        <tr>
               			  <th style="color: #777;vertical-align:middle;">제목</th>
                          <td>
                          	<input type="text" placeholder="제목을 입력하세요. " name="n_title" class="form-control" maxlength="33"/></td>
                         </tr>
                         <tr>
                            <th style="color:#777">내용</th>
                            <td>
                            	<textarea id="contents" cols="50" placeholder="내용을 입력하세요." name="n_content" class="form-control" maxlength="1000"></textarea>
                            </td>
                         </tr>
                         <tr>
                            <th style="color:#777;vertical-align:middle;">첨부파일</th>
                            <td>
                            	<input type="file" placeholder="파일을 선택하세요." name="upfile" class="form-control" />
                             </td>
                         </tr>

                         <tr>
                            <td colspan="2" style="border: 0;">
                            	<input type="button" value="글목록" class="btn btn-dark" style="text-aglin:left"/>
                            	<div style="float:right">
	                            	<button type="button" class="btn btn-danger" style="margin-right: 5px;">취소</button>
	                                <button type="submit" class="btn btn-primary">등록</button>
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
   <%@ include file="../etc/footer.jsp"%>
</body>
</html>