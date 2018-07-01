<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="/classKing/css/normalize.css" />
<link rel="stylesheet" type="text/css" href="/classKing/css/demo.css" />
<link rel="stylesheet" type="text/css"
	href="/classKing/css/component.css" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link href="/classKing/css/join.css" rel="stylesheet" type="text/css">
<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<title>Class King</title>
<script>
	(function(e, t, n) {
		var r = e.querySelectorAll("html")[0];
		r.className = r.className.replace(/(^|\s)no-js(\s|$)/, "$1js$2")
	})(document, window, 0);
</script>
<script>
	var vCodeCheck = false;
	var vCode;
	var idCheck = false;
	function idValidation() {
		idCheck = false;
	}
	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#profile').attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
	function sendMail(){//이메일 인증코드 발송  
		$.ajax({
			url : "/classKing/sendmail",
			data : {email : $('#userEmail').val()},
			type : "get",
			success : function(data){
				vCode = data;
				alert("인증번호를 발송 하였습니다.");
			},error : function(a,b,c){
				console.log("error : " + a + ", " + b + ", " + c);
			}
		});
	}
	function vCodeCheck1(){
		if(vCode == $("#vCode").val()){
			vCodeCheck = true;
			alert("인증에 성공하였습니다.");
		}else{
			vCodecheck = false;
			alert("인증번호를 확인해주세요.");
		}
	}
	function vCodeChange(){
		vCodeCheck = false;
	}
//////////////////아이디 중복체크///////////////////////////////////////////////////
	
	function idValidity(){
		var idPattern = /^[A-Za-z]{1}[A-Za-z0-9]{3,19}$/;
		var id = $("#userId").val();

	if(!idPattern.test(id)){
		alert("ID: 4 ~ 20 자리 영(대,소)문자,숫자만 입력 가능하며 첫 문자는 숫자를 사용할 수 없습니다.");
		idCheck=false;	
	}else if(idPattern.test(id)){
			$.ajax({
				url : "/classKing/idcheck",
				data : {
					userid : $("#userId").val()
				},
				type : "get",
				success : function(data) {
					data *= 1;
					if (data == 1) {
						alert("이미 사용중인 아이디입니다.");
					} else {
						alert("사용 가능한 아이디입니다.");
						idCheck = true;
					}
				}
			});
		}
		
	}
	/////////////////아이디 중복검사를 한 후에 바꾸었을 경우에 다시 중복 검사를 요구하는 코드//////////////////
	$(function(){
	
	});
/////////////////////////////////////////////////////////////////////////////////
	/*비밀번호 일치 여부 알아보는 스크립트*/
	$(function() {
		$('input[type=password]').blur(function() {
			var pwd1 = $("#inputPwd1").val();
			var pwd2 = $("#inputPwd2").val();
			if (pwd1 == pwd2) {
				$("#confirmPwd").css("color", "green").css("display", "block");
				$("#confirmPwd").html("비밀번호가 일치합니다.");
			} else {
				$("#confirmPwd").css("color", "red").css("display", "block");
				$("#confirmPwd").html("비밀번호가 불일치합니다.");
				$("#inputPwd2").val("");
			}
		});
	});
	function formValidation() {
		/////////////////////////////정규식 목록/////////////////////////
		//아이디 : 	4 ~ 20 자리 영(대, 소), 숫자 / 첫글자는 숫자 사용 불가
		var idPattern = /^[A-Za-z]{1}[A-Za-z0-9]{3,19}$/;
		var id = $("#userId").val();
		//비밀번호: 6~20자이상 영문 숫자 혼합
		var pwdPattern = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
		var pwd = $("#inputPwd1").val();
		//이메일 정규식 @,.을 무조건 포함할것
		var emailPattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		var email = $("#userEmail").val();
		//생일 정규식: 주민번호 앞자리 6자리 정규식
		var birthPattern = /^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))$/;
		var birthday = $("#userBirth").val();
		/////////////////////////////정규식 목록/////////////////////////
//////////////////////이제 정규식을 검사합니다.//////////////////////////////////////
		if (idCheck == false) {
			//submit을 하기 위해 위한 조건을 아이디 체크를 하지 않았을경우 false로 주어놨으므로 
			alert("id를 체크해주세요");
			$("#userId").focus();
			return false;
		} else if (idCheck == true) {
			if (!idPattern.test(id)) {
				alert("ID: 4 ~ 20 자리 영(대,소)문자,숫자만 입력 가능하며 첫 문자는 숫자를 사용할 수 없습니다.");
				$("#userId").focus();
				return false;
			} else if (!pwdPattern.test(pwd)) {
				alert("비밀번호: 6~20자리 이상 숫자 및 영문자를 혼합 해주세요.");
				$("#inputPwd1").focus();
				return false;
			} else if (!emailPattern.test(email)) {
				alert("올바른 이메일 형식이 아닙니다.");
				$("#userEmail").focus();
				return false;
			} else if (!birthPattern.test(birthday)) {
				alert("주민등록번호 6자리만 적어주세요.");
				$("#userBirth").focus();
				return false;
			} else if(vCodeCheck == false){
				alert("이메일 인증을 해주세요.");
				$("#userEmail").focus();
				return false;
			} else {
				alert("회원가입이 되었습니다!!");
				return true; // 차후 서버 연결이 되면 true로 바꾸어 줄 것;
			}
		}
	}
