<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.sql.Timestamp" %>
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
<div class="middle-bar">
	<a href="<%=request.getContextPath() %>/helpboard/list.do">문의 게시판</a>
</div>
<br>
<div class="tbframe">
<form name='frm' method='POST' action='./create.do' enctype="multipart/form-data" onsubmit="return HBcheck(this)">
<table class="tb1" border="0">
	<tr>
		<td colspan='2' style="background-color: #ff5050; color: white; text-align: center">문의 게시글 작성</td>
	<tr>
	<tr>
		<th>제목</th>
		<td><input type='text' name='title' style="border: 1px solid #dcdcdc; width:100%;"></td>
	</tr>
	
	<tr height="200">
		<th>내용</th>
		<td height="100"><textarea name="content" rows="10" style="border: 1px solid #dcdcdc; width:100%;"></textarea></td>
	</tr>

	<tr>
		<th>작성자</th>
		<td><input type='text' name='id' style="border: 0; width:15%;" value="${sessionScope.s_id }" readonly></td>
	</tr>

	<tr>
		<th>이미지 첨부</th>
		<td><input type='file' name='file' size='50'></td>
	</tr>
	
	<tr>
		<th>공개여부</th>
		<td>
			
			<input type="radio" name="locked" id="locked" value="O" checked="checked">공개
			&nbsp;
			<input type="radio" name="locked" id="locked" value="X">비공개
			
		</td>
	</tr>
	
	<tr>
		<th>비밀번호</th>
		<td><input type="text" name="passwd" value="비공개 선택 시에만 입력" onfocus="this.value=''"></td>
	</tr>
	
	
</table>
<hr>
<div align="center">
	<input type='submit' value='등록'>
    <input type='button' value='목록' onclick="location.href='./list.do'">
</div>
</form>
</div>

<script>
function HBcheck(f)
{
	//비공개 선택 시 비밀번호 유효성 검사
	// 1) 비공개 선택시 패스워드 여부확인
	var locked = f.locked.value;  //jquery : var locked = $("#locked").val()
	var passwd = f.passwd.value;
	locked = locked.trim(); // 공백지우기
	passwd = passwd.trim();
	if (locked=="X") {
		if(passwd.length==0) {
			alert("비밀번호를 입력해 주십시오");
			f.passwd.focus(); // 커서생성
			return false;
		}
		
		if(isNaN(passwd)) { // 숫자인지?
			alert("비밀번호는 숫자 4자리로 입력해 주십시오");
			f.passwd.focus(); // 커서생성
			return false;
		}
		
		if(passwd.length<4 || passwd.length>4) {
			alert("비밀번호는 숫자 4자리로 입력해 주십시오");
			f.passwd.focus(); // 커서생성
			return false;
		}		
	}
	return true;
}
</script>



<!-- 본문 끝 -->			
<%@ include file="../footer.jsp" %>