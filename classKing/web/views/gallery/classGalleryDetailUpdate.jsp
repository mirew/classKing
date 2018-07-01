<!-- 수정페이지 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page
   import="member.model.vo.Member,gallery.model.vo.Gallery,java.util.ArrayList"%>
<%
   Member loginUser = (Member) session.getAttribute("loginUser");
   Gallery DgalNo = (Gallery) request.getAttribute("DgalNo");
   int classNo = 0;
   if (request.getParameter("classNo") != null) {
      classNo = Integer.parseInt(request.getParameter("classNo"));
   } else if (request.getAttribute("classNo") != null) {
      classNo = ((Integer) request.getAttribute("classNo")).intValue();
   }
   int currentPage = ((Integer) request.getAttribute("currentPage")).intValue();
   int classking =((Integer)request.getAttribute("classking")).intValue();
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

<title>clssGalleryDetailUpdate 이미지 수정</title>
<script type="text/javascript">
	var oriimg1 = '<%= DgalNo.getGalOriginalImg()%>';
	var oriimg2 = '<%= DgalNo.getGalOriginalImg2()%>';
	var reimg2 = '<%= DgalNo.getGalRenameImg2()%>';
	var count =0;
	var oriimg3 = '<%= DgalNo.getGalOriginalImg3()%>';
	var reimg3 = '<%= DgalNo.getGalRenameImg3()%>';
	var null2 = 'null';
	var size = $("#imgslide > img").length;
/* 겔러리 사진이 1개 이상일때 */
$(function(){
	
	if(oriimg2 != null2){
		count =1;
		$("#imgslide").append(
		        "<img  width='150' id='profile1' border='1px solid' "+
                " height='150' float='left'"+ 
                "src='/classKing/upload/gal_upload/"+reimg2+"'>&nbsp;"+
                "<input type='hidden' name='imgname' value='galfilename1'>"
		);
		$("#addImg").append(
		"<div id='imgdiv"+(count)+"'>" +
        "<input type='file' id='file-1' style='margin-top:10px;margin-bottom:10px;width:420px; float:left;'"+
        "data-multiple-caption='{count} files selected'"+
        "onchange='readURL1(this);' name='galfilename1' accept ='image/gif,image/jpeg,image/pnp'"+
        "class='form-control'/>" +
        "<button type='button' id='imgremove"+(count)+"' onclick='addremove("+(count)+");'"+
            "style='floagt:rigth; height:40px;margin-top:10px;margin-bottom:10px;' class='btn pull-left'>"+
            "삭제</button>"+
        "</div>"
        );  
		
	}if(oriimg3 != null2){
		 count =2; 
		$("#imgslide").append(
		        "<img  width='150' id='profile2' border='1px solid' "+
                " height='150' float='left'"+ 
                "src='/classKing/upload/gal_upload/"+reimg3+"'>"+
                "<input type='hidden' name='imgname' value='galfilename2'>"
		);
	 	$("#addImg").append(
				"<div id='imgdiv"+(count)+"'>" +
		        "<input type='file' id='file-2' style='margin-top:10px;margin-bottom:10px;width:420px; float:left;'"+
		        "data-multiple-caption='{count} files selected'"+
		        "onchange='readURL2(this);' name='galfilename2' accept ='image/gif,image/jpeg,image/pnp'"+
		        "class='form-control'/>"+
		        "<button type='button' id='imgremove"+(count)+"' onclick='addremove("+(count)+");'"+
		             "style='floagt:rigth; height:40px;margin-top:10px;margin-bottom:10px;' class='btn pull-left'>"+
		             "삭제</button></div>" 
		        );
	 	
	}
});


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
          "<input type='file' id='file-"+(count+1)+"' accept ='image/gif,image/jpeg,image/pnp'"+
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
    
    size = $("#imgslide > img").length;
	if (size == 3) {
			alert("이미지 파일을 3개까지만  수정 하실 수 있습니다.");
			addbutton.disabled = 'disabled';
		} 
	}
 
	/* 추가 이미지 삭제버튼 */
	function addremove(i) {
		$('#imgdiv'+i).remove();
		$('#profile'+i).remove(); 
		$('#origalImg'+i).remove();
		$('#renameImg'+i).remove();
		if(i == 1){
			count = 0;
		}else {
			count=1;
		}
		if (count <= 2) {
			addbutton.disabled = false;
		}

	}
