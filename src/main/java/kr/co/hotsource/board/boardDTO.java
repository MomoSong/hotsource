package kr.co.hotsource.board;

import org.springframework.web.multipart.MultipartFile;

public class boardDTO {
	private int boardno;
	private String title;
	private String content;
	private String id;
	private String date;
	private int readcnt;
	private String code;
	private String filename;
	private long filesize;
	private int good;
	private int rnum;
	private int replycnt;
		
	
	
	//스프링 파일 객체-----------------------------------------  		  
	//<input type='file' name='comfile'>
	private MultipartFile comfile;
	//--------------------------------------------------------- 

	public boardDTO() {}

	public int getBoardno() {
		return boardno;
	}

	public void setBoardno(int boardno) {
		this.boardno = boardno;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public int getGood() {
		return good;
	}

	public void setGood(int good) {
		this.good = good;
	}

	public MultipartFile getComfile() {
		return comfile;
	}

	public void setComfile(MultipartFile comfile) {
		this.comfile = comfile;
	}

	public int getRnum() {
		return rnum;
	}

	public void setRnum(int rnum) {
		this.rnum = rnum;
	}

	public int getReplycnt() {
		return replycnt;
	}

	public void setReplycnt(int replycnt) {
		this.replycnt = replycnt;
	}
	
	
	
	
	
}
