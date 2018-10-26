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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- 트리 -->
<script src="${ pageContext.request.contextPath }/resources/jquery/jquery.js" type="text/javascript"></script>
<script src="${ pageContext.request.contextPath }/resources/jquery/jquery-ui.custom.js" type="text/javascript"></script>
<script src="${ pageContext.request.contextPath }/resources/jquery/jquery.cookie.js" type="text/javascript"></script>
<link href="${ pageContext.request.contextPath }/resources/src/skin/ui.dynatree.css" rel="stylesheet" type="text/css" id="skinSheet">
<script src="${ pageContext.request.contextPath }/resources/src/jquery.dynatree.js" type="text/javascript"></script>
<!-- member에서 필요한 경로 -->
<script src="${ pageContext.request.contextPath }/resources/js/myscript.js"></script>
<script src="${ pageContext.request.contextPath }/resources/js/npm.js"></script>
<script src="${ pageContext.request.contextPath }/resources/js/jquery-3.3.1.min.js"></script>

<script src="${ pageContext.request.contextPath }/resources/js/jquery.js"></script>
<script src="${ pageContext.request.contextPath }/resources/js/jquery.cookie.js"></script>
<script type="text/javascript"
	src="${ pageContext.request.contextPath }/resources/ckeditor/ckeditor.js"></script>
<style>
.fakeimg {
	height: 200px;
	background: #ffffff;
	border: 1px solid gray;
}

.dropdown {
	position: relative;
	display: inline-block;
	z-index: 5;
}

.dropdown-content {
	display: none;
	position: absolute;
	background-color: #f9f9f9;
	min-width: 160px;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	padding: 12px 16px;
}

.dropdown-content>a:link {
	text-decoration: none;
	color: black;
}

.dropdown-content>a:visited {
	text-decoration: none;
	color: black;
}

.dropdown-content>a:active {
	text-decoration: none;
	color: black;
}

.dropdown-content>a:hover {
	text-decoration: none;
	color: black;
}

.dropdown:hover .dropdown-content {
	display: block;
}

.pj-bar {
	width: 100%;
	background-color: #3c3c3c;
	overflow: auto;
	border-radius: 50px;
}

.pj-bar a {
	float: left;
	width: 25%;
	text-align: center;
	padding: 10px 0;
	transition: all 0.3s ease;
	color: white;
	font-size: 15px;
	margin: 0 auto;
}

.pj-bar a:hover {
	background-color: #ff5050;
	text-decoration: none;
}

.board-bar {
	width: 100%;
	background-color: #3c3c3c;
	overflow: auto;
	border-radius: 50px;
}

.board-bar a {
	float: left;
	width: 50%;
	text-align: center;
	padding: 10px 0;
	transition: all 0.3s ease;
	color: white;
	font-size: 15px;
	margin: 0 auto;
}

.board-bar a:hover {
	background-color: #ff5050;
	text-decoration: none;
}

.middle-bar {
	width: 100%;
	background-color: #3c3c3c;
	overflow: auto;
	border-radius: 50px;
}

.middle-bar a {
	float: left;
	width: 100%;
	text-align: center;
	padding: 10px 0;
	transition: all 0.3s ease;
	color: white;
	font-size: 15px;
	margin: 0 auto;
}

.middle-bar a:hover {
	background-color: #ff5050;
	text-decoration: none;
}

.active {
	background-color: #FF5050;
}

.tbframe {
	border: 3px solid #3c3c3c;
	border-radius: 10px;
	padding: 10px 10px; 
}

.tb1 th {
	font-size: 18px;
	text-align: center;
	background-color: #c8c8c8;
	color: #3c3c3c;
	width: 150px;
}

.tb1 {
	width: 100%;
	max-width: 100%;
	height: auto;
	font-size: 20px;
	border-spacing: 10px;
	border-collapse: separate;
}

.tb1-td {
	max-width: 100%
}

.tb2 td {
	padding: 11px;
	border-spacing: 10px;
}

.tb2 th {
	font-size: 18px;
	text-align: center;
	background-color: #c8c8c8;
	color: #3c3c3c;
	width: 150px;
}

.tb3 td {
	padding: 11px;
	border-spacing: 10px;
	border-bottom:dashed 1px #c8c8c8;
}

