<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../header.jsp" %>

<!-- 본문 시작 read.jsp (view안에 페이지 작성할 것)-->
<div class="middle-bar">
	<a href="<%=request.getContextPath() %>/notice/list.do">공지 게시판</a>
</div>
<br>
<div class="tbframe">
<form name='frm' method='POST' enctype="multipart/form-data">
<input type="hidden" name="noticeno" value=${dto.noticeno }>
<table class="tb1" border="0">
	<tr>
		<td colspan='6' style="background-color: #ff5050; color: white; text-align: center">공지 게시글</td>
	<tr>
	<tr>
		<th>제목</th>
		<td colspan='6'>${dto.title }</td>
	</tr>
	
	<tr height="200">
		<th>내용</th>
		<td colspan='6' height="100" style="white-space : pre-line;">
		${dto.content }
		<br>
		<c:if test="${dto.filesize>0 }">
		<img src="./n_storage/${dto.filename }" width="100%">
		</c:if>
	  	</td>
	</tr>

	<tr>
		<th>조회수</th>
		<td>${dto.readcnt }</td>
		
	</tr>
</table>

<hr>
<img src="../resources/images/reply.PNG">&nbsp;&nbsp;댓글
<table class="replyTB">
	<c:forEach var="reply" items="${reply }">
	<tr>
		<th>${reply.id }</th>
		<td>${reply.content }
		<input type="hidden" id="noticeno" value=${reply.noticeno }> 
		<input type="hidden" id="ntrno" value=${reply.ntrno }>
		<c:if test="${reply.id.equals(sessionScope.s_id) || sessionScope.s_rating=='M'}">
		<input type="button" id="replyupdate" style ="width:30px; 
							 	  			 		height:16px; 
							 	   					font-size : 3px; 
							 	  					text-align : center; 
							 	   					padding:0; 
							 	   					margin :0;" 
							 	   					value="수정" 
							 	   					onclick="window.open('./replyupdate.do?ntrno=${reply.ntrno}','댓글 수정',
							 	   					'width=600px, height=300px, top=250px, left=500px')">
		<input type="button" id="replydelete" style ="width:30px; 
							 	   					height:16px; 
							 	   					font-size : 3px; 
							 	   					text-align : center; 
							 	   					padding:0; 
							 	   					margin :0;" 
							 	   					value="삭제" onclick="replydel(${reply.ntrno })">
		</c:if>
		</td>
	</tr>
	</c:forEach>
	<tr>
	<td>&nbsp;</td>
	</tr>
	<tr>
		<th style="font-weight:bold; background-color : #FF5050; color : white;">${sessionScope.s_id } </th>
		<td style="color:grey;">
			<input type="hidden" id="noticeno" value=${dto.noticeno }>
			<input type="hidden" id="id" value="${sessionScope.s_id }"> <!-- 원래는 세션 아이디 값 -->
			<input type="text"   id="content" value="댓글을 작성해 주세요..." onfocus="this.value=''" style="width:95%;"
			onkeydown = "if (event.keyCode == 13)
                     	document.getElementById('replycreate').click()"> <!-- keyCode 13 은 Enter key -->
			<input type="button" id="replycreate" style ="width:45px; 
								 	  						height:24px; 
								 	   						font-size : 13px; 
								 	  						text-align : center; 
								 	   						padding:0; 
							 		   						margin :0;" 
							 	   							value="작성">
		</td>
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

<script>

$("#replycreate").click(function(){  
	
	var params = "noticeno=" + $("#noticeno").val()
			   + "&id=" + $("#id").val()
			   + "&content=" + $("#content").val();
	
	$.post("/hotsource/notice/replycreate.do", params, responseProc);
}); //replycreate() end 

function replydel(ntrno) {
	//alert(ntrno);
	
	var params = "ntrno=" + ntrno
			   + "&noticeno=" + $("#noticeno").val();
	
	$.post("/hotsource/notice/replydelete.do",
			params, responseProc);
}

function responseProc(result) {
	alert(result);
	location.reload();
} // responseProc() end

</script>

<!-- 본문 끝 -->			
<%@ include file="../footer.jsp" %>