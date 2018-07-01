<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="member.model.vo.Member,board.model.vo.Board, java.util.*, java.sql.Date, java.util.ArrayList, comment.model.vo.Comment"%>
<%
	Member loginUser = (Member) session.getAttribute("loginUser");
	Board board = (Board)request.getAttribute("board");
	int boardNo =  Integer.parseInt(request.getParameter("boardNo"));
	int classNo =  Integer.parseInt(request.getParameter("classNo"));
	int currentPage =  Integer.parseInt(request.getParameter("page"));
	 ArrayList<Comment> commentlist = (ArrayList<Comment>)request.getAttribute("bComment");
	int classking = ((Integer)request.getAttribute("classking")).intValue();
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
<script>
//$commentup = $('#commentup').attr('disabled', true); 
$commentup = $('#commentup').attr('enabled', true);
	function showPopupReport() {
		 window.open("/classKing/views/report/reportPopup.jsp?classNo="+<%=classNo%>+"&boardNo="+<%=boardNo%>,"게시글신고","width=400, height=380, left=100, top=50");
	}
	function showBoardUpdate(){
		location.href="/classKing/bupdate?page="+<%= currentPage%>+"&classNo="+<%=classNo%>+"&boardNo="+<%=boardNo%>;

	}
	function boardComDelete(com){
		   if(confirm("정말로 삭제하시겠습니까?") == true){
				location.href="/classKing/bcommentdelete?commentNo="+com.id+"&boardNo="+<%= board.getBoardNo()%>+"&page="+<%= currentPage%>+"&classNo="+<%= classNo%>+"&classking="+<%=classking%>;
			}else{
				return false;
			}
	    }
	   // alert('adsfasdf');
	function commentReport(creport){
		var creportNo = $('.creport').attr('id');
		alert(creportNo);  
		 <%-- window.open("/classKing/views/comment_report/commentReport.jsp?commentNo="+creport.id+"&classNo="+<%=classNo%>+"&boardNo="+<%=boardNo%>,"width=400, height=330, left=100, top=50");--%>	
	}
	function boardComUpdate(i, comUp){
		var MemberId ="<%= loginUser.getMemberId() %>";
		console.log(comUp);
		$("#trcom"+i).after(
				 "<tr id='tr"+comUp+"'>"+
                 "<td style='border:0; text-align:right;border:0;width:98px;'> " +                        
                       "<div style='padding-right:15px; padding-top:35px; margin-right:10px; margin-left:5px;'>"+
                       MemberId  + 
                       "</div>"+
                 "</td>"+
				
                 "<td style='width:500px; border:0; padding-right:15px;' colspan='2'>"
                 +"<textarea id='commentParentText2'"
                 +"class='form-control col-lg-12' style='width: 100%' name='commentsNote' "+
                          "rows='4' cols='100' required></textarea>"
                 +"</td>"
                 +"<td style='border:0; width:40px;'>"
                 //댓글 수정 취소버튼 생성을 위해 테이블만듬!!
                 +"<table><tr><td>"
                 +"<div style=' padding-top:35px;'>"
                 +"<input type='button' id='commentParentSubmit'"
                 +"name='commentParentSubmit' class='' value='확인' onclick='comcomUpdate("+comUp+");'>"
                 +"</div></td></tr>"
                 +"<tr><td><input type='button' id='commentParentSubmit'"
                 +"name='commentParentSubmit' class='' value='취소' onclick='comcomUpdateCancle("+comUp+","+i+");'>"
                 +"</td></tr></table>"
                 +"</td></tr>"               
		);	
			$("#commentup"+i).attr("disabled",true);
		}
	/* 댓글 수정 Update 버튼 확인 누룰 시  */
	function comcomUpdate(comUp){
		var comNote = $("#commentParentText2").val();
		
		var comupup =comUp;
		location.href = "/classKing/bcommentup?commentNo="+comupup+"&boardNo="+<%= board.getBoardNo()%>+"&page="+<%= currentPage%>+"&classNo="+<%= classNo%>+"&commentsNote="+comNote+"&classking="+<%=classking%>; 
	}
	/* 댓글 update 취소 버튼 누를 시 */
	function comcomUpdateCancle(comUp,i){
		 $("#tr"+comUp).remove();
		 $("#commentup"+i).attr("disabled",false);
	}
