<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	if(request.getParameter("message") != null)
    		
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link href="/classKing/css/join.css" rel="stylesheet" type="text/css">
<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script>
	<% if(request.getParameter("message") != null){ %>
		alert("일치하는 정보가 없습니다.");
	<% } %>
	<% if(request.getParameter("memberId") != null){ %>
		alert("ID는 <%=request.getParameter("memberId")%> 입니다.");
		location.href="/classKing/main.jsp";
	<% } %>	
</script>
<title>Class King</title>
</head>
<body>
	<%@ include file="../etc/header2.jsp" %>
	<section>
		<div id="wrap">
			<div class="join_form join_form2" style="padding:60px 40px;"> <!--div구역내에 mainform을 담았음  -->
				<form id="mainform" method="post" action="/classKing/idfind">
					<div>
						<div class="page-header">
						<h2 align="center" style="color:#777">
								ID 찾기
						</h2>
						</div>
						<div>
								<div class="form-group"> <!--이름 label 및 이름 입력란 -->
									<label for="userName">이름</label> <input type="text"
										class="form-control" id="userName" name="userName" placeholder="이름을 입력해주세요.">
								</div>
								<div class="form-group"> <!--생년월일 label 및 생년월일 입력란  -->
									<label for="userBirth">생년월일</label> <input type="text"
										class="form-control" id="userBirth" name="userBirth"
										placeholder="생년월일 앞 6자리(ex:900523)">
								</div>
								<div class="form-group"> <!--e-mail label 및 e-mail 입력란  -->
									<label for="userMail">E-mail</label> <input type="text"
										class="form-control" id="userMail" name="userEmail"
										placeholder="ex)abcd@naver.com">
								</div>
								<div class="form-group text-center"><!--아이디 찾기 버튼 및 아이디 찾기 취소 버튼  -->
									<input type="submit" class="btn btn-info" id="searchId" name="searchId" value="찾기"/>
									<input type="button" class="btn btn-danger" id="cancelBt" name="cancelBt" value="취소">
								</div>
							
						</div>
					</div>
					<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
					<script
						src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
					<!-- Include all compiled plugins (below), or include individual files as needed -->
					<script src="/classKing/js/bootstrap.min.js"></script>
					</form>
			</div>
		</div>
	</section>
	<%@ include file="../etc/footer.jsp" %>
</body>
</html>