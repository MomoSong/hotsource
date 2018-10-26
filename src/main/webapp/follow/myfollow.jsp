<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="../header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
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
<br>
 	<div class="tbframe" >
			<input type="text" id="flwr" name="flwr" style="border: 0; width: auto; text-align:right;" value=${sessionScope.s_id } readonly>님의 관심 개발자 목록<br>
			<table>
			<c:forEach var="foll" items="${list}">
			<tr>
			<td>
				<input type="text" id="foll" name="foll" value="${foll.flw }" style="border: 0; cursor: pointer;"
							  onclick="location.href='../follow/followpjt.do?flw=${foll.flw}'" readonly>
			</td>
			</tr>
			</c:forEach>
			</table>
			</div>
</html>