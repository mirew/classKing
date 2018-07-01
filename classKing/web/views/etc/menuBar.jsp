<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	 <%@ page import="member.model.vo.Member" %> 
	<%
		int classNo2 = Integer.parseInt(request.getParameter("classNo"));
		Member loginUser4 = (Member) session.getAttribute("loginUser");
		int result3 = 0;
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="/classKing/css/menuBar.css" rel="stylesheet" type="text/css">
<title>menuBar</title>

<script>
<% if(loginUser4 != null){%>
$.ajax({
	url : "/classKing/joincheck",
	data : {classNo : "<%= classNo2 %>",
			memberId : "<%= loginUser4.getMemberId() %>"},
	type : "get",
	success : function(data){
		var result = data*1;
		console.log(result);
		if("<%=loginUser4.getMemberId()%>" == "admin"){
			$("#calendar0").attr("href","/classKing/callist?classNo=<%= classNo2 %>");
			$("#member0").attr("href","/classKing/views/class/classMember.jsp?classNo=<%= classNo2 %>");
		}else if(result == 0){
			$("#calendar0").attr("href","javascript:alert('클래스 가입 후 이용가능합니다.');");
			$("#member0").attr("href","javascript:alert('클래스 가입 후 이용가능합니다.');");
		}else if(result == 1){
			$("#calendar0").attr("href","/classKing/callist?classNo=<%= classNo2 %>");
			$("#member0").attr("href","/classKing/views/class/classMember.jsp?classNo=<%= classNo2 %>");
		}else{
			$("#calendar0").attr("href","/classKing/callist?classNo=<%= classNo2 %>");
			$("#member0").attr("href","/classKing/views/class/classMember.jsp?classNo=<%= classNo2 %>");
		}
	},error:function(a,b,c){
		console.log("error: " + a + ", " + b + ", " + c);
	}
});
<% } %>
</script>
</head>
<body>
	<nav id="topMenu">
		<ul>
			<li class="topMenuLi"><a class="menuLink" href="/classKing/views/class/classHome.jsp?classNo=<%= classNo2 %>">HOME</a>
			</li>
			<li class="divider">|</li>
			<li class="topMenuLi"><a class="menuLink"
				href="/classKing/blist?classNo=<%= classNo2 %>">BOARD</a></li>
			<li class="divider">|</li>
			<li class="topMenuLi"><a class="menuLink"
				href="/classKing/cGallery2?classNo=<%= classNo2 %>">GALLERY</a></li>
			<li class="divider">|</li>
			<li class="topMenuLi"><a href="javascript:alert('클래스 가입 후 이용가능합니다.');" class="menuLink" id="calendar0">CALENDAR</a></li>
			<li class="divider">|</li>
			<li class="topMenuLi"><a href="javascript:alert('클래스 가입 후 이용가능합니다.');" class="menuLink" id="member0">MEMBER</a></li>
		</ul>
	<%-- 	<% if(loginUser != null){ %>
					/classKing/views/class/classMember.jsp?classNo=<%= classNo2 %>
				<% }else{ %>
					javascript:alert('로그인 후 이용가능합니다.');
				<% } %> --%>
	</nav>
</body>
</html>