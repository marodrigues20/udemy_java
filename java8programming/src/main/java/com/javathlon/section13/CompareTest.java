package com.javathlon.section13;

import java.util.Arrays;

public class CompareTest {


  public static void main(String[] args) {

    BlogPost[] blogPosts = new BlogPost[4];

    BlogPost blog1 = new BlogPost();
    blog1.setText("Mario");

    BlogPost blog2 = new BlogPost();
    blog2.setText("Alexandre");

    BlogPost blog3 = new BlogPost();
    blog3.setText("dos Reis");

    BlogPost blog4 = new BlogPost();
    blog4.setText("Rodrigues");

    blogPosts[0] = blog1;
    blogPosts[1] = blog2;
    blogPosts[2] = blog3;
    blogPosts[3] = blog4;

    Arrays.sort(blogPosts);

    for (BlogPost blog : blogPosts) {

      System.out.println(blog.getText() + " --> length: " + blog.getText().length());

    }

  }


}
