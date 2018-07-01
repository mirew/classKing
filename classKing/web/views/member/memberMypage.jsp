<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member"%>
<%
	Member loginUser = (Member) session.getAttribute("loginUser");
	Member checkUser = (Member) request.getAttribute("checkuser");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="/classKing/css/normalize.css" />
<link rel="stylesheet" type="text/css" href="/classKing/css/demo.css" />
<link rel="stylesheet" type="text/css"
	href="/classKing/css/component.css" />
<script src="/classKing/js/jquery-3.3.1.min.js"></script>

<link href="/classKing/css/reset.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>

<link href="/classKing/css/main.css" rel="stylesheet" type="text/css">
<link href="/classKing/css/mypage.css" rel="stylesheet" type="text/css">

<script>
var pwdChecked =false;   
$(function() {
      $('input[type=password]').blur(function() {
         var pwd1 = $("#inputPwd1").val();
         var pwd2 = $("#inputPwd2").val();
         if (pwd1 == pwd2) {
            $("#pwdSame").css("color", "green").css("display", "block");
            $("#pwdSame").html("비밀번호가 일치합니다.");
            pwdChecked=true;
         } else {
            $("#pwdSame").css("color", "red").css("display", "block");
            $("#pwdSame").html("비밀번호가 불일치합니다.");
            $("#inputPwd2").val("");
            pwdChecked=false;
         }
      });
   });
function submition(){
   var pwdPattern = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
   var pwd = $("#inputPwd1").val();
   if (!pwdPattern.test(pwd)) {
         alert("비밀번호: 6~20자리 이상 숫자 및 영문자를 혼합 해주세요.");
         $("#inputPwd1").focus();
         pwdChecked=false;
         return pwdChecked;
   }else if (pwdPattern.test(pwd)){
      pwdChecked=true;
      return pwdChecked;   
   }
   }
