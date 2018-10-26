<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../header.jsp" %>

<!-- 본문 시작 template.jsp (view안에 페이지 작성할 것)-->
*결과 페이지*
<c:set var="msg" value="${sessionScope.msg}" scope="session" />
	<c:choose>
		<c:when test="${msg!=null && msg=='0'}">
			<font size='6'>회원정보가 수정되었습니다.</font>
			<c:remove var="msg" scope="session"></c:remove>
		</c:when>
		<c:when test="${msg!=null && msg=='1'}">
			<font size='6'>회원가입을 축하드립니다.</font>
			<c:remove var="msg" scope="session"></c:remove>
		</c:when>
		<c:otherwise>
			<font size='6'>회원정보가 삭제되었습니다.</font>
		</c:otherwise>
	</c:choose>

	<br><br>
	<input type="button" value="메인으로" onclick="location.href='../index.jsp'"/>

<!-- 본문 끝 -->			
<%@ include file="../footer.jsp" %>