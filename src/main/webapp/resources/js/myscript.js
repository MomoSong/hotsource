/* myscript.js */
/* <script>태그를 생략한다 */

function test() {
  alert("test()호출");
}//end

function validate(frm) { //로그인 유효성 검사
  //문) 아이디 글자갯수가 8~15사이인지?
  var uid = frm.uid.value;
  uid = uid.trim();  //공백 지우기
  if(uid.length<8 || uid.length>15) {
    alert("아이디 글자갯수 확인해 주세요");
	frm.uid.focus(); //커서 생성
	return;          //호출시점으로 되돌아 감
  }

  //문) 비밀번호 글자갯수가 8~15사이인지?
  var upw = frm.upw.value;
  upw = upw.trim();  
  if(upw.length<8 || upw.length>15) {
    alert("비밀번호 글자갯수 확인해 주세요");
	frm.upw.focus(); 
	return;          
  }

  //문)핸드폰 관련한 각 텍스트상자에
  //   숫자형태의 글자가 입력되었는지 확인
  //   isNaN('123') //false
  //   isNaN('Hello') //true
  var phone1 = frm.phone1.value;
  phone1 = phone1.trim();
  if(phone1.length<3) {
    alert("핸드폰 번호 3글자 입력해 주세요");
    frm.phone1.focus(); 
	return;
  }

  if(isNaN(phone1)) { //숫자인지?
    alert("핸드폰 번호는 숫자로 입력해 주세요");
    frm.phone1.focus(); 
	return; 
  }

  var phone2 = frm.phone2.value;
  phone2 = phone2.trim();
  if(phone2.length<4) {
    alert("핸드폰 번호 4글자 입력해 주세요");
    frm.phone2.focus(); 
	return;
  }

  if(isNaN(phone2)) { //숫자인지?
    alert("핸드폰 번호는 숫자로 입력해 주세요");
    frm.phone2.focus(); 
	return; 
  }

  var phone3 = frm.phone3.value;
  phone3 = phone3.trim();
  if(phone3.length<4) {
    alert("핸드폰 번호 4글자 입력해 주세요");
    frm.phone3.focus(); 
	return;
  }

  if(isNaN(phone3)) { //숫자인지?
    alert("핸드폰 번호는 숫자로 입력해 주세요");
    frm.phone3.focus(); 
	return; 
  }

  frm.submit(); //type=submit 동일한 기능

}//end


function bbsCheck(f) { //게시판 유효성 감사
  //1) 작성자 글자수가 최소 2글자 인지?
	
  //2) 제목 글자수가 최소 2글자 인지?
	
  //3) 내용 글자수가 최소 2글자 인지?
	
  //4) 비밀번호 글자수가 최소 4글자이고, 숫자형태인지?
  var passwd=f.passwd.value;
  passwd=passwd.trim(); 
   
  if(passwd.length<4){
	  alert("비밀번호는 4~10글자 이상 입력해주세요");
	  f.passwd.focus();
	  return false;
  }
	
  if(isNaN(passwd)) {
    alert("비밀번호는 숫자로 입력해 주세요");
    f.passwd.focus(); 
	return false; 
  }
  
  return true; //onsubmit이벤트에서 서버로 전송하고자 할때
	
}//end


function pwCheck(f){  //비밀번호 입력되었는지 유효성 검사
  var passwd=f.passwd.value;
  passwd=passwd.trim(); 
   
  if(passwd.length<4){
	  alert("비밀번호는 4~10글자 입력해주세요");
	  f.passwd.focus();
	  return false;
  }
	
  if(isNaN(passwd)) {
    alert("비밀번호는 숫자로 입력해 주세요");
    f.passwd.focus(); 
	return false; 
  }
  
  var msg="진행된 내용은 복구되지 않습니다\n계속 진행 할까요?";
  if(confirm(msg)){ //확인 true, 취소 false
	return true;    //서버 전송
  }else{
	return false;
  }
	
}//end


function searchCheck(f) {
  var word=f.word.value;
  word=word.trim();
  if(word.length==0){
	  alert("검색어 입력하세요");
	  f.word.focus();
	  return false;
  }
  return true;
}//searchCheck() end


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


function emailCheck(){
	//새창띄워서 이메일 중복확인
	
	//새창띄우기
	var win=window.open("emailCheckForm.jsp","emailwin","width=400,height=350")
	
	//새창이 출력되는 위치 지정
	var sx=parseInt(screen.width);
    var sy=parseInt(screen.height);
    var x=(sx/2)+50;
    var y=(sy/2)-25;
		
	//화면이동
	win.moveTo(x,y);	
}//emailCheck() end

function memberCheck(f){    //회원가입 유효성 검사
  //1)아이디 5~10글자 이내
	
  //2)비밀번호 5~10글자 이내
	
  //3)비번에 특수문자 포함되어있는지
	
  //4)첫번째 비번과 두번째 비번이 서로 일치하는지
	
  //5)이름 2~20글자 이내
	
  //6)이메일에 @문자가 있는지
	
  //7)직업선택했는지
  var job=f.job.value;
  if(job=="0"){
	  alert("직업 선택 하세요");
	  return false;
  }

  //유효성 검사를 모두 통과했으므로
  //memberProc.jsp로 요청
  return true; 
  
}//memberCheck() end


function loginCheck(f){
	//로그인 유효성 검사
	//1)아이디 5~10글자 이내
	
	//2)비밀번호 5~10글자 이내
	
	return true;
}//loginCheck() end


function pdsCheck(f){
	//포토갤러리 유효성 검사
	//1)이름
	
	//2)제목
	
	//3)비밀번호
	
	//4)첨부파일
	var filename=f.filename.value;
	filename=filename.trim();
	if(filename.length<5){
		alert("첨부 파일 선택하세요");
		return false;
	}
	
	//이미지파일(.png .jpg .gif)만 업로드 가능
	var ext=filename.toLowerCase(); //파일명 전부 소문자로 바꿈
	if(!(ext.lastIndexOf(".png")>0 ||
		 ext.lastIndexOf(".jpg")>0 ||
		 ext.lastIndexOf(".gif")>0))
	{
		alert("이미지 파일만 가능합니다");
		return false;
	}
	
	return true;	
	
}//pdsCheck() end




