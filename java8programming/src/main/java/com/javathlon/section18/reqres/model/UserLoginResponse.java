package com.javathlon.section18.reqres.model;

public class UserLoginResponse extends BaseResponse {

  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
