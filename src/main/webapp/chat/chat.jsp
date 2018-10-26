<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page session="true"%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- 세션에  필요한 import -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>

<%@ page import="kr.co.hotsource.*"%>
<%@ page import="net.utility.*"%>

<!-- =============================================================== -->


<!DOCTYPE html>
<html lang="ko">
<head>
<title>Hot Source</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">	
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	

<!-- member에서 필요한 경로 -->
<script src="${ pageContext.request.contextPath }/resources/js/myscript.js"></script>
<script src="${ pageContext.request.contextPath }/resources/js/npm.js"></script>
<script src="${ pageContext.request.contextPath }/resources/js/jquery-3.3.1.min.js"></script>

<script src="${ pageContext.request.contextPath }/resources/js/jquery.js"></script>
<script src="${ pageContext.request.contextPath }/resources/js/jquery.cookie.js"></script>
<script type="text/javascript"
	src="${ pageContext.request.contextPath }/resources/ckeditor/ckeditor.js"></script>

<!-- 본문시작 bbsForm.jsp -->
<head>
<style>
strong {
  font-weight: bold;
}
.button {
  background-color: #E66B58;
  background-image: linear-gradient(to bottom, #E66B58, #E66B58);
  border: 1px solid #E66B58;
  box-shadow: inset 0 1px 0 white, inset 0 -1px 0 #E66B58, inset 0 0 0 1px #f2f2f2, 0 2px 4px rgba(0, 0, 0, 0.2);
  color: #E66B58;
  text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
  border-radius: 3px;
  cursor: pointer;
  display: inline-block;
  font-family: Verdana, sans-serif;
  font-size: 12px;
  font-weight: 400;
  line-height: 20px;
  padding: 9px 16px 9px;
  margin: 13px 0 0 10px;
  transition: all 20ms ease-out;
  vertical-align: top;
}
.button-blue {
  background-color: #E66B58;
  background-image: linear-gradient(to bottom, #E66B58, #E66B58);
  border: 1px solid #E66B58;
  box-shadow: inset 0 1px 0 #7cd4fc, inset 0 -1px 0 #E66B58, inset 0 0 0 1px #E66B58, 0 2px 4px rgba(0, 0, 0, 0.2);
  color: white;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}
.button-blue:hover, .button-blue:focus {
  background: #E66B58;
  border-color: #E66B58;
  box-shadow: inset 0 1px 0 #7cd4fc, inset 0 -1px 0 #E66B58, inset 0 0 0 1px #59b7e3;
}
.button-blue:active {
  background: #E66B58;
  box-shadow: inset 0 2px 3px rgba(0, 0, 0, 0.2);
}
.button-blue .fa {
  color: #E66B58;
  text-shadow: 0 1px 0 rgba(255, 255, 255, 0.3);
}

input[type=text] {   /* text입력 창  */
    width: 330px;
    padding: 12px 20px;
    margin: 8px 10px 10px ;
    box-sizing: border-box;
    border: 2px solid #E66B58;
    border-radius: 4px;
}

.box2 {  /*채팅창 */
	
	width: 400px;
	height: 650px;
	margin: 8px 0 0 8px;
	padding: 30px;
	/*color: black;	 글자색 */
	font-weight: bold;
	border: 11px solid #E66B58;		/* 태두리 */
	
	-moz-border-radius: 32px;
	-webkit-border-radius: 32px;
	border-radius: 10px;
	behavior: url(/data/201010/IJ12884818006718/border-radius.htc); /* For IE6 */
}

.box3 {   /* 참여자 목록  */
	width: 200px;
	height: 650px;
	margin: 8px 0 0 8px;
	padding: 30px;
	color: #E66B58;
	font-weight: bold;
	border: 11px solid #202121;
	
	-moz-border-radius: 32px;
	-webkit-border-radius: 32px;
	border-radius: 10px;
	behavior: url(/data/201010/IJ12884818006718/border-radius.htc); /* For IE6 */
}

.intro {
	fontcolor : blue;
}



</style>
</head>

<body>
    <!-- 로그인한 상태일 경우와 비로그인 상태일 경우의 chat_id설정 -->
    <c:if test="${(login.id ne '') and !(empty login.id)}">
        <input type="hidden" value='${login.id }' id='chat_id' />
    </c:if>
    <c:if test="${(login.id eq '') or (empty login.id)}">
        <input type="hidden" value='${sessionScope.s_id }'
            id='chat_id' />
    </c:if>
    <!--     채팅창 -->
    <div  id="_chatbox" >
     
        <fieldset >
            <textarea id="messageWindow" rows="20"  class="box2" readonly></textarea>
            <textarea id="chating" rows="10" class="box3" readonly></textarea>
            <br /> <input id="inputMessage" type="text" onkeyup="enterkey()" />
            <button type="submit" onclick="send()" class="button button-blue" > <i class="fa fa-globe"></i>메세지 보내기</button>
            <button onclick="onClose()" class="button button-blue" > <i class="fa fa-globe"></i>채팅방 나가기</button>
          
        </fieldset>
        
   

    </div>
    <!-- <img class="chat" src="./images/chat.png" /> -->
</body>
<!-- 말풍선아이콘 클릭시 채팅창 열고 닫기 
<script>
    $(".chat").on({
        "click" : function() {
            if ($(this).attr("src") == "./images/chat.png") {
                $(".chat").attr("src", "./images/chathide.png");
                $("#_chatbox").css("display", "block");
            } else if ($(this).attr("src") == "./images/chathide.png") {
                $(".chat").attr("src", "./images/chat.png");
                $("#_chatbox").css("display", "none");
            }
        }
    });
</script>
-->
<script type="text/javascript">

	
    var textarea = document.getElementById("messageWindow");
    var webSocket = new WebSocket('ws://172.16.4.75:9090/hotsource/broadcasting'); //brodcasting
    var inputMessage = document.getElementById('inputMessage');
    webSocket.onerror = function(event) {
        onError(event)
    };
    webSocket.onopen = function(event) {
        onOpen(event)
    };
    webSocket.onmessage = function(event) {
        onMessage(event)
    };
    webSocket.onclose=function(event){
    	onClose(event)
    };
    
    function onMessage(event) { //귓속말 이벤트
        var message = event.data.split("|"); // "|" 기호를 입력하면 데이터 뿌려주기
        var sender = message[0];
        var content = message[1];
        if (content == "") { //입력내용이 없는 경우
            
        } else {
            if (content.match("/")) { 
                if (content.match(("/" + $("#chat_id").val()))) {
                    var temp = content.replace("/" + $("#chat_id").val(), "(귓속말) :").split(":");
                    if (temp[1].trim() == "") {
                    } else {
                        $("#messageWindow").html($("#messageWindow").html() 
                            + sender + content.replace("/" + $("#chat_id").val(), "(귓속말) :") + "\n");
                    }
                }//귓속말 end
                
            } else {
                if (content.match("님이 입장하셨습니다")) {
                	$
                	$("#messageWindow").html($("#messageWindow").html()+  sender + content+ "\n");//"상대방"님이 입장하셨습니다
                	$("#chating").html($("#chating").html()+sender+"\n"); // 참여자 목록 아이디 추가
                
                
                }else if(content.match("님이 퇴장하셨습니다")){
                	var leaveid =$("#chating").val();
                	//alert(leaveid);
                	$("#messageWindow").html($("#messageWindow").html() + sender + content+ "\n");  //"상대방"님이 퇴장하셨습니다.
                	//alert(sender)	
                	leaveid = leaveid.replace(sender+"\n","");
                	$("#chating").html(leaveid);//replace를 하고 난 다음에 다시한번 찍어줘야한다.
                
                
                }else if(content!=null){
                	var idcheck = $("#chating").val();   //문자열에서 특정문자열 찾기 *frontend에서 적용되는 언어중에 찾기 script언어나 제이쿼리등
                	/* alert(idcheck);
                	alert(sender); */
                	if(idcheck.indexOf(sender)<0){
                		$("#chating").html($("#chating").html()+sender+"\n")	
                	}                	
                    $("#messageWindow").html($("#messageWindow").html()
                            + sender + " : " + "\n" + content + "\n");
                	
                }
            }
        }
    }
   
    
    function onOpen(event) { //서버에 접속할때 event
    	var intro = "프로젝트채팅에 참여하셨습니다!!"
        $("#messageWindow").html(intro+"\n");
       // $("#chating").html("채팅참여자 목록\n");
        webSocket.send($("#chat_id").val() + "|" +"님이 입장하셨습니다");
        $("#chating").html("채팅참여자 목록 \n"+$("#chat_id").val()+"\n" );
    }
    function onClose(event){
    	webSocket.send($("#chat_id").val() + "|" + "님이 퇴장하셨습니다");
    	window.close();
    }
    
    function onError(event) {
        alert(event.data);
    }
    function send() { 
        if (inputMessage.value == "") {
        } else {
        	
            $("#messageWindow").html($("#messageWindow").html()
                + "나 : "+"\n" + inputMessage.value + "\n");
        }
        webSocket.send($("#chat_id").val() + "|"+ inputMessage.value);
        inputMessage.value = "";
        
    }
    
   
 //-------firefox enter key 사용
 $("#inputMessage").keyup(function(e){ 
    var code = e.which; 
    var elem = document.getElementById('messageWindow');
    if(code==13)e.preventDefault();
    if(code==13){
        send();
        elem.scrollTop = elem.scrollHeight+20; //엔터칠때마다 스크롤 내려감
    } 
});
 
 /* 	function enterkey() {  //enter할때마다 send event 발생 하지만 firefox에서는 안됌
 if (window.event.keyCode == 13) {
 	textarea.value += connid.value + " :\n" + inputMessage.value + "\n";
		webSocket.send(connid.value + " :\n" + inputMessage.value);
		inputMessage.value = ""; 
 }
}
*/
 
    //     채팅이 많아져 스크롤바가 넘어가더라도 자동적으로 스크롤바가 내려가게함
/*      window.setInterval(function() {
        var elem = document.getElementById('messageWindow');
        elem.scrollTop = elem.scrollHeight+20;
    }, 0);   */
</script>
<!-- 본문끝 -->
