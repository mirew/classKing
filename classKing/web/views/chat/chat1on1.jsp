<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
   <%@ page import="java.util.Arrays,member.model.vo.Member,java.util.ArrayList,chat.model.vo.Chat"%>
<%
   /*  String[] array = new String[] {"skc0924","mirew","kimminsun","aa","cc"};
    Arrays.sort(array);
    System.out.println(array[0] + ", " + array[1]); */
   Member loginUser = (Member) session.getAttribute("loginUser");
   String uId = (String)request.getAttribute("uId");
   ArrayList<Chat> list = (ArrayList<Chat>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>채팅창</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
#td_1 {
   width: 400px;
   height:420px;
   overflow-y:scroll;
   
} 
#messageWindow{
 width: 400px;
   height:420px;
}
 #td_2{
   overflow-y:scroll;
   width:200px;
   }
#ul_none{
   list-style:none;
   padding:10px;
}
   
</style>

</head>
<body>
<input type="hidden" value='<%=loginUser.getMemberId()%>' id='chat_id' />
   <!-- 챗팅 -->

   <div class="container">
      <h2>classKing 채팅</h2>
      <table class="table table-bordered">
         <thead>
            <tr>
               <th>채팅</th>
               <th>목록</th>
            </tr>
         </thead>
         <tbody>
            <tr>
               <td id="td_1"><!-- <div style="border:1px dashed gray;">
                     <ul class="chat" id="ul_none">
                     <br>
                        ng-repeat을 이용하여 chats 배열요소의 개수만큼 채팅글을 보여준다.
                        <li class="{{ $even && 'left' || 'right' }} clearfix"
                           ng-repeat="chat in chats">
                           $even은 ng-repeat안에서 사용할 수 있고 짝수일경우 true이다. 짝수이면 left이고 홀수이면 right인 표현식을 나타낸다. 본인글만 왼쪽으로 나타내는 건 별도의 isMine()등으로 ng-class를 이용하여 구현할 수도 있다.
                           <span class="chat-img pull-{{ $even && 'left' || 'right' }}">
                              <img src="http://placehold.it/50/55C1E7/fff" alt="사용자 이미지"
                              class="img-circle">
                        </span>
                           <div class="chat-body clearfix">
                              <div class="header text-{{ $even && 'left' || 'right' }}">
                                 <strong>{{ 사용자 아이디 }}</strong>
                              </div>
                              <p>{{ chat.msg }}</p>
                           </div>
                           <p class='chat_content'>안녕하세요</p>
                        </li>
                     </ul>
                  </div> -->
                  <div id="messageWindow">
                   
                   </div>
                  </td>

               <td id="td_2" ><div class="realtime_srch _aside_news_tab">
                     <div class="hotkwd">
                        <ul class="api_realtime_tab _tab" id="ul_none">
                           
                        </ul>
                     </div>
                  </div></td>

            </tr>
            <tr>
               <td colspan="2"><div style="float:left;">
                     <input id="inputMessage" type="text" class="form-control input-sm"
                        ng-model="chatTxt" style="width:490px; float:left; " onkeyup="enterkey()">
                  </div>
                  <div>
                     <button class="btn btn-warning btn-sm" id="btn-chat" onclick="send();"
                        ng-click="sendMsg(chatTxt)" style="float:right">전송</button></div>
                  </td>
            </tr>
         </tbody>
      </table>
   </div>
</body>
<script type="text/javascript">
   window.onbeforeunload=exit;
    var textarea = document.getElementById("messageWindow");
    var webSocket = new WebSocket('ws://192.168.20.233:5500/classKing/broadcasting1on1');
    var inputMessage = document.getElementById('inputMessage');
    webSocket.onerror = function(event) {
        onError(event)
    };
    webSocket.onopen = function(event) {
        onOpen(event);
        join();
        <% if(list.size() > 0 ){%>
         <% for(Chat c : list){ %>
            <% if(c.getMemberId().equals(loginUser.getMemberId())){ %>
                $("#messageWindow").html($("#messageWindow").html()
                         + "<p style='text-align:right'><strong><%=loginUser.getMemberId()%></strong><p style='text-align:right'><%=c.getChatNote()%></p>");
            <% }else{ %>
               $("#messageWindow").html($("#messageWindow").html()
                       + "<p><strong><%=c.getMemberId()%></strong><p><%=c.getChatNote()%></p>");
            <% } %>
         <% } %>
      <% } %>
      $("#td_1").scrollTop($("#td_1")[0].scrollHeight);
    };
    webSocket.onmessage = function(event) {
        onMessage(event)
    };
    webSocket.onclose = function(event){
       exit();
    };
    function onMessage(event) {
       var myId = ["<%=loginUser.getMemberId()%>","<%=uId%>"];
      var message = event.data.split("|");
      var sender = message[0];
      var content = message[1];
      var memberId = message[2];
      var uId = null;
      if(sender == "" || sender == "!"){
         uId = [content,memberId];
      }else{
         uId = [sender,memberId];
      }
        myId.sort();
        uId.sort();
      if(sender == ""){
         if (myId[0] == uId[0] && myId[1] == uId[1]) {
             $("#ul_none").html($("#ul_none").html()+"<li id='" + content + "'>"+content+"</li>");
           }
      }else if(sender == "!"){
         if (myId[0] == uId[0] && myId[1] == uId[1]) {
             $("#"+content).remove();
           }
       }else{
          if (content == "") {
               
         } else {
             if (myId[0] == uId[0] && myId[1] == uId[1]) {
                 $("#messageWindow").html($("#messageWindow").html()
                            + "<p><strong>" + sender + "</strong></p><p>" + content + "</p>");
                 $("#td_1").scrollTop($("#td_1")[0].scrollHeight);
               }else{
                     
              }
          }
       }
   }
    
    function onOpen(event) {
        $("#messageWindow").html("<p class='chat_content'>채팅에 참여하였습니다.</p>");
    }
    function onError(event) {
        alert(event.data);
    }
    function join(){
       webSocket.send("|" + $("#chat_id").val() + "|<%=uId%>");
    }
    function exit(){
       webSocket.send("!|" + $("#chat_id").val() + "|<%=uId%>");
    }
    function send() {
        if (inputMessage.value == "") {
        } else {
            $("#messageWindow").html($("#messageWindow").html()
                + "<p style='text-align:right;'><strong><%=loginUser.getMemberId()%></strong></p><p style='text-align:right;'>" + inputMessage.value + "</p>");
            $("#td_1").scrollTop($("#td_1")[0].scrollHeight);
        }
        webSocket.send($("#chat_id").val() + "|" + inputMessage.value + "|<%=uId%>");
        inputMessage.value = "";
    }
    //     엔터키를 통해 send함
    function enterkey() {
        if (window.event.keyCode == 13) {
            send();
        }
    }
    //     채팅이 많아져 스크롤바가 넘어가더라도 자동적으로 스크롤바가 내려가게함
    window.setInterval(function() {
        var elem = document.getElementById('messageWindow');
        elem.scrollTop = elem.scrollHeight;
    }, 0);
</script>
</html>