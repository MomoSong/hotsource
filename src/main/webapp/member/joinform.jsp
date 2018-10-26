<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../header.jsp"%>

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
      if (parseInt(this_year/4)!=parseInt(this_year/4)){
    	  maxdays=28; 
      }
      else maxdays=29; 
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


<!-- 본문 시작 template.jsp (view안에 페이지 작성할 것)-->
<script type="text/javascript">
	function idCheck() {
		  //아이디 중복확인
			
		  //새창만들기
		  var win=window.open("idCheckForm.jsp", "idwin", "width=400,height=350");	
			
		  //새창이 출력되는 위치 지정
		  var sx=parseInt(screen.width);  //모니터 해상도 넓이
		  var sy=parseInt(screen.height); //모니터 해상도 높이
		  var x=(sx/2)+50;
		  var y=(sy/2)-25;
			
		  //화면이동
		  win.moveTo(x, y);
		}//idCheck() end
		
		function memberCheck(f) {
			// 회원가입 유효성 검사

			// 1) 아이디 영문대,소문자 , 숫자 4 ~12 자리
			var id = f.id.value;
			id = id.trim();
			var patternId = /^[A-Za-z0-9]{4,10}$/;
			if (!patternId.test(id)) {
				alert("id는 영어(대,소) , 숫자 포함 4~10 자리로 입력해 주세요.");
				return false;
			}// if end

			// 2) 비밀번호 4~10 글자 이내
			var pw = f.pw.value;
			var patternPw = /^[A-Za-z0-9]{4,10}$/;
			if (!patternPw.test(pw)) {
				alert("pw는 영어(대,소), 숫자 포함 4~10 자리로 입력해 주세요.");
				return false;
			}// if end

			// 3) 2개의 비밀번호가 서로 일치하는지 검사
			var repw = f.repw.value;
			repw = repw.trim();
			if (pw != repw) {
				alert("비밀번호가 동일하지 않습니다.");
				return false;
			}//if end

			// 4) 이름 2글자 이상 한+영
			var name = f.name.value;
			name = name.trim();
			var pattenName = /^[가-힣]{2,4}|[a-zA-Z]{2,10}\s[a-zA-Z]{2,10}$/;
			if (!pattenName.test(name)) {
				alert("이름을 2~10 글자로 입력하세요.(한글,영어만)");
				return false;
			}//if end


			// 5) 이메일 @문자가 있는지 검사
			var mail = f.mail.value;
			mail = mail.trim();
			var pattenMail=/([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
			if (!pattenMail.test(mail)) {
				alert("올바른 이메일 형식이 아닙니다.");
				return false;
			}

			// 6) 생일에 숫자만 입력
			var year = f.birthy.value;
			var month = f.birthm.value;
			var date = f.birthd.value;
			var today = new Date(); // 날자 변수 선언
		    var yearNow = today.getFullYear();

			var pattenNum = /^[0-9]+$/;

			if(!pattenNum.test(year) && year.leanth!=4){
				alert("년도에 4자리 숫자만 입력하세요.");
				return false;
			}
			if(!pattenNum.test(month) && month.leanth!=2){
				if(month<1 || month>12)
				alert("월에 2자리 숫자만 입력하세요.");
				return false;
			}
			if(!pattenNum.test(date) && year.leanth!=2){
				if(date<1 || date >31)
				alert("일에 2자리 숫자만 입력하세요.");
				return false;
			}
			 if ((month==4 || month==6 || month==9 || month==11) && date==31) {
		          alert(month+"월은 31일이 존재하지 않습니다.");
		          return false;
		     }
		     if (month == 2) {
		          var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
		          if (date>29 || (date==29 && !isleap)) {
		               alert(year + "년 2월은  " + date + "일이 없습니다.");
		               return false;
		          }
		     }//if end

			// 7) 휴대폰 번호 숫자만 입력
			var tel = f.tel.value;
			var pattenNum2 = /^[0-9]+$/;
			if(!pattenNum2.test(tel) && tel.length<13){
				alert("- 를 제외한 숫자만 입력하세요.(최대 11자리)");
				return false;
			}

			// 유효성검사를 통과 했으므로 회원가입폼을 서버로 전송
			return true;

		}// memberCheck() end

</script>



<div class="middle-bar">

	<a>HotSorce 회원가입</a>
</div>
<br>
<div class="tbframe">
	<form name='userInfo' method='POST' action='join.do' onsubmit="return memberCheck(this)">
		<table class="tb1" border="0">
	
				<th>아이디</th>
				<td><input type="text" name="id" id="id" size="20"
					style="border: 1.2px solid #dcdcdc;" readonly> 
					<input type="button" value="ID중복확인" onclick="idCheck()"></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type='password' name='passwd' id="passwd" size="20"
					required style="border: 1px solid #dcdcdc;"></td>
			</tr>
			<tr>
				<th>비밀번호확인</th>
				<td><input type='password' name='repasswd' id="passwdcheck"
					size="20" required style="border: 1px solid #dcdcdc;"></td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type='text' name='name' id="name" size="20"
					style="border: 1px solid #dcdcdc;"></td>
			</tr>

			<tr>
			<th>전화번호</th>
			<td><input type="text" name="ph_no" size="15" style="border: 1px solid #dcdcdc;"></td>		
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name="email" id="email" size="20"
					style="border: 1px solid #dcdcdc;"> </td>
			</tr>

			<tr>
	<th>생일</th>
	<td colspan=3>
	 <script> Today('null','null','null'); </script>
    </td>

</tr>

		</table>
		<hr>
		<div align="center">
			<input type='submit' value='회원가입' > 
			<input type='button' value='취소' onclick="location.href='<%=request.getContextPath() %>/index.do'">
		</div>

		<!--//--------------------------------------------------------------------------------------------  -->


	</form>
</div>
<!-- 본문 끝 -->
<%@ include file="../footer.jsp"%>