<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../header.jsp" %>

<!-- 본문 시작 template.jsp (view안에 페이지 작성할 것)-->
<div class="pj-bar">
	<a href="<%=request.getContextPath() %>/commit/list.do">COMMIT</a>
	<a href="<%=request.getContextPath() %>/release/list.do">RELEASE</a>
	<a class="active" href="<%=request.getContextPath() %>/issue/list.do">ISSUE</a>
	<a onclick="window.open('<%=request.getContextPath()%>/chat/chat.do')">CHATTING</a>
</div>
<br>
<div class="tbframe">
<form method="post" action="./delete.do">
	<input type="hidden" name="icno" value="${dto.icno }">
	<div align="center">
		<p>게시글을 삭제하시겠습니까?</p>
		<p>※ 관련 이미지도 삭제됩니다</p>
	</div>
	<div align="center">
		<input type="submit" value="삭제">
		<input type="button" value="목록" onclick="location.href='./list.do?icno=${dto.icno}'">
	</div>
</form>
</div>
<!-- 본문 끝 -->			
<%@ include file="../footer.jsp" %>