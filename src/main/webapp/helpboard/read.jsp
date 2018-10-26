<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../header.jsp" %>

<!-- 본문 시작 read.jsp (view안에 페이지 작성할 것)-->
<div class="middle-bar">
	<a href="<%=request.getContextPath() %>/helpboard/list.do">문의 게시판</a>
</div>
<br>
<div class="tbframe">
<form name='frm' method='POST' enctype="multipart/form-data">
<table class="tb1" border="0">
	<tr>
		<td colspan='6' style="background-color: #ff5050; color: white; text-align: center">문의 게시글</td>
	<tr>
	<tr>
		<th>제목</th>
		<td colspan='6'>${dto.title }</td>
	</tr>

	<tr height="200">
		<th>내용</th>
		<td colspan='6' height="100" style="white-space : pre-line;">
		${dto.content }
		<br>
		<c:if test="${dto.filesize>0 }">
		<img src="./help_storage/${dto.filename }" width="100%">
		</c:if>
	  	</td>
	</tr>

	<tr>
		<th>작성자</th>
		<td>${dto.id }</td>
		<th width="10%" align="right">조회수</th>
		<td width="5%" align="right" style="text-align:center;">${dto.readcnt }</td>
	</tr>
</table>

<hr>
<div align="center">
    <input type='button' value='답변쓰기' onclick="location.href='./answer.do?helpno=${dto.helpno}'">
    <input type='button' value='목록' onclick="location.href='./list.do'">
    <c:if test="${dto.id.equals(sessionScope.s_id) || sessionScope.s_rating=='M'}">}">
    <input type='button' value='수정' onclick="location.href='./update.do?helpno=${dto.helpno}'">
    <input type='button' value='삭제' onclick="location.href='./delete.do?helpno=${dto.helpno}'">
    </c:if>
</div>
</form>
</div>


<!-- 본문 끝 -->			
<%@ include file="../footer.jsp" %>