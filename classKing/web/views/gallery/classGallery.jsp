<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page
   import="member.model.vo.Member,gallery.model.vo.Gallery,java.util.ArrayList"%>
<%
   Member loginUser = (Member) session.getAttribute("loginUser");
   int classNo = 0;
   if(request.getParameter("classNo") != null){
    classNo = Integer.parseInt(request.getParameter("classNo"));
   }else if(request.getAttribute("classNo") != null){
   classNo =((Integer)request.getAttribute("classNo")).intValue();
   }
   ArrayList<Gallery> list = (ArrayList<Gallery>)request.getAttribute("list");
   int listCount =((Integer)request.getAttribute("listCount")).intValue();
   int startPage = ((Integer)request.getAttribute("startPage")).intValue();
   int endPage =((Integer)request.getAttribute("endPage")).intValue();
   int maxPage =((Integer)request.getAttribute("maxPage")).intValue();
   int currentPage =((Integer)request.getAttribute("page")).intValue();
   String gal_search = (String)request.getAttribute("gal_search");
   String galcategory = (String)request.getAttribute("galcategory");   
   int result2 =((Integer)request.getAttribute("result2")).intValue();
   
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"
   href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
   integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
   crossorigin="anonymous">
<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<link href="/classKing/css/classGallery.css" rel="stylesheet"
   type="text/css">
<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<title>Class King</title>
<style type="text/css">
.thumbnail>img {
   width: 185px;
   height: 185px;
}
</style>

<script type="text/javascript">

$(function(){
   <%if(result2 == 1){%>
      alert("검색 결과가 없습니다.");
      
   <%}%>
});

/* -------------로그인 멤버가 아닐시-------------------- */
 $(function(){
    /*  alert("함수 구동!!!"); */
      $.ajax({
         url : "/classKing/cmlist2",
         data : {classNo : "<%=classNo%>"},
         type : "get",
         datatype: "json",
         success : function(data){
            var jsonStr = JSON.stringify(data);
            var json = JSON.parse(jsonStr);         
              var values ="";
              var values2 ="";
              var classking = 5;
            
            var num1 = 0;
            var num2 = 0;
         
            for(var i =0; i< Object.keys(json.cmlist2).length; i++){                        
                  if(((json.cmlist2[i].classNo == <%= classNo%> && json.cmlist2[i].memberId=='<%= loginUser.getMemberId()%>')) || (<%=loginUser.getMemberId().equals("admin")%>==true)){                     
                     num1 =1;
                     if(json.cmlist2[i].classKing =="Y"){
                        classking = 0;
                        
                     }else{
                        classking = 1 ;
                     }   
                  
                  }else{
                     num2=0;
                  }
            }/* for끝 */
            if(num1==1){
               <% if(!loginUser.getMemberId().equals("admin")){%>
             values2 ="<button type='button' class='btn btn-secondary' style='float:right;'"+
                        "onclick='cGUpload();'>"+
                        "<i class='far fa-file-alt'></i> UpLoad</button>"; 
                  <%}else{%>
                  values2 ="";
                  <%}%>
             <% int count = 1;%> 
            <% for(Gallery g : list ){%>
            <% if(count == 1 || count == 4 || count ==7 ){%>
             values+="<tr>";
            <% }%>
             values+="<td><div class='thumbnail'>"+
                     "<img src='/classKing/upload/gal_upload/<%=g.getGalRenameImg()%>' alt='이미지를 추가해 주세요.'>"+
                     "<div class='caption'>"+
                     "<a href='/classKing/cGDetail?DgalNo=<%=g.getGalNo()%>&classNo=<%= classNo%>&classking="
                           +classking+"'><%=g.getGalTitle()%></a></div>"+
                     "</div></td>";   
            <% if (count == 3 || count == 6 || count == 9) {%>
             values+= "</tr>";   
            <% }  count++;%>
            <% }%> /* for문 끝 */
                                 

            }else{
                <% int count2 =1;%>
               <% for(Gallery g : list ){%>
               <% if(count2 == 1 || count2 == 4 || count2 ==7 ){%>
                values+="<tr>";
               <% }%>
                values+="<td><div class='thumbnail'>"+
                        "<img src='/classKing/upload/gal_upload/<%=g.getGalRenameImg()%>' alt='이미지를 추가해 주세요.'>"+
                        "<div class='caption' style='text-align:center;'>"+
                        "<%=g.getGalTitle()%></div>"+
                        "</div></td>";   
               <%if (count2 == 3 || count2 == 6 || count2 == 9) {%>
                values+= "</tr>";   
               <%}
            count2++;%>
               <%}%> /* for문 끝 */
            }/* else끝 */
            
            $("#classGalleryMainList2").html(values);       
            $("#uploadbt").append(values2);
            
         }/* function(data) 끝 */
      });
 });
      
 /* -------------로그인 멤버가 아닐시 끝-------------------- */

   
   <%--  $(function(){
      
         <%if(request.getAttribute("message")!=null){%>
            alert("<%=request.getAttribute("message")%>");
         <%}%>
        
         <%if (request.getParameter("searchmsg") != null) {%>
            alert("<%=request.getAttribute("searchmsg")%>");
         <%}%>
      }); --%>
   
   function cGUpload(){
      
      location.href="/classKing/views/gallery/classGalleryMake.jsp?classNo="+<%=classNo%>;
      
   }
   

</script>   

