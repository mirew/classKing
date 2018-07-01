<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member,gallery.model.vo.Gallery"%>
<%
   Member loginUser = (Member) session.getAttribute("loginUser");
   //String memberId = request.getParameter("memberId");
   /* int classNo =Integer.parseInt(request.getParameter("classNo")); */
   /*   int currentPage =((Integer)request.getAttribute("currentPage")).intValue(); */
   // Gallery DgalNo = (Gallery)request.getAttribute("DgalNo");

   int classNo = 0;
   if (request.getParameter("classNo") != null) {
      classNo = Integer.parseInt(request.getParameter("classNo"));
   } else if (request.getAttribute("classNo") != null) {
      classNo = ((Integer) request.getAttribute("classNo")).intValue();
   }

   //int currentPage =((Integer)request.getAttribute("currentPage")).intValue();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<link
   href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
   rel="stylesheet" />
<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/menuBar.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/classBoardMake.css" rel="stylesheet"
   type="text/css">

<script src="/classKing/js/jquery-3.3.1.min.js"></script>

<title>classKing</title>
<script type="text/javascript">
	
   var count =0;
	
   function backgalDetailList() {
      location.href = "/classKing/cGallery2?classNo=" +<%=classNo%>;
   }

   function readURL0(input) {
      if (input.files && input.files[0]) {
         var reader = new FileReader();

         reader.onload = function(e) {
            $('#profile0').attr('src', e.target.result);
         }
         reader.readAsDataURL(input.files[0]);
      }
      
      
   }
    function readURL1(input) {
      if (input.files && input.files[0]) {
         var reader = new FileReader();

         reader.onload = function(e) {
            $('#profile1').attr('src', e.target.result);
         }
         reader.readAsDataURL(input.files[0]);
      }
   }
   function readURL2(input) {
      if (input.files && input.files[0]) {
         var reader = new FileReader();

         reader.onload = function(e) {
            $('#profile2').attr('src', e.target.result);
         }
         reader.readAsDataURL(input.files[0]);
      }
   } 
   
   
   /* 이미지 추가 버튼 */
   function addimg(){
      $("#addImg").append(
            "<div id='imgdiv"+(count+1)+"'>"+
            "<input type='file' id='file-"+(count+1)+"' accept ='image/gif,image/jpeg,image/pnp' "+
            "style='margin-top:10px;margin-bottom:10px;margin-right:15px;width:420px; float:left;'"+
               "data-multiple-caption='{count} files selected'"+
               "onchange='readURL"+(count+1)+"(this);' name='galfilename"+(count+1)+"'"+
               "class='form-control' /> "+
               "<button type='button' id='imgremove"+(count+1)+"' onclick='addremove("+(count+1)+");'"+
               "style='floagt:rigth; height:40px;margin-top:10px;margin-bottom:10px;' class='btn pull-left'>"+
               "삭제</button>"+
               "</div>"
      );
      $('#imgslide').append(
    		  "<img  width='150' id='profile"+(count+1)+"' border='1px solid' height='150 margin-right:50px;'>&nbsp"
       );
      count++;
      
     var size = $("#imgslide > img").length;
	if (size == 3) {
			alert("이미지 파일을 3개까지만  upLoad 하실 수 있습니다.");
			addbutton.disabled = 'disabled';
		}
	}

	/* 추가 이미지 삭제버튼 */
	function addremove(i) {
	
		$('#imgdiv'+i).remove();
		$('#profile'+i).remove();
		if(i == 1){
			count = 0;
		}else{
			count=1;
		}
		if (count <= 2) {
			addbutton.disabled = false;
		}

	}
</script>

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
         <div id="left_box">

            <!-- menu -->
            <%@ include file="../etc/menuBar.jsp"%>
            <!-- menu -->

            <!-- 게시판 시작 -->
            <div id="main_box">
               <div id="container">
                  <form action="/classKing/cGMake" method="post"
                     encType="multipart/form-data">
                     <input type="hidden" name="classNo" value="<%=classNo%>">
                     <input type="hidden" name="memberId"
                        value="<%=loginUser.getMemberId()%>">

                     <table class="table table-bordered">
                        <thead>

                           <div class="board_title">
                              <div class="title">
                                 <i class="fas fa-bullhorn"></i> 이미지 등록
                              </div>
                           </div>
                        <tbody>

                           <tr>
                              <th style="border: 0; color: #777;">제목</th>
                              <td style="border: 0;"><input type="text"
                                 placeholder="제목을 입력하세요. " name="galTitle"
                                 class="form-control" maxlength="33" required/></td>
                           </tr>
                           <tr>
                              <th style="border: 0; color: #777">내용</th>
                              <td style="border: 0;"><textarea id="contents" cols="50"
                                    placeholder="내용을 입력하세요. " name="galNote"
                                    class="form-control" maxlength="1000" required></textarea></td>
                           </tr>
                           <!-- 1 -->
                           <tr>
                              <th style="border: 0; color: #777; width:100px;">사진첨부</th>
                              <td style="border: 0;">
                                 <!-- 시작 -->
                                <!--  <div id="demo" class="carousel slide" data-ride="carousel">  -->
                                 	
                                 <div style="width:500px; height:163px;"><!-- 이미지 감싸기 -->
                                    <div id="imgslide">
                                       
                                          <img  width="150" id="profile0" border="1px solid" 
                                             height="150">
                                          
                                       </div>
                                     </div> <!-- 이미지 감싸기 -->
                                <!--  </div>  -->
                                 <!-- 끝 --> 
                                 <div id="addImg">
                                 <input type="file" id="file-0" style="margin-top:10px;margin-bottom:10px;margin-right:15px;
                                 width:420px; float:left;" accept ="image/gif,image/jpeg,image/pnp"
                                 data-multiple-caption="{count} files selected"
                                 onchange="readURL0(this);" name="galfilename"
                                 class="form-control"required/> 
                             
                                 <button type="button" style="float:right; height:40px;margin-top:10px;margin-bottom:10px; ""
                                 class="btn pull-left" onclick="addimg();" id="addbutton">
                               		  추가</button>
                                 </div>
                              </td>
                           </tr>

                           <tr>
                              <td colspan="2" style="border: 0;"><input type="button"
                                 value="목록" class="btn pull-left"
                                 onclick="backgalDetailList();"
                                 style="background: #3b64af; color: white;" /> <input
                                 type="submit" value="등록" class="btn pull-right"
                                 style="margin-right: 5px; background: red; color: white;" />
                                 
                              </td>
                           </tr>

                        </tbody>


                     </table>
                  </form>
               </div>
               <!-- container 끝 -->
               <!-- 게시판 끝 -->

            </div>

         </div>
   </section>


   <%@ include file="../etc/footer.jsp"%>

</body>
</html>