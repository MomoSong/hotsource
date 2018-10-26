<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <!-- 본문 시작 -->


<section>
<c:if test="${empty sessionScope.s_id }">
<%
//사용자 pc에 저장된 쿠키값 가져오기
Cookie[] cookies=request.getCookies();
String c_id="";
if(cookies!=null){//쿠키가 존재한다면
   for(int idx=0; idx<cookies.length; idx++){
	   Cookie item=cookies[idx];
	   if(item.getName().equals("c_id")==true){
		   c_id=item.getValue();
	   }
   }
}
%>
   <center>
	<form name="loginfrm" method="post" action="./login.do" onsubmit="return loginCheck(this)" >
      <table>
      <tr>
         <td><input type="text" name="id" class="signUpInput"  size="28"  placeholder="Username" required></td>
      </tr>
      <tr>
         <td><input type="password" name="passwd" class="signUpInput" size="28" placeholder="Password" required></td>
      </tr>
      <tr>
          <td style="text-align: center">
            <!-- type="image"의 기본속성은 submit -->
            <!--<input type="image" src="../images/bt_login.gif"  style="margin-left:10px; cursor:pointer;">  -->
            <input type="submit" value=" Login" class="signUpButton" >
          </td>
      </tr>
      <tr>
         <td colspan="2">
         
         <p style="font-size:12px">
          
            <input type="checkbox" name="c_id" class="signUpEtc" value="SAVE" <%if(!c_id.isEmpty()){out.print("checked"); } %>/>아이디저장  
            &nbsp;|&nbsp;
            <a href="join.do">회원가입</a>
            &nbsp;|&nbsp;
          <a href="findIdpw.do">아이디/비번찾기</a>
         </p>
         </td>
      </tr>
      </table>
   </form>
</center>        
</c:if>
 <% //로그인 성공했을때 %>

<c:if test="${sessionScope.s_id !=null}">
    <table width="500" cellpadding="0" cellspaceing="0" align="center" border="1">
    <tr>
        <td rowspan="3" bgcolor="${value_c }" align="left">${sessionScope.s_id }님이 방문하셨습니다.
          <form method="post" action="./logout.do">
            <input type="submit" value="로그아웃">
          </form>
          
          <input type="button" value="회원정보 수정" onclick="location.href='./myinfoUpdate.do'">
       <input type="button" value="회원 탈퇴" onclick="location.href='./withdraw.do'">          
       
          </td>
        </tr>
      </table>
    </c:if> 
</section>
     <!-- 본문 끝 -->
<%@ include file="../footer.jsp" %>