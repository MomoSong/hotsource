<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.sql.Timestamp" %>
<%@ include file="../header.jsp" %>

<!-- 본문 시작 template.jsp (view안에 페이지 작성할 것)-->
<div class="board-bar">
	<a class="active" href="<%=request.getContextPath() %>/board/list.do">자유게시판</a>
	<a href="<%=request.getContextPath() %>/qboard/list.do">질문게시판</a>
</div>
<br>
<div class="tbframe">
<form name='frm' method='POST' action='./create.do' enctype="multipart/form-data">
<table class="tb1" border="0">
	<tr>
		<td colspan='2' style="background-color: #ff5050; color: white; text-align: center">자유게시글 작성</td>
	<tr>
	<tr>
		<th>제목</th>
		<td><input type='text' name='title' style="border: 1px solid #dcdcdc; width:100%;"></td>
	</tr>
	
	<tr height="200">
		<th>내용</th>
		<td height="100"><textarea name="content" rows="10" style="border: 1px solid #dcdcdc; width:100%;"></textarea></td>
	</tr>

	<tr>
		<th>작성자</th>
		<td><input type='text' name='id' style="border: 0; width:15%;" value="${sessionScope.s_id }" readonly></td>
	</tr>

	<tr>
		<th>이미지 첨부</th>
		<td><input type='file' name='comfile' size='50'></td>
	</tr>
</table>
<hr>
<div align="center">
	<input type='submit' value='등록'>
    <input type='button' value='목록' onclick="location.href='./list.do'">
</div>
</form>
</div>

<!-- 본문 끝 -->			
<%@ include file="../footer.jsp" %>