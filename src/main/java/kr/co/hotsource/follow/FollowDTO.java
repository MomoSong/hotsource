package kr.co.hotsource.follow;

public class FollowDTO {
  private int flwno;
  private String flw;
  private String flwr;
  
  public FollowDTO() {}

  public int getFlwno() {
    return flwno;
  }

  public void setFlwno(int flwno) {
    this.flwno = flwno;
  }

  public String getFlw() {
    return flw;
  }

  public void setFlw(String flw) {
    this.flw = flw;
  }

  public String getFlwr() {
    return flwr;
  }

  public void setFlwr(String flwr) {
    this.flwr = flwr;
  }

  @Override
  public String toString() {
    return "FollowDTO [flwno=" + flwno + ", flw=" + flw + ", flwr=" + flwr + "]";
  }
  
}