.tb3 th {
	font-size: 18px;
	text-align: center;
	background-color: #ff5050;
	color: white;
	width: 150px;
	border-radius: 50px;
	
}

.rcmbtn {
	border: 2px solid #c8c8c8;
	background-color: #FF5050;
	margin: 0 auto;
	width: 13%;
	border-radius: 5px;
	color: black;
}

.rcmbtn:hover {
	background-color: #ff3232;
	font-weight: bold;
	transition: all 0.2s ease;
	cursor: pointer;
}

.replyTB {
	width: 100%;
	border-spacing: 10px;
	border-collapse: separate;
}

.replyTB th {
	width: 10%;
	font-weight: bold;
	font-size: 15px;
	text-align: center;
	background-color: #aafa82;
	border-radius: 10px;
}

.comment {
	position: relative;
	top: -8%;
	left: 1%;
	width: 98%;
	height: 82%;
	font-size: 18px;
	font-weight: bold;
	color: #3c3c3c;
	z-index: 2;
	text-align: center;
	overflow: auto;
	-ms-overflow-style: none;
}

::-webkit-scrollbar {
	display: none;
}

.memmanage {
	width: 100%;
	border : 2px solid black;
	border-spacing: 10px;
	border-collapse: separate;
	}
.memmanage th {
	text-align : center;
	background-color : #f0b6b6;
	}

.memmanage td {
	text-align : center;
	}

//---------------------------------------------------------------------

/* Full-width input fields */
input[type=text], input[type=password] {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	box-sizing: border-box;
}

/* Set a style for all buttons */
button {
	background-color: black;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width: 100%;
}

button:hover {
	opacity: 0.8;
}

/* Extra styles for the join button */
.joinbtn {
	width: auto;
	padding: 10px 18px;
	background-color: black;
}

/* Center the image and position the close button */
.imgcontainer {
	text-align: center;
	margin: 24px 0 12px 0;
	position: relative;
}

img.avatar {
	width: 40%;
	border-radius: 50%;
}

.container {
	padding: 16px;
}

span.psw {
	float: right;
	padding-top: 16px;
}

/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
	padding-top: 60px;
}

/* Modal Content/Box *
.modal-content {
    background-color: white;
    margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
border


:

 

1
px

 

solid

 

#888


;
width


:

 

80%; /* Could be more or less, depending on screen size */
}

/* The Close Button (x) */
.close {
	position: absolute;
	right: 25px;
	top: 0;
	color: #000;
	font-size: 35px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: red;
	cursor: pointer;
}

/* Add Zoom Animation */
.animate {
	-webkit-animation: animatezoom 0.6s;
	animation: animatezoom 0.6s
}

@
-webkit-keyframes animatezoom {
	from {-webkit-transform: scale(0)
}

to {
	-webkit-transform: scale(1)
}

}
@
keyframes animatezoom {
	from {transform: scale(0)
}

to {
	transform: scale(1)
}

}

/* Change styles for span and cancel button on extra small screens */
@media screen and (max-width: 300px) {
	span.psw {
		display: block;
		float: none;
	}
	.joinbtn {
		width: 100%;
	}
}

</style>
</head>
<body>

	<div
		style="margin-top: 0; margin-bottom: 0; background-color: #dcdcdc;"
		align="center">
		<img src="../resources/images/mainlogo.png">
	</div>

	<nav class="navbar navbar-inverse" style="z-index: 1;">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand"
					href="<%=request.getContextPath()%>/index.do">HOT Source</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">게시판 <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="<%=request.getContextPath()%>/board/list.do">자유게시판</a></li>
							<li><a href="<%=request.getContextPath()%>/qboard/list.do">질문게시판</a></li>
						</ul></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">프로젝트 <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="<%=request.getContextPath()%>/pjt/pjtlist.do">프로젝트
									게시판</a></li>
							<%-- <li><a href="<%=request.getContextPath()%>/commit/list.do">이슈/커밋
									게시판</a></li> --%>
						</ul></li>
