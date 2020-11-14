package com.javathlon.section15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileConverter {

  public void convertFile(String inputPath, String outputPath, String newDateFormat) {
    File inputFile = new File(inputPath);
    File outputFile = new File(outputPath);

    BufferedReader reader = null;
    FileWriter writer = null;

    SimpleDateFormat sdfOld = new SimpleDateFormat("MMMM dd, yyyy");
    SimpleDateFormat sdfNew = new SimpleDateFormat(newDateFormat);

    try {
      reader = new BufferedReader(new FileReader(inputFile));
      writer = new FileWriter(outputFile);
      String line;
      while ((line = reader.readLine()) != null) {

        Date date = sdfOld.parse(line);
        String newFormatDate = sdfNew.format(date);
        System.out.println(date);
        writer.write(newFormatDate);
        writer.append(System.lineSeparator());

      }
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    } finally {
      try {
        writer.flush();
        writer.close();
        reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    FileConverter converter = new FileConverter();
    converter.convertFile("c://Principal//dates.txt", "c://Principal//dates2.txt", "yyyy - MM - dd EEEE");

  }

}