</script>
</head>
<body>
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
            <%@ include file="../etc/menuBar.jsp"%>
            <div id="main_box">
               <div class="board_title">
                  <div class="title detail_title">
                     <i class="fas fa-bullhorn"></i>Gallery 수정
                     <div id="container" style="text-align: center;">
                        <br>
                        <form action="/classKing/cGUpdate" method="post"
                           encType="multipart/form-data">
                              <input type="hidden" name="classNo" value="<%=classNo%>">
                              <input type="hidden" name="page" value="<%=currentPage%>">
                              <input type="hidden" name="galNo"value="<%=DgalNo.getGalNo()%>">
                              <input type="hidden" id="origalImg0" name="origalImg0"value="<%= DgalNo.getGalOriginalImg()%>">
                              <input type="hidden" id="renameImg0" name="renameImg0"value="<%= DgalNo.getGalRenameImg()%>">
                        	 <input type="hidden" id="origalImg1" name="origalImg1"value="<%= DgalNo.getGalOriginalImg2()%>">
                        	 <input type="hidden" id="renameImg1" name="renameImg1"value="<%= DgalNo.getGalRenameImg2()%>">
                        	 <input type="hidden" id="origalImg2" name="origalImg2"value="<%= DgalNo.getGalOriginalImg3()%>">
                        	 <input type="hidden" id="renameImg2"name="renameImg2"value="<%= DgalNo.getGalRenameImg3()%>">
                        	 <input type="hidden" name="classking" value="<%= classking%>">
                        	 
                           <table class="table table-bordered">
                              <tr>
                                 <th style="border: 0; color: #777">제목</th>
                                 <td style="border: 0;padding-right:10px;">
                                    <input class="form-control" class="step01" type="text"
                                       style="width: 156px; margin-top: 10px; margin-bottom: 10px; text-align:left;"
                                       name="galTitle" value="<%=DgalNo.getGalTitle()%>">
                                 </td>
                              </tr>

                              <tr>
                                 <th style="border: 0; color: #777">내용</th>
                                 <td style="border: 0;padding-right:10px;"><textarea id="contents" cols="50" "
                                       rows="8" name="galNote" class="form-control"><%=DgalNo.getGalNote()%></textarea></td>
                              </tr>
                              <tr>
                                 <th style="border: 0; color: #777; width: 120px;" >이미지</th>
                                 <td style="border: 0; padding-top: 10px; padding-bottom:10px;padding-right:10px;">
                                 	
                                 	<div style="width:500px; height:163px;"><!-- 이미지 감싸기 -->
                                    <div id="imgslide" float="left" align="left">
                                   		 <!-- 이미지 1 -->
                                          <img  width="150" id="profile0" border="1px solid" 
                                             height="150" float="left" src="/classKing/upload/gal_upload/<%= DgalNo.getGalRenameImg() %>">
                                             <input type='hidden' name='imgname' value='galfilename0'>
                                       </div>
                                    </div> <!-- 이미지 감싸기 -->
                                    
                                 <div id="addImg"> 
                                <input type="file" id="file-0" style="margin-top:10px;margin-bottom:10px;
                                 width:420px; float:left;" accept ="image/gif,image/jpeg,image/pnp"
                                 data-multiple-caption="{count} files selected"
                                 onchange="readURL0(this);" name="galfilename0" alt="<%= DgalNo.getGalRenameImg() %>>"
                                 class="form-control"/>  
                              
                                 <button type="button" style=" height:40px;margin-top:10px;margin-bottom:10px;"
                                 class="btn pull-left" onclick="addimg();" id="addbutton">
                               	 추가</button>
                                 	</div>
                                
                                 </td>
                              </tr>

                               <tr>
                                 <td colspan="2" style="border: 0;">
                                 
                                 </td>
                              </tr>

                           </table>
                           <input type="submit" value="확인" class="btn pull-right"
                              style="margin-right: 5px; background: gray; color: white; float: center; align: center;">
                        </form>
                        
                     </div><!-- container 끝 -->

                  </div>

               </div>


            </div>


         </div>
      </div>


   </section>
   <%@ include file="../etc/footer.jsp"%>
</body>
</html>