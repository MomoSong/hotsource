<%@ page contentType="text/html; charset=UTF-8" buffer="128kb"%>
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

<!-- member에서 필요한 경로 -->
<script
	src="${ pageContext.request.contextPath }/resources/js/myscript.js"></script>
<script src="${ pageContext.request.contextPath }/resources/js/npm.js"></script>
<script
	src="${ pageContext.request.contextPath }/resources/js/jquery-3.3.1.min.js"></script>

<script
	src="${ pageContext.request.contextPath }/resources/js/jquery.js"></script>
<script
	src="${ pageContext.request.contextPath }/resources/js/jquery.cookie.js"></script>
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

.bestTB {
	text-align: center;
	border-spacing: 10px;
	border-collapse: separate;
	font-size: 20px;
	border-bottom: 1px solid gray;
}

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

@
keyframes animatezoom {
	from {transform: scale(0)
}

to {
	transform: scale(1)
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
		<img src="resources/images/mainlogo.png">
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
					<!-- <li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">마이페이지 <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="./member/myinfo.do">내 정보 수정</a></li>
							<li><a href="#">관심 개발자</a></li>
							<li><a href="#">나의 프로젝트</a></li>
						</ul></li> -->
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
						<li>
							<a href="./master/manage.do">
								<span class="glyphicon glyphicon-user"></span> 관리자
							</a>
						</li>
					</c:if>
					<c:if test="${empty sessionScope.s_id  }">
						<li><a href="./member/login.do">
							<span class="glyphicon glyphicon-log-in"></span> 로그인</a></li>
						<li><a href="./member/join.do">
							<span class="glyphicon glyphicon-log-in"></span> 회원가입</a></li>
					</c:if>
					<c:if test="${sessionScope.s_id!=null  }">
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown" href="#">${sessionScope.s_id }님 <span
								class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="./member/myinfo.do">마이페이지</a></li>
								<li><a href="./member/logout.do">로그아웃</a></li>
							</ul>	
						</li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>
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
	<%
		//로그인 성공했을때
	%>
	<div class="container"
		style="margin-top: 20px; margin-bottom: 20px; width: 100%;">
		<div class="row" style="vertical-align: middle;">
			<div class="col-sm-4" align="center">
				<p style="font-size: 25px; font-weight: bold;">
					<img src="./resources/images/best_icon.png" width="10%">
					인기게시글
				</p>
				<div class="fakeimg" style="border: 0">
					<table class="bestTB" align="center">
						<tr>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>조회수</th>
							<th>추천수</th>
						</tr>
						<c:forEach var="dtobest" items="${best }">
							<tr>
								<td style="text-align:left;"><a href="./board/read.do?boardno=${dtobest.boardno }">${dtobest.title }</a></td>
								<td>${dtobest.id }</td>
								<td>${dtobest.date.substring(0,10) }</td>
								<td>${dtobest.readcnt }</td>
								<td>${dtobest.good }</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div class="col-sm-4" align="center">
				<p style="font-size: 25px; font-weight: bold;">
					<img src="./resources/images/bestproject_icon.png" width="10%">
					인기프로젝트
				</p>
				<div class="fakeimg" style="border: 0">
					<table class="bestTB" align="center"
						style="text-align: center; border-spacing: 10px; border-collapse: separate; font-size: 20px;">
						<tr>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>추천수</th>
						</tr>
						<c:forEach var="dtobestproject" items="${bestproject }">
							<tr>
								<td style="text-align:left;"><a
									href="./pjt/pjtlist.do?prono=${dtobestproject.prono }">${dtobestproject.ptitle }</a></td>
								<td>${dtobestproject.id }</td>
								<td>${dtobestproject.date }</td>
								<td>${dtobestproject.good }</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<br>
			</div>
			<p style="font-size: 25px; font-weight: bold" align="center">
				<img src="./resources/images/faq_icon.jpg" width="50px">자주하는질문
			</p>
			<div class="col-sm-4" align="center">
				<div class="fakeimg" style="border: 0">
					<table class="bestTB" align="center"
						style="text-align: left; border-spacing: 10px; border-collapse: separate; font-size: 20px;">
						<tr>
							<th>제목</th>
							<th>조회수</th>
						</tr>
						<c:forEach var="dtobestfaq" items="${bestfaq }">
							<tr>
								<td><a
									href="./faq/read.do?noticeno=${dtobestfaq.noticeno }">${dtobestfaq.title }</a></td>
								<td>${dtobestfaq.readcnt }</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
	<br><br>
	<!-- 본문 끝 -->
	</div>

	<div class="jumbotron text-center"
		style="margin-top: 30; margin-bottom: 0;">
		<p>CopyRight HOT Source 2018</p>
	</div>

</body>
</html>