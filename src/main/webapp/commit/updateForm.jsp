<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../header.jsp" %>

<script> //스마트에디터
	CKEDITOR.on('dialogDefinition', function (ev) {
        var dialogName = ev.data.name;
        var dialog = ev.data.definition.dialog;
        var dialogDefinition = ev.data.definition;
        if (dialogName == 'image') {
            dialog.on('show', function (obj) {
                this.selectPage('Upload'); });
            dialogDefinition.removeContents('advanced');
            dialogDefinition.removeContents('Link'); }
    });
	 var editor = null;
	jQuery(function() { 
		editor = CKEDITOR.replace('content');
		}
	);
	function form_save(form) {
		editor.updateElement();	
	}
</script>

<!-- 본문 시작 template.jsp (view안에 페이지 작성할 것)-->
<div class="pj-bar">
	<a class="active" href="<%=request.getContextPath() %>/commit/list.do">COMMIT</a>
	<a href="<%=request.getContextPath() %>/release/list.do">RELEASE</a>
	<a href="<%=request.getContextPath() %>/issue/list.do">ISSUE</a>
	<a onclick="window.open('<%=request.getContextPath()%>/chat/chat.do')">CHATTING</a>
</div>
<br>
<div class="tbframe">
<form name='frm' method='POST' action='./update.do' enctype="multipart/form-data">
<input type='hidden' name='icno' value='${dto.icno }'>
<table class="tb1" border="0">
	<tr>
		<td colspan='2' style="background-color: #ff5050; color: white; text-align: center">COMMIT 게시글 수정</td>
	<tr>
	<tr>
		<th>제목</th>
		<td><input type='text' name='title' style="border: 1px solid #dcdcdc; width:100%;" value="${dto.title }"></td>
	</tr>
	
	<tr height="200">
		<th>내용</th>
		<td height="100"><textarea name="content" rows="10" wrap="physical" style="border: 1px solid #dcdcdc; width:100%;">${dto.content }</textarea></td>
	</tr>

	<tr>
		<th>작성자</th>
		<td>${dto.id }</td>
	</tr>

	<tr>
		<th>파일 업로드</th>
		<td><input type='file' name='comfile' size='50'>기존 파일 : ${dto.c_filename }</td>
	</tr>
</table>
<hr>
<div align="center">
	<input type='submit' value='수정'>
    <input type='button' value='목록' onclick="location.href='./list.do'">
</div>

</form>
</div>
<!-- 본문 끝 -->			
<%@ include file="../footer.jsp" %>