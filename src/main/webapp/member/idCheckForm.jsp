<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="../header.jsp"%>

<div style="text-align:center;">
<p> 아이디 중복확인 </p>
<form method="post" action="./duplicateID.do" onsubmit="return blankCheck(this)">
<table border=1 style="margin:auto;">
<tr>
 <td>아이디 : </td>
 <td><input type="text" name="id" maxlength="10" autofocus></td>
 </tr>
 <tr>
 <td colspan="2"><input type="submit" value="검색"></td>
 </tr>
 </table>
</form>                                                                  
</div>
<script>
function blankCheck(f){
	var id=f.id.value;
	id = id.trim();
	var pattern = /^[A-Za-z0-9]{4,10}$/;
	if(!pattern.test(id)){
		alert("영문대,소문자 , 숫자 4 ~10 자리로 입력해 주세요.");
		return false;
	}//if end		
	return true;
}
</script>
<!-- 본문 끝 -->
<%@ include file="../footer.jsp"%>