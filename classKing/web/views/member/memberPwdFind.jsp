<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member"%>
<%@ page import="member.controller.*"%>
<%
int result=1;
if(request.getParameter("result")!=null){
   result = Integer.parseInt(request.getParameter("result"));
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!---------------- 원래 있던 css파일 -------------------------------------->
<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<!-- Bootstrap -->
<link rel="stylesheet"
   href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
   integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
   crossorigin="anonymous">
<!-- 이름만 달리하고 내용물은 똑같은 CSS파일로 연결하였습니다. -->
<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/memberPwdFind.css" rel="stylesheet"
   type="text/css">
<!---------------- 원래 있던 css파일 -------------------------------------->
<!-- 추가되는 CSS -->
<!-- font awesome -->
<link rel="stylesheet" href="css/font-awesome.min.css" media="screen"
   title="no title" charset="utf-8">
<script type="text/javascript" src="../../js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
   //정규화 항목 중 이름만 하면 되므로, 일단 주석처리를 하고 코드만 작성해 놓겠습니다.
   // 1. 이름 정규화 항목
   function formValidation() {
      /////////////////////////////정규식 목록/////////////////////////
      //아이디 :    4 ~ 20 자리 영(대, 소), 숫자 / 첫글자는 숫자 사용 불가
      var idPattern = /^[A-Za-z]{1}[A-Za-z0-9]{3,19}$/;
      var id = $("#userId").val();
      /////////////////////////////정규식 목록/////////////////////////

      //////////////이제 정규식을 검사합니다.//////////////////////////////////////
      if (!idPattern.test(id)) {
         alert("ID: 4 ~ 20 자리 영(대,소)문자,숫자만 입력 가능하며 첫 문자는 숫자를 사용할 수 없습니다.");
         $("#userId").focus();
         return false;
      } else {
         return true;
         
      }
}
</script>

<%if(result==0){ %>
<script type="text/javascript">
alert("아이디가 없습니다. 회원가입을 진행해주세요");
</script>
<%}%>

<title>Class King</title>
</head>
<body>
   <%@ include file="../etc/header2.jsp"%>
   <section>
      <div id="wrap">
         <div class="join_form">
            <!-- ************************************************************ -->
            <form id="mainform" action="/classKing/pfind"
               onsubmit="return formValidation();">
               <div class="pwdAll">
                  <div class="page-header">
                     <h2 style="text-align: center; color: #777">비밀번호 찾기</h2>
                  </div>
                  <br> <br>
                  <div class="form-group">
                     <label for="userId">아이디를 입력해주세요</label> <input type="text"
                        class="form-control" id="userId" name="userId"
                        placeholder="아이디를 입력해 주세요" required>
                  </div>
                  <br>
                  <div class="form-group">
                     <label for="InputPassword2">비밀번호 분실 시 질문</label> <br>
                     <!--차후에 옵션을 추가할 것!!-->
                     <select id="optionQ" name="optionQ" class="form-control">
                        <option>기억에 남는 영화는?</option>
                        <option>초등학교 시절 가장 친한 친구는?</option>
                        <option>키웠던 애완동물 이름은?</option>
                        <option>기억에 남는 책은?</option>
                        <option>어린 시절 가장 무서워했던 것은?</option>
                        <option>어린 시절 내 별명은?</option>
                     </select> <br> <input type="text" class="form-control" id="userA"
                        name="userA" placeholder="질문에 대한 답" required>
                  </div>
                  <div class="form-group text-center">
                     <button type="submit" class="btn btn-info" name="findPwd">확인</button>
                     <button type="button" class="btn btn-danger" name="cancel" onclick="location.href='/classKing/main.jsp'">취소</button>
                  </div>
               </div>
            </form>
            <!-- ************************************************************ -->
         </div>
      </div>
   </section>
   <%@ include file="../etc/footer.jsp"%>
</body>
</html>