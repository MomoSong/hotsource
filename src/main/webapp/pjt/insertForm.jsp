<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.sql.Timestamp" %>
<%@ include file="../../header.jsp" %>
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
		editor = CKEDITOR.replace('Pexplain');
		}
	);
	function form_save(form) {
		editor.updateElement();	
	}
</script>
<!-- 본문 시작 template.jsp (view안에 페이지 작성할 것)-->

<br>
<div class="tbframe">
<form name='frm' method='POST' action='./insert.do' enctype="multipart/form-data">
<table class="tb1" border="0">
	<tr>
		<td colspan='2' style="background-color: #ff5050; color: white; text-align: center">PJT 게시글 작성</td>
	<tr>
	<tr>
		<th>제목</th>
		<td><input type='text' name='ptitle' style="border: 1px solid #dcdcdc; width:100%;"></td>
	</tr>
	
	<tr height="200">
		<th>내용</th>
		<td height="100"><textarea name="Pexplain" rows="10" style="border: 1px solid #dcdcdc; width:100%;"></textarea></td>
	</tr>

	<tr>
		<th>작성자</th>
		<td><input type='text' name='id' style="border: 0; width:15%;" value="${sessionScope.s_id }" readonly></td>
	</tr>
	<tr>
		<th>버전정보</th>
		<td><input type='text' name='verInfo' style="border: 1px solid #dcdcdc; width:15%;"></td>
	</tr>
	<tr>
		<th>프로젝트 언어</th>
		<td><input type='text' name='planguage' style="border: 1px solid #dcdcdc; width:15%;"></td>
	</tr>
	<tr>
		<th>파일 업로드</th>
		<td><input type='file' name='comfile' size='50'></td>
	</tr>
</table>
<br>
<div align="center">
	<input type='submit' value='등록'>
    <input type='button' value='목록' onclick="location.href='./pjtlist.do'">
</div>
</form>
</div>

<!-- 본문 끝 -->			
<%@ include file="../../footer.jsp" %>