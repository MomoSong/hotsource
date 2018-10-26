package kr.co.hotsource.member;

public class emailcode {
	//emailaddress @뒤의 값 code 이용
	//사용이유 : 전달받은 값은 label형태이지만db에 넘겨줄때는 code값이어야할때
	/*
	@naver.com   : 001
	@hanmail.net : 002
	@gmail.com	 : 003
	@daum.net	 : 004
	*/
	
	private String code; // db에 전달될 값
    private String label; // 화면에 보여지는 값
	public emailcode() {System.out.println("------------emailcode객체생성");}
	
	public emailcode(String code, String label) {
        this.code = code;
        this.label = label;
    } // 인자 생성자 
	public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    public String getLabel() {
        return label;
    }
 
    public void setLabel(String label) {
        this.label = label;
    }
	

}
