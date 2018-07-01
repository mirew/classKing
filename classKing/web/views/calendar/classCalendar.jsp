<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="member.model.vo.Member,java.util.*,calendar.model.vo.Calendar"%>
<%
	Member loginUser = (Member) session.getAttribute("loginUser");
	
	int classNo = Integer.parseInt(request.getParameter("classNo"));
	
	ArrayList<Calendar> list = null;
	if(request.getAttribute("list") != null){
		list = (ArrayList<Calendar>)request.getAttribute("list");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<script src="/classKing/js/jquery-3.3.1.min.js"></script>

<link rel="stylesheet"
   href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
   integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
   crossorigin="anonymous">

<link rel='stylesheet' type='text/css'
   href='http://www.blueb.co.kr/data/201010/IJ12872423858253/fullcalendar.css' />
<script type='text/javascript'
   src='http://www.blueb.co.kr/data/201010/IJ12872423858253/jquery.js'></script>
<script type='text/javascript'
   src='http://www.blueb.co.kr/data/201010/IJ12872423858253/jquery-ui-custom.js'></script>
<script type='text/javascript'
   src='http://www.blueb.co.kr/data/201010/IJ12872423858253/fullcalendar.min.js'></script>

<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/menuBar.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/classCalendar.css" rel="stylesheet" type="text/css">
<!--  calendar js!!!!!!!!!!!!!!!!!!!!!!!! -->
<script type='text/javascript'>
	function updateCal(calno){
		 window.open("/classKing/caldetail?calno="+calno, "일정", "width=400, height=380, left=100, top=50");
	}
   $(document).ready(function() {
      $('#calendar').fullCalendar({
         header : {
            left : 'prev,next today',
            center : 'title',
            right : 'month,basicWeek,basicDay'
         },
         editable : false,
         events : [
         <% if(list != null){ %>
     	<% int count = 0;%>
     		<% for(Calendar cal : list) { %>
     		{
     			<% String smonth = null;
     				String emonth = null;
     			if(Integer.parseInt(cal.getCalSdate().toString().substring(5,7))-1 <10 ){ 
     				smonth = "0" + (Integer.parseInt(cal.getCalSdate().toString().substring(5,7))-1);
     			}else{
     				smonth = "" + (Integer.parseInt(cal.getCalSdate().toString().substring(5,7))-1);
     			 }
     			if(cal.getCalEdate() !=null){
	     			if(Integer.parseInt(cal.getCalEdate().toString().substring(5,7))-1 <10 ){
	 					emonth = "0" + (Integer.parseInt(cal.getCalEdate().toString().substring(5,7))-1);
		 			}else{
		 				emonth = "" + (Integer.parseInt(cal.getCalEdate().toString().substring(5,7))-1);
		 			} 
	 			}%>
     			title : "<%= cal.getCalTitle() %>",
     			start : new Date(<%= cal.getCalSdate().toString().substring(0,4) %>,<%= smonth %>,<%= cal.getCalSdate().toString().substring(8,10) %>)
     			<% if(cal.getCalEdate() !=null){%>
     			,end : new Date(<%= cal.getCalEdate().toString().substring(0,4) %>,<%= emonth %>,<%= cal.getCalEdate().toString().substring(8,10) %>)
				<%}%>,
				url : "javascript:updateCal(" + <%= cal.getCalNo() %> + ")"
     		}
     			<% if(count != list.size()-1){ %>
					,
					<%count++;%>
				<% } %>
     		<% } %>
     	<% } %>]
      });
   });
</script>
<script>
;(function($){
	<%if(request.getAttribute("message")!=null){%>
		alert("<%=request.getAttribute("message")%>");
	<%}%>
})($);
</script>
<script language="javascript">
	function calInsert(){
		var link = "/classKing/calinsert?title=" + $('#title').val() + "&note=" + $('#note').val()
					+ "&sdate=" + $('#sdate').val() + "&memberid=" + $('#memberid').val()
					+ "&classno=" + $('#classno').val();
		if($('#edate').val() != null && $('#edate').val() != ""){
			link += "&edate=" + $('#edate').val();
		}
		location.href = link;
	}
	function calUpdate(){
		var link = "/classKing/calupdate?title=" + $('#title').val() + "&note=" + $('#note').val()
					+ "&sdate=" + $('#sdate').val()
					+ "&classno=" + $('#classno').val() + "&calno=" + $("#calno").val();
		if($('#edate').val() != null && $('#edate').val() != ""){
			link += "&edate=" + $('#edate').val();
		}
		location.href = link;
	}
	function calDelete(){
		location.href="/classKing/caldelete?calno=" + $("#calno").val() + "&classno=" + $('#classno').val();
	}
  function showPopupCalen()
{ window.open("/classKing/views/calendar/calenPopup.html", "일정추가", "width=400, height=380, left=100, top=50"); }
</script>
<!--  calendar js!!!!!!!!!!!!!!!!!!!!!!!! -->
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
            <jsp:include page="../etc/joinCheck.jsp"> 
				  <jsp:param name="classNo" value="<%=classNo %>"/> 
				</jsp:include>
            <!-- 알람박스 시작 -->
            <% if (loginUser != null) { %>
					<%@ include file="../../views/etc/alarm.jsp"%>
				<% } %>
            <!-- 알람박스 끝 -->
         </div>
         <div id="left_box">
            <%@ include file="../etc/menuBar.jsp" %>
            <div id="main_box">
               <!-- 캘린더 시작 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!-->
               <div id="calendar"></div>
               <!-- 캘린더 끝 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!-->

               <div style="margin-bottom:60px;">
               	<input type="hidden" name="sdate" id="sdate">
               	<input type="hidden" name="edate" id="edate">
               	<input type="hidden" name="title" id="title">
               	<input type="hidden" name="note" id="note">
               	<input type="hidden" name="note" id="calno">
               	<input type="hidden" name="classno" id="classno" value="<%= classNo %>">
               	<input type="hidden" name="memberid" id="memberid" value ="<%= loginUser.getMemberId() %>">
                  <input type="button" value="일정추가" class="btn pull-left"
                     style="background: #3b64af; color: white;  float:right;" 
                     onclick="showPopupCalen();"/>
               </div>

            </div>
         </div>
      </div>
   </section>
   <%@ include file="../etc/footer.jsp" %>
</body>
</html>