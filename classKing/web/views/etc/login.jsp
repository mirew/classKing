<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
<link href="/classKing/css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
   <div id="login_box" style="padding:38px 18px">
   
      
         <input type="button" class="btn btn-primary" name="login_btn" id="login_btn"
         value="로그인" onclick="location.href='/classKing/views/member/memberLogin.jsp'"/>
         <input type="button" class="btn" id="join_btn"
         value="회원가입" onclick="location.href='/classKing/views/member/memberJoin.jsp'"/>
         <div class="join">
            <label><a href="/classKing/views/member/memberIdFind.jsp">아이디찾기
                  / </a></label>
            <label><a href="/classKing/views/member/memberPwdFind.jsp"> 비밀번호 찾기</a></label>
         </div>
   
      
      
      
   </div>
</body>
</html>