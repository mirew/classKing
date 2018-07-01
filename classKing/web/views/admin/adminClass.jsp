<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member,classes.model.vo.Classes,java.util.*"%>
<%
   Member loginUser = (Member) session.getAttribute("loginUser");   
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/board.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/admin.css" rel="stylesheet" type="text/css">
<style type="text/css">
   #board_searchbox .class_srch{
      float:left;
      line-height:36px;
      height:36px;
      margin-right:5px;
   }
</style>

<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script defer src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<script type="text/javascript">

   $(function(){            
      moveTab(1,1);   
   });//onload
   
   function moveTab(category,page){
      $.ajax({      
         url:"/classKing/adminclist",
         data:{classcategory:category,
            page : page},
         type:"get",
         datatype:"json",
         success:function(data){
            
            var jsonStr = JSON.stringify(data);
            var json = JSON.parse(jsonStr);
            var values="";
            var size = Object.keys(json.list).length;
            
            if(size>0){
               values="<table class='table' id='cl_table'><tr><th>번호</th><th>이름</th><th>클래스장</th>"+
               "<th>회원수</th><th>클래스생일</th><th>클래스삭제</th></tr>";
               for(var i in json.list){
                  values+="<tr><td>"+json.list[i].rnum+"</td><td><a href='/classKing/views/class/classHome.jsp?classNo="
                  + json.list[i].cno + "'>"+json.list[i].title+"</a>"+
                  "</td><td>"+json.list[i].king+"</td><td>"+json.list[i].mc+"</td><td>"+json.list[i].date+
                  "</td><td><button name='class_delete' class='btn btn-secondary btn_cdelete' onclick='deleteClass("+json.list[i].cno+","+json.list[i].classcategory+")'><i class='far fa-trash-alt'></i> 삭제</button></tr>"   ;         
               }   
               var maxPage = json.list[0].maxPage;
               var startPage = json.list[0].startPage;
               var endPage = json.list[0].endPage;
               var currentPage = json.list[0].currentPage;            
                        
               values += "</table><div id='notice_page1' class='pageList'><ul class='pagination' id='classPage'>";
               
               if(startPage > 5){
                  values += "<li class='page-item'><a class='page-link' href='javascript:moveTab("+category+","+(startPage-1)+")'>[prev]</a></li>";
                }else{
                   values +="<li class='page-item'><a class='page-link'>prev</a></li>"
                 }
               
               for(var p = startPage;p<=endPage;p++){ 
                  if(p == currentPage){
                     values += "<li class='page-item'><a class='page-link'>"+p+"</a></li>";
                  }else{
                     values+= "<li class='page-item'><a class='page-link' href='javascript:moveTab("+category+","+p+")'>"+ p+"</a></li>"
                  }    
               }
               
               if(endPage < maxPage){
                  values += "<li class='page-item'><a class='page-link' href='javascript:moveTab("+category+","+(endPage+1)+")'>"+"[next]</a></li>";
               }else{
                  values +="<li class='page-item'><a class='page-link'>next</a></li>";
               }
               values += "</ul></div>";
               
               if(category=='01'){
                  $("#exercise").html(values);
               }else if(category=='02'){
                  $("#study").html(values);      
               }else if(category=='03'){
                  $("#company").html(values);
               }else if(category=='04'){
                  $("#travel").html(values);
               }else if(category=='05'){
                  $("#pet").html(values);
               }else if(category=='06'){
                  $("#hobby").html(values);
               }
            }else{
               values ="<p style='width:100%;height:50px;text-align:center;font-size:24px;color:#ccc;margin-top:50px;'> 개설 된 클래스가 없습니다.</p>";

               if(category=='01'){
                  $("#exercise").html(values);
               }else if(category=='02'){
                  $("#study").html(values);      
               }else if(category=='03'){
                  $("#company").html(values);
               }else if(category=='04'){
                  $("#travel").html(values);
               }else if(category=='05'){
                  $("#pet").html(values);
               }else if(category=='06'){
                  $("#hobby").html(values);
               }
            }
               
         },error:function(a,b,c){
            console.log("error: " + a + ", " + b + ", " + c)
         }
         
      })//ajax
   }
   
   function deleteClass(classNo,category){
      if(confirm("정말로 삭제하시겠습니까?") == true){
         $.ajax({      
            url:"/classKing/admincdel",
            data:{classNo:classNo,
               classcategory : category},
            type:"get",
            datatype:"json",
            success:function(data){
               moveTab(data.category,data.page);
            }            
         });
         
      }else{
         return false;
      }
   }
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
         <!-- 로그인박스 끝 -->
         
         <!-- 알람박스 시작 -->
         <% if (loginUser != null) { %>
               <%@ include file="../../views/etc/alarm.jsp"%>
         <% } %>            
          <!-- 알람박스 끝 -->
      </div>   
       <div id="left_box">
          <div class="board_title">
             <div class="title"><i class="fas fa-bars"></i> 클래스 관리</div>   
          </div>

          <div id="class_list" style="clear:left;">
            <ul class="nav nav-tabs" role="tablist" id="">
               <li class="nav-item"><a class="nav-link active" href="#exercise" role="tab" data-toggle="tab" onclick="moveTab('01',1)">운동</a></li>
               <li class="nav-item"><a class="nav-link" href="#study" role="tab" data-toggle="tab" id="01" onclick="moveTab('02',1)">스터디</a></li>
               <li class="nav-item"><a class="nav-link" href="#company" role="tab" data-toggle="tab" onclick="moveTab('03',1)">회사</a></li>
               <li class="nav-item"><a class="nav-link" href="#travel" role="tab" data-toggle="tab" onclick="moveTab('04',1)">여행</a></li>
               <li class="nav-item"><a class="nav-link" href="#pet" role="tab" data-toggle="tab" onclick="moveTab('05',1)">반려동물</a></li>
               <li class="nav-item"><a class="nav-link" href="#hobby" role="tab" data-toggle="tab" onclick="moveTab('06',1)">취미</a></li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content" style="overflow:hidden;">
               <div role="tabpanel" class="tab-pane active" id="exercise">
               </div>
               <div role="tabpanel" class="tab-pane" id="study">
               </div>
               <div role="tabpanel" class="tab-pane" id="company">   
               </div>
               <div role="tabpanel" class="tab-pane" id="travel">   
               </div>
               <div role="tabpanel" class="tab-pane" id="pet">
               </div>
               <div role="tabpanel" class="tab-pane" id="hobby">
               </div>
            </div>
         </div>
          <!-- <div id="board_searchbox">             
             <span class="class_srch">클래스명   </span>
             <input type="text" name="notice_search" class="notice_search form-control">    
             <input type="submit" name="search_submit" class="btn btn-dark" value="검색">   
          </div>  -->
       </div>
   </div>
   </section>
   <%@ include file="../etc/footer.jsp" %>
   
</body>
</html>