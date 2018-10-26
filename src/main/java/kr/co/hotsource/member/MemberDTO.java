package kr.co.hotsource.member;

public class MemberDTO {
    private String id;            
    private String name;    
    private String passwd; 
    private String ph_no;     
    private String email;      
    private String birth;        
    private String joindate;  
    private String rating;
    private String s_id;
    
    public String getS_Id() {
        return s_id;
      }
      public void setS_Id(String s_id) {
        this.s_id = s_id;
      }
      
	public String getId() {
      return id;
    }
    public void setId(String id) {
      this.id = id;
    }
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public String getPasswd() {
      return passwd;
    }
    public void setPasswd(String passwd) {
      this.passwd = passwd;
    }
    public String getPh_no() {
      return ph_no;
    }
    public void setPh_no(String ph_no) {
      this.ph_no = ph_no;
    }
    public String getEmail() {
      return email;
    }
    public void setEmail(String email) {
      this.email = email;
    }
    public String getBirth() {
      return birth;
    }
    public void setBirth(String birth) {
      this.birth = birth;
    }
    public String getJoindate() {
      return joindate;
    }
    public void setJoindate(String joindate) {
      this.joindate = joindate;
    }
    public String getRating() {
      return rating;
    }
    public void setRating(String rating) {
      this.rating = rating;
    }
    
  public MemberDTO() {
		System.out.println("¸â¹öDTO°´Ã¼ »ý¼º");
	}
@Override
public String toString() {
	return "MemberDTO [id=" + id + ", name=" + name + ", passwd=" + passwd + ", ph_no=" + ph_no + ", email=" + email
			+ ", birth=" + birth + ", joindate=" + joindate + ", rating=" + rating + ", s_id=" + s_id + "]";
}
  

  

    
  
}//class end