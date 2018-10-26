<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../header.jsp"%>

<script
	src="${ pageContext.request.contextPath }/resources/jquery/jquery.js"
	type="text/javascript"></script>
<script
	src="${ pageContext.request.contextPath }/resources/jquery/jquery-ui.custom.js"
	type="text/javascript"></script>
<script
	src="${ pageContext.request.contextPath }/resources/jquery/jquery.cookie.js"
	type="text/javascript"></script>
<link
	href="${ pageContext.request.contextPath }/resources/src/skin/ui.dynatree.css"
	rel="stylesheet" type="text/css" id="skinSheet">
<script
	src="${ pageContext.request.contextPath }/resources/src/jquery.dynatree.js"
	type="text/javascript"></script>

<script type="text/javascript">
	/* $(document).ready(function() {
		$("#tree").dynatree({
			initAjax : {
				url : "/ListByAjax"
			}
		});
	}); */

	$(function() {
		$('#content').click(function() {
			alert('hi');
		});
	});
	$(function() {
		$("#tree").dynatree(
				{
					initAjax : {
						url : "ListByAjax",
						data : "pname='${dto.pname}'"
					},
					onActivate : function(node) {
						location.href = "./pjtread2.do?node=" + node.data.title + "&prono=" + ${dto.prono};
					}
				});
	});
	$(
			function() {
				$("#btn")
						.click(
								function() {
									$
											.ajax({
												type : "GET",
												url : "downcnt.do",
												data : "prono=${dto.prono}",
												error : function() {
													alert('통신실패!!');
												},
												success : function(data) {
													//alert("통신데이터 값 : " + data);
													location.href = '${root }/download?dir=/pjt/p_storage&filename=${dto.pname}';
												}
											});
								})
			})
</script>

<!-- 본문 시작 read.jsp (view안에 페이지 작성할 것)-->
<div class="pj-bar">
	<a
		href="<%=request.getContextPath() %>/commit/list.do?prono=${dto.prono}">COMMIT</a>
	<a
		href="<%=request.getContextPath() %>/release/list.do?prono=${dto.prono}">RELEASE</a>
	<a
		href="<%=request.getContextPath() %>/issue/list.do?prono=${dto.prono}">ISSUE</a>
	<a onclick="window.open('<%=request.getContextPath()%>/chat/chat.do')">CHATTING</a>
</div>
<br>
<div class="tbframe">
	<form name='frm' method='POST' enctype="multipart/form-data">
		<table class="tb1">
			<tr>
				<th>프로젝트제목 -</th>
				<td>${dto.ptitle }&nbsp;&nbsp;&nbsp;</td>
				<th>작성자 -</th>
				<td><input type="text" id="flw" name="flw" value="${dto.id }"
					style="border: 0; cursor: pointer;"
					onclick="window.open('../follow/follow.do?flw=${dto.id}','관심개발자 추가','width=600px, height=300px, top=250px, left=500px')"
					readonly>&nbsp;&nbsp;&nbsp;</td>
				<th>작성일 -</th>
				<td>${dto.date }</td>
			</tr>
		</table>
		<div id="tree"></div>

		<div>
			<textarea rows="30" style="width: 100%;">${contents}</textarea>
		</div>

		<input type="hidden" id="prono" value=${dto.prono }>
		<table class="tb1">
			<tr>
				<th>파일명 &nbsp;&nbsp;&nbsp;</th>
				<td>${dto.pname }&nbsp;&nbsp;&nbsp;<input type="button"
					id="btn" value="다운로드"> <c:set var="filesize"
						value="${dto.filesize/1024 }"></c:set> <c:set var="filesize"
						value="${fn:substringBefore(filesize,'.') }"></c:set> ${filesize }KB
				</td>
			</tr>
			<tr>
				<th>조회수 &nbsp;&nbsp;&nbsp;</th>
				<td>${dto.pjtcnt }</td>
			</tr>
			<tr>
				<th>추천수 &nbsp;&nbsp;&nbsp;</th>
				<td>${dto.good }&nbsp;&nbsp;&nbsp;<input type='button'
					value='추천하기' name='good'
					onclick="location.href='./pjtgood.do?prono=${dto.prono }'">
				</td>
			</tr>
		</table>
		<hr>
		<div align="center">
			<input type='button' value='목록'
				onclick="location.href='./pjtlist.do'">
			<c:if
				test="${dto.id.equals(sessionScope.s_id) || sessionScope.s_rating=='M'}">
				<input type='button' value='수정'
					onclick="location.href='./pjtupdate.do?prono=${dto.prono}'">
				<input type='button' value='삭제'
					onclick="location.href='./pjtdelete.do?prono=${dto.prono}'">
			</c:if>
		</div>
	</form>
</div>

<!-- 본문 끝 -->
<%@ include file="../footer.jsp"%>