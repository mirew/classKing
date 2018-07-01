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
<script defer src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>
<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/board.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/admin.css" rel="stylesheet" type="text/css">


<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<script type="text/javascript">	

	$(function(){			
		reportGetPage(1,1);		
	})//onload 닫기
	
	function reportGetPage(cano,page){			
		
		$.ajax({						
			url:"/classKing/adminrlist",	
			data:{cano:cano,page:page},
			type:"get",
			datatype:"json",
			success:function(data){
				var jsonStr = JSON.stringify(data);
				var json = JSON.parse(jsonStr);
				var maxPage = json.list[0].maxPage;
				var startPage = json.list[0].startPage;
				var endPage = json.list[0].endPage;
				var currentPage = json.list[0].currentPage;
				var reportcategory=json.list[0].reportcategory;	
				
				
				if(reportcategory == "board"){
						var values="<table class='table' id='rp_table'><tr><th>번호</th><th>게시글 제목</th><th>작성자</th>"+
					"<th>신고사유</th><th>신고일자</th><th>승인여부</th></tr>";
					
						for(var i in json.list){
							values+="<tr><td>"+json.list[i].rnum+"</td><td><a href='/classKing/kingcheck?page=1&classNo="+json.list[i].classNo+"&boardNo="+json.list[i].boardNo+"&memberId=<%=loginUser.getMemberId()%>'>"
									+json.list[i].boardTitle+"</a></td><td>"+json.list[i].memberId+"</td><td><a href='javascript:reportDetail("+cano+","+json.list[i].reportNo+")'>"+json.list[i].categoryNote+"</a></td><td>"+json.list[i].reportDate+"</td><td>";							
																	
							if(json.list[i].reportFake=='0'){
								values+="<input type='button' class='btn rp_ok' onclick='rpCheck(\"1\","+cano+","+ json.list[i].reportNo +","+currentPage+")' value='승인'>&nbsp;<input type='button' class='btn rp_no' onclick='rpCheck(\"2\","+cano+","+ json.list[i].reportNo +","+currentPage+")' value='거부'></td></tr>";
							}else if(json.list[i].reportFake=='1'){
								values+="<input type='button' class='btn rp_ok' value='승인' disabled></tr>";
							}else{
								values+="<input type='button' class='btn rp_no' value='거부' disabled></td></tr>";
							};						
						}
						
				}else{
					var values ="<table class='table' id='rp_table'><tr><th>번호</th><th>댓글내용 제목</th><th>작성자</th>"+
					"<th>신고사유</th><th>신고일자</th><th>승인여부</th></tr>";
					
					for(var i in json.list){
						values+="<tr><td>"+json.list[i].rnum+"</td><td><a href=/classKing/kingcheck?page=1&classNo="+json.list[i].classNo+"&boardNo="+json.list[i].boardNo+"&memberId=<%=loginUser.getMemberId()%>'>"
								+json.list[i].commentsNote+"</a></td><td>"+json.list[i].memberId+"</td><td><a href='javascript:reportDetail("+cano+","+json.list[i].reportNo+")'>"+json.list[i].categoryNote+"</a></td><td>"+json.list[i].reportDate+"</td><td>";							
						
						if(json.list[i].reportFake=='0'){
							values+="<input type='button' class='btn rp_ok' onclick='rpCheck(\"1\","+cano+","+ json.list[i].reportNo +","+currentPage+")' value='승인'>&nbsp;<input type='button' class='btn rp_no' onclick='rpCheck(\"2\","+cano+","+ json.list[i].reportNo +","+currentPage+")' value='거부'></td></tr>";
						}else if(json.list[i].reportFake=='1'){
							values+="<input type='button' class='btn rp_ok' value='승인' disabled></tr>";
						}else{
							values+="<input type='button' class='btn rp_no' value='거부' disabled></td></tr>";
						};						
					}									    				  
				}
									
				
				values += "</table><div id='notice_page1' class='pageList'><ul class='pagination' id='classPage'>";
				
				if(startPage > 5){
					values += "<li class='page-item'><a class='page-link' href='javascript:reportGetPage("+cano+","+(startPage-1)+")'>PREV</a></li>";
			    }else{
			    	values +="<li class='page-item'><a class='page-link'>PREV</a></li>"
		        }
				
				for(var p = startPage;p<=endPage;p++){ 
					if(p == currentPage){
						values += "<li class='page-item'><a class='page-link'>"+p+"</a></li>";
					}else{
						values+= "<li class='page-item'><a class='page-link' href='javascript:reportGetPage("+cano+","+p+")'>"+ p+"</a></li>"
					} 	
				}
				
				if(endPage < maxPage){
					values += "<li class='page-item'><a class='page-link' href='javascript:reportGetPage("+cano+","+(endPage+1)+")'>"+"NEXT</a></li>";
				}else{
					values +="<li class='page-item'><a class='page-link'>NEXT</a></li>";
				}
				values += "</ul></div>";
				
				if(reportcategory=="board"){
					$("#board").html(values);		
				}else{
					$("#comment").html(values);		
				}
						
			},error:function(a,b,c){
				console.log("error: " + a + ", " + b + ", " + c)
			}
		});//ajax
		
	} // report에서 페이지 이동
	//0보류 1수락 2거절이요!
	function rpCheck(status,cano,reportNo,page){
		
		 $.ajax({						
			url:"/classKing/reportc",	
			data:{status:status,cano:cano,reportNo:reportNo},
			type:"get",
			datatype:"json",
			success:function(data){
				reportGetPage(cano,page);
			},error:function(a,b,c){
				console.log("error: " + a + ", " + b + ", " + c)
			}
		});//ajax 
	}
	
	function reportDetail(cano,reportNo){
		//alert(cano+","+reportNo);
		location.href="/classKing/views/report/reportDetail.jsp?cano="+cano+"&reportNo="+reportNo;
	}
	function reportDetail(cano,reportNo){
		window.open("/classKing/ardetail?cano="+cano+"&reportNo="+reportNo,"신고 세부 내용","width=450, height=300, left=100,top=50");
		window.close();
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
		 		<div class="title"><i class="fas fa-bars"></i> 신고관리</div>	
		 	</div>
		 	<div id="class_list" style="clear:left;">
				<ul class="nav nav-tabs" role="tablist">
					<li class="nav-item"><a class="nav-link active" href="#board" role="tab" data-toggle="tab" onclick="reportGetPage(1,1)">게시글</a></li>
					<li class="nav-item"><a class="nav-link" href="#comment" role="tab" data-toggle="tab" onclick="reportGetPage(2,1)">댓글</a></li>
				</ul>

				<!-- Tab panes -->
				<div class="tab-content" style="overflow:hidden;">
					<div role="tabpanel" class="tab-pane active" id="board">						
					</div>
					<div role="tabpanel" class="tab-pane" id="comment">											
					</div>
				</div>
			</div>	
	 	
	 	</div>
	 
	</div>
	
	
	</section>
	<%@ include file="../etc/footer.jsp" %>
	
</body>
</html>