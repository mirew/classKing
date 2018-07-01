<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="member.model.vo.Member" %>
    <%
    	int classNo5 = Integer.parseInt(request.getParameter("classNo"));
    	Member loginUser9 = (Member)session.getAttribute("loginUser");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
<% if(loginUser9 != null){%>
function applyCheck(){
	$.ajax({
		url : "/classKing/applyCheck",
		data : {MemberId :"<%= loginUser9.getMemberId()%>",
			ClassNo : "<%= classNo5%>"},
			type : "get",
			dataType : "json",
			success : function(data){
				if(data.result > 0) {
					alert("이미 가입신청한 Class 입니다.");
				}else{
					showClassJoin();
				}
			},error : function(a,b,c){
				console.log("error : " + a + ", " + b + ", " + c);
			}
	});
}
function showClassJoin() {
	 window.open("/classKing/cJoin?classNo=<%= classNo5%>&loginUser=<%= loginUser9.getMemberId()%>","가입신청", "width=350, height=330, left=100, top=50");
}
function showChat(){
	<% if(loginUser9 != null){%>
	window.open("/classKing/cchat?classNo=<%=classNo5%>&memberId=<%=loginUser9.getMemberId()%>", "채팅", "width=610, height=590, left=100, top=50");
	<%}%>
}
$.ajax({
	url : "/classKing/joincheck",
	data : {classNo : "<%= classNo5 %>",
			memberId : "<%= loginUser9.getMemberId() %>"},
	type : "get",
	success : function(data){
		var result = data*1;
		if("<%=loginUser9.getMemberId()%>" == "admin"){
			$("#join_button").html("<input type='button' name='login_btn' id='login_btn' onclick='showChat()'" +
				"class='btn btn-primary' style='width:100%' value='채팅'>"
				+"<div style='margin-top:5px;'><input type='button' name='login_btn' id='login_btn' onclick='location.href=\"/classKing/alist?classNo=<%=classNo5%>\"'" +
				"class='btn btn-secondary' value='회원관리' style='width:128px;margin-right:5px'>"
				+"<input type='button' name='class_info' id='class_info' onclick='location.href=\"/classKing/cinfo?classNo=<%=classNo5%>&memberId=<%=loginUser9.getMemberId()%>\"'" +
				"class='btn btn-secondary' value='클래스관리' style='width:128px'></div>");
		}else if(result == 0){
			$("#join_button").html("<input type='button' name='login_btn' id='login_btn' onclick='applyCheck()'" +
			"class='btn btn-primary' value='가입신청' style='width:260px;'>");
		}else if(result == 1){
			$("#join_button").html("<input type='button' name='login_btn' id='login_btn' onclick='showChat()'" +
			"class='btn btn-primary' style='width:100%' value='채팅'>"
			+"<div style='margin-top:5px;'><input type='button' name='login_btn' id='login_btn' onclick='location.href=\"/classKing/alist?classNo=<%=classNo5%>\"'" +
			"class='btn btn-secondary' value='회원관리' style='width:128px;margin-right:5px'>"
			+"<input type='button' name='class_info' id='class_info' onclick='location.href=\"/classKing/cinfo?classNo=<%=classNo5%>&memberId=<%=loginUser9.getMemberId()%>\"'" +
			"class='btn btn-secondary' value='클래스관리' style='width:128px'></div>");
		}else{
			$("#join_button").html("<input type='button' name='login_btn' id='login_btn' onclick='showChat()'" +
			"class='btn btn-primary' value='채팅' style='width:260px'>");
		}
	},error:function(a,b,c){
		console.log("error: " + a + ", " + b + ", " + c);
	}
});
<% } %>
</script>
</head>
<body>
<div id="join_button"></div>
</body>
</html>