package kr.co.hotsource.member;

public class emailcode {
	//emailaddress @���� �� code �̿�
	//������� : ���޹��� ���� label����������db�� �Ѱ��ٶ��� code���̾���Ҷ�
	/*
	@naver.com   : 001
	@hanmail.net : 002
	@gmail.com	 : 003
	@daum.net	 : 004
	*/
	
	private String code; // db�� ���޵� ��
    private String label; // ȭ�鿡 �������� ��
	public emailcode() {System.out.println("------------emailcode��ü����");}
	
	public emailcode(String code, String label) {
        this.code = code;
        this.label = label;
    } // ���� ������ 
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
