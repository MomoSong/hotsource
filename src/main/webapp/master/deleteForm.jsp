<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html> 
<html> 
<head> 
<meta charset="UTF-8"> 
<title>issue/reupdateForm.jsp</title> 
</head> 
<body>
<div align="center" style="margin-top: 13%">
<form method="post" action="./memberdelete.do" enctype="multipart/form-data">
	<input type="hidden" name="id" value="${id }">
	<div align="center">
		<p>해당 회원을 강퇴시키시겠습니까 ?</p>
		<p>※ 한 번 강퇴된 회원의 정보는 복구되지 않습니다.</p>
	</div>
	<div align="center">
		<input type="submit" value="삭제">
	</div>
</form>
</div>
</body>
</html>