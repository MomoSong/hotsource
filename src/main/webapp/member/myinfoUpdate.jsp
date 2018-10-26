<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../header.jsp" %>
<!-- 본문 시작  -->
<!-- myinfoupdate.jsp -->
  <div class="pj-bar" >
	<a href="<%=request.getContextPath()%>/index.do">HOT source</a> 
	<a href="<%=request.getContextPath()%>/member/myinfo.do">나의 가입정보</a> 
	<a href="<%=request.getContextPath()%>/mylist/mylist.do">나의 프로젝트</a> 
		<a href="<%=request.getContextPath()%>/follow/myfollow.do">관심 개발자</a> 
	<a class="active">내 정보 수정</a> 
	<a href="<%=request.getContextPath()%>/member/withdraw.do">회원탈퇴</a>
  </div>
  <br>
   <form method="post" action="myinfoUpdate.do" onsubmit="return pwCheck(this)">   
      <table style="margin:auto">
       <tr>
        <td colspan="2" align="center">비밀번호를 입력하세요.</td>        
       </tr> 
       <tr>
        <th>비밀번호</th>
         <td><input type="password" name="passwd" size="10" autofocus required></td>
       </tr>
       <tr>
        <td colspan="2" align="center">
         <input type="submit" value="확인">
        </td>        
       </tr>       
      </table>
  </form>  
<!-- 본문 끝 -->
<%@ include file="../footer.jsp" %>