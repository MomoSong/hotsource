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
			<th style="width: 5%;">더보기</th>
		</tr>
		<c:forEach var="item" items="${list}">
			<tr height="70px">
				<td>${item.prono}</td>

				<td><a href="./pjtread.do?prono=${item.prono }">${item.ptitle}</a></td>
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
				<td><input type="button" value="이동"
					style="height: 25px; font-size: 14px;"
					onclick="location.href='../commit/list.do?prono=${item.prono }'"></td>
			</tr>
		</c:forEach>
	</table>

	<div style="float: right;">전체 글 : ${count } &nbsp;&nbsp;</div>
	<hr>
	<!-- 페이지 리스트 -->
	<div align="center">
		<c:if test="${count>0 }">
			<c:set var="pageCount" value="${totalPage }" />
			<c:set var="startPage" value="${startPage }" />
			<c:set var="endPage" value="${endPage }" />

			<c:if test="${endPage>pageCount }">
				<c:set var="endPage" value="${pageCount+1 }" />
			</c:if>

			<c:if test="${startPage>0 }">
				<a href="./list.do?pageNum=${startPage }">[이전]</a>
			</c:if>

			<c:forEach var="i" begin="${startPage+1 }" end="${endPage-1 }">
				<a href="./list.do?pageNum=${i }">[${i }]</a>
			</c:forEach>

			<c:if test="${endPage<pageCount }">
				<a href="./list.do?pageNum=${startPage+11 }">[다음]</a>
			</c:if>
		</c:if>
	</div>

	<br>
	<div class="bottom" align="center">
		<input type="button" value="프로젝트 등록"
			onclick="location.href='./insert.do'">
	</div>
	<form action="./pjtlist.do" method="post" align="right">
		<select id="searchtype" name="searchtype" style="height: 26px;">
			<option value='1'>제목</option>
			<option value='2'>내용</option>
			<option value='3'>제목 + 내용</option>
		</select> <input type="text" id="search" name="search"
			placeholder="검색어를 입력해주세요" aria-label="Search through site content">
		<input type="submit" value="검색">
	</form>
</div>
</c:if>
<!-- 본문 끝 -->
<%@ include file="../../footer.jsp"%>