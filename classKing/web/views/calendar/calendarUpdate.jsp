<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="member.model.vo.Member,calendar.model.vo.Calendar" %>
    <%
    	Member loginUser = (Member)session.getAttribute("loginUser");
    	Calendar cal = (Calendar)request.getAttribute("cal");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/main2.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">

<!-- font awesome -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script src="/classKing/js/jquery.lbslider.js"></script>
<script>
	function result(){
		var startDate = $('#sdate').val();
		var endDate = $('#edate').val();
		var startArray = startDate.split('-');
		var endArray = endDate.split('-');   
		var start_date = new Date(startArray[0], startArray[1], startArray[2]);
		var end_date = new Date(endArray[0], endArray[1], endArray[2]);
		if($("#sdate").val() && $("#title").val() && $("#note").val()){
			if(start_date.getTime() > end_date.getTime()) {
				alert("종료날짜보다 시작날짜가 작아야합니다.");
			}else{
				$('#sdate',opener.document).val($("#sdate").val());
				$('#edate',opener.document).val($("#edate").val());
				$('#title',opener.document).val($("#title").val());
				$('#note',opener.document).val($("#note").val());
				$('#calno',opener.document).val(<%=cal.getCalNo()%>);
				window.close();
				opener.location.href="javascript:calUpdate()";
			}
		}else {
			alert("종료날짜를 제외한 모든값을 입력 후 일정등록이 가능합니다.");
		}
	}
	function calDelete(){
		var result = confirm("일정을 정말 삭제하시겠습니까?");
		if(result){
			$('#calno',opener.document).val(<%=cal.getCalNo()%>);
			window.close();
			opener.location.href="javascript:calDelete()";
		}
	}
</script>
<title>일정 추가</title>
<style>
  table {
    width: 100%;
    border: 0;
  }
  table tr th{
    border: 0;
  }
  table tr td{
    border: 0;
  }
</style>
</head>
<body>
<% if(cal.getMemberId().equals(loginUser.getMemberId())){ %>
<!-- 일정추가 시작 -->
	<div id="cal_board" style="margin: 5px;">
			<table class="table table-bordered" style="border: 0;">
				<thread>
				<caption
					style="padding-left: 10px; background: #3b64af; color: white;"><i class="far fa-edit">일정추가</i></caption>
				<tr>
					<th style="background: #c5cdd7;">시작 날짜</th>
					<td><input type="date" name="sdate" id="sdate" value="<%= cal.getCalSdate() %>"></td>
				</tr>

				<tr>
					<th style="background: #c5cdd7;">종료 날짜</th>
					<td><input type="date" name="edate" id="edate" value="<%= cal.getCalEdate() %>"></td>
							</tr>
				<tr>
					<th style="background: #c5cdd7;">제목</th>
					<td><input type="text" name="addcalen" id="title" size="34" value="<%=cal.getCalTitle()%>"></td>
				</tr>
				<tr>
					<th style="background: #c5cdd7;">내용</th>
					<td><textarea type="text" cols="36" rows="7" id="note"><%=cal.getCalNote()%></textarea></td>
				</tr>
				<tr>
					<td colspan="2" align="center" style="border:none;"><button type="button" style="align: center;" onclick="result();">등록</button>
					<button type="button" style="align: center;" onclick="calDelete();">삭제</button></td>
				</tr>
				</thread>
			</table>
	</div>
	<% }else{ %>
			<div id="cal_board" style="margin: 5px;">
			<table class="table table-bordered" style="border: 0;">
				<thread>
				<caption
					style="padding-left: 10px; background: #3b64af; color: white;"><i class="far fa-edit">일정추가</i></caption>
				<tr>
					<th style="background: #c5cdd7;">시작 날짜</th>
					<td><input type="date" name="sdate" id="sdate" value="<%= cal.getCalSdate() %>" readonly></td>
				</tr>

				<tr>
					<th style="background: #c5cdd7;">종료 날짜</th>
					<td><input type="date" name="edate" id="edate" value="<%= cal.getCalEdate() %>" readonly></td>
							</tr>
				<tr>
					<th style="background: #c5cdd7;">제목</th>
					<td><input type="text" name="addcalen" id="title" size="34" value="<%=cal.getCalTitle()%>" readonly></td>
				</tr>
				<tr>
					<th style="background: #c5cdd7;">내용</th>
					<td><textarea type="text" cols="36" rows="7" id="note" readonly><%=cal.getCalNote()%></textarea></td>
				</tr>
				<tr>
					<td colspan="2" align="center" style="border:none;"></td>
				</tr>
				</thread>
			</table>
	</div>
	<% } %>
	<!-- 일정추가 끝! -->
</body>
</html>