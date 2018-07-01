<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>

</head>


<body><%if(exception!=null)  {%>
에러:<%=exception.getMessage() %>
<%}else{ %>
에러:<%=(String)request.getAttribute("message") %>
<%} %>
</body>



</html>