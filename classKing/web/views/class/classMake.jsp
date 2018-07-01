<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member"%>
<%
	Member loginUser = (Member) session.getAttribute("loginUser");
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
	var submitTf=false;
	var nameCheck=false;
	   function classNameCheck(){
	      var clsName= $("#InputClassName").val();
	      if (clsName.length>16) {
	         alert("클래스 명은 최소 1글자 최대 16글자까지 가능합니다");
	         $("#InputClassName").focus();
	         nameCheck=false;
	      }else if(clsName.length<=16){
	         $.ajax(
	         {
	         url: "/classKing/cnamecheck",
	         data: {classname: $("#InputClassName").val()},
	         type:"get",   
	         success:
	         function(a){
	         var re="이미 사용 중인 클래스 명입니다.";
	         if(a==re){
	         alert(a);
	         $("#InputClassName").focus();
	         }else{
	         alert(a);
	         $("#InputClassName").focus();
	         nameCheck=true;
	         
	            }
	         }
	      });
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
</script>
<script type="text/javascript">
   $(function(){
      $("input[name=category]").click(function(){
      if($(this).val()=="01"){
      $("#profile").attr("src","/classKing/upload/class_upload/default_class_exercise.png")                                    
      }else if($(this).val()=="03"){
      $("#profile").attr("src","/classKing/upload/class_upload/default_class_company.png")
      }else if($(this).val()=="04"){
      $("#profile").attr("src","/classKing/upload/class_upload/default_class_travel.png")
      }else if($(this).val()=="05"){
      $("#profile").attr("src","/classKing/upload/class_upload/default_class_pet.png")
      }else if($(this).val()=="06"){
      $("#profile").attr("src","/classKing/upload/class_upload/default_class_hobby.png")
      }else if($(this).val()=="02"){
      $("#profile").attr("src","/classKing/upload/class_upload/default_class_book.png")
      }
   }
);
});
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
				<!-- 알람박스 시작 -->
				<div id="alarm_box">알람!</div>
				<!-- 알람박스 끝 -->
			</div>
			<div id="left_box">
				<div id="main_box" style="magin-top:0px;">
					<div class="board_title">
						<div class="title">
							<i class="fas fa-bullhorn"></i> 클래스 만들기
						</div>
					</div>
					<form action="/classKing/cmake" method="post"
						encType="multipart/form-data" onsubmit="return formValidation();">
						<input type="hidden" name="imageSourceName" value=""/>
						<div class="container">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<th><label for="InputCategory">Category</label></th>
										<td><div class="center">
												<div class='checks'>
													<input name='category' type='radio' id='sports' value="01"
														checked='checked'> <label class='radio-label'>운동</label>
													<input name='category' type='radio' id='study' value="02">
													<label class='radio-label'>스터디</label> <input
														name='category' type='radio' id='company' value="03">
													<label class='radio-label'> 회사</label> <input
														name='category' type='radio' id='travel' value="04">
													<label class='radio-label'> 여행</label> <input
														name='category' type='radio' id='animal' value="05">
													<label class='radio-label'> 반려동물</label> <input
														name='category' type='radio' id='hobby' value="06">
													<label class='radio-label'> 취미</label>
												</div>
											</div></td>
									</tr>
									<tr>
										<th><label for="InputClassName">Class Name</label></th>
										<td><input type="text" class="form-control"
											style="width: 350px; float: left;" class="form-control"
											id="InputClassName" name="className"  onchange="nameVerification();"
											placeholder=" 클래스명을 입력해주세요." required> <input
											type="button" class="btn btn-primary"
											name="classNameValidity" value="중복검사"
											onclick="classNameCheck();" style="background-color: green;"></td>
									</tr>
									<tr>
										<th><label for="InputClassNotice">Class Notice</label></th>
										<td><textarea cols="10" placeholder="클래스 설명을 입력해주세요."
												id="InputClassNotice" name="classNotice"
												class="form-control" required></textarea></td>
									</tr>

									<tr>
										<th><label for="InputImage">Image</label></th>
										<td>
											<!-- 이미지 슬라이드 -->
											<div style="text-align: center;">

												<img src="/classKing/upload/class_upload/default_class_exercise.png"
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
											<div class="checks" style="float: left;">
												<input type="radio" id="ex_rd" name="ex_rds" value="Y" checked='checked'>
												<label for="ex_rd">공개</label>
											</div>
											<div class="checks second" style="float: left;">
												<input type="radio" id="ex_rd2" name="ex_rds" value="N">
												<label for="ex_rd2">비공개</label>
											</div>
										</td>
									</tr>
									<input type="hidden" value="<%=loginUser.getMemberId()%>"
										name="memberid">
								</tbody>
							</table>
							<div class="button">
								<button type="submit" class="btn btn-primary">만들기</button>
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