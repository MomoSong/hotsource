<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html> 
<html> 
<head> 
<meta charset="UTF-8"> 
<title>helpboard/hbpswForm.jsp</title> 
</head> 
<body>
<div align="center" style="margin-top: 20%">
<form name='frm' method='POST' action='./hbpsw.do' enctype="multipart/form-data">
<input type="hidden" name="helpno" value="${helpno }">
비 / 밀 / 번 / 호 / 입 / 력
<br><br>
<input type="password" name="passwd" style="width : 80%;">
<input type="submit" value="입력">
</form>
</div>
</body>
</html>