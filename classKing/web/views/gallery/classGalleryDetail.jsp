<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page
   import="member.model.vo.Member, gallery.model.vo.Gallery, java.util.ArrayList, comment.model.vo.Comment"%>
<%
   Member loginUser = (Member) session.getAttribute("loginUser");
    Gallery DgalNo = (Gallery)request.getAttribute("DgalNo");
    int classNo = 0;
    if(request.getParameter("classNo") != null){
        classNo = Integer.parseInt(request.getParameter("classNo")); 
       
     }else if(request.getAttribute("classNo") != null){
       classNo = ((Integer)request.getAttribute("classNo")).intValue();
    } 
    int currentPage =((Integer)request.getAttribute("currentPage")).intValue();
    ArrayList<Comment> commentlist = (ArrayList<Comment>)request.getAttribute("galComment");
   /*  int commentNo = ((Integer)request.getAttribute("commentNo")).intValue(); */
    /*  String commentsNote = request.getParameter("commentsNote"); */
    int classking = ((Integer)request.getAttribute("classking")).intValue();
   
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
<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/menuBar.css" rel="stylesheet" type="text/css">

<link href="/classKing/css/classBoardDetail.css" rel="stylesheet"
   type="text/css">
<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script src="/classKing/js/jquery.lbslider.js"></script>
<script defer
   src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>
<!-- 슬라이드 -->
<script src="/classKing/js/jquery.slides.js"></script>
<script src="/classKing/js/jquery.slides.min.js"></script>
<script>
   /* function showPopupReport() {
      window.open("/classKing/views/report/reportPopup.jsp", "신고",
            "width=400, height=330, left=100, top=50");
   } */
   
   /* (삭제)galleryDetail page 전체 삭제 버튼 */
   <% if(classNo != 0){%>
   function gDelete(){
      var result = confirm("삭제 하시겠습니까?");
      if(result == true){
         location.href="/classKing/cGDelete?galNo="+<%= DgalNo.getGalNo()%>+"&page="+<%= currentPage%>+"&classNo="+<%= classNo%>;
      }
   }
   <% } %>
   
   /* 수정버튼 */
   function gUpdate() {      
      location.href="/classKing/cGDUpdate?galNo="+<%= DgalNo.getGalNo()%>+"&page="+<%= currentPage%>+"&classNo="+<%= classNo%>+"&classking="+<%= classking%>;
   }
   /* 목록버튼 */
   function cGlist(){
      location.href="/classKing/cGallery2?page="+<%= currentPage%>+"&classNo="+<%= classNo%>;
   }
   
   /* 댓글 삭제 버튼 */
   function galcomDelete(com){
      location.href = "/classKing/gComDelete?commentNo="+com.id+"&galNo="+<%= DgalNo.getGalNo()%>+"&classking="+<%= classking%>+"&classNo="+<%= classNo%>;
   }
   
   /* 수정 버튼 */
   function galcomUpdate(i, comUp){
      var MemberId ="<%= loginUser.getMemberId() %>";
     
      $("#trcom"+i).after(
             "<tr id='tr"+comUp+"'>"+
                 "<td style='border:0; text-align:right;border:0;width:98px;'> " +                        
                       "<div style='padding-right:15px; padding-top:35px; margin-right:10px; margin-left:5px;'>"+
                       MemberId  + 
                       "</div>"+
                " </td>"+
            
               "<td style='width:500px; border:0; padding-right:15px;' colspan='2'>"+
                   " <textarea id='commentParentText2'"+
                         " class='form-control col-lg-12' style='width: 100%' name='commentsNote'"+
                          "rows='4' cols='100' required='required'></textarea>"+
                 "</td>"+
                "<td style='border:0; width:40px;padding-left:12px;text-align:center;'>"+
                 //댓글 수정 취소 버튼을 위해 테이블 생성
                 "<table><tr><td>"+
                " <div style='padding-top:35px;'>"+
                    "<input type='button' id='commentParentSubmit'"+
                          "name='commentParentSubmit' value='확인' onclick='comcomUpdate("+comUp+");'>"+
                 "</div></td></tr>"+
                 "<tr><td><input type='button' id='commentParentSubmit'"+
                 "name='commentParentSubmit' value='취소' onclick='comcomupUpdateCancle("+comUp+","+i+");'>"+
                 "</td></tr></table>"+
                 "</td></tr>"
                
      );
      $("#commentup"+i).attr("disabled",true);
   
      }
   
   /* 댓글 수정 Update 버튼 확인 누룰 시  */
   function comcomUpdate(comUp){
      var comNote = $("#commentParentText2").val();
      
      var comupup =comUp;
      
      location.href = "/classKing/galCUpdate?commentNo="+comupup+"&galNo="+<%= DgalNo.getGalNo()%>+"&page="+<%= currentPage%>+"&classNo="+<%= classNo%>+"&commentsNote="+comNote+"&classking="+<%= classking%>; 
   }
   
   /* 댓글 update 취소 버튼 누를 시 */
   function comcomupUpdateCancle(comUp,i){
       $("#tr"+comUp).remove();
       $("#commentup"+i).attr("disabled",false);
   }
   
   /* 겔러리 사진이 1개 이상일때 */
   $(function(){
      
      var oriimg2 = '<%= DgalNo.getGalOriginalImg2()%>';
      var reimg2 = '<%= DgalNo.getGalRenameImg2()%>';
      var null2 = 'null';
      var oriimg3 = '<%= DgalNo.getGalOriginalImg3()%>';
      var reimg3 = '<%= DgalNo.getGalRenameImg3()%>';
      
      $("#slides").prepend(
            "<img src='/classKing/upload/gal_upload/<%= DgalNo.getGalRenameImg()%>' style='width:600px; height:400px;left: 0px;'> " 
      ); 
       if(oriimg2 != null2){   
          
         $("#slides").append(
               "<img src='/classKing/upload/gal_upload/"+reimg2+"' style='width:600px; height:400px;'> " 
         );
         $("#slides").append(
               "<a class='slidesjs-previous slidesjs-navigation' href='#' title='Previous' style='font-size:30px;float:left;'><</a>&nbsp;"
                +  "<a class='slidesjs-next slidesjs-navigation' href='#' title='Next' style='font-size:30px;float:right; '>></a>"
                       
         );
        
      }
       if(oriimg3 != 'null'){
         $("#slides").append(
               "<img src='/classKing/upload/gal_upload/"+reimg3+"'"+
                  "alt='이미지를 추가해 주세요.'"+
                  "style='width:600px; height:400px;'> " 
         );   
         
      }
       
       if((oriimg2 != null2) || (oriimg3 != 'null')){
           $("#slides").slidesjs({
                  width: 940,
                  height: 528,
                  pagination: {
                       active: false,
                 },
                 navigation: {
                      active: false,
                    }

                }); 
       }
      
   });
   
   /* 사진 슬라이드 크기 및 버튼 */
   $(function(){
    
/*          $("#slides").slidesjs({
           width: 940,
           height: 528,
           pagination: {
                active: false,
          },
          navigation: {
               active: false,
             }

         });  */
   
   
         
       }); 
   
  
