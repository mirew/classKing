<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%
String userId =(String)request.getAttribute("memberId");

%>
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
$("#joinBT").attr("disabled","true");
$(function(){
   $('input[type=password]').blur(function(){         
    var pwd1 = $("#inputPwd1").val();
    var pwd2 = $("#inputPwd2").val();
 if(pwd1 == pwd2) {        
    $("#confirmPwd").css("color","green").css("display","block");
    $("#confirmPwd").html("비밀번호가 일치합니다.");
    $("#joinBT").attr("disabled","false");    
    } else {
    $("#confirmPwd").css("color","red").css("display","block");
    $("#confirmPwd").html("비밀번호가 불일치합니다.");
    $("#inputPwd2").val("");
    }
   });
});
   
</script>
</head>
<body>
   <%@ include file="../etc/header2.jsp"%>
   <section>
      <div id="wrap">
         <div class="join_form">
            <!-- start of form -->

            <form id="mainform" action="/classKing/pwdreset" method="post">
               <input type="hidden" name="userId" value="<%=userId%>"/>
               <div>
                  <div class="page-header">
                     <h2 align="center" style="color: #777">비밀번호 재설정</h2>
                  </div>
                  <br>
                  <br>
                  <div>
                     <div style="text-align: center;">
                        <div class="form-group">
                           <!--비밀번호 입력란    -->
                           <label for="InputPwd1">비밀번호를 입력하세요.</label> 
                           <input type="password" class="form-control" id="inputPwd1" name="userpwd" placeholder="최소 8자리 대/소문자와 하나의 특수문자 포함" required>
                        <br>
                        <div class="form-group">
                           <!--비밀번호 다시 입력란  -->
                           <label for="InputPwd2">비밀번호를 다시 입력하세요.</label> <input
                              type="password" class="form-control" id="inputPwd2"
                              name="inputPwd2" placeholder="비밀번호를 다시 한 번 입력해주세요" required>
                           <br> <label id="confirmPwd"></label>
                        </div>

                        <div class="form-group text-center">
                           <!--회원가입 버튼과 가입취소 버튼으로 한 div 구역내에 존재함  -->
                           <input type="submit" class="btn btn-info" id="joinBt" name="joinBt" value="확인"> 
                           <input type="reset" class="btn btn-danger" id="cancelBt" name="cancelBt" value="취소">
                        </div>
                     </div>
                  </div>
                  <script
                     src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
            </form>
            <!-- end of form -->
         </div>
      </div>
   </section>
   <%@ include file="../etc/footer.jsp"%>

</body>
</html>