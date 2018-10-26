<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../header.jsp" %>

<!-- 본문 시작 template.jsp (view안에 페이지 작성할 것)-->
<div class="middle-bar">
	<a href="<%=request.getContextPath() %>/faq/list.do">자주 묻는 질문 (FAQ)</a>
</div>
<br>
<div class="tbframe">
<form method="post" action="./delete.do">
	<input type="hidden" name="noticeno" value="${dto.noticeno }">
	<div align="center">
		<p>게시글을 삭제하시겠습니까?</p>
		<p>※ 관련 이미지도 삭제됩니다</p>
	</div>
	<div align="center">
		<input type="submit" value="삭제">
		<input type="button" value="목록" onclick="location.href='./list.do?noticeno=${dto.noticeno}'">
	</div>
</form>
</div>
<!-- 본문 끝 -->			
<%@ include file="../footer.jsp" %>