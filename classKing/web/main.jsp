<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member,classes.model.vo.Classes,java.util.*"%>
<%
   Member loginUser = (Member) session.getAttribute("loginUser");
int signedOut=0;
if(request.getParameter("signedOut")!=null){
   signedOut=Integer.parseInt(request.getParameter("signedOut"));
}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="css/reset.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"
   href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
   integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
   crossorigin="anonymous">
<link href="css/main.css" rel="stylesheet" type="text/css">
<script src="/classKing/js/jquery-3.3.1.min.js"></script>
<script defer
   src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>

<script type="text/javascript">
   var eventSource;

   function loginAlert(){
      alert("로그인 후 이용해주세요.");
   }
   $(function(){

      
       
      <% if(loginUser != null){%>
      $.ajax({
         url : "/classKing/mclist",
         data : {memberid : "<%= loginUser.getMemberId() %>"},
         type : "get",
         datatype: "json",
         success : function(data){
            var jsonStr = JSON.stringify(data);
            var json = JSON.parse(jsonStr);
            var values = "";
            var values2="";
            
            for(var i in json.list){
               values += "<li class='item'><div class='class_img_top' style='margin: none;'><a href='/classKing/views/class/classHome.jsp?classNo="
                     + json.list[i].no + "'><img style='width:100px;height:100px' src='/classKing/upload/class_upload/" + json.list[i].img +"'></a></div>"
                     + "<p>" + json.list[i].title +"</p></li>";
            }
            $("#ul_mc").append(values);
            values2 +="<button class='btn btn-secondary leftLst'><i class='fas fa-angle-left'></i></button><button class='btn btn-secondary rightLst'><i class='fas fa-angle-right'></i></button>"
            $(".MultiCarousel").append(values2);
                var itemsMainDiv = ('.MultiCarousel');
                var itemsDiv = ('.MultiCarousel-inner');
                var itemWidth = "";
                $('.leftLst, .rightLst').click(function () {
                    var condition = $(this).hasClass("leftLst");
                    if (condition)
                        click(0, this);
                    else
                        click(1, this)
                });

                ResCarouselSize();
                
                $(window).resize(function () {
                    ResCarouselSize();
                });

                //this function define the size of the items
                function ResCarouselSize() {
                    var incno = 0;
                    var dataItems = ("data-items");
                    var itemClass = ('.item');
                    var id = 0;
                    var btnParentSb = '';
                    var itemsSplit = '';
                    var sampwidth = $(itemsMainDiv).width();
                    var bodyWidth = $('body').width();
                    $(itemsDiv).each(function () {
                        id = id + 1;
                        var itemNumbers = $(this).find(itemClass).length;
                        btnParentSb = $(this).parent().attr(dataItems);
                        itemsSplit = btnParentSb.split(',');
                        $(this).parent().attr("id", "MultiCarousel" + id);


                        if (bodyWidth >= 1200) {
                            incno = itemsSplit[3];
                            itemWidth = sampwidth / incno;
                        }
                        else if (bodyWidth >= 992) {
                            incno = itemsSplit[2];
                            itemWidth = sampwidth / incno;
                        }
                        else if (bodyWidth >= 768) {
                            incno = itemsSplit[1];
                            itemWidth = sampwidth / incno;
                        }
                        else {
                            incno = itemsSplit[0];
                            itemWidth = sampwidth / incno;
                        }
                        $(this).css({ 'transform': 'translateX(0px)', 'width': itemWidth * itemNumbers });
                        $(this).find(itemClass).each(function () {
                            $(this).outerWidth(itemWidth);
                        });

                        $(".leftLst").addClass("over");
                        $(".rightLst").removeClass("over");

                    });
                }


                //this function used to move the items
                function ResCarousel(e, el, s) {
                    var leftBtn = ('.leftLst');
                    var rightBtn = ('.rightLst');
                    var translateXval = '';
                    var divStyle = $(el + ' ' + itemsDiv).css('transform');
                    var values = divStyle.match(/-?[\d\.]+/g);
                    var xds = Math.abs(values[4]);
                    if (e == 0) {
                        translateXval = parseInt(xds) - parseInt(itemWidth * s);
                        $(el + ' ' + rightBtn).removeClass("over");

                        if (translateXval <= itemWidth / 2) {
                            translateXval = 0;
                            $(el + ' ' + leftBtn).addClass("over");
                        }
                    }
                    else if (e == 1) {
                        var itemsCondition = $(el).find(itemsDiv).width() - $(el).width();
                        translateXval = parseInt(xds) + parseInt(itemWidth * s);
                        $(el + ' ' + leftBtn).removeClass("over");

                        if (translateXval >= itemsCondition - itemWidth / 2) {
                            translateXval = itemsCondition;
                            $(el + ' ' + rightBtn).addClass("over");
                        }
                    }
                    $(el + ' ' + itemsDiv).css('transform', 'translateX(' + -translateXval + 'px)');
                }

                //It is used to get some elements from btn
                function click(ell, ee) {
                    var Parent = "#" + $(ee).parent().attr("id");
                    var slide = $(Parent).attr("data-slide");
                    ResCarousel(ell, Parent, slide);
                }
                var listSize=$('.item').length;
                if(listSize<=4){
                   $('.leftLst').remove();
                   $('.rightLst').remove();
                }

            
         },error:function(a,b,c){
            console.log("error: " + a + ", " + b + ", " + c)
         }
      });
      <% } %>
      $.ajax({
         url : "/classKing/newnotice",
         type : "get",
         datatype: "json",
         success : function(data){
            var jsonStr = JSON.stringify(data);
            var json = JSON.parse(jsonStr);
            var values = "";
            for(var i in json.list){
               values += "<li><a href='/classKing/ndetail?no="+json.list[i].no+"&page=1'>" + json.list[i].title + "</a><span class='notice_date'>"
               + json.list[i].date + "</span></li>";
            }
            $("#notice_new").append(values);
         },error:function(a,b,c){
            console.log("error: " + a + ", " + b + ", " + c)
         }
      });
      $.ajax({
         url : "/classKing/rankclass",
         type : "get",
         datatype: "json",
         success : function(data){
            var jsonStr = JSON.stringify(data);
            var json = JSON.parse(jsonStr);
            var values = "";
            for(var i in json.list){
               values += "<div class='card'><div class='card-img-top'><a href='/classKing/views/class/classHome.jsp?classNo=" + json.list[i].no + "'>"
                     + "<img src='/classKing/upload/class_upload/" + json.list[i].img + "' alt='Card image cap' class='circle'></a>"
                     + "</div><div class='card-body'><h5 class='card-title'>" + json.list[i].title + "</h5>"
                     + "<p class='card-text'>" + json.list[i].subtitle + "</p></div></div>";
            }
            $(".card-group").append(values);
         },error:function(a,b,c){
            console.log("error: " + a + ", " + b + ", " + c)
         }
      });
   });
