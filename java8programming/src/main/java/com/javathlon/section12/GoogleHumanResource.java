package com.javathlon.section12;

public class GoogleHumanResource implements HumanResourceAgreement {

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
    System.out.println("Apply the social security as soon as worker accepted the contract");
  }


}