</script>

<!-- 이미지 슬라이드 스타일 -->
<style>
    /* Prevents slides from flashing */
    #slides {
      
    }
  </style>

<title>clssGalleryDetail</title>
</head>
<body>
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
         <!-- right_box -->
         <div id="left_box">
            <%@ include file="../etc/menuBar.jsp"%>
            <div id="main_box">
               <div class="board_title">
                  <div class="title detail_title">
                     <i class="fas fa-bullhorn"></i>Gallery
                     <div class="button_box">
                     <% if(loginUser.getMemberId().equals(DgalNo.getMemberId())==true || classking == 0 && loginUser.getMemberId().equals("admin")==true){ %>
                        <button type="button" name="modified" class="btn btn-success"
                           onclick="gUpdate();">수정</button>
                        <button type="button" name="delete" class="btn btn-danger"
                           onclick="gDelete();">삭제</button>
                           <%} %>
                           
                     </div>
                  </div>

               </div>

               <div id="notice_detail">
                  <div id="notice_title">
                  <table style="width:690px;height:100px;">
                  <tr>
                     <td style="margin-right:10px;float:left;">작성자( <%= DgalNo.getMemberId()%> ) : </td>
                    <td style="margin-right:10px;float:left;"><%= DgalNo.getGalTitle()%></td>
                    <td style="margin-right:10px;float:right;"><%= DgalNo.getGalDate()%></td>
                   </tr>
                  </table>
                  </div>
                  <!-- 여기 이미지 -->
                  <div id="notice_cont" style="overflow:hidden;width:100%;height:100%;">
                     <div id="slides" style="overflow:hidden;width:100%;height:100%;">
                         <%-- <img id="img1"
                        src="/classKing/upload/gal_upload/<%= DgalNo.getGalRenameImg() %>"
                        alt="이미지를 추가해 주세요."
                        style="width:600px; height:400px;">  --%>  
                        
                      <!-- <a class="slidesjs-previous slidesjs-navigation" href="#" title="Previous" style="font-size:30px;float:left;"><</a>&nbsp;
                      <a class="slidesjs-next slidesjs-navigation" href="#" title="Next" style="font-size:30px;float:right; ">></a>
                      -->
                      </div>
                      <hr>
                     <!--    내용 -->
                       <div>
                     <%= DgalNo.getGalNote() %>
                     </div>
                  </div>
                  <!-- 이미지 & 내용 끝 -->
                  <div style="text-align: center; margin-top: 10px">
                     <input type="button" name="notice_list" class="btn btn-secondary"
                        value="목록" onclick="cGlist();">
                  </div>
               </div>
               <br>

               <!--    ///////////////////////////////////////////////////////////////////////////////  댓글 -->
               <form action="/classKing/galComment" method="post">
                  <input type="hidden" name="DgalNo" value="<%= DgalNo.getGalNo()%>">
                  <input type="hidden" name="classNo" value="<%= classNo%>">
                  <input type="hidden" name="loginUser" value="<%= loginUser.getMemberId()%>">
                  <input type="hidden" name="classking" value="<%= classking%>">

                  <table class="table table-condensed">
                     <tr>
                        <td style="border:0; text-align:right;border:0;">

                           <div
                             style="width:100px; padding-right:15px; padding-top:35px; margin-right:10px;">

                              <%= loginUser.getMemberId() %>
                           </div>
                        </td>

                        <td style="width: 100%; border: 0;"><textarea
                              id="commentParentText" class="form-control col-lg-12"
                              style="width: 100%" name="commentsNote" rows="4" cols="100" required="required"></textarea>
                        </td>
                        <td style="border: 0;">
                           <div
                              style="padding-left: 15px; padding-top: 35px; margin-right: 10px;">
                              <input type="submit" id="commentParentSubmit"
                                 name="commentParentSubmit" class="btn btn-primary" value="등록">
                           </div>
                        </td>
                     </tr>
                  </table>
               </form>

               <!--------------------------------------- 댓글 나오는 부분 -->
               <%-- <form action="/classKing/galCUpdate" method="post">
                  <input type="hidden" name="galNo" value="<%=DgalNo.getGalNo()%>">
                  <input type="hidden" name="classNo" value="<%=classNo%>">
                  <input type="hidden" name="loginUser" value="<%= loginUser.getMemberId()%>">    --%>

               <div style="background: #EEEEEE; padding: 10px;">
                  <table id="commentTable" class="table table-condensed">
                     <tr>
                        <td style="border: 0; text-align: center; width: 100px;">아이디</td>
                        <td style="text-align: center; width: 365px; border: 0;">댓글</td>
                        <td style="text-align: center; width: 145px; border: 0;">날짜</td>
                        <td style="text-align: center; border: 0;"></td>
                     </tr>
                     <tr>
                        <td style="border: 0;"><hr></td>
                        <td style="border: 0;"><hr></td>
                        <td style="border: 0;"><hr></td>
                        <td style="border: 0;"><hr></td>
                     </tr>
                     <%
                           for (int i = 0; i < commentlist.size(); i++) {
                        %>
                     <tr id="trcom<%= i%>">
                        <td style="border: 0; padding-left: 20px;"><%=commentlist.get(i).getMemberId()%></td>
                        <td style="border: 0; padding-left: 10px;"><%=commentlist.get(i).getCommentNote()%></td>
                        <td style="border: 0; padding-left: 10px; width: 100px;"><%= commentlist.get(i).getCommentDate()%></td>
                        <th style="margin:0; padding-bottom:3px; border:0;">
                        <table>
                              <%   if ((loginUser.getMemberId().equals(commentlist.get(i).getMemberId()) == true) ||classking  == 0 
                                    || (loginUser.getMemberId().equals("admin") == true)){   %>
                              <tr>
                                 <td style="margin: 0; padding-bottom: 3px; border: 0;">
                                    <input type="button" id="commentup<%=i %>" value="수정"
                                    onclick="galcomUpdate(
                                       <%= i%>,
                                       <%= commentlist.get(i).getCommentNo() %>
                                       
                                       );">
                                 </td>
                              </tr>
                              
                              <tr>
                                 <td style="margin: 0; padding: 0; border: 0;">
                                    <input
                                    type="button" value="삭제" id="<%= commentlist.get(i).getCommentNo() %>" onclick="galcomDelete(this);">
                                 </td>
                           </tr>
                              <%   }   %>
                           </table>
                        </th>
                     </tr>

                     <tr>
                        <td style="border: 0;"><hr></td>
                        <td style="border: 0;"><hr></td>
                        <td style="border: 0;"><hr></td>
                        <td style="border: 0;"><hr></td>
                     </tr>

                     <%
                           }
                        %>

                  </table>
               </div>
               <!-- </form> -->
               <!-- 댓글 출력  끝-->

            </div><!-- main_bax -->
            
         </div>
         <!-- left box -->
      </div>
      <!-- warp 끝 -->


   </section>
   <%@ include file="../etc/footer.jsp"%>
</body>
</html>