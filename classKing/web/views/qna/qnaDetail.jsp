<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member,qna.model.vo.Qna"%>
<%
   Member loginUser = (Member) session.getAttribute("loginUser");
   Qna qna = (Qna) request.getAttribute("qna");
   int currentPage=((Integer)request.getAttribute("currentPage")).intValue();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/board.css" rel="stylesheet" type="text/css">

<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script src="/classKing/js/jquery.lbslider.js"></script>
<script defer src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>

<title>Class King</title>
<script type="text/javascript"
   src="/classKing/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">

function golist() {
   
   location.href="/classKing/qlist";
   
}

function gomodify() {
   
   location.href="/classKing/mqna?qnanum="+<%=qna.getQnaNo()%>+"&page="+<%=currentPage%>;
            
}

function admodify(){
   
   location.href="/classKing/admodify?qnanum="+<%=qna.getQnaNo()%>+"&page="+<%=currentPage%>+
   "&area="+$('#textarea').val();
     
  } 
     
function  adtextarea(){
    
   values="<textarea style='width:100%' id='textarea' name='textarea'><%=qna.getQnaAnswer() %></textarea>"+
   "<button type='button' name='modified' class='btn btn-success' onclick='admodify();'>등록</button>"+
"<button type='button' name='cancel' class='btn btn-danger' onclick='admodifycancel();'>취소</button>";

$('#qna_a').html(values);

}

function godelete(){
   var result=confirm("삭제하시겠습니까?");
   if(result==true){
      location.href="/classKing/qdelete?qnanum="+<%=qna.getQnaNo()%>+"&page="+<%=currentPage%>;
   }
   

   
}

function admodifycancel(){
   
   location.href="/classKing/qdetail?qnanum="+<%=qna.getQnaNo()%>+"&page="+<%=currentPage%>;
   
}

function addelete(){
   var result=confirm("삭제하시겠습니까?");
   if(result==true){
   
   
   location.href="/classKing/qaddelete?qnanum="+<%=qna.getQnaNo()%>+"&page="+<%=currentPage%>;
   }
}

</script>
</head>
<body>
<%@ include file="../../views/etc/alramAlert.jsp" %>
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
         
         <!-- 알람박스 시작 -->
         <% if (loginUser != null) { %>
               <%@ include file="../../views/etc/alarm.jsp"%>
            <% } %>            
          <!-- 알람박스 끝 -->
      </div>   
       <div id="left_box">
            <div class="board_title detail_title">
               <div class="title">
                  <i class="fas fa-question-circle"></i> Q & A
               </div>
               <%if(loginUser.getMemberId().equals("admin")) { %>
                  <button type="button" name="delete" class="btn btn-danger" onclick="godelete();" style="float:right">삭제</button>
                  <% }%>
               <% if(loginUser!=null)  {%>
               <%if (loginUser.getMemberId().equals(qna.getQnaWriter()) && qna.getQnaAnswer()==null){%>
               <div class="button_box">
                  
                  
                  
                  <button type="button" name="modified" class="btn btn-success" onclick="gomodify();" >수정</button>
                     
               
                  <button type="button" name="delete" class="btn btn-danger" onclick="godelete();">삭제</button>
               </div>
               <% } %>
               <%} %>
            </div>
            <div id="qna_cont">
               <div id="qna_title">
                  <div><%=qna.getQnaTitle() %></div>
                  <div><%=qna.getQnaDate() %></div>
                  
               </div>
               
               
               <div id="qna_q"><%=qna.getQnaNote().replace("\r\n","<br>") %></div>
               <% if(qna.getQnaOriginalFile() !=null) { %>
               <div><i class="fas fa-paperclip"></i>&nbsp;<a href="/classKing/qdown?ofile=<%=qna.getQnaOriginalFile()%>
               &rfile=<%=qna.getQnaRenameFile() %>"><%=qna.getQnaOriginalFile()%></a></div>
               <%} %>
               <% if(qna.getQnaAnswer()!=null && !(loginUser.getMemberId().equals("admin"))) {%>
               
               <div id="qna_a" style="background: #f5f5f5">
                  <p class="answer_title">답변&nbsp;<%=qna.getAnswerDate() %></p>
                  <%=qna.getQnaAnswer() %>
                  <div class="button_box">
            
               </div>
               </div>
               <%} %>
               <% if(qna.getQnaAnswer()!=null && loginUser.getMemberId().equals("admin")) {%>
            
               <div id="qna_a" style="background: #f5f5f5">
                  <p class="answer_title">답변&nbsp;<%=qna.getAnswerDate()%></p>
                  <%=qna.getQnaAnswer() %>
               <div class="button_box" id="admodify">
               </div>   
               
               
               
               </div>
               
                  <div style="margin-top:5px; margin-bottom:50px;"><button type="button" name="modified" class="btn btn-success" onclick="adtextarea();" style="float:right">수정</button>
                  <button type="button" name="delete" class="btn btn-danger" onclick="addelete();" style="float:right; margin-right:3px;">삭제</button></div>
                  
               <% } %>
               <% if(qna.getQnaAnswer()==null && !(loginUser.getMemberId().equals("admin"))) { %>
               
               
               
               <%} %>
               <% if(qna.getQnaAnswer()==null && loginUser.getMemberId().equals("admin")) { %>
               <form action="/classKing/qinsert" method="post">
               <div id="qna_a" class="area" style="background: #f5f5f5; overflow:hidden;">
                  <input type="hidden" name="qnanum" value="<%=qna.getQnaNo()%>">
                  <input type="hidden" name="page" value="<%=currentPage%>">
                  <div style="width:580px; height:120px;; float:left;"><textarea  style="width:100%; height:100%;"  id="textarea" name="textarea" id="answerarea" maxlength="1000"></textarea></div>
            <div style="float:right;"><input type="submit" name="modified" class="btn btn-success" style="float:right"  value="등록"></div>
               </div>
               <%} %>
               </form>
               <div style="text-align: center; margin-top: 10px">
                  <button type="button" name="qna_list" class="btn btn-secondary" onclick="golist();">목록</button>
               </div>
            </div>
         </div>
      </div>
   </section>
   <%@ include file="../etc/footer.jsp"%>
</body>
</html>