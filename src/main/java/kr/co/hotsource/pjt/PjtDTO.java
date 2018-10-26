package kr.co.hotsource.pjt;

import org.springframework.web.multipart.MultipartFile;

public class PjtDTO {
	private Integer Prono;
	private String Pname;
	private String Ptitle;
	private String Pexplain;
	private String Pcourse;
	private String Pversion;
	private Long Filesize;
	private String Id;
	private Integer Good;
	private Integer pjtcnt;
	private String Date;
	private String Planguage;

	public String getPlanguage() {
		return Planguage;
	}

	public void setPlanguage(String planguage) {
		Planguage = planguage;
	}

	public Integer getPjtcnt() {
		return pjtcnt;
	}

	public void setPjtcnt(Integer pjtcnt) {
		this.pjtcnt = pjtcnt;
	}

	public String getPtitle() {
		return Ptitle;
	}

	public void setPtitle(String ptitle) {
		Ptitle = ptitle;
	}

	// 스프링 파일 객체-----------------------------------------
	// <input type='file' name='comfile'>
	private MultipartFile comfile;
	// ---------------------------------------------------------

	public MultipartFile getComfile() {
		return comfile;
	}

	public void setComfile(MultipartFile comfile) {
		this.comfile = comfile;
	}

	@Override
	public String toString() {
		return "PtjDTO [Prono=" + Prono + ", Pname=" + Pname + ", Pexplain=" + Pexplain + ", Pcourse=" + Pcourse
				+ ", Pversion=" + Pversion + ", Filesize=" + Filesize + ", Id=" + Id + ",  Good="
				+ Good + ", Date=" + Date + "]";
	}

	public Integer getProno() {
		return Prono;
	}

	public void setProno(Integer prono) {
		Prono = prono;
	}

	public String getPname() {
		return Pname;
	}

	public void setPname(String pname) {
		Pname = pname;
	}

	public String getPexplain() {
		return Pexplain;
	}

	public void setPexplain(String pexplain) {
		Pexplain = pexplain;
	}

	public String getPcourse() {
		return Pcourse;
	}

	public void setPcourse(String pcourse) {
		Pcourse = pcourse;
	}

	public String getPversion() {
		return Pversion;
	}

	public void setPversion(String pversion) {
		Pversion = pversion;
	}

	public Long getFilesize() {
		return Filesize;
	}

	public void setFilesize(Long filesize) {
		Filesize = filesize;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Integer getGood() {
		return Good;
	}

	public void setGood(Integer good) {
		Good = good;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public PjtDTO() {
	} // 기본 생성자

}
