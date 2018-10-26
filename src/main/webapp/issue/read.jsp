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
<form name='frm' method='POST' enctype="multipart/form-data">
<input type="hidden" name="icno" value=${dto.icno }>
<table class="tb1" border="0">
	<tr>
		<td colspan='6' style="background-color: #ff5050; color: white; text-align: center">ISSUE 게시글</td>
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
		<c:if test="${dto.c_filesize>0 }">
		<img src="http://172.16.4.75:9090/issueimg/${dto.c_filename }"/>
		</c:if>
	  	</td>
	</tr>

	<tr>
		<th>작성자</th>
		<td>${dto.id }</td>
		<th width="10%" align="right">조회수</th>
		<td width="5%" align="right" style="text-align:center;">${dto.readcnt }</td>
		<th width="10%" align="right">추천수</th>
		<td width="5%" align="right" style="text-align:center;">${dto.rcmcnt }</td>
	</tr>
</table>
<div class="rcmbtn" id="rcmbtn" align="center">
  <input type="hidden" id="icno" value=${dto.icno }>
  <img src="${ pageContext.request.contextPath }/resources/images/fire.png" width="50px" height="50px">추천 &nbsp;&nbsp;
</div>
<hr>
<img src="${ pageContext.request.contextPath }/resources/images/reply.PNG">&nbsp;&nbsp;댓글
<table class="replyTB">
	<c:forEach var="reply" items="${reply }">
	<tr>
		<th>${reply.id }</th>
		<td>${reply.content }
		<input type="hidden" id="icno" value=${reply.icno }> 
		<input type="hidden" id="icrno" value=${reply.icrno }>
		<c:if test="${reply.id.equals(sessionScope.s_id) || sessionScope.s_rating=='M'}">
		<input type="button" id="replyupdate" style ="width:30px; 
							 	  			 		height:16px; 
							 	   					font-size : 3px; 
							 	  					text-align : center; 
							 	   					padding:0; 
							 	   					margin :0;" 
							 	   					value="수정" 
							 	   					onclick="window.open('./replyupdate.do?icrno=${reply.icrno}','댓글 수정',
							 	   					'width=600px, height=300px, top=250px, left=500px')">
		<input type="button" id="replydelete" style ="width:30px; 
							 	   					height:16px; 
							 	   					font-size : 3px; 
							 	   					text-align : center; 
							 	   					padding:0; 
							 	   					margin :0;" 
							 	   					value="삭제" onclick="replydel(${reply.icrno })">
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
			<input type="hidden" id="icno" value=${dto.icno }>
			<input type="hidden" id="id" value="${sessionScope.s_id }"> <!-- 세션 아이디 값 -->
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
    <input type='button' value='목록' onclick="location.href='./list.do?prono=${dto.prono}'">
    <c:if test="${dto.id.equals(sessionScope.s_id) || sessionScope.s_rating=='M'}">
    <input type='button' value='수정' onclick="location.href='./update.do?icno=${dto.icno}&prono=${dto.prono}'">
    <input type='button' value='삭제' onclick="location.href='./delete.do?icno=${dto.icno}&prono=${dto.prono}'">
    </c:if>
</div>
</form>
</div>

<script>
$("#rcmbtn").click(function(){  
	$.post("/hotsource/issue/recommend.do",
			"icno=" + $("#icno").val(),
			responseProc);
}); //recommend() end

$("#replycreate").click(function(){  
	
	var params = "icno=" + $("#icno").val()
			   + "&id=" + $("#id").val()
			   + "&content=" + $("#content").val();
	
	$.post("/hotsource/issue/replycreate.do", params, responseProc);
}); //replycreate() end 

function replydel(icrno) {
	//alert(icrno);
	
	var params = "icrno=" + icrno
			   + "&icno=" + $("#icno").val();
	
	$.post("/hotsource/issue/replydelete.do",
			params, responseProc);
}

function responseProc(result) {
	alert(result);
	location.reload();
} // responseProc() end

</script>

<!-- 본문 끝 -->			
<%@ include file="../footer.jsp" %>