function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			$('#profile').attr('src', e.target.result);
		}
		reader.readAsDataURL(input.files[0]);
	}
}
$(function(){
	MyClass();
});
	function classGetPage(page){
		$.ajax({
			url : "/classKing/mpclass",
			data : {memberid : "<%=loginUser.getMemberId()%>",
					page : page},
			type : "get",
			datatype: "json",
			success : function(data){
				var jsonStr = JSON.stringify(data);
				var json = JSON.parse(jsonStr);
				var values = "<table class='table' id='classtable'><tr><th>No.</th><th>클래스 명</th><th>클래스 개설일</th><th>멤버수</th></tr>";
				for(var i in json.list){
					values += "<tr><td>" + json.list[i].no + "</td><td><a href='/classKing/views/class/classHome.jsp?classNo="+json.list[i].no+"'>" + json.list[i].title
							+ "</a></td><td>" + json.list[i].date + "</td><td>" + json.list[i].count; + "</td></tr>"
				}

				var maxPage = json.list[0].maxPage;
				var startPage = json.list[0].startPage;
				var endPage = json.list[0].endPage;
				var currentPage = json.list[0].currentPage;
				
				values += "</table><div id='notice_page1' class='pageList'><ul class='pagination' id='classPage'>";
				
				if(startPage > 5){
					values += "<li class='page-item'><a class='page-link' href='javascript:classGetPage("+(startPage-1)+")'>[prev]</a></li>";
			    }else{
			    	values +="<li class='page-item'><a class='page-link'>prev</a></li>"
		        }
				
				for(var p = startPage;p<=endPage;p++){ 
					if(p == currentPage){
						values += "<li class='page-item'><a class='page-link'>"+p+"</a></li>";
					}else{
						values+= "<li class='page-item'><a class='page-link' href='javascript:classGetPage("+p+")'>"+ p+"</a></li>"
					} 	
				}
				if(endPage < maxPage){
					values += "<li class='page-item'><a class='page-link' href='javascript:classGetPage("+(endPage+1)+")'>"+"[next]</a></li>";
				}else{
					values +="<li class='page-item'><a class='page-link'>next</a></li>";
				}
				values += "</ul></div>";
				$("#class").html(values);
			},error:function(a,b,c){
				console.log("error: " + a + ", " + b + ", " + c)
			}
		});
	}
	function MyClass(){
		$.ajax({
			url : "/classKing/mpclass",
			data : {memberid : "<%=loginUser.getMemberId()%>"},
			type : "get",
			datatype: "json",
			success : function(data){
				var jsonStr = JSON.stringify(data);
				var json = JSON.parse(jsonStr);
				var size=Object.keys(json.list).length;
				if(size>0){
					var values = "<table class='table' id='classtable'><tr><th>No.</th><th>클래스 명</th><th>클래스 개설일</th><th>멤버수</th></tr>";
					for(var i in json.list){
						values += "<tr><td>" + json.list[i].no + "</td><td><a href='/classKing/views/class/classHome.jsp?classNo="+json.list[i].no+"'>" + json.list[i].title
								+ "</a></td><td>" + json.list[i].date + "</td><td>" + json.list[i].count; + "</td></tr>"
					}
		
					var maxPage = json.list[0].maxPage;
					var startPage = json.list[0].startPage;
					var endPage = json.list[0].endPage;
					var currentPage = json.list[0].currentPage;
		
					values += "</table><div id='notice_page1' class='pageList'><ul class='pagination' id='classPage'>";
					
					if(startPage > 5){
						values += "<li class='page-item'><a class='page-link' href='javascript:classGetPage("+(startPage-1)+")'>[prev]</a></li>";
				    }else{
				    	values +="<li class='page-item'><a class='page-link'>prev</a></li>"
			        }
					
					for(var p = startPage;p<=endPage;p++){ 
						if(p == currentPage){
							values += "<li class='page-item'><a class='page-link'>"+p+"</a></li>";
						}else{
							values+= "<li class='page-item'><a class='page-link' href='javascript:classGetPage("+p+")'>"+ p+"</a></li>"
						} 	
					}
					
					if(endPage < maxPage){
						values += "<li class='page-item'><a class='page-link' href='javascript:classGetPage("+(endPage+1)+")'>"+"[next]</a></li>";
					}else{
						values +="<li class='page-item'><a class='page-link'>next</a></li>";
					}
					values += "</ul></div>";
					$("#class").html(values);
				}else{ 
					var values2="<br><br><p style='width:100%;margin-top:20px;font-size:20px;color:#999;text-align:center;'>"
					+"가입하신 클래스가 없습니다.</p><br><br>";
					$("#class").html(values2);
				}
			},error:function(a,b,c){
				console.log("error: " + a + ", " + b + ", " + c)
			}
		});
	}
	function boardGetPage(page){
		$.ajax({
			url : "/classKing/mpboard",
			data : {memberid : "<%=loginUser.getMemberId()%>",
					page : page},
			type : "get",
			datatype: "json",
			success : function(data){
					var jsonStr = JSON.stringify(data);
					var json = JSON.parse(jsonStr);
					var values = "<table class='table' id='board_table'><tr><th>No.</th><th>클래스 명</th><th>제목</th><th>작성일자</th></tr>";
					for(var i in json.list){
						values += "<tr><td>" + json.list[i].no + "</td><td>" + json.list[i].classtitle
								+ "</td><td><a href='/classKing/kingcheck?boardNo="+json.list[i].no + "&classNo="+json.list[i].classno +"&page=1&memberId=<%=loginUser.getMemberId()%>'>" + json.list[i].boardtitle + "</a></td><td>" + json.list[i].date; + "</td></tr>"
					}
					var maxPage = json.list[0].maxPage;
					var startPage = json.list[0].startPage;
					var endPage = json.list[0].endPage;
					var currentPage = json.list[0].currentPage;
					
					//values += $("#class").html();
					values += "</table><div id='notice_page2' class='pageList'><ul class='pagination' id='classPage'>";
					
					if(startPage > 5){
						values += "<li class='page-item'><a class='page-link' href='javascript:boardGetPage("+(startPage-1)+")'>[prev]</a></li>";
				    }else{
				    	values +="<li class='page-item'><a class='page-link'>prev</a></li>"
			        }
					
					for(var p = startPage;p<=endPage;p++){ 
						if(p == currentPage){
							values += "<li class='page-item'><a class='page-link'>"+p+"</a></li>";
						}else{
							values+= "<li class='page-item'><a class='page-link' href='javascript:boardGetPage("+p+")'>"+ p+"</a></li>"
						} 	
					}
					
					if(endPage < maxPage){
						values += "<li class='page-item'><a class='page-link' href='javascript:boardGetPage("+(endPage+1)+")'>"+"[next]</a></li>";
					}else{
						values +="<li class='page-item'><a class='page-link'>next</a></li>";
					}
					values += "</ul></div>";
					$("#board").html(values);
				},error:function(a,b,c){
					console.log("error: " + a + ", " + b + ", " + c)
				}
			});
	}
	function MyBoard(){
		$.ajax({
			url : "/classKing/mpboard",
			data : {memberid : "<%=loginUser.getMemberId()%>"},
			type : "get",
			datatype: "json",
			success : function(data){
				var jsonStr = JSON.stringify(data);
				var json = JSON.parse(jsonStr);
				var size = Object.keys(json.list).length;
				
				if(size>0){
					var values = "<table class='table' id='board_table'><tr><th>No.</th><th>클래스 명</th><th>제목</th><th>작성일자</th></tr>";
					for(var i in json.list){
						values += "<tr><td>" + json.list[i].no + "</td><td>" + json.list[i].classtitle
								+ "</td><td><a href='/classKing/kingcheck?boardNo="+json.list[i].no + "&classNo="+json.list[i].classno +"&page=1&memberId=<%=loginUser.getMemberId()%>'>" + json.list[i].boardtitle + "</a></td><td>" + json.list[i].date; + "</td></tr>"
					}
					var maxPage = json.list[0].maxPage;
					var startPage = json.list[0].startPage;
					var endPage = json.list[0].endPage;
					var currentPage = json.list[0].currentPage;
					
					//values += $("#class").html();
					values += "</table><div id='notice_page2' class='pageList'><ul class='pagination' id='classPage'>";
					
					if(startPage > 5){
						values += "<li class='page-item'><a class='page-link' href='javascript:boardGetPage("+(startPage-1)+")'>[prev]</a></li>";
				    }else{
				    	values +="<li class='page-item'><a class='page-link'>prev</a></li>"
			        }
					
					for(var p = startPage;p<=endPage;p++){ 
						if(p == currentPage){
							values += "<li class='page-item'><a class='page-link'>"+p+"</a></li>";
						}else{
							values+= "<li class='page-item'><a class='page-link' href='javascript:boardGetPage("+p+")'>"+ p+"</a></li>"
						} 	
					}
					
					if(endPage < maxPage){
						values += "<li class='page-item'><a class='page-link' href='javascript:boardGetPage("+(endPage+1)+")'>"+"[next]</a></li>";
					}else{
						values +="<li class='page-item'><a class='page-link'>next</a></li>";
					}
					values += "</ul></div>";
					$("#board").html(values);
				}else{
					var values2="<br><br><p style='width:100%;margin-top:20px;font-size:20px;color:#999;text-align:center;'>"
					+"등록한 게시물이 없습니다.</p><br><br>";
					$("#board").html(values2);
				}
			},error:function(a,b,c){
				console.log("error: " + a + ", " + b + ", " + c)
			}
		});
	}

	function commentGetPage(page){
		$.ajax({
			url : "/classKing/mpcomment",
			data : {memberid : "<%=loginUser.getMemberId()%>",
				page : page},
			type : "get",
			datatype: "json",
			success : function(data){
				var jsonStr = JSON.stringify(data);
				var json = JSON.parse(jsonStr);
				var values = "<table class='table' id='comment_table'><tr><th>No.</th><th>클래스 명</th><th>내용</th><th>작성일자</th></tr>";
				for(var i in json.list){
					values += "<tr><td>" + json.list[i].no + "</td><td>" + json.list[i].classtitle
							+ "</td><td><a href='/classKing/kingcheck?boardNo="+json.list[i].no + "&classNo="+json.list[i].classno +"&page=1&memberId=<%=loginUser.getMemberId()%>'>" + json.list[i].note + "</a></td><td>" + json.list[i].date; + "</td></tr>"
				}
				var maxPage = json.list[0].maxPage;
				var startPage = json.list[0].startPage;
				var endPage = json.list[0].endPage;
				var currentPage = json.list[0].currentPage;
				
				//values += $("#class").html();
				values += "</table><div id='notice_page3' class='pageList'><ul class='pagination' id='classPage'>";
				
				if(startPage > 5){
					values += "<li class='page-item'><a class='page-link' href='javascript:commentGetPage("+(startPage-1)+")'>[prev]</a></li>";
			    }else{
			    	values +="<li class='page-item'><a class='page-link'>prev</a></li>"
		        }
				
				for(var p = startPage;p<=endPage;p++){ 
					if(p == currentPage){
						values += "<li class='page-item'><a class='page-link'>"+p+"</a></li>";
					}else{
						values+= "<li class='page-item'><a class='page-link' href='javascript:commentGetPage("+p+")'>"+ p+"</a></li>"
					} 	
				}
				
				if(endPage < maxPage){
					values += "<li class='page-item'><a class='page-link' href='javascript:commentGetPage("+(endPage+1)+")'>"+"[next]</a></li>";
				}else{
					values +="<li class='page-item'><a class='page-link'>next</a></li>";
				}
				values += "</ul></div>";
				$("#comment").html(values);
			},error:function(a,b,c){
				console.log("error: " + a + ", " + b + ", " + c)
			}
		});
	}
	function MyComment(){
		$.ajax({
			url : "/classKing/mpcomment",
			data : {memberid : "<%=loginUser.getMemberId()%>"},
			type : "get",
			datatype: "json",
			success : function(data){
				var jsonStr = JSON.stringify(data);
				var json = JSON.parse(jsonStr);
				var size = Object.keys(json.list).length;
				if(size>0){
					var values = "<table class='table' id='comment_table'><tr><th>No.</th><th>클래스 명</th><th>내용</th><th>작성일자</th></tr>";
					for(var i in json.list){
						values += "<tr><td>" + json.list[i].no + "</td><td>" + json.list[i].classtitle
								+ "</td><td><a href='/classKing/kingcheck?boardNo="+json.list[i].no + "&classNo="+json.list[i].classno +"&page=1&memberId=<%=loginUser.getMemberId()%>'>" + json.list[i].note + "</a></td><td>" + json.list[i].date; + "</td></tr>"
					}
					var maxPage = json.list[0].maxPage;
					var startPage = json.list[0].startPage;
					var endPage = json.list[0].endPage;
					var currentPage = json.list[0].currentPage;
					
					//values += $("#class").html();
					values += "</table><div id='notice_page3' class='pageList'><ul class='pagination' id='classPage'>";
					
					if(startPage > 5){
						values += "<li class='page-item'><a class='page-link' href='javascript:commentGetPage("+(startPage-1)+")'>[prev]</a></li>";
				    }else{
				    	values +="<li class='page-item'><a class='page-link'>prev</a></li>"
			        }
					
					for(var p = startPage;p<=endPage;p++){ 
						if(p == currentPage){
							values += "<li class='page-item'><a class='page-link'>"+p+"</a></li>";
						}else{
							values+= "<li class='page-item'><a class='page-link' href='javascript:commentGetPage("+p+")'>"+ p+"</a></li>"
						} 	
					}
					
					if(endPage < maxPage){
						values += "<li class='page-item'><a class='page-link' href='javascript:commentGetPage("+(endPage+1)+")'>"+"[next]</a></li>";
					}else{
						values +="<li class='page-item'><a class='page-link'>next</a></li>";
					}
					values += "</ul></div>";
					$("#comment").html(values);
				}else{
					var values2="<br><br><p style='width:100%;margin-top:20px;font-size:20px;color:#999;text-align:center;'>"
								+"등록한 댓글이 없습니다.</p><br><br>";
								$("#comment").html(values2);					
				}
			},error:function(a,b,c){
				console.log("error: " + a + ", " + b + ", " + c)
			}
		});
	}