</script>

<script type="text/javascript">
$(document).ready(function(){
   if(<%=signedOut%>!=0){
      alert("회원탈퇴가 되었습니다.");
   }
});
</script>
<!-- javascript -->
<title>Class King</title>
</head>
<body>
<%@ include file="views/etc/alramAlert.jsp" %>
   <%@ include file="views/etc/header.jsp"%>
   <section>
      <div id="wrap">
         <div id="right_box">
            <!-- 로그인박스 시작 -->
            <% if (loginUser != null && loginUser.getMemberId().equals("admin")) { %>
               <%@ include file="views/etc/loginAdmin.jsp"%>
            <% } else if (loginUser != null) { %>
               <%@ include file="views/etc/loginMember.jsp" %>
            <% } else { %>
               <%@ include file="views/etc/login.jsp"%>
            <% } %>
            <!-- 로그인박스 끝 -->

            <!-- 알람박스 시작 -->
            <!-- <div id="alarm_box">알람!</div> -->
            <% if (loginUser != null) { %>
               <%@ include file="views/etc/alarm.jsp"%>
            <% } %>
            <!-- 알람박스 끝 -->
         </div>
         <div id="left_box">
            <!-- 공지사항 -->
            <img src="images/notice2.jpg">
            <div id="notice_box">
               <p>
                  공지사항<span class="btn_more"><a
                     href="/classKing/nlist?page=1">more <i
                        class="fas fa-angle-right" style="vertical-align: middle;"></i></a></span>
               </p>
               <ul id="notice_new">
                  
               </ul>
            </div>
            <!-- 추천클래스 -->
            <div id="best_class">
               <p>추천 클래스</p>
               <div class="card-group">
                  
               </div>
            </div>
            <!-- 마이클래스 -->
            <div id="my_class" class="slider">
               <p>마이 클래스</p>
               <div class="MultiCarousel" data-items="1,2,3,4" data-slide="1" id="MultiCarousel"  data-interval="1000">
               <ul class="MultiCarousel-inner" id="ul_mc">
                  <li class="item">
                     <div class="class_img_top" style="margin: none;">
                     <% if(loginUser != null){ %>
                        <a href="/classKing/views/class/classMake.jsp" class="new_class">
                           <i class="fas fa-plus"></i></a>
                     <% }else{ %>
                        <a href="javascript:loginAlert();" class="new_class">
                           <i class="fas fa-plus"></i></a>
                     <% } %>
                     </div>
                     <p>클래스 만들기</p>
                  </li>

               </ul>
               
                 </div>
            </div>
         </div>
         <!-- right box 끝 -->
      </div>
      <!-- wrap 끝 -->
   </section>
   <%@ include file="views/etc/footer.jsp"%>
</body>
</html>