</head>
<%@ include file="../etc/header.jsp"%>
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
            <!-- 가입신청버튼 -->
            <jsp:include page="../etc/joinCheck.jsp"> 
              <jsp:param name="classNo" value="<%=classNo %>"/> 
            </jsp:include>
            <!-- 가입신청버튼 끝 -->
            <!-- 알람박스 시작 -->
            <% if (loginUser != null) { %>
               <%@ include file="../../views/etc/alarm.jsp"%>
            <% } %>
         <!-- 알람박스 끝 -->
      </div>
      <div id="left_box">
         <!-- 메뉴바 시작 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->
         <%@ include file="../etc/menuBar.jsp"%>
         <!-- 메뉴바 끝 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->
         <div id="main_box">
            <!--gallery title 시작!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->
            <div class="board_title">
               <div class="title">
                  <i class="fas fa-bullhorn"></i> Gallery
               </div>
               <div id="uploadbt">
                  
               </div>
            </div>
            <!--gallery title 끝!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->

            <!-- -------------------겔러리 게시물이 없을시 시작---------------------------- -->

            <%
               if (list.size() == 0  &&  result2 == 0)  {
               
                  
            %>

            <div style="text-align: center;">
               <br> <br> <br> <br> <br> <br> <br>
               <br> <br> <br> <br>

               <h2 style="color: #DDDDDD; text-align: center;">등록된 게시물이 없습니다</h2>
               <br> <br> <br> <br> <br> <br> <br>
               <br> <br> <br> <br> <br>
            </div>
            <!-- -------------------겔러리 게시물이 없을시 끝---------------------------- -->
            <%
               } else {
            %>


            <!-- 앨범 시작 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!-->
            <div class="container">
               <table class="table table-condensed" id="classGalleryMainList">
                  <tbody id="classGalleryMainList2">
                     <%-- 
                     <%
                        int count = 1;
                     %>
                     <%
                        for (Gallery g : list) {
                     %>
                     <%
                        if (count == 1 || count == 4 || count == 7) {
                     %>
                     <tr>
                        <%
                           }
                        %>
                        <td>
                           <div class="thumbnail">

                              <img
                                 src="/classKing/upload/gal_upload/<%=g.getGalRenameImg()%>"
                                 alt="이미지를 추가해 주세요.">
                              <div class="caption">
                                 <a
                                    href="/classKing/cGDetail?DgalNo=<%=g.getGalNo()%>&classNo=<%=classNo%>"><%=g.getGalTitle()%></a>
                              </div>
                              <!--    </a> -->
                           </div>
                        </td>

                        <%
                           if (count == 3 || count == 6 || count == 9) {
                        %>
                     </tr>
                     <%
                        }
                              count++;
                     %>
                     <%
                        }
                     %>
 --%>

                  </tbody>
               </table>
            </div>
            <!-- 앨범 끝 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!-->

            <!-- 하단 페이지 번호 시작 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->
            <div id="gallery_searchbox">

               <form action="/classKing/cGallery2" method="post">
                  <input type="hidden" name="classNo" value="<%=classNo%>">
                  <input type="hidden" name="page" value="<%=currentPage%>">

                  <select class="form-control galcategory" name="galcategory"
                     style="width: 90px; float: left;">
                     <option value="제목">제목</option>
                     <option value="내용">내용</option>
                  </select> <input type="text" name="gal_search"
                     class="notice_search form-control"> <input type="submit" 
                     name="search_submit" class="btn btn-dark" value="검색">

               </form>
            </div>
            <!-- board_searchbox끝 -->



            <div id="gallery_page">
               <ul class="pagination">
                  <%
                     if (startPage > 5) {
                  %>
                  <li class='page-item'><a class='page-link'
                     href="/classKing/cGallery2?page=<%=startPage - 1%>&galcategory=<%=galcategory%>&gal_search=<%=gal_search%>&classNo=<%= classNo%>">PREV</a></li>
                  <%
                     } else {
                  %>
                  <li class="page-item"><a class='page-link'>PREV</a></li>
                  <%
                     }
                  %>

                  <!-- 현재 페이지가 포함된 그룹의 페이지 숫자 출력 -->
                  <%
                     for (int p = startPage; p <= endPage; p++) {
                           if (p == currentPage) {
                  %>
                  <li class="page-item"><a class="page-link"><%=p%></a></li>
                  <%
                     } else {
                  %>
                  <li class="page-item"><a class="page-link"
                     href="/classKing/cGallery2?page=<%=p%>&classNo=<%=classNo%>&galcategory=<%=galcategory%>&gal_search=<%=gal_search%>"><%=p%></a></li>
                  <%
                     }
                        }
                  %>

                  <%
                     if (endPage < maxPage) {
                  %>
                  <li class="page-item"><a class="page-link"
                     href="/classKing/cGallery2?page=<%=endPage + 1%>&classNo=<%=classNo%>&galcategory=<%=galcategory%>&gal_search=<%=gal_search%>">NEXT</a></li>
                  <%
                     } else {
                  %>
                  <li class="page-item"><a class="page-link">Next</a></li>
                  <%
                     }
                  %>

               </ul>

            </div>
            <%
               }/* 등록된 겔러리 게시물이 있으면 else 끝 */
            %>
            <!-- 하단 페이지 번호 끝 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->

         </div>
         <!-- left-box 끝 -->
      </div>
   </div>


</section>
<%@ include file="../etc/footer.jsp"%>
</body>
</html>