  package com.javathlon.section12.simpleblogging;

public class TestBlog {

  public static void main(String[] args) {

      JavathlonBlogPost javathlonBlogPost = new JavathlonBlogPost();
      javathlonBlogPost.setText("I mean, sounds like it's a really cool place");

      javathlonBlogPost.getHeader();
      String header  = javathlonBlogPost.getHeader();

      System.out.println(header);



  }

}