function qnaGetPage(page){
		$.ajax({
			url : "/classKing/myqna",
			data : {memberid : "<%=loginUser.getMemberId()%>", page : page},
			type : "get",
			datatype: "json",
			success : function(data){
				var jsonStr = JSON.stringify(data);
				var json = JSON.parse(jsonStr);
				var values="";
				
				if(Object.keys(json.list).length>0){
				values = "<table class='table' id='qnatable'><tr><th>No.</th><th>질문</th><th>처리결과</th><th>작성일자</th></tr>";
				for(var i in json.list){
			values+="<tr><td>" + json.list[i].no + "</td><td><a href='/classKing/qdetail?qnanum="+json.list[i].no+"'>" +json.list[i].qnatitle+"</a></td>";
							
				if(json.list[i].answer==null){
			values+="<td><span class='qna_false'>답변대기</span></td><td>"+json.list[i].date+"</td></tr>";
				}else{
			values+="<td><span class='qna_true'>답변완료</span></td><td>"+json.list[i].date+"</td></tr>";
				}
				
			}
			
				
				
					
				
				var maxPage = json.list[0].maxPage;
				var startPage = json.list[0].startPage;
				var endPage = json.list[0].endPage;
				var currentPage = json.list[0].currentPage;
				
				values += "</table><div id='notice_page3' class='pageList'><ul class='pagination' id='classPage'>";
				
				if(startPage > 5){
					values += "<li class='page-item'><a class='page-link' href='javascript:qnaGetPage("+(startPage-1)+")'>[prev]</a></li>";
			    }else{
			    	values +="<li class='page-item'><a class='page-link'>prev</a></li>"
		        }
				
				for(var p = startPage;p<=endPage;p++){ 
					if(p == currentPage){
						values += "<li class='page-item'><a class='page-link'>"+p+"</a></li>";
					}else{
						values+= "<li class='page-item'><a class='page-link' href='javascript:qnaGetPage("+p+")'>"+ p+"</a></li>"
					} 	
				}
				
				if(endPage < maxPage){
					values += "<li class='page-item'><a class='page-link' href='javascript:qnaGetPage("+(endPage+1)+")'>"+"[next]</a></li>";
				}else{
					values +="<li class='page-item'><a class='page-link'>next</a></li>";
				}
				values += "</ul></div>";
				$("#qanda").html(values);
				}else{
					var values="<br><br><p style='width:100%;margin-top:20px;font-size:20px;color:#999;text-align:center;'>"
						+"등록한 질문이 없습니다.</p><br><br>";								
						$("#qanda").html(values);				
					
				}
			},error:function(a,b,c){
				console.log("error: " + a + ", " + b + ", " + c)
			}
		});
	}
	
	
	
	
	function MyQna(){
		$.ajax({
			url : "/classKing/myqna",
			data : {memberid : "<%=loginUser.getMemberId()%>"},
			type : "get",
			datatype: "json",
			success : function(data){
				
				var jsonStr = JSON.stringify(data);
				var json = JSON.parse(jsonStr);
				var values="";
				
				if(Object.keys(json.list).length>0){
				values = "<table class='table' id='qnatable'><tr><th>No.</th><th>질문</th><th>처리결과</th><th>작성일자</th></tr>";
					
				
					
					for(var i in json.list){
						values += "<tr><td>" + json.list[i].no + "</td><td><a href='/classKing/qdetail?qnanum="+json.list[i].no+" '>" +json.list[i].qnatitle+"</a></td>";
								
						if(json.list[i].answer==null){
							values+="<td><span class='qna_false'>답변대기</span></td><td>"+json.list[i].date+"</td>";
						}else{
							values+="<td><span class='qna_true'>답변완료</span></td><td>"+json.list[i].date+"</td></tr>";
						}
					}
					
					var maxPage = json.list[0].maxPage;  
					var startPage = json.list[0].startPage;
					var endPage = json.list[0].endPage;
					var currentPage = json.list[0].currentPage;
					
					values += "</table><div id='notice_page3' class='pageList'><ul class='pagination' id='classPage'>";
					
					if(startPage > 5){
						values += "<li class='page-item'><a class='page-link' href='javascript:qnaGetPage("+(startPage-1)+")'>[prev]</a></li>";
				    }else{
				    	values +="<li class='page-item'><a class='page-link'>prev</a></li>"
			        }
					
					for(var p = startPage;p<=endPage;p++){ 
						if(p == currentPage){
							values += "<li class='page-item'><a class='page-link'>"+p+"</a></li>";
						}else{
							values+= "<li class='page-item'><a class='page-link' href='javascript:qnaGetPage("+p+")'>"+ p+"</a></li>"
						} 	
					}
					
					if(endPage < maxPage){
						values += "<li class='page-item'><a class='page-link' href='javascript:qnaGetPage("+(endPage+1)+")'>"+"[next]</a></li>";
					}else{
						values +="<li class='page-item'><a class='page-link'>next</a></li>";
					}
					values += "</ul></div>";
					$("#qanda").html(values);
					
				}else{
					var values="<br><br><p style='width:100%;margin-top:20px;font-size:20px;color:#999;text-align:center;'>"
						+"등록한 질문이 없습니다.</p><br><br>";								
						$("#qanda").html(values);	

					
					
					
					$("#qanda").html(values);
				}
			},error:function(a,b,c){
				console.log("error: " + a + ", " + b + ", " + c)
			}
		});
	}

	
	function boardreportGetPage(page){
		$.ajax({
			url : "/classKing/rboard",
			data : {memberid : "<%=loginUser.getMemberId()%>",
				page : page},
			type : "get",
			datatype: "json",
			success : function(data){
				var jsonStr = JSON.stringify(data);
				var json = JSON.parse(jsonStr);
				var values="";
				if(Object.keys(json.list).length>0) {
				 values = "<table class='table' id='reportboard'><tr><th>No.</th><th>클래스 명</th><th>내용</th><th>작성일자</th></tr>";
				for(var i in json.list){
				values += "<tr><td>" + json.list[i].no + "</td><td>" + json.list[i].classname
							+ "</td><td>" + json.list[i].category + "</td><td>" + json.list[i].date; + "</td></tr>"
				}
				var maxPage = json.list[0].maxPage;
				var startPage = json.list[0].startPage;
				var endPage = json.list[0].endPage;
				var currentPage = json.list[0].currentPage;
				
				values += "</table><div id='notice_page3' class='pageList'><ul class='pagination' id='classPage'>";
				
				if(startPage > 5){
					values += "<li class='page-item'><a class='page-link' href='javascript:boardreportGetPage("+(startPage-1)+")'>[prev]</a></li>";
			    }else{
			    	values +="<li class='page-item'><a class='page-link'>prev</a></li>"
		        }
				
				for(var p = startPage;p<=endPage;p++){ 
					if(p == currentPage){
						values += "<li class='page-item'><a class='page-link'>"+p+"</a></li>";
					}else{
						values+= "<li class='page-item'><a class='page-link' href='javascript:boardreportGetPage("+p+")'>"+ p+"</a></li>"
					} 	
				}
				
				if(endPage < maxPage){
					values += "<li class='page-item'><a class='page-link' href='javascript:boardreportGetPage("+(endPage+1)+")'>"+"[next]</a></li>";
				}else{
					values +="<li class='page-item'><a class='page-link'>next</a></li>";
				}
				values += "</ul></div>";
				$("#boardreport").html(values);
				}else{
					values="<div style='text-align: center'><br><br><br><br><br><br><br><br><br><br><br>"+
					"<h2 style='color:#DDDDDD; text-align:center;'>신고한 게시물이 없습니다.</h2>"+
							"<br><br><br><br><br><br><br><br><br><br><br><br></div>"
					$("#boardreport").html(values);
				}
			},error:function(a,b,c){
				console.log("error: " + a + ", " + b + ", " + c)
			}
		});
	}
	
	function boardreport(){
		$.ajax({
			url : "/classKing/rboard",
			data : {memberid : "<%=loginUser.getMemberId()%>"},
			type : "get",
			datatype: "json",
			success : function(data){
				var jsonStr = JSON.stringify(data);
				var json = JSON.parse(jsonStr);
				var values="";
				if(Object.keys(json.list).length>0) {
					values = "<table class='table' id='reportboard'><tr><th>No.</th><th>클래스 명</th><th>내용</th><th>작성일자</th></tr>";
					for(var i in json.list){
						values += "<tr><td>" + json.list[i].no + "</td><td>" + json.list[i].classname
								+ "</td><td>" + json.list[i].category + "</td><td>" + json.list[i].date; + "</td></tr>"
					}
					var maxPage = json.list[0].maxPage;
					var startPage = json.list[0].startPage;
					var endPage = json.list[0].endPage;
					var currentPage = json.list[0].currentPage;
					
					values += "</table><div id='notice_page3' class='pageList'><ul class='pagination' id='classPage'>";
					
					if(startPage > 5){
						values += "<li class='page-item'><a class='page-link' href='javascript:boardreportGetPage("+(startPage-1)+")'>[prev]</a></li>";
				    }else{
				    	values +="<li class='page-item'><a class='page-link'>prev</a></li>"
			        }
					
					for(var p = startPage;p<=endPage;p++){ 
						if(p == currentPage){
							values += "<li class='page-item'><a class='page-link'>"+p+"</a></li>";
						}else{
							values+= "<li class='page-item'><a class='page-link' href='javascript:boardreportGetPage("+p+")'>"+ p+"</a></li>"
						} 	
					}
					
					if(endPage < maxPage){
						values += "<li class='page-item'><a class='page-link' href='javascript:boardreportGetPage("+(endPage+1)+")'>"+"[next]</a></li>";
					}else{
						values +="<li class='page-item'><a class='page-link'>next</a></li>";
					}
					values += "</ul></div>";
					$("#boardreport").html(values);
				}else{
					var values="<br><br><p style='width:100%;margin-top:20px;font-size:20px;color:#999;text-align:center;'>"
						+"신고한 게시물이 없습니다.</p><br><br>";								
						$("#qanda").html(values);	
					$("#boardreport").html(values);
				}
			},error:function(a,b,c){
				console.log("error: " + a + ", " + b + ", " + c)
			}
		});
	}
	function commentreportGetPage(page){
		$.ajax({
			url : "/classKing/rcomment",
			data : {memberid : "<%=loginUser.getMemberId()%>",
				page : page},
			type : "get",
			datatype: "json",
			success : function(data){
				var jsonStr = JSON.stringify(data);
				var json = JSON.parse(jsonStr);
				var values="";
				if(Object.keys(json.list).length>0) {
				values = "<table class='table' id='reportcomment'><tr><th>No.</th><th>클래스 명</th><th>내용</th><th>작성일자</th></tr>";
				for(var i in json.list){
					values += "<tr><td>" + json.list[i].no + "</td><td>" + json.list[i].classname
							+ "</td><td>" + json.list[i].category + "</td><td>" + json.list[i].date; + "</td></tr>"
				}
				var maxPage = json.list[0].maxPage;
				var startPage = json.list[0].startPage;
				var endPage = json.list[0].endPage;
				var currentPage = json.list[0].currentPage;
				
				values += "</table><div id='notice_page3' class='pageList'><ul class='pagination' id='classPage'>";
				
				if(startPage > 5){
					values += "<li class='page-item'><a class='page-link' href='javascript:commentreportGetPage("+(startPage-1)+")'>[prev]</a></li>";
			    }else{
			    	values +="<li class='page-item'><a class='page-link'>prev</a></li>"
		        }
				
				for(var p = startPage;p<=endPage;p++){ 
					if(p == currentPage){
						values += "<li class='page-item'><a class='page-link'>"+p+"</a></li>";
					}else{
						values+= "<li class='page-item'><a class='page-link' href='javascript:commentreportGetPage("+p+")'>"+ p+"</a></li>"
					} 	
				}
				
				if(endPage < maxPage){
					values += "<li class='page-item'><a class='page-link' href='javascript:commentreportGetPage("+(endPage+1)+")'>"+"[next]</a></li>";
				}else{
					values +="<li class='page-item'><a class='page-link'>next</a></li>";
				}
				values += "</ul></div>";
				$("#commentreport").html(values);
				}else{
					values="<div style='text-align: center'><br><br><br><br><br><br><br><br><br><br><br>"+
					"<h2 style='color:#DDDDDD; text-align:center;'>신고한 댓글이 없습니다.</h2>"+
							"<br><br><br><br><br><br><br><br><br><br><br><br></div>"
					
					$("#commentreport").html(values);
				}
			},error:function(a,b,c){
				console.log("error: " + a + ", " + b + ", " + c)
			}
		});
	}
	function commentreport(){
		$.ajax({
			url : "/classKing/rcomment",
			data : {memberid : "<%=loginUser.getMemberId()%>"
					},
					type : "get",
					datatype : "json",
					success : function(data) {
						var jsonStr = JSON.stringify(data);
						var json = JSON.parse(jsonStr);
						var values="";
						if(Object.keys(json.list).length>0) {
						values = "<table class='table' id='reportcomment'><tr><th>No.</th><th>클래스 명</th><th>내용</th><th>작성일자</th></tr>";
						for ( var i in json.list) {
							values += "<tr><td>" + json.list[i].no
									+ "</td><td>" + json.list[i].classname
									+ "</td><td>"
									+ json.list[i].category + "</td><td>"
									+ json.list[i].date;
							+"</td></tr>"
						}
						var maxPage = json.list[0].maxPage;
						var startPage = json.list[0].startPage;
						var endPage = json.list[0].endPage;
						var currentPage = json.list[0].currentPage;

						values += "</table><div id='notice_page3' class='pageList'><ul class='pagination' id='classPage'>";

						if (startPage > 5) {
							values += "<li class='page-item'><a class='page-link' href='javascript:commentreportGetPage("
									+ (startPage - 1) + ")'>[prev]</a></li>";
						} else {
							values += "<li class='page-item'><a class='page-link'>prev</a></li>"
						}

						for (var p = startPage; p <= endPage; p++) {
							if (p == currentPage) {
								values += "<li class='page-item'><a class='page-link'>"
										+ p + "</a></li>";
							} else {
								values += "<li class='page-item'><a class='page-link' href='javascript:commentreportGetPage("
										+ p + ")'>" + p + "</a></li>"
							}
						}

						if (endPage < maxPage) {
							values += "<li class='page-item'><a class='page-link' href='javascript:commentreportGetPage("
									+ (endPage + 1) + ")'>" + "[next]</a></li>";
						} else {
							values += "<li class='page-item'><a class='page-link'>next</a></li>";
						}
						values += "</ul></div>";
						$("#commentreport").html(values);
					}else{
						var values="<br><br><p style='width:100%;margin-top:20px;font-size:20px;color:#999;text-align:center;'>"
							+"신고한 댓글이 없습니다.</p><br><br>";								
							$("#qanda").html(values);	
						
						$("#commentreport").html(values);
					}
					},
					error : function(a, b, c) {
						console.log("error: " + a + ", " + b + ", " + c)
					}
				});

	}
