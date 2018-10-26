<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="../header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta chars
et="EUC-KR">
<title>Insert title here</title>
  <div class="pj-bar" >
	<a href="<%=request.getContextPath()%>/index.do">HOT source</a> 
	<a href="<%=request.getContextPath()%>/member/myinfo.do">나의 가입정보</a> 
	<a href="<%=request.getContextPath()%>/mylist/mylist.do">나의 프로젝트</a> 
	<a class="active">관심 개발자</a> 
	<a href="<%=request.getContextPath()%>/member/myinfoUpdate.do">내 정보 수정</a> 
	<a href="<%=request.getContextPath()%>/member/withdraw.do">회원탈퇴</a>
  </div>

</head>
<body>
		<form name='followInsert' method='POST' action='./follow.do'>
			<input type="text" id="flwr" name="flwr" style="border: 0; width: auto; text-align:right;" value=${sessionScope.s_id } readonly>님, <br>
			<input type='text' id='flw' name='flw' style="border: 0; width: auto; text-align:right;"value="${flw }" readonly>님을 관심 개발자로 추가하시겠습니까?<br><br> 
			<center>
			<input type="submit" value="예"/>
			<input type="button" value="아니요"/>
			</center>
		</form>
</body>
</html>