<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../header.jsp" %>
    <!-- 본문 시작 -->
* 아이디/비밀번호 찾기 * <br>
	<form name="findIdpw" method="post" action="./findIdpw.do" onsubmit="return idpwCheck(this)" >
      <input type ="hidden" name = "id" value = "${s_id }">
      <table border=1 style="margin:auto;">
      <tr>
         <th>이름 : </th>
         <td><input type="text" name="name" placeholder="name" required></td>
      </tr>
      <tr>
         <th>생년월일 : </th>
         <td>
          <input type="text" name="birth_y" size="5"  placeholder="yyyy" required>년
          <input type="text" name="birth_m" size="3"  placeholder="mm" required>월
          <input type="text" name="birth_d" size="3"  placeholder="dd" required>일
         </td>
      </tr> 
      <tr>
         <td colspan="2"> 
      <input type='submit' value="확인"> <input type='reset' value="취소">
         </td>
      </tr> 
      </table>         
   </form>       

     <!-- 본문 끝 -->
<%@ include file="../footer.jsp" %>