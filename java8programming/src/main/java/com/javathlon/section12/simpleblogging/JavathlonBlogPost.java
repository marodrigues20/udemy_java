package com.javathlon.section12.simpleblogging;

public class JavathlonBlogPost extends BlogPost {

  @Override
  public String getHeader() {
    String words[] = text.split(" ");
    if (this.text != null){
      return words[0].toUpperCase() + " " + words[1].toUpperCase() + " " + words[2].toUpperCase() + " " + words[3].toUpperCase();
    }
    return "";
  }
}
