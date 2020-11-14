package com.javathlon.section12;

public class AmazonHumanResource implements HumanResourceAgreement {


  @Override
  public String getLeaveRequest(String id) {
    return null;
  }

  @Override
  public float[] getWorkersSalary() {
    return new float[0];
  }

  @Override
  public void applyForSocialSecurity() {
    System.out.println("Apply for social security after worker starting working");
  }
}