<!-------------------- <li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">마이페이지 <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">내 정보 수정</a></li>
							<li><a href="#">관심 개발자</a></li>
							<li><a href="#">나의 프로젝트</a></li>
						</ul></li> --------------------------------------------------------------------------------------->
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">고객센터 <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="<%=request.getContextPath()%>/faq/list.do">자주하는
									질문(FAQ)</a></li>
							<li><a href="<%=request.getContextPath()%>/notice/list.do">공지
									게시판</a></li>
							<li><a
								href="<%=request.getContextPath()%>/helpboard/list.do">문의
									게시판</a></li>
						</ul></li>

					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Chat <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a
								onclick="window.open('<%=request.getContextPath()%>/chat/chat.do')">Project
									Chat</a></li>
						</ul></li>

				</ul>
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${sessionScope.s_rating=='M' }">
					<li><a href="../master/manage.do"><span class="glyphicon glyphicon-user"></span>
							관리자</a></li>
					</c:if>
					<c:if test="${empty sessionScope.s_id  }">
						<li><a href="../member/login.do">
							<span class="glyphicon glyphicon-log-in"></span> 로그인</a></li>
						<li><a href="../member/join.do">
							<span class="glyphicon glyphicon-log-in"></span> 회원가입</a></li>
					</c:if>
					<c:if test="${sessionScope.s_id!=null  }">
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown" href="#">${sessionScope.s_id }님 <span
								class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="../member/myinfo.do">마이페이지</a></li>
								<li><a href="../member/logout.do">로그아웃</a></li>
							</ul>	
						</li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>

	<!-- //----------------------------------------------------------------------------------------------------------------------------------------- -->

	<!-- <c:if test="${empty sessionScope.memid }">
		<div id="id01" class="modal" style="z-index: 2;"> -->

	<%
		//사용자 pc에 저장된 쿠키값 가져오기 
			//로그인 하기전
			Cookie[] cookies = request.getCookies();
			String c_id = "";
			if (cookies != null) { //쿠키가 존재한다면
				for (int idx = 0; idx < cookies.length; idx++) {
					Cookie item = cookies[idx]; //쿠키1개 가져오기
					if (item.getName().equals("c_id") == true) { //쿠키변수 c_id
						c_id = item.getValue(); //쿠키값 가져오기
					} //getName if end
				} //for end
			} //if end
	%>
	<!-- <form class="modal-content animate" action="../member/login.do"
				method="post">
				<div style="z-index: 3;" class="imgcontainer">
					<span
						onclick="document.getElementById('id01').style.display='none'"
						class="close" title="Close Modal">&times;</span> <img
						src="../images/mainlogo.png" alt="Avatar" class="avatar">
				</div>

				<div class="container">
					<label for="uname"><b>Your ID</b></label> <input type="text"
						placeholder="Enter The ID" name="id" value="<%=c_id%>" required>
					<br> <label for="psw"><b>Password</b></label> <input
						type="password" placeholder="Enter Password" name="passwd"
						required>

					<button type="submit"
						onclick="location.href='javascript:logincheck()'">Login</button>
					<label> <input type="checkbox" checked="checked"
						name="c_id" value="SAVE"
				<%-- <%if (!c_id.isEmpty()) {
					out.print("checked");
				}%> /> Remember me		--%>
					</label>
				</div>

				<div class="container" style="background-color: #f1f1f1">
					<button type="button"
						onclick="document.getElementById('id01').style.display='none';location.href='<%=request.getContextPath()%>/member/joinform.do'"
						class="joinbtn">Join Member</button>
					<span class="psw">Forgot <a href="#">password?</a></span>
				</div>
			</form>

		</div>
	</c:if> -->


	<%
		//로그인 성공했을때
	%>

	<%-- <c:if test="${sessionScope.memid !=null}">
		<table width="500" cellpadding="0" cellspaceing="0" align="center"
			border="1">
			<tr>
				<td rowspan="3" bgcolor="${value_c }" align="left">${sessionScope.memid }님이
					방문하셨습니다.
					<form method="post" action="./logout.do">
						<input type="submit" value="로그아웃">
					</form>

					<form method="post" action="./modifyForm.do">
						<input type='hidden' name='id' value="${sessionScope.memid }">
						<input type="submit" value="회원정보변경">
					</form>

					<form method="post" action="./withdrawForm.do">
						<input type="submit" value="회원탈퇴">
					</form>
				</td>
			</tr>
		</table>
	</c:if> --%>



	<div class="container" style="margin-top: 30px; margin-bottom: 30px; vertical-align: middle; width:90%;">
	