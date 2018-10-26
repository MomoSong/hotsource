<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="net.utility.*" %>
<%@ include file="../header.jsp" %>
<%
//현재페이지-------------------------------------------
int nowPage=1;
if(request.getParameter("nowPage")!=null){
	nowPage=Integer.parseInt(request.getParameter("nowPage"));
}
%>
<!-- 본문 시작 template.jsp (view안에 페이지 작성할 것)-->
<c:if test="${sessionScope.s_id == NULL }">
	<div align="center">
		<img src="../resources/images/hotsource.PNG" height="200px">
		<h2>로그인 후 이용가능 합니다.</h2>
	</div>
</c:if>
<c:if test="${sessionScope.s_id != NULL }">
<div class="middle-bar">
	<a href="<%=request.getContextPath() %>/helpboard/list.do">문의 게시판</a>
</div>
<br>
<div class="tbframe">
<c:if test="${count==0 }">
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
<c:set var="today" value="<%=Utility.getDate()%>"></c:set>
<table class="tb2" width=100% style="text-align : center; font-size: 20px;">
	<tr>
		<th style="width : 10%;">글 번호</th>
		<th>제목</th>
		<th style="width : 15%;">작성자</th>
		<th style="width : 20%;">작성일</th>
		<th style="width : 8%;">조회수</th>
	</tr>
<c:forEach var="dto" items="${list }">
	<tr>
		<td>${dto.helpno }</td>
		<td>
		<c:forEach var="i" begin="1" end="${dto.indent }" step="1">
		<img src="../resources/images/reply.gif"> 
		</c:forEach>
		
		<c:set var="regdt" value="${dto.date.substring(0,10) }"></c:set>
		<c:if test="${regdt.equals(today)}">
		<img src="../resources/images/new.gif">
		</c:if>
		<c:if test="${dto.readcnt>=10 }">
		<img src="../resources/images/hot.gif">
		</c:if>
		
		<c:if test="${dto.locked.equals('X') }"> <!-- 비공개 일시 -->
		<img src="../resources/images/locked.png" width="25px" height="25px">
		<a onclick="window.open('./hbpsw.do?helpno=${dto.helpno}','비밀번호 입력',
				'width=330px, height=220px, top=300px, left=650px')">${dto.title }</a>
		</c:if>
		<c:if test="${dto.locked.equals('O') }"> <!-- 공개 일시 -->
		<a href="./read.do?helpno=${dto.helpno }">${dto.title }</a>
		</c:if>
		
		</td>
		<td>${dto.id }</td>
		<td>${fn:substring(dto.date,0,16) }</td>
		<td>${dto.readcnt }</td>
	</tr>	
</c:forEach>
</table>
</c:if>
<div style="float:right;">전체 글 : ${count } &nbsp;&nbsp;</div>
<hr>
<!-- 페이지 리스트 -->
<div align="center">
<c:if test="${count>0 }">
   <c:set var="pageCount" value="${totalPage }"/>
   <c:set var="startPage" value="${startPage }"/>
   <c:set var="endPage" value="${endPage }"/>

   <c:if test="${endPage>pageCount }">
	  <c:set var="endPage" value="${pageCount+1 }"/>
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
	<input type="button" value="문의등록" onclick="location.href='./create.do'">
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
</c:if>
<!-- 본문 끝 -->			
<%@ include file="../footer.jsp" %>