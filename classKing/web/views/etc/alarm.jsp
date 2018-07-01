<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member,classes.model.vo.Classes,java.util.*"%>
<%
	Member loginUser2 = (Member) session.getAttribute("loginUser");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(function(){
		<% if(loginUser2 != null){%>		
			$.ajax({
				url : "/classKing/plist",
				data : {memberid : "<%= loginUser2.getMemberId() %>"},
				type : "get",
				datatype: "json",
				success : function(data){
					
					var jsonStr = JSON.stringify(data);
					var json = JSON.parse(jsonStr);
					var size =Object.keys(json.list).length;
					 if(size>0){
						 var values = "";
							
							for(var i in json.list){
								values += "<li class='push_list'><a href='/classKing/kingcheck?boardNo="+json.list[i].boardNo+"&classNo="+json.list[i].classNo+"&page=1&memberId=<%=loginUser2.getMemberId()%>'>"
								+json.list[i].classTitle+"클래스 "+json.list[i].boardNo+"번 글에 "+json.list[i].commentsWriter+"님이 댓글을 달았습니다.</a>"
								+"<div class='push_read'><span class='pushdate'>"+json.list[i].pushDate+"</span><a href='javascript:checkPush(\""+json.list[i].pushNo+"\");'>확인</a></div></li>";
							}
							$("#push").html(values);
					 }else{
						 $("#push").html("새로운 댓글이 없습니다.");
					 }
				},error:function(a,b,c){
					console.log("error: " + a + ", " + b + ", " + c)
				}
			}); //ajax
		
		<% } %>
		})//onload
		function selectPush(){
			$.ajax({
				url : "/classKing/plist",
				data : {memberid : "<%= loginUser2.getMemberId() %>"},
				type : "get",
				datatype: "json",
				success : function(data){
					var jsonStr = JSON.stringify(data);
					var json = JSON.parse(jsonStr);
					var size =Object.keys(json.list).length;
					if(size>0){
						var values = "";
						for(var i in json.list){
							values += "<li class='push_list'><a href='/classKing/kingcheck?boardNo="+json.list[i].boardNo+"&classNo="+json.list[i].classNo+"&page=1&memberId=<%=loginUser2.getMemberId()%>'>"
							+json.list[i].classTitle+"클래스 "+json.list[i].boardNo+"번 글에 "+json.list[i].commentsWriter+"님이 댓글을 달았습니다.</a>"
							+"<div class='push_read'><span class='pushdate'>"+json.list[i].pushDate+"</span><a href='javascript:checkPush(\""+json.list[i].pushNo+"\");'>확인</a></div></li>";
						}
						$("#push").html(values);
					}else{
						 $("#push").html("새로운 댓글이 없습니다.");
					}
				},error:function(a,b,c){
					console.log("error: " + a + ", " + b + ", " + c)
				}
			});
		};
		setInterval(selectPush,3000);
		
		function checkPush(pushNo){
			$.ajax({
				url : "/classKing/pread",
				data : {pushNo : pushNo},
				type : "get",
				datatype: "json",
				success : function(data){
					selectPush()				
				},error:function(a,b,c){
					console.log("error: " + a + ", " + b + ", " + c)
				}
			});
		}
</script>
</head>
<body>
	<div id="alarm_box">
		<p>댓글 알림</p><hr>
		<ul id="push">
		</ul>
	</div>
</body>
</html>