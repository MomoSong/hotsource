package kr.co.hotsource.notice;

public class ReplyDTO {
	private int ntrno;
	private String id;
	private String content;
	private String date;
	private int noticeno;
	
	public ReplyDTO() {} // 기본생성자

	public int getNtrno() {
		return ntrno;
	}

	public void setNtrno(int ntrno) {
		this.ntrno = ntrno;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNoticeno() {
		return noticeno;
	}

	public void setNoticeno(int noticeno) {
		this.noticeno = noticeno;
	}

	
	
	
} // class end