</script>
<title>Class King</title>
</head>
<body>
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
			<%@ include file="../etc/menuBar.jsp"%>
			<div id="main_box">
			<form action="/classKing/bdelete" method="post" onsubmit="return boardComDelete();">
				<input type="hidden" name="classNo" value="<%=classNo%>">
				<input type="hidden" name="boardNo" value="<%=boardNo%>">
				<div class="board_title">
					<div class="title detail_title" style="margin:5px; padding-right:10px; padding-left:5xp;">
						<i class="fas fa-bullhorn"></i> 게시글
						<div class="button_box">
						<% if(!loginUser.getMemberId().equals(board.getMemberId()) && classking != 0 && !loginUser.getMemberId().equals("admin")){ %>
							<button type="button" name="report" class="btn btn-warning"
							onclick="showPopupReport();" style="color:white;">신고</button>
							<%} %>
							<% if(loginUser.getMemberId().equals(board.getMemberId())==true && classking != 0 && !loginUser.getMemberId().equals("admin")){ %>
							<button type="button" name="modified" class="btn btn-success" 
							onclick="showBoardUpdate();" >수정</button>
							<button type="submit" name="delete" class="btn btn-danger"
							>삭제</button>
							<%} %>
							<% if(loginUser.getMemberId().equals("admin")==true || classking == 0 ){ %>
							<button type="button" name="report" class="btn btn-warning"
							onclick="showPopupReport();" style="color:white;">신고</button>
							<button type="button" name="modified" class="btn btn-success" 
							onclick="showBoardUpdate();">수정</button>
							<button type="submit" name="delete" class="btn btn-danger">삭제</button>
							<%} %>
						</div>
					</div>

				</div>
				</form>
				<div id="board_detail">
					<!-- talbe -->
					
					<table class="table table-bordered">
									<tr>
										<th style="color: #777; width:100px;"><div style="font-size:10pt;margin-top:5px;">카테고리</div></th>
										<td style=""><div style="float:left;margin:10px; padding:5px;"><%=board.getBoardTagName()%></div>
										<div style="float:right;margin:10px; padding:5px;"><%=board.getBoardDate() %></div></td>
									</tr>
									<tr>
										<th style="color: #777;"><div style="font-size:10pt;margin-top:5px;">제목</div></th>
										<td style=""><div style="margin:10px; padding:5px;"><%=board.getBoardTitle().replace("\r\n","<br>") %></div></td>
									</tr>
									<tr>
										<th style=" color: #777"><div style="font-size:10pt;margin-top:5px; height:300px;">내용</div></th>
										<td style=""><div style="margin:10px; padding:5px;"><%=board.getBoardNote().replace("\r\n","<br>") %></div></td> 
									</tr>
									<tr>
										<th style=" color: #777"><div style="font-size:10pt;margin-top:5px;">첨부파일</div></th>
										<td style=""><div style="margin:10px;">
										<% if(board.getBoardOriginalFile()!=null){ %>
								<i class="fas fa-paperclip"></i>&nbsp;
								<a href="/classKing/bfdown?ofile=<%= board.getBoardOriginalFile()%>&rfile=<%=board.getBoardRenameFile()%>"><%= board.getBoardOriginalFile() %></a>
							<% }else{ %>
								<input type="text" class="form-control" value=" 첨부파일 없음" readonly>
							<% } %>
										</div></td>
									</tr>
									<tr>
									<th colspan="2" style="text-align:center; ">
									<button type="button" id="list" class="btn" style="background:gray; color:white;"
									onclick="location.href='/classKing/blist?page=<%= currentPage%>&classNo=<%=classNo%>&boardNo=<%=boardNo%>'">목록</button>
									</th>
									</tr>
							</table>
							
					</div>
						<div class="container">
						
							<div class="row">
								<div class="col-md-10">
				<!-- --------------------------댓글 입력 테입블 ---------------------------- -->
						<form action="/classKing/bcommentinsert" method="post">
							<input type="hidden" name="loginUser" value="<%= loginUser.getMemberId() %>">
							<input type="hidden" name="boardNo" value="<%= boardNo%>">
							<input type="hidden" name="page" value="<%=currentPage%>">
							<input type="hidden" name="classNo" value="<%=classNo %>">
							<input type="hidden" name="classking" value="<%=classking %>">
							<%-- <input type="hidden" name ="classNo" value="<%= classNo%>"> --%>
							
							<div style="width:670px;background: #EEEEEE; ">
								<table class="table table-condensed">
										<tr>
											<td style="border:0; text-align:right;border:0;">				
													<div style="width:100px; padding-right:15px; padding-top:35px; margin-right:10px;">
												
														<%= loginUser.getMemberId() %>
													</div>
											</td>
										
										
											<td style="width: 100%; border:0; padding-top:10px; padding-bottom:10px;">
												<textarea class="form-control col-lg-12" style="width: 100%" name="commentsNote"
														rows="4" cols="100" required></textarea>
											</td>
											<td style="border:0;">
											<div style="padding-left:15px; padding-top:35px; margin-right:10px;">
												<input type="submit" id="addBoardComment"
														name="addBoardComment" class="btn btn-primary" value="등록">
											</div>

											</td>
											</tr>

									</table>
							</div>
							</form>
				<!-- --------------------------댓글 입력 테입블 끝 ---------------------------- -->
				<!-- ------------------------댓글 출력 table------------------------------------ -->
				
					
					<%if(commentlist.size() != 0){ %>
				<div style="background: #EEEEEE; padding:10px; width:670px;">
								<table id="commentTable" class="table table-condensed">
									<tr>
									<td style="border:0; text-align:center; width:100px;">아이디</td>
									<td style="text-align:center; width:365px; border:0;">댓글내용</td>
									<td style="text-align:center; width:145px; border:0;">날짜</td>
									<td style="text-align:center; border:0;"></td>
									</tr>
									<tr><td style="border:0; width:100px;" ><hr></td>
									<td style="border:0;"><hr></td>
									<td style="border:0;"><hr></td>
									<td style="border:0;"><hr></td></tr>
									<% for(int i =0; i<commentlist.size(); i++){ %>
									
									<tr id="trcom<%= i%>">
									
									<td style="border:0; padding-left:20px;"><%= commentlist.get(i).getMemberId() %></td>									
									<td style="border:0; padding-left:10px;"><%= commentlist.get(i).getCommentNote()%></td>
									<td style="border:0; padding-left:20px;"><%= commentlist.get(i).getCommentDate()%></td>
									<td style="margin:0; padding-bottom:3px; border:0;">
									<%  if(loginUser.getMemberId().equals("admin")==true || classking == 0 ){ %>
									<table>
									<tr>
									<td style="margin: 0; padding-bottom: 3px; border: 0;">
									<input type="button" class="creport" 
									onclick="window.open('/classKing/views/comment_report/commentReport.jsp?commentNo=<%=commentlist.get(i).getCommentNo() %>&classNo=<%=classNo %>&boardNo=<%=boardNo %>'
									,'댓글신고','width=397, height=380, left=100, top=50');" value="신고">										
									</td>
									</tr>
									<tr>
									<td><input type="button" class="commentup" id="commentup<%=i %>" onclick="boardComUpdate(
											<%= i%>,
											<%= commentlist.get(i).getCommentNo() %>
											
											);" value="수정"></td>
									<tr>
									<td style="margin: 0; padding: 0; border: 0;">
									<input
											type="button" value="삭제" id="<%= commentlist.get(i).getCommentNo() %>" onclick="boardComDelete(this);"></td>
									</tr>
									</table>
								<%} else if(!loginUser.getMemberId().equals(commentlist.get(i).getMemberId()) && classking !=0){ %>
									
									<input type="button" class="creport" onclick="window.open('/classKing/views/comment_report/commentReport.jsp?commentNo=<%=commentlist.get(i).getCommentNo() %>&classNo=<%=classNo %>&boardNo=<%=boardNo %>','댓글신고','width=397, height=380, left=100, top=50');" value="신고">
								
									
								<% }else if(loginUser.getMemberId().equals(commentlist.get(i).getMemberId())==true && classking !=0){%>
										<table>
										<tr>
										<td><input type="button" class="commentup" id="commentup<%=i %>" onclick="boardComUpdate(
												<%= i%>,
												<%= commentlist.get(i).getCommentNo() %>
											
												);" value="수정"></td>
										<tr>
										<td><input
												type="button" value="삭제" id="<%= commentlist.get(i).getCommentNo() %>" onclick="boardComDelete(this);"></td>
										</tr>
										</table>
								<%}%>
							
									</td>
									</tr>
								
									<tr><td style="border:0;"><hr></td><td style="border:0;"><hr></td><td style="border:0;"><hr></td><td style="border:0;"><hr></td></tr>								
									<% } %>
								</table>
				</div>
			<% } %>
		</div>
	</div>
</div>
	</section>
	<%@ include file="../etc/footer.jsp"%>
</body>
</html>