</script>
</script>
<!-- 회원탈퇴 -->
<script type="text/javascript">
function signOut(){
if(confirm("정말로 회원탈퇴를 하시겠습니까?")==true){
   location.href="/classKing/signout?userid=<%=checkUser.getMemberId()%>";
   }   
}
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
				<%
					if (loginUser != null && loginUser.getMemberId().equals("admin")) {
				%>
				<%@ include file="../etc/loginAdmin.jsp"%>
				<%
					} else if (loginUser != null) {
				%>
				<%@ include file="../etc/loginMember.jsp"%>
				<%
					} else {
				%>
				<%@ include file="../etc/login.jsp"%>
				<%
					}
				%>
				<!-- 로그인박스 끝 -->

				<!-- 알람박스 시작 -->
				<% if (loginUser != null) { %>
					<%@ include file="../../views/etc/alarm.jsp"%>
				<% } %>
				<!-- 알람박스 끝 -->
			</div>
			<div id="left_box">

				<div id="area_box">
					<div>
						<div class="title">설정</div>
					</div>

					<div id="mypage" style="clear: left;">
						<!--tab 목록 -->
						<ul class="nav nav-tabs" role="tablist">
							<li class="nav-item"><a class="nav-link active"
								onclick="MyClass();" href="#class" role="tab" data-toggle="tab">My
									Class</a></li>
							<li class="nav-item"><a class="nav-link" href="#info"
								onclick="MyInfo();" role="tab" data-toggle="tab">My Info</a></li>
							<li class="nav-item"><a class="nav-link" href="#board"
								onclick="MyBoard();" role="tab" data-toggle="tab">My Board</a></li>
							<li class="nav-item"><a class="nav-link" href="#comment"
								onclick="MyComment();" role="tab" data-toggle="tab">My
									Comment</a></li>
							<li class="nav-item"><a class="nav-link" href="#qanda"
								onclick="MyQna();" role="tab" data-toggle="tab">My Q&A</a></li>
							<li class="nav-item"><a class="nav-link" href="#report"
								onclick="boardreport();" role="tab" data-toggle="tab">My
									Report</a></li>
						</ul>

						<!-- Tab panes -->
						<div class="tab-content" style="overflow: hidden;">

							<div role="tabpanel" class="tab-pane active" id="class">
								<!-- my class tab -->

							</div>
							<!--ㅡmt class tab 끝  -->

							 <div role="tabpanel" class="tab-pane" id="info"> <!--info tab  -->

                        <form id="mainform" action="/classKing/mupdate" method="post" enctype="multipart/form-data"
                        onsubmit="return submition();">
                        <input type="hidden" name="originimg" value="<%=checkUser.getMemberOriginalImg()%>">
                        
                        <input type="hidden" name="renameimg" value="<%=checkUser.getMemberRenameImg() %>">
                           <!-- 사진을 추가하는 영역 -->
                           <div class="image-group">
                              <img src="/classKing/upload/member_upload/<%= checkUser.getMemberRenameImg() %>" id="profile" style="text-align: center" alt="이미지를 선택해주세요"></img><br>
                        <input type="hidden" name="userid" value="<%= checkUser.getMemberId() %>">
                        <input type="file" id="file-1" accept ='image/gif,image/jpeg,image/pnp'
                           class="inputfile inputfile-1" value="<%= checkUser.getMemberId() %>"
                           data-multiple-caption="{count} files selected" name="upfile"
                           onchange="readURL(this);" style="width: 1px; height: 1px;" /> <label
                           for="file-1"><svg xmlns="http://www.w3.org/2000/svg"
                              width="20" height="17" viewBox="0 0 20 17">
                              <path
                                 d="M10 0l-5.2 4.9h3.3v5.1h3.8v-5.1h3.3l-5.2-4.9zm9.3 11.5l-3.2-2.1h-2l3.4 2.6h-3.5c-.1 0-.2.1-.2.1l-.8 2.3h-6l-.8-2.2c-.1-.1-.1-.2-.2-.2h-3.6l3.4-2.6h-2l-3.2 2.1c-.4.3-.7 1-.6 1.5l.6 3.1c.1.5.7.9 1.2.9h16.3c.6 0 1.1-.4 1.3-.9l.6-3.1c.1-.5-.2-1.2-.7-1.5z" /></svg>
                           <span>프로필 사진&hellip;</span></label>
                           </div>
                           <!-- 사진을 추가하는 영역 -->

                           <br>
                           <!--이름/주민번호 부분을 같이 묶어놓은 div-->
                           <div class="nameAndBirthday">
                              <div class="form-group">
                                 <label for="username">이름</label> <input type="text"
                                    class="form-control" id="userName" name="userName" value="<%= checkUser.getMemberName() %>"
                                    placeholder="이름을 입력해 주세요" readonly>
                              </div>
                              <div class="form-group">
                                 <label for="username"></label>생년월일<input type="text"
                                    class="form-control" id="userBirth" name="userBirth"
                                    placeholder="주민번호 앞자리 6자리를 적어주세요" value="<%= checkUser.getMemberBirthday() %>" readonly>
                              </div>
                           </div>
                           <!--이름/주민번호 부분을 같이 묶어놓은 div끝-->

                           <!-- 이메일 주소 -->
                           <div class="form-group">
                              <label for="InputEmail">Email</label> <input type="email"
                                 class="form-control" id="userEmail" name="userEmail" value="<%= checkUser.getMemberEmail() %>"
                                 placeholder="이메일 주소">
                           </div>
                           <!-- 이메일 주소 -->


                           <!-- 비밀번호 수정 부분 -->
                           <!-- 비밀번호 1-->
                           <div class="form-group">
                              <label for="InputPassword1">비밀번호</label>
                              <!--passwordInput-->
                              <input type="password" class="form-control" id="inputPwd1"   name="userpwd" placeholder="비밀번호 수정" onclick="pwdCheck();" required>
                           </div>
                           <!-- 비밀번호 1-->

                           <!-- 비밀번호 재확인-->
                           <div class="form-group">
                              <label for="InputPassword2">비밀번호 확인</label> <input
                                 type="password" class="form-control" id="inputPwd2"
                                 name="inputPwd2" placeholder="비밀번호 확인" onchange="pwdVary();" required>
                              <label class="help-block" id="pwdSame"></label>
                           </div>
                           <!-- 비밀번호 재확인-->
                           

                           <!-- 비밀번호 찾기 질문 저장하는 부분 -->
                           <div class="form-group">
                              <label for="InputPassword2">비밀번호 분실 시 질문</label> <br> <select
                                 id="optionQ" name="optionQ" class="form-control">
                                 <!--차후에 옵션을 추가할 것!!-->
                                 <option><%= checkUser.getQuestion() %></option>
                              </select>
                              <!-- 비밀번호 찾기 질문 저장하는 부분 -->

                              <!-- 비밀번호 찾기 답적는 부분-->
                              <input type="text" value="<%= checkUser.getAnswer() %>" class="form-control" id="userA" name="userA" placeholder="질문에 대한 답" style="margin-top:10px;">
                           </div>

                           <div class="form-group text-center">
                              <button type="submit" class="btn btn-info">저장</button>&nbsp;&nbsp;
                         <button type="button" class="btn btn-danger" onclick="signOut();">회원탈퇴</button>
                           </div>
                           <!-- 비밀번호 찾기 답적는 부분-->
                        </form>
                     </div> <!--ㅡmyinfo tab 끝  -->

							<!--ㅡmyinfo tab 끝  -->

							<div role="tabpanel" class="tab-pane" id="board">
								<!--my post tab 시작   -->


							</div>
							<!--my post tab 끝  -->

							<div role="tabpanel" class="tab-pane" id="comment">
								<!--my comment tab 시작  -->
							</div>
							<!-- my comment tab 끝 -->
							<div role="tabpanel" class="tab-pane" id="qanda">
								<!--Q & A TAB 시작 -->

							</div>
							<!-- QnA TAB 끝 -->
							<div role="tabpanel" class="tab-pane" id="report">
								<ul class="nav nav-tabs" role="tablist">
									<li class="nav-item"><a class="nav-link active"
										href="#boardreport" onclick="boardreport();" role="tablist"
										data-toggle="tab">board</a></li>
									<li class="nav-item"><a class="nav-link"
										href="#commentreport" onclick="commentreport();"
										role="tablist" data-toggle="tab">comment</a></li>
								</ul>
								<div class="tab-content">
									<div role="tabpanel" class="tab-pane active" id="boardreport">
									</div>
									<div role="tabpanel" class="tab-pane" id="commentreport">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<%@ include file="../etc/footer.jsp"%>
</body>
</html>