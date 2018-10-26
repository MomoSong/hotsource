<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="net.utility.*"%>
<%@ include file="../header.jsp"%>
<%
	//현재페이지-------------------------------------------
	int nowPage = 1;
	if (request.getParameter("nowPage") != null) {
		nowPage = Integer.parseInt(request.getParameter("nowPage"));
	}
%>
<c:if test="${sessionScope.s_id == NULL }">
	<div align="center">
		<img src="../resources/images/hotsource.PNG" height="200px">
		<h2>로그인 후 이용가능 합니다.</h2>
	</div>
</c:if>
<c:if test="${sessionScope.s_id != NULL }">
<!-- 본문 시작 template.jsp (view안에 페이지 작성할 것)-->
<div class="board-bar">
<a class="active" href="<%=request.getContextPath()%>/board/list.do">자유게시판</a> 
<a href="<%=request.getContextPath()%>/qboard/list.do">질문게시판</a> 
</div>
<br>
<div class="tbframe">
	<c:if test="${count==0 }">
		<table class="tb2" width=100%
			style="text-align: center; font-size: 20px; font-weight: bold;">
			<tr>
				<td><img src="../images/nothing.PNG" width="350px"
					height="345px"><br> 작성된 글이 없습니다</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${count>0 }">
		<c:set var="today" value="<%=Utility.getDate()%>"></c:set>
		<table class="tb2" width=100%
			style="text-align: center; font-size: 20px;">
			<tr>
				<th style="width: 10%;">글 번호</th>
				<th>제목</th>
				<th style="width: 15%;">작성자</th>
				<th style="width: 20%;">작성일</th>
				<th style="width: 8%;">조회수</th>
				<th style="width: 8%;">추천수</th>
			</tr>
			<c:forEach var="dto" items="${list }">
				<tr>
					<td>${dto.rnum }</td>
					<td><c:set var="regdt" value="${dto.date.substring(0,10) }"></c:set>
						<c:if test="${regdt.equals(today)}">
							<img src="../images/new.gif">
						</c:if> <c:if test="${dto.good>=10 }">
							<img src="../images/hot.gif">
						</c:if> <a href="./read.do?boardno=${dto.boardno }">${dto.title }</a> <span
						style="font-size: 15px;">(${dto.replycnt })</span></td>
					<td>${dto.id }</td>
					<td>${fn:substring(dto.date,0,16) }</td>
					<td>${dto.readcnt }</td>
					<td>${dto.good }</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
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
		<input type="button" value="게시글 등록"
			onclick="location.href='./create.do?boardno=${dto.boardno }'">
		<input type="button" value="전체글 목록"
			onclick="location.href='../board/list.do'">
	</div>
		<form action="./list.do" method="post" align="right">
        <select id="searchtype" name="searchtype" style="height:26px;">
            <option value='1'>제목</option>
            <option value='2'>내용</option>
            <option value='3'>제목 + 내용</option>
            </select>    
            
            <input type="text" id="search" name="search"
               placeholder="검색어를 입력해주세요"
               aria-label="Search through site content">
               <input type="submit" value="검색">
	    </form>
	
</div>

<!-- <script src="../js/jquery.js"></script>
<script>
$("input[id=doit]").keyup(function() { 

    if (e.keyCode == 13){
        alert("엔터키");

    }
}); 
</script> -->

<!-- 본문 끝 -->
</c:if>
<%@ include file="../footer.jsp"%>