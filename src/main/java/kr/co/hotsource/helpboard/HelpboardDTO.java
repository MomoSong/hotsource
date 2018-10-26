package kr.co.hotsource.helpboard;

import org.springframework.web.multipart.MultipartFile;

public class HelpboardDTO {
	
	private int helpno;
	private String id;
	private String title;
	private String content;
	private String date;
	private String filename;
	private long filesize;
	private int readcnt;
	private String passwd;
	private String locked;
	private int grpno;
	private int indent;
	private int ansno;
	
	
	//스프링 파일 객체-----------------------------------------  		  
	//<input type='file' name='file'>
	private MultipartFile file;
	//--------------------------------------------------------- 
	
	public HelpboardDTO() {} // 기본 생성자 

	public int getHelpno() {
		return helpno;
	}

	public void setHelpno(int helpno) {
		this.helpno = helpno;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public int getReadcnt() {
		return readcnt;
	}

	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public int getGrpno() {
		return grpno;
	}

	public void setGrpno(int grpno) {
		this.grpno = grpno;
	}

	public int getIndent() {
		return indent;
	}

	public void setIndent(int indent) {
		this.indent = indent;
	}

	public int getAnsno() {
		return ansno;
	}

	public void setAnsno(int ansno) {
		this.ansno = ansno;
	}
	
}
