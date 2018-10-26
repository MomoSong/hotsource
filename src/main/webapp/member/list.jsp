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
  <div class="pj-bar" >
	<a href="<%=request.getContextPath()%>/index.do">HOT source</a> 
	<a href="<%=request.getContextPath()%>/member/myinfo.do">나의 가입정보</a> 
	<a class="active">나의 프로젝트</a> 
	<a href="<%=request.getContextPath()%>/follow/myfollow.do">관심 개발자</a> 
	<a href="<%=request.getContextPath()%>/member/myinfoUpdate.do">내 정보 수정</a> 
	<a href="<%=request.getContextPath()%>/member/withdraw.do">회원탈퇴</a>
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
			<th style="width: 5%;">글 번호</th>
			<th>제목</th>
			<th>프로젝트버전</th>
			<th>프로젝트 사이즈</th>
			<th>프로젝트 작성자</th>
			<th>언어</th>
			<th style="width: 5%;">추천수</th>
			<th>등록일</th>
		</tr>
			<c:forEach var="item" items="${list }">
				<tr>
				<td>${item.prono}</td>

				<td><a href="<%=request.getContextPath() %>/pjt/pjtread.do?prono=${item.prono }">${item.ptitle}</a></td>
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