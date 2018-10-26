<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../header.jsp" %>

<!-- 본문 시작 template.jsp (view안에 페이지 작성할 것)-->
<p>로그인 결과</p>
<%-- <%
  String id    =request.getParameter("id").trim();
  String passwd=request.getParameter("passwd").trim();
  dto.setId(id);
  dto.setPasswd(passwd);
  String rating=dao.login(id, passwd);
  if(rating==null){
    out.println("<p>아이디/비번 다시 한번 확인해주세요!!</p>");
    out.println("<p><a href='javascript:history.back()'>[다시시도]</a></p>");
  }else{
    //out.println("<p>로그인성공!!</p>");
    //out.println(mlevel);
    //다른페이지에서 공유할수 있도록
    //session영역에 값 올리기
    session.setAttribute("s_id", id);
    session.setAttribute("s_passwd", passwd);
    session.setAttribute("s_rating", rating);
    
    //쿠키(아이디저장)-------------------------
    //<input type=checkbox name=c_id value=SAVE
    String c_id=Utility.checkNull(request.getParameter("c_id"));
    Cookie cookie=null;
    if(c_id.equals("SAVE")){
      cookie=new Cookie("c_id", id); //new Cookie("쿠키변수명", 값)
      cookie.setMaxAge(60*60*24*30); //쿠키생존기간. 1개월
    }else{
      cookie=new Cookie("c_id", "");
      cookie.setMaxAge(0);
    }
    
    //사용자PC에 쿠키값을 저장
    response.addCookie(cookie);
    //-----------------------------------------
    
    //첫페이지 이동
    String root=request.getContextPath();
    response.sendRedirect(root+"../index.jsp");    
    
  }
%> --%>

<c:if test="${res==1 }">
    <c:set var="s_id" value="${sessionScope.s_id }" scope="session"></c:set>
    <c:set var="s_rating" value="${sessionScope.s_rating }" scope="session"></c:set>
     <meta http-equiv="Refresh" content="0;url=../index.jsp">
</c:if>

<c:if test="${res==0 }">
    아이디 및 비밀번호가 다릅니다<br/>
    <a href="javascript:history.go(-1)">[돌아가기]</a>
    </c:if>
<!-- 본문 끝 -->			
<%@ include file="../footer.jsp" %>