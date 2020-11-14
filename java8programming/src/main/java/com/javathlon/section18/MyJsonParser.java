package com.javathlon.section18;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MyJsonParser {

  public static void main(String[] args) {

    File filePath = new File(MyJsonParser.class.getClassLoader().getResource("myjson.json")
        .getPath());

    StringBuffer stringBuffer = null;
    FileReader fileReader = null;
    BufferedReader bufferedReader = null;
    try {
      //FileReader use character reader.
      fileReader = new FileReader(filePath);
      bufferedReader = new BufferedReader(fileReader);

      stringBuffer = new StringBuffer();
      String line = "";
      while ((line = bufferedReader.readLine()) != null) {
        stringBuffer.append(line).append(System.lineSeparator());
      }

      //System.out.println(stringBuffer.toString());

      Gson gson = new Gson();

      MyJsonObject myJsonObject = gson.fromJson(stringBuffer.toString(),MyJsonObject.class);
      System.out.println("duration milliseconds:" + myJsonObject.getDurationMs());
      System.out.println("metadata:" + myJsonObject.getMetadata());

      System.out.println("customer name:" + myJsonObject.getCustomer().getName());

      System.out.println("number of shopping items of the customer:" + myJsonObject.getCustomer().getShoppingCart().getItems().size());

      List<ItemInformation> informationList = myJsonObject.getCustomer().getShoppingCart().getInformations();

      for ( ItemInformation info : informationList ){

        System.out.println("information type: " + info.getType());
        System.out.println("information price range: " + info.getPriceRange());

      }



    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        bufferedReader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
