<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="member.model.vo.Member"%>
<%
   Member loginUser = (Member) session.getAttribute("loginUser");
   int classNo = Integer.parseInt(request.getParameter("classNo"));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>class_member</title>
<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script
   src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"></script>
<script>
   window.module = {}
</script>
<!-- <meta name="csrf-param" content="authenticity_token" />
<meta name="csrf-token"
   content="GKXLl7SM44I5mhanYy/7E5veMjLFrvdPBC95cSXcQvmkfxOHEvNqCBOnUkQzmipAt20TlWP8yijAkUnu7yWRvA==" /> -->

<!-- <script
   src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> -->
<script defer
   src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/menuBar.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/classMember.css" rel="stylesheet" type="text/css">
<script>
;(function($){
   <%if(request.getAttribute("message")!=null){%>
      alert("<%=request.getAttribute("message")%>");
   <%}%>
})($);
</script>
<script>
function showChat1(data){
   window.open("/classKing/chat11?uId="+data+"&memberId=<%=loginUser.getMemberId()%>", "채팅", "width=610, height=590, left=100, top=50");
}
   ;(function($){
      $.ajax({
         url : "/classKing/cmlist",
         data : {classNo : "<%=classNo%>"},
         type : "get",
         datatype: "json",
         success : function(data){
            var jsonStr = JSON.stringify(data);
            var json = JSON.parse(jsonStr);
            var values = "";
            for(var i in json.list){
               values += "<tr class='user false'> <td><div class='media' style='width: 200px;'>"
                        + "<img class='d-flex mr-3 rounded-circle' src='/classKing/upload/member_upload/"
                        + json.list[i].img + "' style='width: 50px;' /><div class='media-body' style='font-size: 12pt; margin-top:10px;'>" 
                        + "<div class='media-heading'>" + json.list[i].id + "</div></div></td><td>"
                        + "<span class='active-users--permitted'>";
               switch(json.list[i].classking){
               case "Y" : values += "클래스장"; break;
               case "N" : values += "멤버"; break;
               }
               if(json.list[i].id != "<%=loginUser.getMemberId()%>"){
                  values += "</span></td><td><span class='tooltip-trigger' data-placement='top'"
                        + "data-toggle='tooltip' title='by " + json.list[i].id + "'>" + json.list[i].joindate
                        + "</span></td><td>" + json.list[i].latestdate 
                        + "</td><td><a href='javascript:showChat1(\""+ json.list[i].id +"\");'><img src='/classKing/images/chat.png'"
                        + " style='width:25px; height:25px;'></a></td></tr>";
               }else{
                  values += "</span></div></td><td><span class='tooltip-trigger' data-placement='top'"
                     + "data-toggle='tooltip' title='by " + json.list[i].id + "'>" + json.list[i].joindate
                     + "</span></td><td>" + json.list[i].latestdate 
                     + "</td><td></td></tr>";
               }
            }
            $("#member_list").append(values);
            $("#member_count").append("(" + Object.keys(json.list).length + ")");
         },error:function(a,b,c){
            console.log("error: " + a + ", " + b + ", " + c)
         }
      });
   })($);
</script>
<title>Class King</title>
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
            <jsp:include page="../etc/joinCheck.jsp"> 
              <jsp:param name="classNo" value="<%=classNo %>"/> 
            </jsp:include>
            <!-- 알람박스 시작 -->
            <% if (loginUser != null) { %>
               <%@ include file="../../views/etc/alarm.jsp"%>
            <% } %>
            <!-- 알람박스 끝 -->
         </div>
         <div id="left_box">
            <%@ include file="../etc/menuBar.jsp" %>
            <div id="main_box">
               <div class="container">
                  <div class="board_title">
                     <div class="title" id="member_count">
                        <i class="fas fa-bullhorn"></i> 전체멤버
                     </div>
                  </div>
                  <!-- <table class="table table-hover"
               style="width: 663px; heigth: 700px; border: 1px solid lightgray; margin: 0px; padding: 0px">
               <thead>
                  <tr>
                     <th>ID</th>
                     <th>IMAGES</th>
                     <th>신고</th>
                     <th>버튼</th>
                  </tr>
               </thead>
               <tbody>
                  <tr>
                     <td>로그인한 사람</td>
                     <td><img src="images/smile.png" width="25px" height="25px"></td>
                     <td>0</td>
                     <td><img src="images/x.png" width="20px" height="20px"></td>
                  </tr>
                  <tr>
                     <td>반장</td>
                     <td><img src="images/smile.png" width="25px" height="25px"></td>
                     <td>0</td>
                     <td><img src="images/seting.png" width="30px" height="30px"></td>
                  </tr>
                  <tr>
                     <td>멤버</td>
                     <td><img src="images/smile.png" width="25px" height="25px"></td>
                     <td>0</td>
                     <td><img src="images/x.png" width="20px" height="20px"></td>
                  </tr>
               </tbody>
            </table> -->
            <!-- 멤버리스트!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->
                  <!-- 권한선택? 셀렉트메뉴 -->
                  <!-- <div style="float: right;">
                     <select name="user_type" id="user_type" class="custom-select"
                        style="float: right; width: 130px; height: 40px; font-size: 10pt;"><option
                           value="-1">관리자 권한</option>
                        <option value="2">마스터</option>
                        <option value="1">매니저</option>
                        <option value="0">권한 없음</option></select> <select name="permission_id"
                        id="permission_id" class="custom-select"
                        style="float: right; width: 130px; height: 40px; font-size: 10pt;"><option
                           value="-1">멤버 타입</option>
                        <option value="">멤버</option>
                        <option value="1">제한된 멤버</option>
                        <option value="2">게스트</option></select>

                  </div> -->
                  <!-- 권한선택? 셀렉트메뉴 -->
                  <table class="table ml_table" id="activated-users"
                     style="">
                     <!-- <colgroup>
                        <col width="*" />
                        <col width="10%" />
                        <col width="10%" />
                        <col width="15%" />
                        <col width="15%" />
                        <col width="8%" />
                     </colgroup> -->
                     <thead>
                        <th>ID</th>
                        <!-- <th>권한</th> -->
                        <th>멤버타입</th>
                        <th>가입일</th>
                        <th>최근접속일</th>
                        <th>채팅</th>
                     </thead>
                     <tbody id="member_list">
                        <!-- 멤버리스트뿌리기 -->
                     </tbody>
                  </table>
                  <!-- 멤버리스트!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->
               </div>
            </div>
         </div>
      </div>
   </section>
   <%@ include file="../etc/footer.jsp" %>
</body>
</html>