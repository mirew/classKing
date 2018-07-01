<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
	    int result =0;
		if(request.getParameter("result") != null){
			result = Integer.parseInt(request.getParameter("result"));
		}	  
	 %>
<!DOCTYPE html>
<html>
<head>
<meta  charset="UTF-8">
<title>가입신청 완료</title>
<script type="text/javascript">

<%-- function close(result){
		<%if(result > 0){%>
		window.close();
		<% }%>
} --%>
	<%-- $(function)(){
		<%if(result > 0){%>
		
			window.close();
		<% }%>
	}); --%>

</script>
</head>
<body>
<div style="text-align:center"><br><br><br><br><br>
<h3>가입신청 완료 되었습니다.</h3><br></div>
<br><br><br>
<div style="text-align:center;">
<input type="button" style="float:center" onclick="window.close();" value="확인"></div>
</body>
</html>