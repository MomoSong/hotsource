<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.sql.Timestamp"%>
<%@ include file="../../header.jsp"%>
<!-- 본문 시작 template.jsp (view안에 페이지 작성할 것)-->
  <div class="pj-bar" >
	<a href="<%=request.getContextPath()%>/index.do">HOT source</a> 
	<a href="<%=request.getContextPath()%>/member/myinfo.do">나의 가입정보</a> 
	<a href="<%=request.getContextPath()%>/mylist/mylist.do">나의 프로젝트</a> 
	<a class="active">관심 개발자</a> 
	<a href="<%=request.getContextPath()%>/member/myinfoUpdate.do">내 정보 수정</a> 
	<a href="<%=request.getContextPath()%>/member/withdraw.do">회원탈퇴</a>
  </div>
  
<c:if test="${sessionScope.s_id == NULL }">
	<div align="center">
		<img src="../resources/images/hotsource.PNG" height="200px">
		<h2>로그인 후 이용가능 합니다.</h2>
	</div>
</c:if>
<c:if test="${sessionScope.s_id != NULL }">
<div align="center">
	<img src="../resources/images/answer_icon.png" width="70px"> <span style="font-size: 25px;">PROJECT LIST</span>
</div>
<br>
<div class="tbframe" style="border:0;">
	<table class="tb3" width='100%'
		style="text-align: center; font-size: 20px;">
		<tr>
			<th style="width: 5%;">글 번호</th>
			<th>제목</th>
			<th>프로젝트버전</th>
			<th>프로젝트 사이즈</th>
			<th>프로젝트 작성자</th>
			<th>언어</th>
			<th style="width: 5%;">추천수</th>
			<th>등록일</th>
		</tr>
		<c:forEach var="item" items="${list}">
			<tr height="70px">
				<td>${item.prono}</td>

				<td><a href="../pjt/pjtread.do?prono=${item.prono }">${item.ptitle}</a></td>
				<td>${item.pversion}</td>
				<td>
					<c:set var="filesize" value="${item.filesize/1024 }"></c:set>
					<c:set var="filesize" value="${fn:substringBefore(filesize,'.') }"></c:set>
					${filesize }KB
				</td>
				<td>${item.id}</td>
				<td>${item.planguage}</td>
				<td>${item.good}</td>
				<td>${item.date}</td>
			</tr>
		</c:forEach>
	</table>

	<br>
	
</div>
</c:if>
<!-- 본문 끝 -->
<%@ include file="../../footer.jsp"%>