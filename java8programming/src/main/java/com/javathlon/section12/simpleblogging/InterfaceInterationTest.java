package com.javathlon.section12.simpleblogging;

import com.javathlon.section12.AmazonHumanResource;
import com.javathlon.section12.GoogleHumanResource;
import com.javathlon.section12.HumanResourceAgreement;

public class InterfaceInterationTest {

  public static void main(String[] args) {
    AmazonHumanResource amazon = new AmazonHumanResource();
    AmazonHumanResource amazon2 = new AmazonHumanResource();

    GoogleHumanResource google = new GoogleHumanResource();
    GoogleHumanResource google2 = new GoogleHumanResource();

    HumanResourceAgreement resource[] = new HumanResourceAgreement[4];

    resource[0] = amazon;
    resource[1] = amazon2;
    resource[2] = amazon2;
    resource[3] = amazon2;

    for(HumanResourceAgreement agreement: resource){
      agreement.applyForSocialSecurity();
    }
  }



}
