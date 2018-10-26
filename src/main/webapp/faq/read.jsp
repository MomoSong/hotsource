<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../header.jsp" %>

<!-- 본문 시작 read.jsp (view안에 페이지 작성할 것)-->
<div class="middle-bar">
	<a href="<%=request.getContextPath() %>/faq/list.do">자주 묻는 질문 (FAQ)</a>
</div>
<br>
<div class="tbframe">
<form name='frm' method='POST' enctype="multipart/form-data">
<input type="hidden" name="noticeno" value=${dto.noticeno }>
<table class="tb1" border="0">
	<tr>
		<td colspan="2" style="background-color: #3c3c3c; color: white; text-align: center; width : 100%;">${dto.title }</td>
	<tr>
	
	<tr>
		<td colspan="2" 
			style="white-space : pre-line; height:350px;
				   background-image : url('../resources/images/question.png');
				   background-size: 100% 100%; background-repeat: no-repeat;">
				<div class="comment">
				${dto.content }
				</div>
	  	</td>
	</tr>
	
	<tr>
	<td>
		<img src="../resources/images/question_icon.png" width="150px" style="margin-left:80px;">
	</td>
	</tr>

 
	<tr>
		<td colspan="2" 
			style="white-space : pre-line; height:350px;
				   background-image : url('../resources/images/answer.png');
				   background-size: 100% 100%; background-repeat: no-repeat;">
				<div class="comment">
			<div class="comment">
				${dto.answer }
				<br>
				<c:if test="${dto.filesize>0 }">
				<img src="./faq_storage/${dto.filename }" width="100%">
				</c:if>
			</div>
	  	</td>
	</tr>
	
	<tr>
	<td colspan="2" style="text-align:right;">
		<img src="../resources/images/answer_icon.png" width="150px" style="margin-right:30px;">
	</td>
	</tr>


	<tr>
		<th style="width: 10%;">조회수</th>
		<td>${dto.readcnt }</td>
		
	</tr>
</table>

<hr>
<div align="center">
    <input type='button' value='목록' onclick="location.href='./list.do'">
    <c:if test="${sessionScope.s_rating=='M'}">
    <input type='button' value='수정' onclick="location.href='./update.do?noticeno=${dto.noticeno}'">
    <input type='button' value='삭제' onclick="location.href='./delete.do?noticeno=${dto.noticeno}'">
    </c:if>
</div>
</form>
</div>


<!-- 본문 끝 -->			
<%@ include file="../footer.jsp" %>