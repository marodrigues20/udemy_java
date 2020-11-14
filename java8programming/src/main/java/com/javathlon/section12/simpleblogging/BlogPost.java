package com.javathlon.section12.simpleblogging;

public abstract class BlogPost {

  protected String text = null;
  protected String[] tags  = null;
  protected String firstName  = null;
  protected String lastName  = null;
  protected String nickName  = null;
  protected String header  = null;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String[] getTags() {
    String myTags[] = null;
    if(text != null){
      String[] words = text.split(" ");
      myTags = new String[3];
      tags[0] = words[2];
      tags[1] = words[8];
      tags[2] = words[5];
    }
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
    if(firstName != null&& lastName != null){
      return firstName + "_" + lastName;
    }
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getHeader() {

    if(text != null){
      String[] words = text.split(" ");
      return words[0] + " " + words[1] + " " + words[2];
    }

    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }
}
