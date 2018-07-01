<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member"%>
<% 
	Member loginUser = (Member) session.getAttribute("loginUser"); 
	int classNo = Integer.parseInt(request.getParameter("classNo"));
	int cmResult = 0;
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
<script defer
src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>
	
<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/menuBar.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/classHome.css" rel="stylesheet" type="text/css">
<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script src="/classKing/js/jquery.newsTicker.js"></script>
<script>
	var classking = 5;
	var cmResult2 = 5;
</script>
<script>
$(function(){
	<%if(request.getAttribute("message")!=null){%>
		alert("<%=request.getAttribute("message")%>");
	<%}%>
	<% if(loginUser != null){%>
	$.ajax({
		url : "/classKing/latestupdate",
		data : {classNo : "<%= classNo %>",
			memberId : "<%= loginUser.getMemberId() %>"},
		type : "get",
		success : function(){
			console.log("완료");
		},error : function(a,b,c){
			console.log("error: " + a + ", " + b + ", " + c);
		}
	});
	<% } %>
});
</script>
<script>

$(function(){
	$.ajax({
		url : "/classKing/sclasses",
		data : {classNo : "<%= classNo %>"},
		type : "get",
		success : function(data){
			$("#class_name").html(data);
		},error:function(a,b,c){
			console.log("error: " + a + ", " + b + ", " + c);
		}
	});
	function ajaxFunction(cmResult){
		if(cmResult == 1){
		 	$.ajax({
				url : "/classKing/new3board",
				data : {classNo : "<%= classNo %>"},
				type : "get",
				dataType : "json",
				success : function(data){
					var jsonStr = JSON.stringify(data);
					var json = JSON.parse(jsonStr);
					var values = "";
					if(Object.keys(json.list).length > 0){
						for(var i in json.list){
							values += "<li class='recent_board'><div class='recent_title'><a href='/classKing/bdetail?boardNo=" + json.list[i].no + "&classNo=<%=classNo%>&page=1&classking="+classking+"'>" +json.list[i].title +
							"</a></div><div class='recent_cont'>" + json.list[i].note + "</div><div><div class='recent_writer'>"
							+ json.list[i].id + "</div><div class='recent_date'>" + json.list[i].date + "</div></div></li>";
						}
					}else{
						values += "<li style='text-align: center;'><br><br><br>"+
						"<h2 style='color:#DDDDDD; text-align:center;'>등록된 게시물이 없습니다</h2>" +
						"</li>";
					}
					$("#classHome_l").append(values);
				},error:function(a,b,c){
					console.log("error: " + a + ", " + b + ", " + c);
				}		
			}); 
		 	$.ajax({
	            url : "/classKing/new3gallery",
	            data : {classNo : "<%= classNo %>"},
	            type : "get",
	            dataType : "json",
	            success : function(data){
	               var jsonStr = JSON.stringify(data);
	               var json = JSON.parse(jsonStr);
	               var values = "";
	               if(Object.keys(json.list).length > 0){
	                  for(var i in json.list){
	                     values += "<li class='recent_img'><a href='/classKing/cGDetail?DgalNo="+json.list[i].no+"&classNo="+<%= classNo%>+"&classking="+classking+"'>"
	                           +"<div class='thumnail' style='background-image:url(/classKing/upload/gal_upload/"
	                           + json.list[i].img + ")'></div></a><div class='img_title'><a href='/classKing/cGDetail?DgalNo="+json.list[i].no+"&classNo="+ <%= classNo%>+"&classking="+classking+"'>" + json.list[i].title
	                           + "</a></div><div class='img_cont'>" + json.list[i].note + "</div><div><div class='recent_date'>"
	                           + json.list[i].date + "</div></div></li>";
	                  }
	               }else{
	                  values += "<li style='text-align: center;'><br><br><br><br>"+
	                  "<h2 style='color:#DDDDDD; text-align:center;'>등록된 사진이 없습니다</h2>" +
	                  "</li>";
	               }
	               $("#classHome_r").append(values);
	            },error:function(a,b,c){
	               console.log("error: " + a + ", " + b + ", " + c);
	            }
	         });
		}else{
		 	$.ajax({
				url : "/classKing/new3board",
				data : {classNo : "<%= classNo %>"},
				type : "get",
				dataType : "json",
				success : function(data){
					var jsonStr = JSON.stringify(data);
					var json = JSON.parse(jsonStr);
					var values = "";
					for(var i in json.list){
						values += "<li class='recent_board'><div class='recent_title'>" +json.list[i].title +
						"</div><div class='recent_cont'>" + json.list[i].note + "</div><div><div class='recent_writer'>"
						+ json.list[i].id + "</div><div class='recent_date'>" + json.list[i].date + "</div></div></li>";
					}
					$("#classHome_l").append(values);
				},error:function(a,b,c){
					console.log("error: " + a + ", " + b + ", " + c);
				}		
			}); 
			$.ajax({
				url : "/classKing/new3gallery",
				data : {classNo : "<%= classNo %>"},
				type : "get",
				dataType : "json",
				success : function(data){
					var jsonStr = JSON.stringify(data);
					var json = JSON.parse(jsonStr);
					var values = "";
					for(var i in json.list){
						values += "<li class='recent_img'><div class='thumnail' style='background-image:url(/classKing/upload/gal_upload/"
								+ json.list[i].img + ")'></div><div class='img_title'>" + json.list[i].title
								+ "</div><div class='img_cont'>" + json.list[i].note + "</div><div><div class='recent_date'>"
								+ json.list[i].date + "</div></div></li>";
					}
					$("#classHome_r").append(values);
				},error:function(a,b,c){
					console.log("error: " + a + ", " + b + ", " + c);
				}
			});
		}
	}
	function ajaxNotice(cmResult){
		if(cmResult == 1){
			$.ajax({
				url:"/classKing/cnotice",
				data:{classNo:"<%=classNo%>"},
				type:"get",
				dataType:"json",
				success:function(data){
					var jsonStr=JSON.stringify(data);
					var json=JSON.parse(jsonStr);
					var values="";
					var size = Object.keys(json.list).length;
					
					if(size>0) {		
						for(var i=0;i<size;i++){
							values+="<li><a href='/classKing/bdetail?boardNo=" + json.list[i].no + "&classNo=<%=classNo%>&page=1&classking="+classking+"'>"+json.list[i].date+" ㅣ "+decodeURIComponent((json.list[i].note).replace(/\+/g, '%20'))+"</a></li>";
						}					
					}else{
						values="등록된 공지사항이 없습니다."
					}
					$('.ticker').html(values);	
					
					if(size>1){
						$('.ticker').newsTicker({
						    row_height: 48,
						    max_rows: 1,
						    speed: 600,
						    direction: 'up',
						    duration: 4000,
						    autostart: 1,
						    pauseOnHover: 0
						});	
					}
				},error:function(a,b,c){
					console.log("error: " + a + ", " + b + ", " + c);
				}
			});
		}else{
			$.ajax({
				url:"/classKing/cnotice",
				data:{classNo:"<%=classNo%>"},
				type:"get",
				dataType:"json",
				success:function(data){
					var jsonStr=JSON.stringify(data);
					var json=JSON.parse(jsonStr);
					var values="";
					var size = Object.keys(json.list).length;
					
					if(size>0) {		
						for(var i=0;i<size;i++){
							values+="<li>"+json.list[i].date+" ㅣ "+decodeURIComponent((json.list[i].note).replace(/\+/g, '%20'))+"</li>";
						}					
					}else{			
						values="등록된 공지사항이 없습니다."
					}
					$('.ticker').html(values);	
					
					if(size>1){
						$('.ticker').newsTicker({
						    row_height: 48,
						    max_rows: 1,
						    speed: 600,
						    direction: 'up',
						    duration: 4000,
						    autostart: 1,
						    pauseOnHover: 0
						});	
					}
				},error:function(a,b,c){
					console.log("error: " + a + ", " + b + ", " + c);
				}
			});
		}
	}
	<% if(loginUser != null){%>
	$.ajax({
		url : "/classKing/joincheck",
		data : {classNo : "<%= classNo %>",
				memberId : "<%= loginUser.getMemberId() %>"},
		type : "get",
		success : function(data){
			var result = data*1;
			if("<%=loginUser.getMemberId()%>" == "admin"){
				classking = 0;
				cmResult2 = 1;
				ajaxFunction(cmResult2);
				ajaxNotice(cmResult2);
				/* $("#join_button").html("<input type='button' name='login_btn' id='login_btn' onclick='showChat()'" +
				"class='btn btn-primary' value='채팅' style='width:260px'>"); */
			}else if(result == 0){
				classking = 1;
				cmResult2 = 0;
				ajaxFunction(cmResult2);
				ajaxNotice(cmResult2);
				/* $("#join_button").html("<input type='button' name='login_btn' id='login_btn' onclick='applyCheck()'" +
				"class='btn btn-primary' value='가입신청' style='width:260px'>"); */
			}else if(result == 1){
				classking = 0;
				cmResult2 = 1;
				ajaxFunction(cmResult2);
				ajaxNotice(cmResult2);
				/* $("#join_button").html("<input type='button' name='login_btn' id='login_btn' onclick='showChat()'" +
				"class='btn btn-primary' value='채팅' style='width:260px'>"); */
			}else{
				classking = 1;
				cmResult2 = 1;
				ajaxFunction(cmResult2);
				ajaxNotice(cmResult2);
				/* $("#join_button").html("<input type='button' name='login_btn' id='login_btn' onclick='showChat()'" +
				"class='btn btn-primary' value='채팅' style='width:260px'>"); */
			}
		},error:function(a,b,c){
			console.log("error: " + a + ", " + b + ", " + c);
		}
	});
	<% }else{ %>
		ajaxFunction(<%=cmResult%>);
		ajaxNotice(<%=cmResult%>)
	<%}%>
});
</script>
<title>Class King</title>
</head>
<body>
<%@ include file="../../views/etc/alramAlert.jsp" %>
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
				<jsp:include page="../etc/joinCheck.jsp" flush="true"> 
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
				<!--메뉴 및 최신글,최신 이미지 테이블을 담고 있음  -->
				<%@ include file="../etc/menuBar.jsp"%>
				<div id="main_box">
					<div id="class_name">
						<!-- 클래스제목 -->
					</div>
					<div id="main_event">
						<ul class="ticker"></ul>
					
					</div>
					<div class="more_btn">						
						<a href="/classKing/blist?classNo=<%= classNo %>">more > </a>
					</div>		
					<ul id="classHome_l">
	 				<!-- 게시글 3개 -->
					</ul>
					<div class="more_btn">						
						<a href="/classKing/cGallery2?classNo=<%= classNo %>">more > </a>
					</div>	
					<ul id="classHome_r">
					<!-- 갤러리 3개 -->
					</ul>
					<!-- wrap끝 -->
				</div>
			</div>
		</div>
	</section>
	<%@ include file="../etc/footer.jsp"%>
	
</body>
</html>