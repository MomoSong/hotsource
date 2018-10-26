<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html> 
<html> 
<head> 
<meta charset="UTF-8"> 
<title>board/reupdateForm.jsp</title> 
</head> 
<body>
<div align="center" style="margin-top: 13%">
<form name='frm' method='POST' action='./replyupdate.do' enctype="multipart/form-data">
<input type="hidden" name="rno" value="${dto.rno }">
<input type="hidden" name="id" value="${dto.id }">
<input type="hidden" name="boardno" value="${dto.boardno }">
댓 / 글 / 수 / 정
<br><br>
<input type="text" name="content" style="width : 80%;" value="${dto.content }">
<input type="submit" value="수정">
</form>
</div>
</body>
</html>