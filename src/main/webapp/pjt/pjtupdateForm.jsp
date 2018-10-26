<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.sql.Timestamp"%>
<%@ include file="../header.jsp"%>

<!-- 본문 시작 template.jsp (view안에 페이지 작성할 것)-->
<div class="pj-bar">
	<a href="<%=request.getContextPath() %>/commit/list.do">COMMIT</a>
	<a href="<%=request.getContextPath() %>/release/list.do">RELEASE</a>
	<a href="<%=request.getContextPath() %>/issue/list.do">ISSUE</a>
	<a onclick="window.open('<%=request.getContextPath()%>/chat/chat.do')">CHATTING</a>
</div>
<br>
<div class="tbframe">
	<form name='frm' method='POST' action='./update.do'
		enctype="multipart/form-data">
		<input type='hidden' name='prono' value='${dto.prono }' />
		<table class="tb1" border="0" width=100%
			style="font-size: 20px; border-spacing: 10px; border-collapse: separate;">
			<tr>
				<td colspan='3'
					style="background-color: #ff5050; color: white; text-align: center">PJT
					게시글 작성</td>
			<tr>
			<tr>
				<th>제목</th>
				<td colspan='2'><input type='text' name='Ptitle' size='95'
					style="border: 1px solid #dcdcdc;" value="${dto.ptitle }"></td>
			</tr>

			<tr height="200">
				<th>내용</th>
				<td height="100" colspan='2'><textarea name="Pexplain"
						rows="10" cols="97" style="border: 1px solid #dcdcdc;"
						value="${dto.pexplain }"></textarea></td>
			</tr>

			<tr>
				<th>작성자</th>
				<td colspan='2'><input type='text' name='id' size='10'
					style="border: 1px solid #dcdcdc;" value="${dto.id }"></td>
			</tr>
			<tr>
				<th>버전정보</th>
				<td colspan='2'><input type='text' name='verInfo' size='10'
					style="border: 1px solid #dcdcdc;" value="${dto.pversion }"></td>
			</tr>

			<tr>
				<th>파일 업로드</th>
				<td><input type='file' name='comfile' size='50'></td>
			</tr>
		</table>
		<br>
		<div align="center">
			<input type='submit' value='등록'> <input type='button'
				value='목록' onclick="location.href='./pjtlist.do'">
		</div>
	</form>
</div>

<!-- 본문 끝 -->
<%@ include file="../../footer.jsp"%>