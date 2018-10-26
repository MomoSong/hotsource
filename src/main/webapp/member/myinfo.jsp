<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../header.jsp" %>
<!-- 본문 시작  -->
<!-- myinfo.jsp -->
  <div class="pj-bar" >
	<a href="<%=request.getContextPath()%>/index.do">HOT source</a> 
	<a class="active">나의 가입정보</a> 
	<a href="<%=request.getContextPath()%>/mylist/mylist.do">나의 프로젝트</a> 
	<a href="<%=request.getContextPath()%>/follow/myfollow.do">관심 개발자</a> 
	<a href="<%=request.getContextPath()%>/member/myinfoUpdate.do">내 정보 수정</a> 
	<a href="<%=request.getContextPath()%>/member/withdraw.do">회원탈퇴</a>
  </div>
  	<br>
  	<div class="tbframe" align="center">
   <form name="frm">
    <table class="tb1" border="0" style="margin:auto" align="center">
      <tr>
        <th>이름</th>
        <td colspan=3>${dto.name }</td>
      </tr>
      <tr>
        <th>생년월일</th>
        <td colspan=3>
         <c:set var="birth" value="${dto.birth }"/>
         <c:set var="year" value="${fn:substring(birth, 0, 4) }"/>
         <c:set var="month" value="${fn:substring(birth, 4, 6) }"/>
         <c:set var="date" value="${fn:substring(birth, 6, 8) }"/>
         <c:out value="${year }년 ${month }월 ${date }일"/>
      </tr>
      <tr>
        <th>전화번호</th>
        <td colspan=3>${dto.ph_no }</td>
      </tr>
      <tr>
        <th>이메일주소</th>
        <td colspan=3>${dto.email }</td>
      </tr>
      <tr>
        <th>회원등급</th>
        <td colspan=3>
         <c:set var="rating" value="${dto.rating }"/>
         <c:choose>
          <c:when test="${rating eq 'M'}">관리자</c:when>
          <c:when test="${rating eq 'D'}">다이아</c:when>
          <c:when test="${rating eq 'G'}">골드</c:when>
          <c:when test="${rating eq 'S'}">실버</c:when>
          <c:when test="${rating eq 'B'}">브론즈</c:when>
         </c:choose>
        </td>
      
     

      
       </tr>
    </table>
   </form>
  </div>
<!-- 본문 끝 -->
<%@ include file="../footer.jsp" %>