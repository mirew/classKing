<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="member.model.vo.Member"%>
<%
	Member loginUser0 = (Member) session.getAttribute("loginUser");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
var eventSource;
<%if(loginUser0!=null){%>
	eventSource = new EventSource("/classKing/sse?userid=<%=loginUser0.getMemberId()%>");
	
	eventSource.onopen = function(event) {};
	eventSource.onmessage = function(event) {
		/*  document.getElementById("alarm_box").innerHTML = event.data;  */
		alert(event.data); 
	};
	
	eventSource.onerror = function(event) {}; 
<%}%>
</script>
</head>
<body>

</body>
</html>