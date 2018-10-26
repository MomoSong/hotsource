<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../header.jsp" %>

<!-- 본문 시작 read.jsp (view안에 페이지 작성할 것)-->
<div class="pj-bar">
	<a href="<%=request.getContextPath() %>/commit/list.do?prono=${dto.prono}">COMMIT</a>
	<a href="<%=request.getContextPath() %>/release/list.do?prono=${dto.prono}">RELEASE</a>
	<a href="<%=request.getContextPath() %>/issue/list.do?prono=${dto.prono}">ISSUE</a>
	<a onclick="window.open('<%=request.getContextPath()%>/chat/chat.do')">CHATTING</a>
</div>
<br>
<div class="tbframe">
<!-- <script type="text/javascript">
$(document).ready(function(){
	
    $.ajax({
        type : "GET", //전송방식을 지정한다 (POST,GET)
        url : "getImage",
        data : "filename=${dto.c_filename}", //호출 URL을 설정한다. GET방식일경우 뒤에 파라티터를 붙여서 사용해도된다.
        dataType : "text",//호출한 페이지의 형식이다. xml,json,html,text등의 여러 방식을 사용할 수 있다.
        error : function(){
            alert('통신실패!!');
        },
        success : function(Parse_data){
        	
            $("#Parse_Area").html(Parse_data); //div에 받아온 값을 넣는다.
            alert("통신데이터 값 : " + Parse_data);
        }
         
    });
}); 
</script> -->
<form name='frm' method='POST' enctype="multipart/form-data">
<input type="hidden" name="icno" value=${dto.icno }>
<input type="hidden" name="prono" value=${dto.prono }/>
<table class="tb1" border="0">
	<tr>
		<td colspan='2' style="background-color: #ff5050; color: white; text-align: center">COMMIT 게시글</td>
	<tr>
	<tr>
		<th>제목</th>
		<td>${dto.title }</td>
	</tr>
	<% pageContext.setAttribute("newLineChar", "\n"); %> <!-- 개행문자 치환 -->
	<tr height="200">
		<th>내용</th>
		<td height="100" style="white-space : pre-line;">
		${fn:replace(dto.content, newLineChar, "<br/>")} <!-- 개행문자 치환 -->
		<img src="http://172.16.4.75:9090/commitimg/${dto.c_filename }"/>
	  	</td>
	</tr>

	<tr>
		<th>작성자</th>
		<td>${dto.id }</td>
	</tr>

	<tr>
		<th>작성일</th>
		<td>${dto.date }</td>
	</tr>
	
	<tr>
		<th>승인상태</th>
		<td>
		<c:choose>
			<c:when test="${dto.conditions=='W'}">
			대기
			</c:when>
			<c:when test="${dto.conditions=='Y'}">
			승인
			</c:when>
			<c:otherwise>
			거부
			</c:otherwise>			
		</c:choose>
		</td>
	</tr>
	
	<tr>
		<th>파일명</th>
		<td>${dto.c_filename }<br>
			<input type="button"  value="다운로드" onclick="location.href='${root }/download?dir=/commit/c_storage&filename=${dto.c_filename}'">
			<c:set var="filesize" value="${dto.c_filesize/1024 }"></c:set>
			<c:set var="filesize" value="${fn:substringBefore(filesize,'.') }"></c:set>
			${filesize }KB
		</td>
	</tr>
	
</table>
<hr>
<div align="center">
    <input type='button' value='목록' onclick="location.href='./list.do?prono=${dto.prono}'">
    <c:if test="${dto.id.equals(sessionScope.s_id) || sessionScope.s_rating=='M'}">
    <input type='button' value='수정' onclick="location.href='./update.do?icno=${dto.icno}&prono=${dto.prono}'">
    <input type='button' value='삭제' onclick="location.href='./delete.do?icno=${dto.icno}&prono=${dto.prono}'">
    </c:if>
</div>
</form>
</div>

<!-- 본문 끝 -->			
<%@ include file="../footer.jsp" %>