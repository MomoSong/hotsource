package kr.co.hotsource.issue;

public class ReplyDTO {
	private int icrno;
	private String id;
	private String content;
	private String date;
	private int icno;
	
	public ReplyDTO() {} // 기본생성자

	public int getIcrno() {
		return icrno;
	}

	public void setIcrno(int icrno) {
		this.icrno = icrno;
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

	public int getIcno() {
		return icno;
	}

	public void setIcno(int icno) {
		this.icno = icno;
	}
	
	
	
} // class end