</script>
<!---------------------------------------------------------------------------->
</head>
<!----------------------------------------------------------------------------->
<body>
	<%@ include file="../etc/header2.jsp"%>
	<section>
		<div id="wrap">
			<div class="join_form">
				<!--  -->
				<form id="mainform" action="/classKing/mjoin" method="post"
					enctype="multipart/form-data" onsubmit="return formValidation();">
					<!--Join_form 구역 내에 mainform을 담았음  -->
					<div>
						<div class="page-header">
							<h2 align="center" style="color: #777">회원가입</h2>
						</div>
						<div>
							<div style="text-align: center;">

								<img src="/classKing/upload/member_upload/default_user.png" id="profile" style="text-align: center"
									alt="이미지를 선택해주세요"></img><br> <input type="file"
									id="file-1" class="inputfile inputfile-1" accept ='image/gif,image/jpeg,image/pnp'
									data-multiple-caption="{count} files selected" name="upfile"
									onchange="readURL(this);" style="width: 1px; height: 1px;" />
								<label for="file-1"><svg
										xmlns="http://www.w3.org/2000/svg" width="20" height="17"
										viewBox="0 0 20 17">
										<path
											d="M10 0l-5.2 4.9h3.3v5.1h3.8v-5.1h3.3l-5.2-4.9zm9.3 11.5l-3.2-2.1h-2l3.4 2.6h-3.5c-.1 0-.2.1-.2.1l-.8 2.3h-6l-.8-2.2c-.1-.1-.1-.2-.2-.2h-3.6l3.4-2.6h-2l-3.2 2.1c-.4.3-.7 1-.6 1.5l.6 3.1c.1.5.7.9 1.2.9h16.3c.6 0 1.1-.4 1.3-.9l.6-3.1c.1-.5-.2-1.2-.7-1.5z" /></svg>
									<span>프로필 사진&hellip;</span></label>
							</div>
							<!--파일 찾기  -->
							<!-- 	</div> -->
							<div class="form-group">
								<label for="userId">ID</label><br> <input type="text"
									class="form-control" id="userId" name="userid"
									placeholder="ID를 입력해주세요." onchange="idValidation();">
								<div class="form-group text-center">
									<input type="button" class="btn btn-info" id="dupliBt" name="dupliBt" value="중복검사" onclick="idValidity();" required/>
								</div>
								<!--중복검사 후 ID 불일치 여부를 확인하여 P에 내용을 담을 수 있도록 함.P-태그 내의 글은구역을 보여주기 위하여 임의의 텍스트를 기입하였음  -->
							</div>

							<div class="form-group">
								<!--비밀번호 입력란    -->
								<label for="InputPwd1">비밀번호를 입력하세요.</label> <input
									type="password" class="form-control" id="inputPwd1"
									name="userpwd" placeholder="비밀번호" required>
							</div>

							<div class="form-group">
								<!--비밀번호 다시 입력란  -->
								<label for="InputPwd2">비밀번호를 다시 입력하세요.</label><input
									type="password" class="form-control" id="inputPwd2"
									name="inputPwd2" placeholder="비밀번호 확인" required> <label
									class="confirmPwd" id="confirmPwd"></label>
								<!--ID 불일치의 P태그와 동일함  -->
							</div>

							<div class="form-group">
								<!--이름 기입란  -->
								<label for="userName">이름</label><input type="text"
									class="form-control" id="userName" name="username"
									placeholder="이름을 입력해 주세요." required>
							</div>
							<div class="form-group">
								<!--생년월일 기입란  -->
								<label for="userBirth">생년월일</label> <input type="text"
									class="form-control" id="userBirth" name="userbirth"
									placeholder="주민번호 앞 6자리(ex:891205)" required>
							</div>
							<div class="form-group">
								<!--e-Mail 기입란  -->
								<label for="userEmail">E-mail</label><br><input type="email"
									class="form-control" id="userEmail" name="useremail"
									placeholder="ex)abcd@naver.com">
								<div class="form-group text-center">
									<input type="button" class="btn btn-info" id="mailsend" name="dupliBt" value="메일인증" onclick="sendMail();" required/>
								</div><br>
							</div>
							<div class="form-group">
								<!--e-Mail 기입란  -->
								<label for="vCode">인증번호</label><br><input type="text"
									class="form-control" id="vCode" name="vCode" onChange="vCodeChange();">
								<div class="form-group text-center">
									<input type="button" class="btn btn-info" id="vCodecheck" name="dupliBt" value="번호확인" onclick="vCodeCheck1();" required>
								</div>
							</div>
						
							<div class="form-group">
								<!--option을 주어 미리 준비한 질문을 선택할수있게하였음 임의로 1~10까지의 내용을 기입하였음  -->
								<label for="optionQ">ID 분실 시 질문 선택</label> <select
									class="form-control" id="optionQ" name="question">
									<option>기억에 남는 영화는?</option>
									<option>초등학교 시절 가장 친한 친구는?</option>
									<option>키웠던 애완동물 이름은?</option>
									<option>기억에 남는 책은?</option>
									<option>어린 시절 가장 무서워했던 것은?</option>
									<option>어린 시절 내 별명은?</option>
								</select>
							</div>
							<div class="form-group">
								<!--위 질문에 대한 답변  -->
								<label for="userA">정답</label> <input type="text"
									class="form-control" id="userA" name="answer" placeholder="정답">
							</div>
							<div class="form-group text-center">
								<!--회원가입 버튼과 가입취소 버튼으로 한 div 구역내에 존재함  -->
								<input type="submit" class="btn btn-info" id="joinBt"
									name="joinBt" value="회원가입"> <input type="reset"
									class="btn btn-danger" id="cancelBt" name="cancelBt"
									value="가입취소">
							</div>
						</div>
					</div>
					<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
					<script
						src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
					<!-- Include all compiled plugins (below), or include individual files as needed -->
				</form>
			</div>
		</div>
	</section>
	<%@ include file="../etc/footer.jsp"%>
</body>
</html>