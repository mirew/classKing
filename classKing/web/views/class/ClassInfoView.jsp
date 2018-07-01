<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member,classes.model.vo.Classes"%>
<%
	Member loginUser = (Member) session.getAttribute("loginUser");
	Classes classes = (Classes)request.getAttribute("class");
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css"
	href="/classKing/css/normalize.css" />
<link rel="stylesheet" type="text/css" href="/classKing/css/demo.css" />
<link rel="stylesheet" type="text/css"
	href="/classKing/css/component.css" />
	<script defer
	src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script src="/classKing/js/jquery.lbslider.js"></script>
<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/menuBar.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/classMake.css" rel="stylesheet"
	type="text/css">

<title>Class King</title>
<script src="/classKing/js/jquery-3.3.1.min.js"></script>

<script>
	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#profile').attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}

	  
	   
	   
	function formValidation() {
		 if(nameCheck==false){
		       alert("유효한 아이디가 아닙니다. ID를 다시 확인해주세요");
		       $("#InputClassName").focus();
		       return false;
		}else if($("#InputClassNotice").val().length>33){
		       alert("클래스 설명은 최소 1글자에서 최대 33글자까지 가능합니다.");
		       $("#InputClassNotice").focus();
		       return false;
		    }else if ($("#InputClassName").val().length>16) {
		       alert("클래스 명은 최소 1글자 최대 16글자까지 가능합니다");
		       $("#InputClassName").focus();
		       return false;
		    }else{
		       return submitTf=true;
		    }
	    
	 }
	 
	function nameVerification(){
		nameCheck=false;
	}		
	function classDel(){
		if(confirm("정말로 삭제하시겠습니까?") == true){
			$.ajax({
				url : "/classKing/cdel",
				data : {classNo : "<%=classes.getClassNo()%>"},
				type : "get",
				datatype: "json",
				success : function(data){
					if(data.result > 0){
						alert("클래스가 삭제되었습니다.");
						location.href='/classKing/main.jsp'
					}
				},error:function(a,b,c){
					console.log("error: " + a + ", " + b + ", " + c)
				}
			});
		}
	}

</script>



</head>
<body>
<%@ include file="../../views/etc/alramAlert.jsp" %>
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
				  <jsp:param name="classNo" value="<%=classes.getClassNo() %>"/> 
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
							<i class="fas fa-bullhorn"></i> 클래스 관리
						</div>
					</div>
					<form action="/classKing/cupdate" method="post" encType="multipart/form-data" onsubmit="return formValidation();">
						<input type="hidden" name="originimg" value="<%=classes.getClassOriginalImg()%>"/>
						<input type="hidden" name="renameimg" value="<%=classes.getClassRenameImg()%>"/>
						<input type="hidden" name="classNo" value="<%=classes.getClassNo()%>"/>
						<div class="container">
							<table class="table table-bordered">
								<tbody>
									
									<tr>
										<th><label for="InputClassName">Class Name</label></th>
										<td><input type="text" class="form-control"
											style="width:100%; float: left;" class="form-control"
											id="InputClassName" name="className"  value="<%=classes.getClassTitle()%>"	placeholder=" 클래스명을 입력해주세요." readonly > </td>
									</tr>
									<tr>
										<th><label for="InputClassNotice">Class Notice</label></th>
										<td><textarea cols="10" placeholder="클래스 설명을 입력해주세요."
												id="InputClassNotice" name="classNotice"
												class="form-control" required><%=classes.getClassSubTitle() %></textarea></td>
									</tr>

									<tr>
										<th><label for="InputImage">Image</label></th>
										<td>
											<!-- 이미지 슬라이드 -->
											<div style="text-align: center;">

												<img src="/classKing/upload/class_upload/<%=classes.getClassRenameImg() %>"
													id="profile" style="text-align: center"></img><br> <input
													type="file" id="file-1" class="inputfile inputfile-1"
													data-multiple-caption="{count} files selected"
													name="upfile" onchange="readURL(this);" accept ='image/gif,image/jpeg,image/pnp'
													style="width: 1px; height: 1px;" /> <label for="file-1"><svg
														xmlns="http://www.w3.org/2000/svg" width="20" height="17"
														viewBox="0 0 20 17"><i class="fas fa-upload"></i></svg>
										
													<span>프로필 사진&hellip;</span></label>
											</div>
										</td>
									</tr>
									<tr>
										<th><label>공개여부</label></th>
										<td>
											<!-- 비공개/공개 체크박스 -->
											<%if(classes.getOpen().equals("N")){ %>
												<div class="checks" style="float: left;">
													<input type="radio" id="ex_rd" name="ex_rds" value="Y">
													<label for="ex_rd">공개</label>
												</div>
												<div class="checks second" style="float: left;">
													<input type="radio" id="ex_rd2" name="ex_rds" value="N" checked>
													<label for="ex_rd2">비공개</label>
												</div>
											<%}else{ %>
												<div class="checks" style="float: left;">
													<input type="radio" id="ex_rd" name="ex_rds" value="Y" checked>
													<label for="ex_rd">공개</label>
												</div>
												<div class="checks second" style="float: left;">
													<input type="radio" id="ex_rd2" name="ex_rds" value="N">
													<label for="ex_rd2">비공개</label>
												</div>
											
											<%} %>
										</td>
									</tr>
									
								</tbody>
							</table>
							<div class="button">
								<button type="submit" class="btn btn-primary" style="float:right">수정</button>
								<button class="btn btn-danger" style="float:left" onclick="classDel()">클래스 삭제</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<%@ include file="../etc/footer.jsp"%>
</body>
</html>