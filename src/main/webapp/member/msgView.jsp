<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>msgView.jsp</title>
<link rel="stylesheet" href="../css/mystyle.css">
</head>
<body>
<!-- msgView.jsp -->
<br>
<div class="content"" >
	<dl>
		<dd>${msg1 != null ? img : ""} ${msg1}</dd>
		<dd>${msg2 != null ? img : ""} ${msg2}</dd>
		<dd>${msg3 != null ? img : ""} ${msg3}</dd>
	</dl>
	<p>
		${link1} ${link2} ${link3}
	</p>
</div>

<script>
function apply(id){
	opener.document.getElementById("id").value=id;
	window.close();
}
</script>
</body>
</html>