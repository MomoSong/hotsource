<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../header.jsp" %>     
<script src="../js/member.js"></script>
<script>
function dateSelect(docForm,selectIndex) {
	watch = new Date(docForm.year.options[docForm.year.selectedIndex].text, docForm.month.options[docForm.month.selectedIndex].value,1);
	hourDiffer = watch - 86400000;
	calendar = new Date(hourDiffer);

	var daysInMonth = calendar.getDate();
		for (var i = 0; i < docForm.day.length; i++) {
			docForm.day.options[0] = null;
		}
		for (var i = 0; i < daysInMonth; i++) {
			docForm.day.options[i] = new Option(i+1);
	}
	document.form1.day.options[0].selected = true;
}
</script>

<script>
function Today(year,mon,day){
     if(year == "null" && mon == "null" && day == "null"){       
     today = new Date();
     this_year=today.getFullYear();
     this_month=today.getMonth();
     this_month+=1;
     if(this_month <10) this_month="0" + this_month;
 
     this_day=today.getDate();
     if(this_day<10) this_day="0" + this_day;     
 }
 else{  
     var this_year = eval(year);
     var this_month = eval(mon); 
     var this_day = eval(day);
     }
 
  montharray=new Array(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31); 
  maxdays = montharray[this_month-1]; 
//아래는 윤달을 구하는 것
  if (this_month==2) { 
      if ((this_year/4)!=parseInt(this_year/4)){
    	  maxdays=28; 
      }else {
    	  maxdays=29; 
      }
  } 
 
 document.writeln("<select name='year' size=1 onChange='dateSelect(this.form,this.form.month.selectedIndex);'>");
     for(i=this_year-100;i<this_year+1;i++){//현재 년도에서 과거로 100년부터 현재까지를 표시함
         if(i==this_year) document.writeln("<OPTION VALUE="+i+ " selected >" +i); 
         else document.writeln("<OPTION VALUE="+i+ ">" +i); 
     }    
 document.writeln("</select>년");      

 
 document.writeln("<select name='month' size=1 onChange='dateSelect(this.form,this.selectedIndex);'>");
     for(i=1;i<=12;i++){ 
         if(i<10){
             if(i==this_month) document.writeln("<OPTION VALUE=0" +i+ " selected >0"+i); 
             else document.writeln("<OPTION VALUE=0" +i+ ">0"+i);
         }         
         else{
             if(i==this_month) document.writeln("<OPTION VALUE=" +i+ " selected >" +i);  
             else document.writeln("<OPTION VALUE=" +i+ ">" +i);  
         }                     
    }         
 document.writeln("</select>월");
 
 document.writeln("<select name='date' size=1>");
     for(i=1;i<=maxdays;i++){ 
         if(i<10){
             if(i==this_day) document.writeln("<OPTION VALUE=0" +i+ " selected >0"+i); 
             else document.writeln("<OPTION VALUE=0" +i+ ">0"+i); 
         }
         
         else{
             if(i==this_day) document.writeln("<OPTION VALUE=" +i+ " selected } >"+i); 
             else document.writeln("<OPTION VALUE=" +i+ ">" +i);  
         }                     
    }         
 document.writeln("</select>일"); 
   
}
</script>
     <!-- 본문 시작 template.jsp -->
  <div class="pj-bar" >
	<a href="<%=request.getContextPath()%>/index.do">HOT source</a> 
	<a href="<%=request.getContextPath()%>/member/myinfo.do">나의 가입정보</a> 
	<a href="<%=request.getContextPath()%>/mylist/mylist.do">나의 프로젝트</a> 
		<a href="<%=request.getContextPath()%>/follow/myfollow.do">관심 개발자</a> 
	<a class="active">내 정보 수정</a> 
	<a href="<%=request.getContextPath()%>/member/withdraw.do">회원탈퇴</a>
  </div>
  <br>
     <form name="regForm" method="post" action="myinfoUpdateProc.do" onsubmit="return updateCheck(this)" >
     <br>
     <table style="margin:auto;">
      <tr>
	   <th>이름</th>
	   <td>
	     <input type="text" name="name" id="name" size="10" value="${dto.name }"></td>
	   </td>
      </tr>
      <tr>
	   <th>비밀번호</th>
	   <td><input type="password" name="passwd" id="passwd" size="15" value="${dto.passwd }" required="required"></td>
      </tr>
      <tr>
	   <th>비밀번호 확인</th>
	   <td><input type="password" name="rew" id="repw" size="15" required="required"></td>
      </tr>
      <tr>
		<th>생일년월일</th>
		<td colspan=3>
		<c:set var="birth" value="${dto.birth }"/>
         <c:set var="birth_y" value="${fn:substring(birth, 0, 4) }"/>
         <c:set var="birth_m" value="${fn:substring(birth, 4, 6) }"/>
         <c:set var="birth_d" value="${fn:substring(birth, 6, 8) }"/>
		 <script> Today('${birth_y}','${birth_m}','${birth_d}'); </script>
	    </td>
	</tr>
      <tr>
	   <th>전화번호</th>
	   <td><input type="text" name="ph_no" id="ph_no" size="15" value="${dto.ph_no }"></td>
      </tr>
      <tr>
	   <th>이메일</th>
	   <td><input type="text" name="email" id="email" size="30" value="${dto.email }"></td>
      </tr>
      
      
     <tr>
	  <td colspan="2">
	   <input type="submit" value="회원정보 수정"/>
	   <input type="button"  value="취소" onclick="location.href='<%=request.getContextPath()%>/login.do'"/>
	  </td>
     </tr>
    </table>

</form>
     <!-- 본문 끝 -->
<%@ include file="../footer.jsp" %>
