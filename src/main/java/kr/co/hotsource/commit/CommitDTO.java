package kr.co.hotsource.commit;

import org.springframework.web.multipart.MultipartFile;

public class CommitDTO {
	private int rnum;
	private int icno;
	private int prono;
	private String title;
	private String content;
	private String id;
	private String date;
	private int readcnt;
	private int rcmcnt;
	private String conditions;
	private String code;
	private String c_filename;
	private long c_filesize;
	
	//스프링 파일 객체-----------------------------------------  		  
	//<input type='file' name='comfile'>
	private MultipartFile comfile;
	//--------------------------------------------------------- 
	
	public CommitDTO() {} // 기본 생성자 

	public int getIcno() {
		return icno;
	}

	public void setIcno(int icno) {
		this.icno = icno;
	}

	public int getProno() {
		return prono;
	}

	public void setProno(int prono) {
		this.prono = prono;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getReadcnt() {
		return readcnt;
	}

	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getC_filename() {
		return c_filename;
	}

	public void setC_filename(String c_filename) {
		this.c_filename = c_filename;
	}

	public long getC_filesize() {
		return c_filesize;
	}

	public void setC_filesize(long c_filesize) {
		this.c_filesize = c_filesize;
	}

	public MultipartFile getComfile() {
		return comfile;
	}

	public void setComfile(MultipartFile comfile) {
		this.comfile = comfile;
	}
	
	public int getRcmcnt() {
		return rcmcnt;
	}

	public void setRcmcnt(int rcmcnt) {
		this.rcmcnt = rcmcnt;
	}

	public int getRnum() {
		return rnum;
	}

	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	
	
	
}
