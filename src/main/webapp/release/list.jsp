<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.sql.Timestamp"%>
<%@ include file="../../header.jsp"%>

<!-- 본문 시작 template.jsp (view안에 페이지 작성할 것)-->
<c:if test="${sessionScope.s_id == NULL }">
	<div align="center">
		<img src="../resources/images/hotsource.PNG" height="200px">
		<h2>로그인 후 이용가능 합니다.</h2>
	</div>
</c:if>
<c:if test="${sessionScope.s_id != NULL }">
<div class="pj-bar">
	<a href="<%=request.getContextPath() %>/commit/list.do?prono=${prono}">COMMIT</a>
	<a href="<%=request.getContextPath() %>/release/list.do?prono=${prono}">RELEASE</a>
	<a href="<%=request.getContextPath() %>/issue/list.do?prono=${prono}">ISSUE</a>
	<a href="#">CHATTING</a>
</div>
<br>
<div class="tbframe">
<c:if test="${count==0 }">
<c:set var="today" value="<%=Utility.getDate()%>"></c:set>
<table class="tb2" width=100% style="text-align : center; font-size: 20px; font-weight: bold;">
	<tr>
		<td>
		<img src="../resources/images/nothing.PNG" width="350px" height="345px"><br>
		작성된 글이 없습니다
		</td>
	</tr>
</table>
</c:if>
<c:if test="${count>0 }">
	<table class="tb2" border='0' width='100%'style="text-align: center; font-size: 20px;">
		<tr>
			<th>글 번호</th>
			<th>제목</th>
			<th>프로젝트설명</th>
			<th>프로젝트버전</th>
			<th>프로젝트 사이즈</th>
			<th>프로젝트 작성자</th>
			<th>추천수</th>
			<th>등록일</th>
		</tr>
		<c:forEach var="item" items="${list}">
		<tr>
			<td>${item.prono}</td>
			
			<td><a href="../pjt/pjtread.do?prono=${item.prono }">${item.ptitle}</a></td>
			<td>${item.pexplain}</td>
			<td>${item.pversion}</td>
			<td>${item.filesize}</td>
			<td>${item.id}</td>
			<td>${item.good}</td>
			<td>${item.date}</td>
		</tr>
		</c:forEach>
	</table>
</c:if>
</div>
</c:if>
<!-- 본문 끝 -->
<%@ include file="../../footer.jsp"%>