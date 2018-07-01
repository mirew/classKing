<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>header</title>
<link href="/classKing/css/header.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
		<div id="top">
			<a href="/classKing/main.jsp"><img src=/classKing/images/logo01.png></a>			
			<div class="top_right">
				<form method="post" action="/classKing/allsrch">
					<div class="box_search">
						<input type="text" name="keyword" class="tf_search" placeholder="클래스 검색" required="required"/>
						<input type="submit" class="btn_search ico_search" />					
					</div>
				</form>
			</div>
		</div>
</header>
</body>
</html>