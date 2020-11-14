package com.javathlon.section13;

public class BlogPost implements Comparable {


  private String text = null;
  private String[] tags = null;
  private String firstName = null;
  private String lastName = null;
  private String nickName = null;
  private String header = null;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String[] getTags() {
    return tags;
  }

  public void setTags(String[] tags) {
    this.tags = tags;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  @Override
  public int compareTo(Object o) {

    if (this.text == null || ((BlogPost) o).getText() == null) {
      return 0;
    }

    if (this.text.length() > ((BlogPost) o).getText().length()) {
      return 1;
    }

    if (this.text.length() < ((BlogPost) o).getText().length()) {
      return -1;
    }

    return 0;
  }
}
