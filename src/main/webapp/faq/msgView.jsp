<%@ page contentType="text/html; charset=UTF-8" %> 
<!DOCTYPE html> 
<html> 
<head> 
<meta charset="UTF-8"> 
<title>commit/msgView.jsp</title> 
<style type="text/css"> 
*{ 
  font-family: gulim; 
  font-size: 24px; 
} 
</style> 
<link href="../css/style.css" rel="stylesheet" type="text/css">
</head> 
<body>
<div class="content" align="center" style="margin-top: 13%">
	<dl>
		<dd>${msg1 != null ? img : "" } ${msg1 }</dd>
		<dd>${msg2 != null ? img : "" } ${msg2 }</dd>
		<dd>${msg3 != null ? img : "" } ${msg3 }</dd>
	</dl>
	<p>
		${link1 } ${link2 } ${link3 }
	</p>
</div>
</body> 
</html> 
