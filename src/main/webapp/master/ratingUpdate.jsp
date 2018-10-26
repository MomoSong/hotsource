<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html> 
<html> 
<head> 
<meta charset="UTF-8"> 
<title>issue/reupdateForm.jsp</title> 
</head> 
<body>
<div align="center" style="margin-top: 13%">
<form name='frm' method='POST' action='./ratingupdate.do' enctype="multipart/form-data">
<input type="hidden" name="id" value="${id }">
등 / 급 / 변 / 경
<br><br>
기존 등급 : ${rating } --> 
<select name="rating">
	<option value="M">Master(관리자)</option>
	<option value="P">platinum</option>
	<option value="G">Gold</option>
	<option value="S">Silver</option>
	<option value="B">Bronze</option>
</select>
<input type="submit" value="변경">
</form>
</div>
</body>
</html>